package yarangi.game.temple.model.enemies;

import yarangi.graphics.quadraturin.objects.Behavior;

public class DroneBehavior implements Behavior<GenericEnemy> 
{

	@Override
	public boolean behave(double time, GenericEnemy entity, boolean isVisible) {
		if(entity.getAABB().x > 800 || entity.getAABB().y > 800
		|| entity.getAABB().x < -800 || entity.getAABB().y < -800)
			entity.setIsAlive(false);
		if(entity.getIntegrity().getHitPoints() <= 0)
		{
			entity.setIsAlive(false);
			return false;
		}
		
		return true;
	}

}
