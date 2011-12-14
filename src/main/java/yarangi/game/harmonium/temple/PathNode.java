package yarangi.game.harmonium.temple;

import yarangi.math.Vector2D;

final public class PathNode
{
	private Vector2D loc;
	
	public PathNode(double x, double y)
	{
		loc = Vector2D.R(x, y);
	}
	
	public Vector2D getLocation() { return loc; }

}
