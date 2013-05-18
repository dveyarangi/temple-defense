package yarangi.game.harmonium.enemies;

import yar.quadraturin.objects.IBehavior;
import yarangi.math.Geometry;
import yarangi.math.IVector2D;
import yarangi.math.Vector2D;

public class ChasingBehavior implements IBehavior <GenericEnemy> 
{

	@Override
	public boolean behave(double time, GenericEnemy bubble, boolean isVisible) 
	{
	
		// if no target, selecting one randomly:
//		if (bubble.getTarget() == null)
//			bbubbleot.setTarget(bubble.getCommandPlatform().getHexagon().getMeshNode(RandomUtil.getRandomInt(6)));
		if(bubble.getIntegrity().getHitPoints() <= 0)
		{
			bubble.markDead();
			return false;
		}
	
		IVector2D targetLocation = bubble.getTarget().isAlive() ? bubble.getTarget().getArea().getAnchor() : Vector2D.ZERO();  
		IVector2D bubbleLocation = bubble.getArea().getAnchor();
		
		double distanceToTarget = Geometry.calcHypot(targetLocation, bubbleLocation);

		Vector2D forceDir = targetLocation.minus(bubbleLocation);
		double a = Math.atan2(-forceDir.y(), -forceDir.x());
		a += Math.PI/1000f * time;
//		double switchCoef = targetLocation.getBoundingRadius()*targetLocation.getBoundingRadius()/distanceToTarget;
//		Vector2D force = forceDir.mul(1-switchCoef).plus(forceDir.rotate(Angles.PI_div_2).mul(switchCoef));
//		Vector2D force = forceDir.rotate(Angles.PI_div_2).mul(1);

		bubble.getArea().move(distanceToTarget * Math.cos(a), distanceToTarget * Math.sin(a));
//		force = force.normalize().mul(0.001/*bubble.getEnginePower()*/);
		
//		bubble.addForce(force.x, force.y); 
		
//		if ( speed.abs() > bubble.getMaxSpeed())
//		{
//			speed = speed.normalize().mul(bubble.getMaxSpeed());
//		}

		return true;
	}


}
