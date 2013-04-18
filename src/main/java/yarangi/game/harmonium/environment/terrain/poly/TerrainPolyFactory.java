package yarangi.game.harmonium.environment.terrain.poly;

import yarangi.graphics.quadraturin.Q;
import yarangi.graphics.quadraturin.objects.EntityShell;
import yarangi.graphics.quadraturin.objects.ILook;
import yarangi.graphics.quadraturin.terrain.ITerrainFactory;
import yarangi.graphics.quadraturin.terrain.ITilePoly;
import yarangi.graphics.quadraturin.terrain.MultilayerTilePoly;
import yarangi.graphics.quadraturin.terrain.PolygonGrid;
import yarangi.spatial.Tile;


public class TerrainPolyFactory implements ITerrainFactory <PolygonGrid>
{

	/**
	 * number of polygonal layers
	 */
	public static final int POLY_DEPTH = 3;
	
	public static final boolean FILL_ALL = false;
	
	@Override
	public EntityShell<PolygonGrid> generateTerrain(float width, float height, int cellsize)
	{
		Q.debug.trace( "creating polygonal grid [" + width + "x" + height + "], cell size: " + cellsize );
		PolygonGrid terrain = new PolygonGrid(cellsize, width, height);
		ILook <PolygonGrid> look = new FBOPolyTerrainLook(terrain, false, false, POLY_DEPTH);

		System.out.println(terrain.getGridWidth() + " : " + terrain.getGridHeight());
		for (int i = 0; i < terrain.getGridWidth(); i ++)
			for (int j = 0; j < terrain.getGridHeight(); j ++)
			{
				Tile <ITilePoly> tile = terrain.getTileByIndex( i, j );
//				System.out.println(tile);
				tile.put( new MultilayerTilePoly(
						(float)tile.getMinX(), (float)tile.getMinY(), 
						(float)tile.getMaxX(), (float)tile.getMaxY(), 
						POLY_DEPTH, FILL_ALL ) );
				terrain.fireGridModified( tile );
			}

//		ILook <PolygonTerrainMap> look = new MultilayerPolyTerrainLook(false, false);
//		Look <PolygonTerrainMap> look = new PolyTerrainLook();
		return new EntityShell<PolygonGrid>( terrain, null, look );
	}
	

}
