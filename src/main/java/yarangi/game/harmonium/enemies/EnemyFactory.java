package yarangi.game.harmonium.enemies;

import yarangi.game.harmonium.battle.ITemple;
import yarangi.game.harmonium.enemies.swarm.agents.SwarmAgent;
import yarangi.graphics.quadraturin.objects.IBeing;
import yarangi.graphics.quadraturin.objects.ISensor;
import yarangi.graphics.quadraturin.objects.Sensor;
import yarangi.spatial.ISpatialFilter;

public class EnemyFactory
{
	public static ISpatialFilter <IBeing> FRIEND_OR_FOE_FILTER = new ISpatialFilter <IBeing> ()
	{
		@Override
		public boolean accept(IBeing entity)
		{
			return entity instanceof SwarmAgent || entity instanceof ITemple;
		}
		
	};
	
	public static ISensor <IBeing> SHORT_SENSOR() 
	{
		return new Sensor <IBeing> (20, 10, FRIEND_OR_FOE_FILTER);
	}
}
