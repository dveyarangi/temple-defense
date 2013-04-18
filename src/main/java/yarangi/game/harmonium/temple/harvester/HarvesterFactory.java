package yarangi.game.harmonium.temple.harvester;

import yarangi.game.harmonium.battle.MazeInterface;
import yarangi.game.harmonium.environment.resources.Port;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.terrain.Bitmap;
import yarangi.graphics.quadraturin.terrain.PolygonGrid;
import yarangi.spatial.Area;
import yarangi.spatial.ISpatialFilter;

public class HarvesterFactory
{
	
	public static Harvester createHarvester(Area area, Port port, double range, MazeInterface maze)
	{
		Harvester harvester = new Harvester(port, null);
		
		harvester.setArea(area);
		ErrodingBehavior behavior = createErrodingBehavior(range, maze);
		harvester.setBehavior( behavior );
		harvester.setTerrainSensor( behavior);
		harvester.setLook(new HarvesterLook((int)behavior.getRadius()));
		
		return harvester;
	}
	public static Waller createWaller(Area area, Port port, double range, MazeInterface maze, PolygonGrid reinforcementMap)
	{
		Waller waller = new Waller(port, null);
		
		waller.setArea(area);
		EnforcingBehavior behavior = createEnforcingBehavior(range, maze, reinforcementMap);
		waller.setBehavior( behavior );
		waller.setTerrainSensor( behavior);
		waller.setLook(new WallerLook((int)behavior.getRadius()));
		
		return waller;
	}
	
	
	public static ErrodingBehavior createErrodingBehavior(double range, MazeInterface maze)
	{
		return new ErrodingBehavior(range, maze);
	}
	public static EnforcingBehavior createEnforcingBehavior(double range, MazeInterface maze, PolygonGrid reinforcementMap)
	{
		return new EnforcingBehavior(range, maze, reinforcementMap);
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
