package yarangi.game.harmonium.temple.harvester;

import yarangi.game.harmonium.environment.resources.Port;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.Sensor;
import yarangi.graphics.quadraturin.terrain.Bitmap;
import yarangi.graphics.quadraturin.terrain.GridyTerrainMap;
import yarangi.spatial.AABB;
import yarangi.spatial.ISpatialFilter;
import yarangi.spatial.ITile;

public class HarvesterFactory
{
	
	public static Harvester createHarvester(double x, double y, GridyTerrainMap terrain)
	{
		Port port = Port.createEndlessPort();
		Harvester harvester = new Harvester(port, null);
		
		harvester.setArea(AABB.createSquare( x, y, 5, 0 ));
		harvester.setLook(new HarvesterLook());
		harvester.setBehavior( createErrodingBehavior(terrain) );
		harvester.setSensor(new Sensor(100, 5, new HarvesterSensorFilter(), true));
		
		return harvester;
	}
	
	
	public static Behavior <Harvester> createErrodingBehavior(GridyTerrainMap terrain)
	{
		return new ErrodingBehavior(terrain);
	}
	
	private static class HarvesterSensorFilter implements ISpatialFilter <IEntity> {

		@Override
		public boolean accept(IEntity entity)
		{
				System.out.println(entity);
			if(entity instanceof Bitmap)
				return true;
			
			return false;
		}
		
	}
}
