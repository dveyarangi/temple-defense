package yarangi.game.spacefight.model.temple.bots;

import yarangi.game.spacefight.model.temple.MeshNode;
import yarangi.game.spacefight.model.temple.TempleStructure;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.math.DistanceUtils;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;
import static yarangi.math.Angles.*;

public class BotBehavior implements Behavior <Bot> 
{

	public boolean behave(double time, Bot bot, boolean isVisible) 
	{
	
		// if no target, selecting one randomly:
		if (bot.getCurrTarget() == null)
			bot.setTarget(bot.getCommandPlatform().getHexagon().getMeshNode(RandomUtil.getRandomInt(6)));
		
		TempleStructure temple = bot.getCommandPlatform().getTempleStructure();
		
		// getting absolute target location (bots movement is independent of temple structure rotation):

		Vector2D targetLocation = temple.toTempleCoordinates(bot.getCurrTarget().getLocation());
		
		double distanceToTarget = DistanceUtils.calcDistanceSquare(targetLocation, bot.getHead());
		if (distanceToTarget < 0.1)
		{
			// redirecting to a new target:
			MeshNode newTarget = null;
			while(newTarget == null)
			{
				newTarget = bot.getCurrTarget().getNeighbor(RandomUtil.getRandomInt(6));
				if ( newTarget == bot.getPrevTarget())
					newTarget = null;
			}
			
			bot.setTarget(newTarget);
			targetLocation = temple.toTempleCoordinates(newTarget.getLocation());
		}

		////////////////////////////////////////////////////
		// moving to target:
		Vector2D forceDir = targetLocation.minus(bot.getHead());
		if (forceDir.abs() == 0) // stale mate
		{
			double randomAngle = Math.random()*PI_2;
			forceDir = new Vector2D(Math.cos(randomAngle), Math.sin(randomAngle));
		}
		
		forceDir.normalize();
		Vector2D speed = bot.velocity.plus(forceDir.mul(bot.getEnginePower()));
		
		if ( speed.abs() > bot.getMaxSpeed())
		{
			speed = speed.normalize().mul(bot.getMaxSpeed());
		}
		
		bot.velocity = speed;

//		bot.setA(Math.atan2(speed.y, speed.x));
		
		Vector2D [] tail = bot.getTail();
		for(int idx = tail.length-1; idx > 0; idx --)
			tail[idx] = tail[idx-1];
		
		tail[0] = tail[0].plus(speed);

		return true;
	}

}
