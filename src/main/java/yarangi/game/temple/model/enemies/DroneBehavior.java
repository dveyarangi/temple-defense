package yarangi.game.temple.model.enemies;

import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.math.Vector2D;

public class DroneBehavior implements Behavior<GenericEnemy> 
{

	@Override
	public boolean behave(double time, GenericEnemy entity, boolean isVisible) 
	{
		Vector2D loc = entity.getArea().getRefPoint();
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
