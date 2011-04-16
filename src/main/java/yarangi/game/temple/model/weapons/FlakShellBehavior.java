package yarangi.game.temple.model.weapons;

import yarangi.graphics.quadraturin.objects.SensorBehavior;
import yarangi.math.Vector2D;
import yarangi.spatial.ISpatialObject;
import yarangi.spatial.SpatialIndexer;

public class FlakShellBehavior extends SensorBehavior <FlakShell>
{

	public FlakShellBehavior(SpatialIndexer<ISpatialObject> indexer) {
		super(indexer);
	}

	public boolean behave(double time, FlakShell shell, boolean isVisible) 
	{
		super.behave(time, shell, isVisible);
		Vector2D ds = shell.getVelocity().mul(time);
		
		
		if(shell.addRangeSquare(ds.x*ds.x + ds.y*ds.y))
		{
			shell.setIsAlive(false);
			return false;
		}
		
//		if(shell.getFragLocations() != null)
//		{
/*			Vector2D [] fragLocations = prj.getFragLocations();
			Vector2D [] fragSpeeds = prj.getFragSpeeds();
			for(int idx = 0; idx < fragLocations.length; idx ++)
			{
				fragLocations[idx].x += fragSpeeds[idx].x;
				fragLocations[idx].y += fragSpeeds[idx].y;
			}*/
//			shell.getAABB().r += FlakShell.EXPLOSION_SPEED * time;
//			System.out.println(shell.getAABB().r);
//		}
//		else
//		if(shell.isAtExplosionRange())
//		{
//			Vector2D [] fragLocations = new Vector2D[10];
/*			Vector2D [] fragSpeeds = new Vector2D[20];
			for(int idx = 0; idx < fragLocations.length; idx ++)
			{
				fragLocations[idx] = new Vector2D(0,0);
				double a = RandomUtil.getRandomDouble(Math.PI*2);
				double s = RandomUtil.getRandomDouble(FlakShell.EXPLOSION_SPEED)+0.1;
				fragSpeeds[idx] = new Vector2D(s*Math.cos(a), s*Math.sin(a));

			}*/
//			shell.setFrags(fragLocations);
//			prj.setSpeeds(fragSpeeds);
//		}
		return true;
			
	}

}
