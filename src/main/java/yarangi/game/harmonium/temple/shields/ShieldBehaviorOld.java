package yarangi.game.harmonium.temple.shields;

import static yarangi.math.Angles.*;
import yar.quadraturin.objects.IBehavior;
import yarangi.game.harmonium.temple.ForcePoint;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;

public class ShieldBehaviorOld implements IBehavior<Shield> 
{


	public boolean behave(double time, Shield shield, boolean isVisible) 
	{

		
//		shield.getAABB().r = shield.getBattleInterface().getTempleRadius();
		
		ForcePoint [] forcePoints = shield.getForcePoints();
		
/*		if ( shieldPoint != null)
		{
			double commandAngle = shield.getBattleInterface().getTargetAngle(this);
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
		else*/
			for(int idx = 0; idx < forcePoints.length; idx ++)
			{
				ForcePoint p = forcePoints[idx];
				if(p == null || p.strength < 0)
				{
					double randomAngle = RandomUtil.getRandomDouble(TAU);
					forcePoints[idx] = new ForcePoint(randomAngle , 0);
				}
				else
					p.step(time);
			}
			
		return false;
	}

}
