package yarangi.game.temple.model.weapons;

import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;

public class FlakShellBehavior implements Behavior <FlakShell>
{

	public boolean behave(double time, FlakShell prj, boolean isVisible) 
	{
		Vector2D ds = prj.getVelocity().mul(time);
		
		
		if(prj.addRangeSquare(ds.x*ds.x + ds.y*ds.y))
		{
			prj.setIsAlive(false);
			return false;
		}
		
		if(prj.getFragLocations() != null)
		{
			Vector2D [] fragLocations = prj.getFragLocations();
			Vector2D [] fragSpeeds = prj.getFragSpeeds();
			for(int idx = 0; idx < fragLocations.length; idx ++)
			{
				fragLocations[idx].x += fragSpeeds[idx].x;
				fragLocations[idx].y += fragSpeeds[idx].y;
			}
			prj.getAABB().r += FlakShell.EXPLOSION_SPEED * time;
		}
		else
		if(prj.isAtExplosionRange())
		{
			Vector2D [] fragLocations = new Vector2D[20];
			Vector2D [] fragSpeeds = new Vector2D[20];
			for(int idx = 0; idx < fragLocations.length; idx ++)
			{
				fragLocations[idx] = new Vector2D(0,0);
				double a = RandomUtil.getRandomDouble(Math.PI*2);
				double s = RandomUtil.getRandomDouble(FlakShell.EXPLOSION_SPEED)+0.1;
				fragSpeeds[idx] = new Vector2D(s*Math.cos(a), s*Math.sin(a));

			}
			prj.setFrags(fragLocations);
			prj.setSpeeds(fragSpeeds);
		}
		return true;
			
	}

}
