package yarangi.game.harmonium.enemies;

import yarangi.graphics.quadraturin.objects.IBehavior;
import yarangi.math.IVector2D;
import yarangi.numbers.RandomUtil;

public class DroneBehavior implements IBehavior<GenericEnemy> 
{
	double da = RandomUtil.STD(0, 0.1);
	
	@Override
	public boolean behave(double time, GenericEnemy entity, boolean isVisible) 
	{
		entity.getArea().setOrientation( entity.getArea().getOrientation()+da );

		IVector2D loc = entity.getArea().getAnchor();
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
