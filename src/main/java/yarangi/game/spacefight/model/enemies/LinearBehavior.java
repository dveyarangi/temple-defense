package yarangi.game.spacefight.model.enemies;

import yarangi.graphics.quadraturin.interaction.spatial.AABB;
import yarangi.graphics.quadraturin.objects.Behavior;

public class LinearBehavior implements Behavior<GenericEnemy> 
{

	public boolean behave(double time, GenericEnemy entity, boolean isVisible) 
	{
		AABB aabb = entity.getAABB();
		
		aabb.x += entity.getVelocity().x;
		aabb.y += entity.getVelocity().y;
		
		if(entity.getIntegrity().getHitPoints() == 0)
			entity.setIsAlive(false);
//		System.out.println(entity);
		return true;
	}

}
