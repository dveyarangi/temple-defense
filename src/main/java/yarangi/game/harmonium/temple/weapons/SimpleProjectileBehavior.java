package yarangi.game.harmonium.temple.weapons;

import yarangi.game.harmonium.temple.BattleInterface;
import yarangi.graphics.quadraturin.objects.IBehavior;
import yarangi.math.Vector2D;

public class SimpleProjectileBehavior implements IBehavior <Projectile>
{
	
	public SimpleProjectileBehavior()
	{
	}
	
	@Override
	public boolean behave(double time, Projectile prj, boolean isVisible) 
	{
		double dx = prj.getBody().getVelocity().x() * time;
		double dy = prj.getBody().getVelocity().y() * time;
		
		if(prj.addRange(Math.sqrt(dx*dx + dy*dy)))
		{
			prj.markDead();
			return false;
		}
		if(prj.getImpactPoint() != null)
		{
			prj.markDead();
			return false;
		}
		
/*		if(prj.getArea().getMaxRadius() != bi.getBattleScale())
		{
		prj.getArea().fitTo(bi.getBattleScale());
		}*/
		return true;
	}

}
