package yarangi.game.harmonium.enemies;

import yarangi.game.harmonium.battle.ITemple;
import yarangi.game.harmonium.enemies.swarm.agents.SwarmAgent;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.ISensor;
import yarangi.graphics.quadraturin.objects.Sensor;
import yarangi.spatial.ISpatialFilter;

public class EnemyFactory
{
	public static ISpatialFilter <IEntity> FRIEND_OR_FOE_FILTER = new ISpatialFilter <IEntity> ()
	{
		@Override
		public boolean accept(IEntity entity)
		{
			return entity instanceof SwarmAgent || entity instanceof ITemple;
		}
		
	};
	
	public static ISensor <IEntity> SHORT_SENSOR() 
	{
		return new Sensor(50, 10, FRIEND_OR_FOE_FILTER, false);
	}
}
