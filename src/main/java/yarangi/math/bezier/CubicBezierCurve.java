package yarangi.math.bezier;


import yarangi.math.Vector2D;

public class CubicBezierCurve implements ParametricCurve
{
	
	private Vector2D p1;
	private Vector2D p2;	
	private Vector2D p3;
	private Vector2D p4;
	public CubicBezierCurve(Vector2D p1, Vector2D p2, Vector2D p3, Vector2D p4) 
	{
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
	}
	
	
	public Vector2D getP1() { return p1; }
	public Vector2D getP2() { return p2; }
	public Vector2D getP3() { return p3; }
	public Vector2D getP4() { return p4; }
	
	public Vector2D pointAt(double t)
	{
		assert(t >=0 && t <=1);
		double invT = 1-t;
		
		return p4.mul(t*t*t).plus(
			   p3.mul(3*t*t*invT)).plus(
			   p2.mul(3*t*invT*invT)).plus(
			   p1.mul(invT*invT*invT));
	}


	public double getMin() { return 0; }
	public double getMax() { return 1; }
}
