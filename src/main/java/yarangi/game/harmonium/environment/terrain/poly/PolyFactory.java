package yarangi.game.harmonium.environment.terrain.poly;

import yarangi.graphics.quadraturin.objects.EntityShell;
import yarangi.graphics.quadraturin.objects.ILook;
import yarangi.graphics.quadraturin.terrain.ITerrainFactory;
import yarangi.graphics.quadraturin.terrain.MultilayerTilePoly;
import yarangi.graphics.quadraturin.terrain.PolygonTerrainMap;
import yarangi.spatial.Tile;


public class PolyFactory implements ITerrainFactory <PolygonTerrainMap>
{

	/**
	 * number of polygonal layers
	 */
	public static final int POLY_DEPTH = 3;
	
	@Override
	public EntityShell<PolygonTerrainMap> generateTerrain(float width, float height, int cellsize)
	{
		PolygonTerrainMap terrain = new PolygonTerrainMap(cellsize, width, height);

		System.out.println(terrain.getGridWidth() + " : " + terrain.getGridHeight());
		for (int i = 0; i < terrain.getGridWidth(); i ++)
			for (int j = 0; j < terrain.getGridHeight(); j ++)
			{
				Tile <MultilayerTilePoly> tile = terrain.getTileAt( i, j );
//				System.out.println(tile);
				tile.put( new MultilayerTilePoly(tile.getMinX(), tile.getMinY(), tile.getMaxX(), tile.getMaxY(), POLY_DEPTH, false ) );
			}

//		ILook <PolygonTerrainMap> look = new MultilayerPolyTerrainLook(false, false);
		ILook <PolygonTerrainMap> look = new FBOPolyTerrainLook(terrain, false, false, POLY_DEPTH);
//		Look <PolygonTerrainMap> look = new PolyTerrainLook();
		return new EntityShell<PolygonTerrainMap>( terrain, null, look );
	}
	

}
