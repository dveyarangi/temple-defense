package yarangi.game.harmonium.temple.harvester;

import yarangi.game.harmonium.environment.resources.Port;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.terrain.Bitmap;
import yarangi.graphics.quadraturin.terrain.GridyTerrainMap;
import yarangi.spatial.AABB;
import yarangi.spatial.Area;
import yarangi.spatial.ISpatialFilter;

public class HarvesterFactory
{
	
	public static Harvester createHarvester(Area area, double range, GridyTerrainMap terrain)
	{
		Port port = Port.createEndlessPort();
		Harvester harvester = new Harvester(port, null);
		
		harvester.setArea(area);
		harvester.setLook(new HarvesterLook());
		ErrodingBehavior behavior = createErrodingBehavior(range, terrain);
		harvester.setBehavior( behavior );
		harvester.setSensor( behavior);
		
		return harvester;
	}
	
	
	public static ErrodingBehavior createErrodingBehavior(double range, GridyTerrainMap terrain)
	{
		return new ErrodingBehavior(range, terrain);
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
