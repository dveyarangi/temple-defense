package yarangi.game.harmonium.enemies.swarm;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import yarangi.game.harmonium.battle.EntityCenter;
import yarangi.game.harmonium.battle.ITemple;
import yarangi.game.harmonium.battle.Integrity;
import yarangi.game.harmonium.enemies.swarm.agents.SwarmAgent;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.terrain.PolygonTerrainMap;
import yarangi.graphics.quadraturin.terrain.TilePoly;
import yarangi.math.FastMath;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.GridMap;
import yarangi.spatial.SpatialHashMap;
import yarangi.spatial.Tile;

public class Swarm extends GridMap<Tile<Beacon>, Beacon>
{
	private int WSIZE ;
	
//	private Vector2D [][] flows;
	
	private Vector2D target = Vector2D.ZERO();
	
	private int cellsize = 8;
	
	private Scene scene;
	
	private double toNodeIdx;
	
	private float halfSize;
	
	private List <SpawnNode> spawnNodes = new LinkedList <SpawnNode> ();
	private Iterator <SpawnNode> nodeIterator;
	private SpawnNode currNode;
	
	static final int MIN_DANGER_FACTOR = 0;
	static final int MAX_DANGER_FACTOR = 1000;
	
	static final double DANGER_FACTOR_DECAY = 0.001;
	static final double OMNISCIENCE_PERIOD = 100.;
	static final double SPAWNING_INTERVAL = 4;
	public static final int SPAWNING_RADIUS = 50;
	public static final Integrity AGENT_INTEGRITY = new Integrity(10, 0, new double [] {0,0,0,0});

	final PolygonTerrainMap terrain;
	private SpatialHashMap <IEntity> index;
	
	/**
	 * 
	 * @param worldSize
	 * @param scene
	 */
	public Swarm(int worldSize, int cellsize, Scene scene)
	{
		super(cellsize, worldSize, worldSize);
		halfSize = worldSize / 2;
		this.scene = scene;
		
		WSIZE = (int)((float)worldSize / (float)cellsize);
		
		terrain = (PolygonTerrainMap)scene.getWorldLayer().<TilePoly>getTerrain();
		index = (SpatialHashMap <IEntity>)scene.getEntityIndex();
		
		this.toNodeIdx = (double)WSIZE / (double)(worldSize);
	}

	public void addSpawnNode(double x, double y)
	{
		spawnNodes.add(new SpawnNode(Vector2D.R(x, y)));
		nodeIterator = spawnNodes.iterator();
	}
	
	public Vector2D getFlow(Vector2D point) 
	{
		int px = toBeaconIdx(point.x());
		int py = toBeaconIdx(point.y());
//		System.out.println(px + " : " + py + " ::: " + point);
		if(px < 0 || px >= WSIZE || py < 0 || py >= WSIZE)
		{
			// pushing inside:
			double fx = 0, fy = 0;
			if(px < 0) fx = 1;
			if(py < 0) fy = 1;
			if(px >= WSIZE) fx = -1;
			if(py >= WSIZE) fy = -1;
			return Vector2D.R(fx, fy); 
		}
		
		Vector2D flow = Vector2D.ZERO();
		
/*		int x, y, dx, dy;
		
		for(dx = -1; dx <= 1; dx ++)
			for(dy = -1; dy <= 1; dy ++)
				if(dx != 0 || dy != 0)
				{
					if(dx == 0 && dy == 0)
						continue;
					x = px + dx;
					if(x < 0 || x >= WSIZE) continue;
					y = py + dy;
					if(y < 0 || y >= WSIZE) continue;
					
					double fear = beacons[x][y].getDangerFactor()/(float)MAX_DANGER_FACTOR;
					if(beacons[x][y].isUnpassable())
						fear = 1;
					flow.add(-dx*fear, -dy*fear);
				}*/
//		System.out.println(px + " : " + py + " ::: " + beacons[px][py].getFlow());
//		System.out.println("push: " + flow + ", flow: " + beacons[px][py].getFlow() + ", res: " + flow.plus(beacons[px][py].getFlow()).normal());
//		flow.normalize();
		flow.add(getContentByIndex(px, py).getFlow().mul(0.5));
		flow.normalize();
		return flow;
	}
	
	protected double getDanger(int i, int j)
	{
		if(i >= 0 && i < WSIZE && j >= 0 && j < WSIZE)
			return getContentByIndex(i, j).getDangerFactor();
		return MAX_DANGER_FACTOR;
	}
	
	public double toBeaconCoord(int idx)
	{
		return cellsize * idx - halfSize;
	}
	public int toBeaconIdx(double v)
	{
		return FastMath.round((v + halfSize) * toNodeIdx);
	}
	
	final public int getWorldSize() { return WSIZE; }

	final public Vector2D getTarget() { return target; }

	final protected AStarNode getBeaconByIndex(int x, int y) { return (AStarNode)getContentByIndex( x, y ); }

	final public void addAgent(SwarmAgent agent) {
		scene.addEntity(agent);
	}
/*	final public void removeAgent(SwarmAgent agent) {
		scene.removeEntity(agent);
	}*/

	public void setTarget(ITemple structure)
	{
		this.target = structure.getArea().getAnchor();
	}

