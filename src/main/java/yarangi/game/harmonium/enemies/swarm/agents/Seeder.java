package yarangi.game.harmonium.enemies.swarm.agents;

import yarangi.game.harmonium.enemies.swarm.Swarm;
import yarangi.game.harmonium.model.Integrity;
import yarangi.graphics.curves.Bezier4Curve;
import yarangi.math.Vector2D;
import yarangi.spatial.Area;

public class Seeder extends SwarmAgent 
{
	private Bezier4Curve left, right;
	
	public static final double PIVOT_COEF = 0.4;
	public static final double WIDTH_COEF = 0.2;

	
	private Vector2D leftOffset, rightOffset;
	
	public Seeder(Swarm swarm, Integrity integrity, Area area, double leadership, double attractiveness) 
	{
		super(swarm, integrity, leadership, attractiveness);
		
		setArea(area);
		
		Vector2D p1 = Vector2D.R(area.getMaxRadius(), 0);
		Vector2D p4 = Vector2D.R(-area.getMaxRadius(), 0);
		
		leftOffset = Vector2D.R(PIVOT_COEF*area.getMaxRadius(), area.getMaxRadius()*WIDTH_COEF); 
		rightOffset = Vector2D.R(PIVOT_COEF*area.getMaxRadius(), -area.getMaxRadius()*WIDTH_COEF); 
		
		left = new Bezier4Curve(p1, Vector2D.COPY(leftOffset), Vector2D.COPY(leftOffset), p4);
		right = new Bezier4Curve(p1, Vector2D.COPY(rightOffset), Vector2D.COPY(rightOffset), p4);
		

	}

	public Bezier4Curve getLeftEdge() { return left; } 
	public Bezier4Curve getRightEdge() { return right; } 
	public Vector2D getLeftOffset() { return leftOffset; }
	public Vector2D getRightOffset() { return rightOffset; }
}
