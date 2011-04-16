package yarangi.game.temple.model.enemies.bubbles;

import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.math.Angles;
import yarangi.math.DistanceUtils;
import yarangi.math.Vector2D;
import yarangi.spatial.AABB;

public class ChasingBubbleBehavior implements Behavior <SimpleBubble> 
{

	public boolean behave(double time, SimpleBubble bubble, boolean isVisible) 
	{
	
		// if no target, selecting one randomly:
//		if (bubble.getTarget() == null)
//			bbubbleot.setTarget(bubble.getCommandPlatform().getHexagon().getMeshNode(RandomUtil.getRandomInt(6)));
		if(bubble.getIntegrity().getHitPoints() <= 0)
		{
			bubble.setIsAlive(false);
			return false;
		}
	
		AABB targetLocation = bubble.getTarget().isAlive() ? bubble.getTarget().getAABB() : new AABB(0,0,30, 0);  
		
		
		double distanceToTarget = DistanceUtils.calcDistanceSquare(targetLocation, bubble.getAABB());

		Vector2D forceDir = targetLocation.minus(bubble.getAABB());
		double switchCoef = targetLocation.getBoundingRadius()*targetLocation.getBoundingRadius()/distanceToTarget;
		Vector2D force = forceDir.mul(1-switchCoef).plus(forceDir.rotate(Angles.PI_div_2).mul(switchCoef));
		
		force = force.normalize().mul(bubble.getEnginePower());
		
		bubble.addForce(force.x, force.y); 
		
//		if ( speed.abs() > bubble.getMaxSpeed())
//		{
//			speed = speed.normalize().mul(bubble.getMaxSpeed());
//		}

		return true;
	}


}
