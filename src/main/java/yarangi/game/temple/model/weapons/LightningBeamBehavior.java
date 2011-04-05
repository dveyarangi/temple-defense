package yarangi.game.temple.model.weapons;

import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.math.Vector2D;

public class LightningBeamBehavior implements Behavior<LightningBeam> 
{

	public boolean behave(double time, LightningBeam prj, boolean isVisible) 
	{
		Vector2D speed = prj.velocity;
		
		if(prj.prev == null)
		{
			double dispersion = prj.getRangeSquare()/prj.maxRange * 10;
//			prj.x += prj.getAbsSpeed();
//			prj.y += speed.y+RandomUtil.getRandomGaussian(0, dispersion);
//			prj.curr.x = prj.x;
//			prj.curr.y = prj.y;
		}
		else
		{
//			System.out.println(prj + " ::: " + prj.prev.curr);
//			prj.curr.x = prj.x;
//			prj.curr.y = prj.y;
//			prj.x = prj.prev.curr.x;
//			prj.y = prj.prev.curr.y;
		}
			
//		prj.age -= time*0.1;
		
		if(prj.addRangeSquare(speed.x*speed.x + speed.y*speed.y))
		{
			prj.setIsAlive(false);
//			return false;
		}
		
		// explosion does not reacts to impact
		
		return true;
	}

}
