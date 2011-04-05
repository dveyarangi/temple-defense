package yarangi.game.temple.model.enemies.bubbles;

import yarangi.math.Vector2D;

public class BubbleNode 
{

	public Vector2D force = new Vector2D(0,0);
	public Vector2D speed = new Vector2D(0,0);
	public double radius;
	public double neighbour;
	
	public BubbleNode(double radius, double neighbour) 
	{
		this.radius = radius;
		this.neighbour = neighbour;
	}
}
