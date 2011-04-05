package yarangi.game.temple.model.temple;

import static yarangi.math.Angles.*;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;

public class ShieldBehavior implements Behavior<ShieldEntity> 
{


	public boolean behave(double time, ShieldEntity shield, boolean isVisible) 
	{

		
		shield.getAABB().r = shield.getBattleInterface().getTempleRadius();
		
		Vector2D shieldPoint = shield.getBattleInterface().getTrackingPoint(null);
		
		ForcePoint [] forcePoints = shield.getForcePoints();
		
		if ( shieldPoint != null)
		{
			double commandAngle = Math.atan2(shieldPoint.y, shieldPoint.x);
			for(int idx = 0; idx < forcePoints.length; idx ++)
			{
				ForcePoint p = forcePoints[idx];
				if(p == null || p.strength < 0)
				{
					double randomAngle = RandomUtil.getRandomGaussian(commandAngle, PI_div_6);
					forcePoints[idx] = new ForcePoint(randomAngle ,0);
					
				}
				else
					p.step(time);

			}
		}
		else
			for(int idx = 0; idx < forcePoints.length; idx ++)
			{
				ForcePoint p = forcePoints[idx];
				if(p == null || p.strength < 0)
				{
					double randomAngle = RandomUtil.getRandomDouble(PI_2);
					forcePoints[idx] = new ForcePoint(randomAngle , 0);
				}
				else
					p.step(time);
			}
			
		return false;
	}

}
