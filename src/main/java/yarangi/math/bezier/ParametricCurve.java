package yarangi.math.bezier;

import yarangi.math.Vector2D;

public interface ParametricCurve 
{
	public Vector2D pointAt(double t);
	
	public double getMin();
	public double getMax();
}
