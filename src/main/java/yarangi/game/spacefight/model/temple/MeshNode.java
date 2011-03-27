package yarangi.game.spacefight.model.temple;

import yarangi.math.Vector2D;

/**
 * Represents a vertex in hexagonal grid
 * 
 * @author Dve Yarangi
 */
public class MeshNode 
{
	/** 
	 * vertex location in grid coordinates.
	 */
	private Vector2D loc;
	
	/**
	 * Neighboring nodes (up to three are non-null)
	 * Index correspond to neighbor angle (idx*PI/3)
	 */
	private MeshNode [] neighbors = new MeshNode[6];
	
	private Hexagon [] hexagons = new Hexagon[6];
	
	public MeshNode ()
	{
	}
	
	public Vector2D getLocation() { return loc; }
	public void setLocation(Vector2D loc) { this.loc = loc; }
	
	/**
	 * Retrieve neighbor at given direction (dir*PI/3)
	 * @param dir
	 * @return
	 */
	public MeshNode getNeighbor(int dir) { return neighbors[dir%6]; }
	
	/**
	 * Adds a neighbor, updating link in both directions.
	 * @param dir
	 * @param node
	 */
	public void linkNeighbor(int dir, MeshNode node)
	{
		neighbors[dir%6] = node;
		node.neighbors[(dir+3)%6] = this;
	}
	
	public void setHexagon(int dir, Hexagon hexagon)
	{
		hexagons[dir%6] = hexagon;
	}

	public Hexagon getHexagon(int dir) {
		
		return hexagons[dir%6];
	}
}
