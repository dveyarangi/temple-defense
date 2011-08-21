package yarangi.game.temple.model.temple;

import yarangi.math.Vector2D;

final public class PathNode
{
	private Vector2D loc;
	
	public PathNode(double x, double y)
	{
		loc = new Vector2D(x, y);
	}
	
	public Vector2D getLocation() { return loc; }

}
