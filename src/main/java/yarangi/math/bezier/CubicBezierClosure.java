package yarangi.math.bezier;

import java.util.ArrayList;
import java.util.List;

import yarangi.math.Angles;
import yarangi.math.Vector2D;

public class CubicBezierClosure implements ParametricCurve
{

	private List <CubicBezierCurve> curves = new ArrayList <CubicBezierCurve> ();
	 
    private Vector2D firstPoint; 
    private double firstA;
    private double firstSmooth;
	public CubicBezierClosure()
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
	public int getPointsCount() { return curves.size()+1; }
	
	public double getMax() { return curves.size(); }
	
	public void setPoint(int index, Vector2D newPoint)
	{
		if(index == 0)
		{
			CubicBezierCurve curve = curves.get(0);
			curve.getP1().x = newPoint.x;
			curve.getP1().y = newPoint.y;
		}
		else
		{
			CubicBezierCurve curve = curves.get(index-1);
			curve.getP4().x = newPoint.x;
			curve.getP4().y = newPoint.y;
		}
	}
	
	public void setCurvature(int index, double angle, double smoothness)
	{
		if(index == 0)
		{
			CubicBezierCurve curve = curves.get(0);
			curve.getP2().x = curve.getP1().x+smoothness*Math.cos(angle-Angles.PI_div_2); 
			curve.getP2().y = curve.getP1().y+smoothness*Math.sin(angle-Angles.PI_div_2);
		}
		else
		{
			CubicBezierCurve curve = curves.get(index-1);
			curve.getP3().x = curve.getP4().x+smoothness*Math.cos(angle+Angles.PI_div_2); 
			curve.getP3().y = curve.getP4().y+smoothness*Math.sin(angle+Angles.PI_div_2);
		}
		
	}
	
	public void addPoint(Vector2D point, double a, double smoothness)
	{
		if(firstPoint == null)
		{
			firstPoint = point;
			firstA = a;
			firstSmooth = smoothness;
			System.out.println("p1: " + firstPoint);
			
			return;
		}
		Vector2D p1, p2, p3;//, p4;
		
		if(curves.size() == 0)
		{
			p1 = firstPoint;
			
			p2 = new Vector2D(firstPoint.x+firstSmooth*Math.cos(firstA-Angles.PI_div_2), 
					          firstPoint.y+firstSmooth*Math.sin(firstA-Angles.PI_div_2));
			
			// p3: just rotate by 90 degrees vector with "smoothness" lenght from "point"
			// TODO: do it smarter
			p3 = new Vector2D(point.x+smoothness*Math.cos(a+Angles.PI_div_2),
					          point.y+smoothness*Math.sin(a+Angles.PI_div_2));
				
			// Vector2D p4 = point;
		}
		else
		{
			CubicBezierCurve tail = curves.get(curves.size()-1);
		
			// p1: the same as the last point in chain
			p1 = tail.getP4();
			
			// p2: reverse of previous bezier segment's smoothness vector.
			p2 = tail.getP3().minus(p1).minus().plus(p1);
			
			// p3: just rotate by 90 degrees vector with "smoothness" lenght from "point"
			// TODO: do it smarter
			p3 = new Vector2D(point.x+smoothness*Math.cos(a+Angles.PI_div_2), 
							  point.y+smoothness*Math.sin(a+Angles.PI_div_2));
			
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

			
}
