package yarangi.game.temple.model.enemies.swarm;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL;

import yarangi.game.temple.model.Damage;
import yarangi.game.temple.model.EffectUtils;
import yarangi.game.temple.model.enemies.swarm.agents.SwarmAgent;
import yarangi.game.temple.model.temple.TempleEntity;
import yarangi.game.temple.model.terrain.Matter;
import yarangi.game.temple.model.weapons.Projectile;
import yarangi.graphics.colors.Color;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.graphics.quadraturin.simulations.ICollisionHandler;
import yarangi.graphics.quadraturin.simulations.IPhysicalObject;
import yarangi.graphics.quadraturin.terrain.GridyTerrainMap;
import yarangi.graphics.quadraturin.terrain.Tile;
import yarangi.math.FastMath;
import yarangi.math.Vector2D;

public class Swarm extends Entity 
{
	private static final int WSIZE = 120;
	
	/**
	 * TODO: replace with GridMap.
	 */
	private AStarNode [][] beacons;
	
//	private Vector2D [][] flows;
	
	private Vector2D target = Vector2D.ZERO();
	
	private float cellsize;
	
	private Scene scene;
	
	private double toNodeIdx;
	
	private int halfSize;
	
	private List <SpawnNode> spawnNodes = new LinkedList <SpawnNode> ();
	private Iterator <SpawnNode> nodeIterator;
	private SpawnNode currNode;
	
	static final int MIN_DANGER_FACTOR = 0;
	static final int MAX_DANGER_FACTOR = 100;
	
	static final double DANGER_FACTOR_DECAY = 1./1000.;
	static final double OMNISCIENCE_PERIOD = 100.;
	final ICollisionHandler <SwarmAgent> agentCollider;
	
	private Damage MATTER_DAMAGE = new Damage(12, 0, 0, 0);
	final GridyTerrainMap<Tile, Color> terrain;
	/**
	 * 
	 * @param worldSize
	 * @param _scene
	 */
	public Swarm(int worldSize, Scene _scene)
	{
		int cellsize = (int)((float)worldSize / (float)WSIZE);
		
//		setLook(Dummy.LOOK);
		beacons = new AStarNode[WSIZE][WSIZE];
		
		
		halfSize = worldSize / 2;
		this.scene = _scene;
		
		terrain = (GridyTerrainMap<Tile, Color>)_scene.getWorldVeil().getTerrain();
		
		this.toNodeIdx = (double)WSIZE / (double)(worldSize);
		
		for(int i = 0; i < WSIZE; i ++)
		for(int j = 0; j < WSIZE; j ++)
		{
			AStarNode node = new AStarNode(i, j); 
			double tx = toBeaconCoord(i);
			double ty = toBeaconCoord(j);
//			Vector2D toCenter = new Vector2D(-tx, -ty).normalize();
			if(tx != 0 || ty != 0)
				node.setFlow(/*toCenter.x(), toCenter.y()*/ 0,0);
			beacons[i][j] = node; 
//			System.out.println(beacons[i][j].getFlow());
		}
		
		agentCollider = new ICollisionHandler <SwarmAgent> (){

			@Override
			public boolean setImpactWith(SwarmAgent source, IPhysicalObject target)
			{
				if(target instanceof Projectile)
					{
						Projectile p = (Projectile) target;
						
						setDanger(source, source.getIntegrity().hit(p.getDamage()));
						
						if(source.getIntegrity().getHitPoints() <= 0)
						{
							source.markDead();
							EffectUtils.makeExplosion(source.getArea().getRefPoint(), scene.getWorldVeil(), new Color(0,1,0,1), 32);
							return true;
						}

					}
					else
					if(target instanceof TempleEntity)
					{
						source.markDead();
						EffectUtils.makeExplosion(source.getArea().getRefPoint(), scene.getWorldVeil(), new Color(1,0,0,1), 64);
						return true;
					}
					else
					if( target instanceof Tile || target instanceof Matter)
					{
//						terrain.consume( source.getArea().getRefPoint().x(), 
//								source.getArea().getRefPoint().y(), 10 );
						setUnpassable(source);
//						setDanger(source, source.getIntegrity().hit(MATTER_DAMAGE));
//						if(source.getIntegrity().getHitPoints() <= 0)
						{
							source.markDead();
							EffectUtils.makeExplosion(source.getArea().getRefPoint(), scene.getWorldVeil(), new Color(0,1,0,1), 32);
							return true;
						}
					}
				
					return false;
				}

			};	
			scene.getCollisionManager().registerHandler(SwarmAgent.class, agentCollider);
		}

	public void addSpawnNode(double x, double y)
	{
		spawnNodes.add(new SpawnNode(new Vector2D(x, y)));
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
			return new Vector2D(fx, fy); 
		}
		
		int x, y, dx, dy;
		Vector2D flow = new Vector2D(0, 0);
		
/*		for(dx = -1; dx <= 1; dx ++)
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
		flow.add(beacons[px][py].getFlow().mul(0.5));
		flow.normalize();
		return flow;
	}
	
	protected double getDanger(int i, int j)
	{
		if(i >= 0 && i < WSIZE && j >= 0 && j < WSIZE)
			return beacons[i][j].getDangerFactor();
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
	final public float getCellSize() { return cellsize;}
	final public int getWorldSize() { return WSIZE; }

	final public Vector2D getTarget() { return target; }

	final protected AStarNode [][] getBeacons() { return beacons; }

	final public void addAgent(SwarmAgent agent) {
		scene.addEntity(agent);
	}
/*	final public void removeAgent(SwarmAgent agent) {
		scene.removeEntity(agent);
	}*/


	public void setDanger(SwarmAgent agent, double damage) 
	{
		int x = toBeaconIdx(agent.getArea().getRefPoint().x());
		int y = toBeaconIdx(agent.getArea().getRefPoint().y());
		
		Beacon beacon = beacons[x][y];
		beacon.update(damage);
	}
	public void setUnpassable(SwarmAgent agent) {
		int x = toBeaconIdx(agent.getArea().getRefPoint().x());
		int y = toBeaconIdx(agent.getArea().getRefPoint().y());
		
		Beacon beacon = beacons[x][y];
		beacon.setUnpassable(true);
	}


	
	public static class AStarNode extends Beacon
	{
		public double g;
		public double h;
		public double f;
		public AStarNode origin;
		
		public int passId;
		
		private boolean closed = false;
		private boolean open = false;
		
		public AStarNode(int x, int y)
		{
			super(x,y);
		}

		public void markClosed() { closed = true;}
		public void unmarkClosed() { closed = false; }
		public boolean isClosed() { return closed; }
		public void markOpen() 
		{ 
			open = true;
			g = 0;
			h = 0;
			f = 0;
		}
		public void unmarkOpen() { open = false; }
		public boolean isOpen() { return open; }

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

	@SuppressWarnings("unchecked")
	public void display(GL gl, double time, IRenderingContext context) 
	{
		// if we here, it must be debug:
		this.getLook().render(gl, time, this, context);
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
		return beacons[toBeaconIdx( point.x() )][ toBeaconIdx( point.y() )];
	}

	public boolean isOmniUnpassable(int x, int y)
	{
		return !terrain.getCell( toBeaconCoord( x ), toBeaconCoord( y ) ).getProperties().isEmpty();
				
	}

}
