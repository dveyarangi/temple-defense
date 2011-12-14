package yarangi.game.temple.model.terrain;

import java.util.ArrayList;
import java.util.List;

import yarangi.math.Vector2D;

public class PolyLine
{
	public List <Vector2D> points = new ArrayList <Vector2D> ();
	
	
	public PolyLine()
	{
		
	}
	
	public void addPoint(Vector2D point)
	{
		points.add(point);
	}
	
	public void addPoint(double x, double y)
	{
		addPoint( Vector2D.R(x, y) );
	}
	public int size() { return points.size(); }
	
	public Vector2D getPoint(int idx)
	{
		 return points.get( idx );
	}
}
