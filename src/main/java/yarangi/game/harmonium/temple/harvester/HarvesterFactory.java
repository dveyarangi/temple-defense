package yarangi.game.harmonium.temple.harvester;

import yarangi.game.harmonium.environment.resources.Port;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.terrain.Bitmap;
import yarangi.graphics.quadraturin.terrain.PolygonGrid;
import yarangi.graphics.quadraturin.terrain.PolygonTerrainMap;
import yarangi.graphics.quadraturin.terrain.TilePoly;
import yarangi.spatial.Area;
import yarangi.spatial.ISpatialFilter;

public class HarvesterFactory
{
	
	public static Harvester createHarvester(Area area, Port port, double range, PolygonTerrainMap terrain)
	{
		Harvester harvester = new Harvester(port, null);
		
		harvester.setArea(area);
		harvester.setLook(new HarvesterLook());
		ErrodingBehavior behavior = createErrodingBehavior(range, terrain);
		harvester.setBehavior( behavior );
		harvester.setSensor( behavior);
		
		return harvester;
	}
	public static Waller createWaller(Area area, Port port, double range, PolygonTerrainMap terrain, PolygonGrid <TilePoly> reinforcementMap)
	{
		Waller waller = new Waller(port, null);
		
		waller.setArea(area);
		waller.setLook(new WallerLook());
		EnforcingBehavior behavior = createEnforcingBehavior(range, terrain, reinforcementMap);
		waller.setBehavior( behavior );
		waller.setSensor( behavior);
		
		return waller;
	}
	
	
	public static ErrodingBehavior createErrodingBehavior(double range, PolygonTerrainMap terrain)
	{
		return new ErrodingBehavior(range, terrain);
	}
	public static EnforcingBehavior createEnforcingBehavior(double range, PolygonTerrainMap terrain, PolygonGrid <TilePoly> reinforcementMap)
	{
		return new EnforcingBehavior(range, terrain, reinforcementMap);
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
