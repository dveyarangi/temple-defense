package yarangi.game.harmonium.temple.weapons;

import yarangi.game.harmonium.temple.BattleInterface;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.math.Vector2D;

public class SimpleProjectileBehavior implements Behavior <Projectile>
{
	private BattleInterface bi;
	
	public SimpleProjectileBehavior(BattleInterface bi)
	{
		this.bi = bi;
	}

	public boolean behave(double time, Projectile prj, boolean isVisible) 
	{
		
		Vector2D ds = prj.getBody().getVelocity().mul(time);
		
		if(prj.addRange(Math.hypot( ds.x(), ds.y() )))
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
