package yarangi.math.bezier;

import java.util.ArrayList;
import java.util.List;

import yarangi.math.Vector2D;

public class CubicBezierChain implements ParametricCurve
{

	private List <CubicBezierCurve> curves = new ArrayList <CubicBezierCurve> ();
	 
	private List <Double> angles = new ArrayList <Double> ();
	private List <Double> smoothnesses = new ArrayList <Double> ();
    private Vector2D firstPoint; 
    private double firstA;
    private double firstSmooth;
	public CubicBezierChain()
	{

		// TODO: constructor requires two points!
//		Vector2D p2 = new Vector2D(point1.x+smoothness1*Math.cos(a1-Angles.PI_2), 
//				   point1.x+smoothness1*Math.sin(a1-Angles.PI_div_2));
//		Vector2D p3 = new Vector2D(point2.x+smoothness2*Math.cos(a2+Angles.PI_2), 
//				   point2.x+smoothness2*Math.sin(a2+Angles.PI_div_2));
		
//		curves.add(new CubicBezierCurve(point1, p2, p3, point2));
	}
	
/*	public void addCurve(CubicBezierCurve curve)
	{
		this.curves.add(curve);
	}*/
	
	
	/**
	 * Number of points in the chain.
	 */
	public int getPointsCount() { return curves.size(); }
	
	public double getMin() { return 0; }
	public double getMax() { return curves.size(); }
	
	public void setPoint(int index, Vector2D newPoint)
	{
		CubicBezierCurve curve = curves.get(index);
		Vector2D pivotDiff = new Vector2D(smoothnesses.get(index), angles.get(index), true); 
		curve.getP1().x = newPoint.x;
		curve.getP1().y = newPoint.y;
		curve.getP2().x = curve.getP1().x + pivotDiff.x; 
		curve.getP2().y = curve.getP1().y + pivotDiff.y; 
		if(index > 0)
		{
			curve = curves.get(index-1);
			pivotDiff = new Vector2D(smoothnesses.get(index-1), angles.get(index-1), true); 
			curve.getP4().x = newPoint.x;
			curve.getP4().y = newPoint.y;
			curve.getP3().x = curve.getP4().x - pivotDiff.x; 
			curve.getP3().y = curve.getP4().y - pivotDiff.y; 
		}
	}
	
	public void setCurvature(int index, double angle, double smoothness)
	{
		CubicBezierCurve curve = curves.get(index);
		Vector2D pivotDiff = new Vector2D(smoothness, angle, true); 
		curve.getP2().x = curve.getP1().x+pivotDiff.x; 
		curve.getP2().y = curve.getP1().y+pivotDiff.y;
		angles.set(index, angle);
		smoothnesses.set(index, smoothness);
		
		if(index > 0)
		{
			curve = curves.get(index-1);
			pivotDiff = new Vector2D(-smoothness, angle, true); 
			curve.getP3().x = pivotDiff.x; 
			curve.getP3().y = pivotDiff.y;
		}
		
	}
	
	public void addPoint(Vector2D point, double a, double smoothness)
	{
		smoothnesses.add(smoothness);
		if(firstPoint == null)
		{
			firstPoint = point;
			firstA = a;
			angles.add(a);
			firstSmooth = -smoothness;
			return;
		}
		Vector2D p1, p2, p3;//, p4;
		angles.add(a);
		
		if(curves.size() == 0)
		{
			p1 = firstPoint;
			
			p2 = new Vector2D(firstPoint.x, firstPoint.y, firstSmooth, firstA);
			
			// p3: just rotate by 90 degrees vector with "smoothness" lenght from "point"
			// TODO: do it smarter
			p3 = new Vector2D(point.x, point.y, smoothness, a);
				
			// Vector2D p4 = point;
		}
		else
		{
			CubicBezierCurve tail = curves.get(curves.size()-1);
		
			// p1: the same as the last point in chain
			p1 = tail.getP4();
			
			// p2: reverse of previous bezier segment's smoothness vector.
			double tailA = angles.get(curves.size());
			double tailSmooth = smoothnesses.get(curves.size());
			p2 = new Vector2D(p1.x, p1.y, -tailSmooth, tailA);
			
			// p3: just rotate by 90 degrees vector with "smoothness" lenght from "point"
			// TODO: do it smarter
			p3 = new Vector2D(point.x, point.y, smoothness, a);
			
			// Vector2D p4 = point;
		}
		curves.add(new CubicBezierCurve(p1, p2, p3, point));
		
	}
	
	public Vector2D pointAt(double t)
	{
		int size = curves.size();
		assert(t >=0 && t <= size);
		if(t >= size) // TODO: brghm...
			return new Vector2D(curves.get(size-1).getP4());
			
		int index = (int)Math.floor(t);
		double offset = t - (double)index;
//		System.out.println("point at: " + t + "(" + index + " : " + offset + ") - " + curves.get(index).pointAt(offset));
		
		return curves.get(index).pointAt(offset);
	}

	public double getAngle(int index)
	{
		return angles.get(index);
	}
}