	public void setDanger(SwarmAgent agent, double damage) 
	{
		int x = toBeaconIdx(agent.getArea().getAnchor().x());
		int y = toBeaconIdx(agent.getArea().getAnchor().y());
		
		if(x >= 0 && x < WSIZE && y >= 0 && y < WSIZE)
		{
			Beacon beacon = getContentByIndex( x, y );
			beacon.update(damage);
			setModified( beacon.getX(), beacon.getY() );
			int i, j;
			for(int dx = -2; dx <= 2; dx ++)
				for(int dy = -2; dy <= 2; dy ++)
				{
					if(dx == 0 && dy == 0)
						continue;
					i = x + dx;
					if(i < 0 || i >= WSIZE) continue;
					
					j = y + dy;
					if(j < 0 || j >= WSIZE) continue;
					
					beacon = getContentByIndex( i, j );
					beacon.update( damage/(dx*dx+dy*dy) );
					
				}
		}
	}
/*	public void setUnpassable(double x, double y) {
		int i = toBeaconIdx(x);
		int j = toBeaconIdx(y);
		int di, dj;
		if(i >= 0 && i < WSIZE && j >= 0 && j < WSIZE)
		{
			Beacon beacon = beacons[i][j];
			beacon.setUnpassable(true);
			cellModified( beacon.getX(), beacon.getY() );
			for(int dx = -1; dx <= 1; dx ++)
				for(int dy = -1; dy <= 1; dy ++)
					{
					if(dx == 0 && dy == 0)
						continue;
						di = i + dx;
						if(di < 0 || di >= WSIZE) continue;
						dj = j + dy;
						if(dj < 0 || dj >= WSIZE) continue;
						
//						setDanger(source, source.getIntegrity().hit(MATTER_DAMAGE));
						beacons[di][dj].setUnpassable(true);
//						System.out.println(x + " " + y + " ::: " + beacons[x][y].getFlow());
					}
		}
	}*/


	
	public static class AStarNode extends Beacon
	{
		public double g;
		public double h;
		public double f;
		public AStarNode origin;
		
		public int openPassId;
		public int closePassId;
		
		private boolean closed = false;
		private boolean open = false;
		
		private int hashcode = RandomUtil.N( Integer.MAX_VALUE );
		
		public AStarNode(int x, int y)
		{
			super(x,y);
		}

		public void markClosed(int passId) { closed = true; this.closePassId = passId; }
		public void unmarkClosed() { closed = false; }
		public boolean isClosed(int passId) { return this.closePassId == passId && closed; }
		public void markOpen(int passId) 
		{ 
			openPassId = passId;
			open = true;
			g = 0;
			h = 0;
			f = 0;
		}
		public void unmarkOpen() { open = false; }
		public boolean isOpen(int passId) { return this.openPassId == passId && open; }
		public void reset()
		{
			open = closed = false;
			g = h = f = 0;
			origin = null;
		}
		
		public int hashCode() 
		{
			return hashcode;
		}
		
		public boolean equals(Object o)
		{
			return o == this;
		}

	}
	
	public class SpawnNode
	{
		private Vector2D spawnLocation;
		
		public SpawnNode(Vector2D spawnLocation)
		{
			this.spawnLocation = spawnLocation;
		}
		
		public Vector2D getSpawnLocation() { return spawnLocation; }
	}

	public void nextNode() 
	{
		if(!nodeIterator.hasNext())
			nodeIterator = spawnNodes.iterator();
		
		currNode = nodeIterator.next();
	}

	public Vector2D getSource() { return currNode.getSpawnLocation(); }

	public IBeacon getBeacon(Vector2D point)
	{
		return getContentByCoord(point.x(), point.y());
	}

	public boolean isOmniUnpassable(int x, int y)
	{
		if(terrain == null)
			return false;
		Tile <TilePoly> tile = terrain.getTile( toBeaconCoord( x ), toBeaconCoord( y ) );
		return tile != null && !tile.get().isEmpty();
//		return !terrain.getCell( toBeaconCoord( x ), toBeaconCoord( y ) ).getProperties().isEmpty();
				
	}
	
	public boolean hasTarget(int x, int y)
	{
		Collection <IEntity> entities = index.getBucket( 
					index.toGridIndex( toBeaconCoord(x) ), index.toGridIndex( toBeaconCoord(y) ) )
					.values();
		
		for(IEntity entity : entities) 
			if(entity instanceof ITemple) {
//				System.out.println("Swarm: target found: " + x + " : " + y + " : " + entity);
				return true;
			}
		return false;
				
	}

	@Override public float getMinX() { return -halfSize; }

	@Override public float getMaxX() { return halfSize; }

	@Override public float getMinY() { return -halfSize; }

	@Override public float getMaxY() { return halfSize; }

	@Override
	protected Tile<Beacon> createEmptyCell(int i, int j, double x, double y)
	{
		return new Tile<Beacon>(i, j, x, y, getCellSize(), getCellSize());
	}

	@Override
	protected Tile<Beacon>[] createMap(int cellSize, int width, int height)
	{
		Tile<Beacon>[] beacons = (Tile<Beacon>[]) new Tile [width*height];
		for(int i = 0; i < width; i ++)
			for(int j = 0; j < height; j ++)
			{
				AStarNode node = new AStarNode(i, j); 
				double tx = toBeaconCoord(i);
				double ty = toBeaconCoord(j);
				Vector2D toCenter = Vector2D.R(-tx, -ty).normalize();
				if(tx != 0 || ty != 0)
					node.setFlow(toCenter.x(), toCenter.y());
				Tile <Beacon> tile = new Tile<Beacon>(i, j, tx, ty, getCellSize(), getCellSize());
				tile.put( node );
				beacons[i + width * j] = tile; 
//				System.out.println(beacons[i][j].getFlow());
			}
		
		return beacons;
		
	}

	@Override
	protected int indexAtTile(int i, int j)
	{
		return i + WSIZE * j;
	}


}
