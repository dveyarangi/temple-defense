package yarangi.game.harmonium.enemies.swarm.agents;


import com.seisw.util.geom.Poly;
import com.seisw.util.geom.PolyDefault;

import yarangi.graphics.colors.Color;
import yarangi.graphics.colors.MaskUtil;
import yarangi.graphics.curves.Bezier4Curve;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.graphics.quadraturin.terrain.PolygonTerrainMap;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.AABB;

public class SeederBehavior implements IBehaviorState <Seeder>
{
	private static int id = RandomUtil.N(Integer.MAX_VALUE);
	
	private double windingPhase = 0;
	public static final double WINDING_COEF = 2;
	public static final double WINDING_SPEED = 0.5;	
	private final DroneBehavior drone = new DroneBehavior(10);
	private final PolygonTerrainMap terrain;
	private static double SEED_INTERVAL = 10;
	private double lifeTime = 0;
	private double lastSeedTime = 0;
	
	private static final int MASK_WIDTH = 32; 
	
	private static final byte [] SEED_MASK = MaskUtil.createCircleMask( MASK_WIDTH/2, new Color(0.0f, 0.0f, 0.0f, 1f), false);
	
	public SeederBehavior(PolygonTerrainMap terrain)
	{
		this.terrain = terrain;
		
	}
	
	@Override
	public double behave(double time, Seeder seeder) 
	{
		drone.behave(time, seeder);
		
		windingPhase += WINDING_SPEED * time;// / seeder.getBody().getMass();
		
		Bezier4Curve left = seeder.getLeftEdge();
		Bezier4Curve right = seeder.getRightEdge();
		
		double phaseOffset = WINDING_COEF * Math.sin(windingPhase); 
		
		left.p2().sety(seeder.getLeftOffset().y() + phaseOffset);
		left.p3().sety(seeder.getLeftOffset().y() - phaseOffset);
		right.p2().sety(seeder.getRightOffset().y() + phaseOffset);
		right.p3().sety(seeder.getRightOffset().y() - phaseOffset);

		if(terrain == null)
			return 0;
		lifeTime += time;
		
		if(lifeTime - lastSeedTime > SEED_INTERVAL)
		{
			
			AABB aabb = (AABB) seeder.getArea();
			Poly poly = new PolyDefault();
			double dx = Math.cos( aabb.getOrientation()*Angles.TO_RAD );
			double dy = Math.sin( aabb.getOrientation()*Angles.TO_RAD );
			for(double i = 0; i < 1; i += 0.5) {
				Vector2D point = seeder.getRightEdge().at( i );
				poly.add( aabb.getAnchor().x() + point.x()*dx-point.y()*dy, aabb.getAnchor().y() + point.x()*dy+point.y()*dx );
			}
			for(double i = 1; i > 0; i -= 0.5) {
				Vector2D point = seeder.getLeftEdge().at( i );
				poly.add( aabb.getAnchor().x() + point.x()*dx-point.y()*dy, aabb.getAnchor().y() + point.x()*dy+point.y()*dx );
			}
			terrain.apply( aabb.getCenterX(), aabb.getCenterY(), aabb.getRX(), aabb.getRY(),false, poly );
			lastSeedTime = lifeTime;
//			Tile <Bitmap> tile = terrain.setPixel( seeder.getArea().getRefPoint().x(), seeder.getArea().getRefPoint().y(), 
//					new Color(0.2f, 0.2f, 0.2f, 1.0f) );
//			if(tile != null)
//				terrain.setModified( tile );
		}
		
		return 0;
	}
	
	
	@Override public int getId() { return id; }

}
