package yarangi.game.temple.model.enemies.swarm.agents;

import yarangi.game.temple.model.Integrity;
import yarangi.game.temple.model.enemies.swarm.Swarm;
import yarangi.graphics.curves.Bezier4Curve;
import yarangi.math.Vector2D;
import yarangi.spatial.Area;

public class Seeder extends SwarmAgent 
{
	private Bezier4Curve left, right;
	
	public static final double PIVOT_COEF = 0.4;
	public static final double WIDTH_COEF = 0.2;

	
	private Vector2D leftOffset, rightOffset;
	
	public Seeder(Swarm swarm, Integrity integrity, Area area) 
	{
		super(swarm, integrity, true);
		
		setArea(area);
		
		Vector2D p1 = new Vector2D(area.getMaxRadius(), 0);
		Vector2D p4 = new Vector2D(-area.getMaxRadius(), 0);
		
		leftOffset = new Vector2D(PIVOT_COEF*area.getMaxRadius(), area.getMaxRadius()*WIDTH_COEF); 
		rightOffset = new Vector2D(PIVOT_COEF*area.getMaxRadius(), -area.getMaxRadius()*WIDTH_COEF); 
		
		left = new Bezier4Curve(p1, new Vector2D(leftOffset), new Vector2D(leftOffset), p4);
		right = new Bezier4Curve(p1, new Vector2D(rightOffset), new Vector2D(rightOffset), p4);
		

	}

	public Bezier4Curve getLeftEdge() { return left; } 
	public Bezier4Curve getRightEdge() { return right; } 
	public Vector2D getLeftOffset() { return leftOffset; }
	public Vector2D getRightOffset() { return rightOffset; }
}
