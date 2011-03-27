package yarangi.math.bezier;

import yarangi.math.Vector2D;

public class QuadriticBezierCurve implements ParametricCurve {

	private Vector2D p0;
	private Vector2D p1;
	private Vector2D p2;
	
	public QuadriticBezierCurve(Vector2D p0, Vector2D p1, Vector2D p2)
	{
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public Vector2D getP0() { return p0; }

	public Vector2D getP1() { return p1; }

	public Vector2D getP2() { return p2; }

	public Vector2D getFirst() { return p0; }

	public Vector2D getLast() { return p2; }
	
	public Vector2D pointAt(double t)
	{
		double invT = 1-t;
		return p0.mul(invT*invT)
			.plus(p1.mul(2*t*invT))
			.plus(p2.mul(t*t));
	}

	public double getMin() { return 0; }
	public double getMax() { return 1; }

	
	
}
