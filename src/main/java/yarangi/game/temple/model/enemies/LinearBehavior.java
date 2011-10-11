package yarangi.game.temple.model.enemies;

import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.spatial.AABB;

public class LinearBehavior implements Behavior<GenericEnemy> 
{

	public boolean behave(double time, GenericEnemy entity, boolean isVisible) 
	{
//		AABB aabb = entity.getAABB();
		
//		aabb.x += entity.getVelocity().x;
//		aabb.y += entity.getVelocity().y;
//		System.out.println(aabb);
		
		if(entity.getIntegrity().getHitPoints() == 0)
			entity.markDead();
//		System.out.println(entity);
		return true;
	}

}
