package yarangi.game.harmonium.temple.harvester;

import yarangi.game.harmonium.environment.resources.Port;
import yarangi.game.harmonium.temple.Serviceable;
import yarangi.graphics.quadraturin.terrain.GridyTerrainMap;
import yarangi.spatial.AABB;
import yarangi.spatial.Area;

/**
 * Invisible marker used by harvesting system as anchor for bots and terrain editing.
 * @author dveyarangi
 *
 */
public class HarvestBeacon implements Serviceable
{
	private GridyTerrainMap terrain;
	
	private AABB area;
	
	public static final double RADIUS = 10;
	
	public HarvestBeacon(double x, double y, GridyTerrainMap terrain)
	{
		this.terrain = terrain;
		
		this.area = AABB.createFromCenter( x, y, RADIUS, RADIUS, 0 );
	}
	

	@Override
	public Area getServiceArea()
	{
		return area;
	}

	@Override
	public Port getPort()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
