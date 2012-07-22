package yarangi.game.harmonium.enemies;

import yarangi.graphics.quadraturin.objects.IBehavior;

public class ElementalDarknessBehavior implements IBehavior<ElementalDarkness> 
{

	public boolean behave(double time, ElementalDarkness entity, boolean isVisible) 
	{
//		AABB aabb = entity.getAABB();
		
//		aabb.x += entity.getVelocity().x;
//		aabb.y += entity.getVelocity().y;
		
		if(entity.getIntegrity().getHitPoints() <= 0)
		{
			entity.markDead();
			return false;
		}
//		BezierBubble curve = entity.getCurve();
/*		for(int idx = 0; idx < curve.getNodesCount(); idx ++)
		{
			curve.setCurvature(idx, curve.getAngle(idx)+Angles.PI_div_20/2, 5);
		}*/
//		System.out.println(entity);
		return true;
	}

}
