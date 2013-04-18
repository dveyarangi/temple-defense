package yarangi.game.harmonium.enemies.swarm.agents;

import java.awt.geom.Point2D;

import yarangi.game.harmonium.battle.ISeed;
import yarangi.game.harmonium.battle.Integrity;
import yarangi.game.harmonium.enemies.swarm.Swarm;
import yarangi.game.harmonium.environment.terrain.EntropySeed;
import yarangi.graphics.curves.Bezier4Curve;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.spatial.Area;

import com.seisw.util.geom.Poly;
import com.seisw.util.geom.PolyDefault;

public class Seeder extends SwarmAgent 
{
	private final Bezier4Curve left, right;
	
	public static final double PIVOT_COEF = 0.4;
	public static final double WIDTH_COEF = 0.2;
	
	private final Vector2D leftOffset, rightOffset;
	
	private final ISeed seed;
	
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
		
		this.seed = new EntropySeed(this);

	}

	public Bezier4Curve getLeftEdge() { return left; } 
	public Bezier4Curve getRightEdge() { return right; } 
	public Vector2D getLeftOffset() { return leftOffset; }
	public Vector2D getRightOffset() { return rightOffset; }
	
	@Override
	protected Poly createErrosionPoly(Area area)
	{
		double size = area.getMaxRadius();
		PolyDefault poly = new PolyDefault();
		for(double a = 0 ; a < Angles.TAU; a += Angles.PI_div_3)
			poly.add( new Point2D.Double(5*size * Math.cos( a ), 5*size * Math.sin( a )) );
		return poly;
	}

	public ISeed getSeed()
	{
		
		return seed;
	}


}
