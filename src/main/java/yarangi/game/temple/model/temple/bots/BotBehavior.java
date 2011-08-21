package yarangi.game.temple.model.temple.bots;

import yarangi.game.temple.model.temple.Serviceable;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.math.Angles;
import yarangi.math.Geometry;
import yarangi.math.Vector2D;

public class BotBehavior implements Behavior <Bot> 
{

	public boolean behave(double time, Bot bot, boolean isVisible) 
	{
	
		// if no target, selecting one randomly:
		if (bot.getCurrTarget() == null)
		{
			bot.setTarget(bot.getStructureInterface().getServiceTarget(bot));
		}
		// getting absolute target location (bots movement is independent of temple structure rotation):

		Vector2D targetLocation = bot.getCurrTarget().getServicePoint();
		
		double distanceToTarget = Geometry.calcHypotSquare(targetLocation, bot.getArea().getRefPoint());
		if (distanceToTarget < 0.2)
		{
			// redirecting to a new target:
			Serviceable newTarget = null;
			while(newTarget == null)
			{
				newTarget = bot.getStructureInterface().getServiceTarget(bot);
			}
			
			bot.setTarget(newTarget);
			targetLocation = newTarget.getServicePoint();
		}

		////////////////////////////////////////////////////
		// moving to target:
		Vector2D forceDir = targetLocation.minus(bot.getArea().getRefPoint());
		if (forceDir.abs() == 0) // stale mate
		{
			double randomAngle = Math.random()*Angles.PI_2;
			forceDir = new Vector2D(Math.cos(randomAngle), Math.sin(randomAngle));
		}
		
		forceDir.normalize().multiply(bot.getEnginePower());
		
		bot.getBody().setForce( forceDir.x(), forceDir.y() );

//		bot.setA(Math.atan2(speed.y, speed.x));
		
		return true;
	}

}
