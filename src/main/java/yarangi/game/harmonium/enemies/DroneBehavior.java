package yarangi.game.harmonium.enemies;

import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;

public class DroneBehavior implements Behavior<GenericEnemy> 
{
	double da = RandomUtil.N(0, 0.1);
	@Override
	public boolean behave(double time, GenericEnemy entity, boolean isVisible) 
	{
		entity.getArea().setOrientation( entity.getArea().getOrientation()+da );
		
		Vector2D loc = entity.getArea().getAnchor();
		if(loc.x() >  800 || loc.y() >  800
		|| loc.x() < -800 || loc.y() < -800)
			entity.markDead();
		if(entity.getIntegrity().getHitPoints() <= 0)
		{
			entity.markDead();
			return false;
		}
		
		return true;
	}

}
