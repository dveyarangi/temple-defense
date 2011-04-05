package yarangi.game.temple.model.temple;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Dve Yarangi
 */
public class Hexagon
{
	public static final int HIGH_RIGHT = 0;
	public static final int TOP = 1;
	public static final int HIGH_LEFT = 2;
	public static final int BOTTOM_LEFT = 3;
	public static final int BOTTOM = 4;
	public static final int BOTTOM_RIGHT = 5;
	
	private Hexagon [] neighbors = new Hexagon[6];
	private MeshNode [] nodes = new MeshNode[6];
	
	private HexagonId id;
	private double x, y;
	
	/**
	 * service flag for traversal function
	 */
	private boolean visited;
	
	protected Hexagon()
	{
		id = new HexagonId(0, 0);
		
		for(int idx = 0; idx < 6; idx ++) // default nodes allocation:
			nodes[idx] = new MeshNode();
		for(int idx = 0; idx < 6; idx ++)
		{
			nodes[idx].linkNeighbor(idx+2, nodes[(idx+1)%6]);
			nodes[idx].setHexagon(idx+3, this);
		}
	}

	/**
	 * Hexagon id represents hexagon location in mesh
	 * @param id
	 */
	private void setId(HexagonId id) { this.id = id; }
	
	/**
	 * Hexagon id represents hexagon location in mesh
	 * @param id
	 */
	public HexagonId getId() { return id; }
	
	public double getX() { return x; }
	public double getY() { return y; }
	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
		
	/**
	 * Retrieves neighbor at 
	 * @param position
	 * @return
	 */
	public Hexagon getNeighbor(int position) {
		return neighbors[position%6];
	}
	
	/***
	 * TODO: propagate changes?
	 * @param position
	 * @param neighbor
	 * @return
	 */
	public Hexagon linkNeighbor(int position, Hexagon neighbor, double radius) 
	{
		neighbors[position%6] = neighbor;
		neighbor.neighbors[(position+3)%6] = this;
		
		neighbor.setId(getNeighborId(position));
		double newa = Math.PI/3*position + Math.PI/6;
		double d = Math.cos(Math.PI/6);
		neighbor.setX(this.getX()+radius * Math.cos(newa) * d);
		neighbor.setY(this.getY()+radius * Math.sin(newa) * d);
		
		return neighbor;
	}
	
	/**
	 * Retrieve mapping of hexagon to it's relative position
	 * XXX: redundant, simplifies indexing 
	 * @return
	 */
	public Map <HexagonId, Integer> getNeighbors()
	{
		Map <HexagonId, Integer> neighbors = new HashMap <HexagonId, Integer> ();
		
		for( int idx = 0; idx < 6; idx ++)
			neighbors.put(getNeighborId(idx), idx);
		
		return neighbors;
	}

	public void search(HexagonObserver observer)
	{
		search(this, observer);
	}
	
	protected void search(Hexagon curr, HexagonObserver observer)
	{
		if ( curr == null || curr.isVisited())
			return;
		
		curr.setVisited(true);
		
		for( int idx = 0; idx < 6; idx ++)
			search(curr.getNeighbor(idx), observer );
		
		observer.hexagonFound(curr, curr.id.x, curr.id.y);
		curr.setVisited(false);
	}

	protected boolean isVisited() { return visited; }
	protected void setVisited(boolean visited) { this.visited = visited; }
	
	public MeshNode getMeshNode(int idx) 
	{
		return nodes[idx%6];
	}
	
	public void setMeshNode(int idx, MeshNode node) 
	{
		nodes[idx%6] = node;
	}

	public class HexagonId {
		private int x;
		private int y;
		
		public HexagonId(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		public int hashCode()
		{
			return x + 100000*y;
		}
		
		public boolean equals(Object o)
		{
			if (! (o instanceof HexagonId))
				return false;
			HexagonId id = (HexagonId) o;
			return (id.x == x) && (id.y == y);
		}
		public String toString()
		{
			return "hid[" + x + "," + y + "]";
		}
	}
	
	public HexagonId getNeighborId(int position)
	{
		switch(position%6)
		{
		case HIGH_RIGHT:   return new HexagonId( id.x +1 , id.y    );
		case TOP:          return new HexagonId( id.x    , id.y -1 );
		case HIGH_LEFT:    return new HexagonId( id.x -1 , id.y -1 );
		case BOTTOM_LEFT:  return new HexagonId( id.x -1 , id.y    );
		case BOTTOM:       return new HexagonId( id.x    , id.y +1 );
		case BOTTOM_RIGHT: return new HexagonId( id.x +1 , id.y +1 );
		}
		throw new IllegalArgumentException("Invalid neughbour position " + position);
	}
}
