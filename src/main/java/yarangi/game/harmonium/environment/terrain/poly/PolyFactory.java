package yarangi.game.harmonium.environment.terrain.poly;

import yarangi.game.harmonium.environment.terrain.ModifiableGridBehavior;
import yarangi.graphics.quadraturin.objects.EntityShell;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.graphics.quadraturin.terrain.ITerrainFactory;
import yarangi.graphics.quadraturin.terrain.PolygonTerrainMap;
import yarangi.graphics.quadraturin.terrain.TilePoly;
import yarangi.spatial.Tile;


public class PolyFactory implements ITerrainFactory <PolygonTerrainMap>
{

	/**
	 * number of polygonal layers
	 */
	public static final int POLY_DEPTH = 5;
	
	@Override
	public EntityShell<PolygonTerrainMap> generateTerrain(float width, float height, int cellsize)
	{
		PolygonTerrainMap terrain = new PolygonTerrainMap(cellsize, width, height);

		System.out.println(terrain.getGridWidth() + " : " + terrain.getGridHeight());
		for (int i = 0; i < terrain.getGridWidth(); i ++)
			for (int j = 0; j < terrain.getGridHeight(); j ++)
			{
				Tile <TilePoly> tile = terrain.getTileAt( i, j );
//				System.out.println(tile);
				tile.put( new TilePoly(tile.getMinX(), tile.getMinY(), tile.getMaxX(), tile.getMaxY(), POLY_DEPTH ) );
			}

		Look <PolygonTerrainMap> look = new FBOPolyTerrainLook(true, true, POLY_DEPTH);
//		Look <PolygonTerrainMap> look = new PolyTerrainLook();
		return new EntityShell<PolygonTerrainMap>( terrain, new ModifiableGridBehavior(), look );
	}

}
