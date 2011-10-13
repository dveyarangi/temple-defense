package yarangi.game.temple.model.temple.bots;

import yarangi.game.temple.model.temple.Serviceable;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.math.Angles;
import yarangi.math.Geometry;
import yarangi.math.Vector2D;

public class ChasingBehavior implements IBehaviorState <Bot> 
{
	private Serviceable target;
	
	public ChasingBehavior(Serviceable target)
	{
		
		this.target = target;
	}
	public double behave(double time, Bot bot, boolean isVisible) 
	{
	
		// getting absolute target location (bots movement is independent of temple structure rotation):

		Vector2D targetLocation = target.getServicePoint().getRefPoint();
		
		double distanceToTarget = Geometry.calcHypotSquare(targetLocation, bot.getArea().getRefPoint());
		if (distanceToTarget < 0.2)
		{
			return 0; // proceed to next state
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
		
		return -1; // stay in this state
	}
	public int getId() { return getStateId(); }
	public static int getStateId() {return ChasingBehavior.class.hashCode(); }

}
