package yarangi.math.bezier;

import java.util.ArrayList;
import java.util.List;

import yarangi.math.Vector2D;

/*
 * 
 * TODO: brrr, remove this junk
 */
public class BezierChain implements ParametricCurve
{
	private List <ParametricCurve> segments;
	
	public BezierChain()
	{
		 segments = new ArrayList <ParametricCurve> ();
	}
	
	public void addSegment(ParametricCurve curve)
	{

		segments.add(curve);
	}
	
	public Vector2D pointAt(double t)
	{
		int size = segments.size();
		assert(t >=0 && t <= size);
		if(t == size)
		{
			ParametricCurve curve = segments.get(size-1); 
			return curve.pointAt(curve.getMax());
		}	
		int index = (int)Math.round(t);
		double offset = t - (double)index;
		
		return segments.get(index).pointAt(offset);
	}
	
	public int size() { return segments.size(); }

	public double getMax() { return 1; }

	public double getMin() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
