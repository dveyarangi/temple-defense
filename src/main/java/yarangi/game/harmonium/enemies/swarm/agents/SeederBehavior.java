package yarangi.game.harmonium.enemies.swarm.agents;

import yarangi.graphics.colors.Color;
import yarangi.graphics.colors.MaskUtil;
import yarangi.graphics.curves.Bezier4Curve;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.graphics.quadraturin.terrain.Bitmap;
import yarangi.graphics.quadraturin.terrain.GridyTerrainMap;
import yarangi.math.BitUtils;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.Tile;

public class SeederBehavior implements IBehaviorState <Seeder>
{
	private static int id = RandomUtil.getRandomInt(Integer.MAX_VALUE);
	
	private double windingPhase = 0;
	public static final double WINDING_COEF = 3;
	public static final double WINDING_SPEED = 0.2;	
	private DroneBehavior drone = new DroneBehavior(100);
	private GridyTerrainMap terrain;
	private static double SEED_INTERVAL = 20;
	private double lifeTime = 0;
	private double lastSeedTime = 0;
	
	private static final int MASK_WIDTH = 8; 
	
	private static final byte [] SEED_MASK = MaskUtil.createCircleMask( MASK_WIDTH/2, new Color(0.1f, 0.1f, 0.1f, 1f) );
	
	public SeederBehavior(GridyTerrainMap terrain)
	{
		this.terrain = terrain;
	}

	@Override
	public double behave(double time, Seeder seeder) 
	{
		drone.behave(time, seeder);
		
		windingPhase += WINDING_SPEED * time;
		
		Bezier4Curve left = seeder.getLeftEdge();
		Bezier4Curve right = seeder.getRightEdge();
		
		double phaseOffset = WINDING_COEF * Math.sin(windingPhase); 
		
		left.p2().sety(seeder.getLeftOffset().y() + phaseOffset);
		left.p3().sety(seeder.getLeftOffset().y() - phaseOffset);
		right.p2().sety(seeder.getRightOffset().y() + phaseOffset);
		right.p3().sety(seeder.getRightOffset().y() - phaseOffset);

		lifeTime += time;
		if(lifeTime - lastSeedTime > SEED_INTERVAL)
		{
			lastSeedTime = lifeTime;
			
			terrain.apply( seeder.getArea().getRefPoint().x()-MASK_WIDTH/2, seeder.getArea().getRefPoint().y()-MASK_WIDTH/2, false, MASK_WIDTH, SEED_MASK );
			
//			Tile <Bitmap> tile = terrain.setPixel( seeder.getArea().getRefPoint().x(), seeder.getArea().getRefPoint().y(), 
//					new Color(0.2f, 0.2f, 0.2f, 1.0f) );
//			if(tile != null)
//				terrain.setModified( tile );
		}
		
		return 0;
	}
	
	@Override public int getId() { return id; }

}
