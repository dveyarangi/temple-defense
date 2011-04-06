package yarangi.game.temple.model.weapons;

import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.math.Vector2D;

public class SimpleProjectileBehavior implements Behavior <Projectile>
{


	public boolean behave(double time, Projectile prj, boolean isVisible) 
	{
		
		Vector2D ds = prj.getVelocity().mul(time);
		
		if(prj.addRangeSquare(ds.x*ds.x + ds.y*ds.y))
		{
			prj.setIsAlive(false);
			return false;
		}
		if(prj.getImpactPoint() != null)
		{
			prj.setIsAlive(false);
			return false;
		}
		
		return true;
	}

}
