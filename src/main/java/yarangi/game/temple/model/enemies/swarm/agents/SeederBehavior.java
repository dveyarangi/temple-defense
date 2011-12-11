package yarangi.game.temple.model.enemies.swarm.agents;

import yarangi.graphics.curves.Bezier4Curve;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.numbers.RandomUtil;

public class SeederBehavior implements IBehaviorState <Seeder>
{
	private static int id = RandomUtil.getRandomInt(Integer.MAX_VALUE);
	
	private double windingPhase = 0;
	public static final double WINDING_COEF = 3;
	public static final double WINDING_SPEED = 0.2;	
	private DroneBehavior drone = new DroneBehavior();
	
	public SeederBehavior()
	{
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

		
		return 0;
	}
	
	@Override public int getId() { return id; }

}
