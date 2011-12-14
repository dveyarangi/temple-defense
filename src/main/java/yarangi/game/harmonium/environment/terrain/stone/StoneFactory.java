package yarangi.game.harmonium.environment.terrain.stone;

import yarangi.game.harmonium.environment.terrain.GridyTerrainBehavior;
import yarangi.game.harmonium.environment.terrain.GridyTerrainLook;
import yarangi.graphics.colors.Color;
import yarangi.graphics.quadraturin.objects.EntityShell;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.graphics.quadraturin.terrain.Bitmap;
import yarangi.graphics.quadraturin.terrain.GridyTerrainMap;
import yarangi.graphics.quadraturin.terrain.ITerrainFactory;
import yarangi.spatial.Tile;


public class StoneFactory implements ITerrainFactory <GridyTerrainMap>
{

	@Override
	public EntityShell<GridyTerrainMap> generateTerrain(float width, float height, int cellsize)
	{
		GridyTerrainMap terrain = new GridyTerrainMap( width, height, cellsize, 1 );

		for (int i = 0; i < terrain.getGridWidth(); i ++)
		{
	    	double tileX = terrain.toRealXIndex( i );
//			xoffset = xstart + toXCoord * i * cellsize;
		    for(int j = 0; j <  terrain.getGridHeight(); j ++)
		    {
//				yoffset = ystart + toYCoord * j * cellsize;
		    	double tileY = terrain.toRealYIndex( j );

		    	
		    	Tile<Bitmap> tile = terrain.createTileAt( i, j );
		    	Bitmap bitmap = tile.get();
		    	for(int x = 0; x < terrain.getCellSize(); x ++)
			    	for(int y = 0; y < terrain.getCellSize(); y ++)
			    	{
			    		if((tileX+x)*(tileX+x) + (tileY+y)*(tileY+y) > 100000) // TODO: variety :)
			    			bitmap.put( new Color(0.1f, 0.1f, 0.1f, 1), x, y );
			    	}
		    	terrain.setModified( tile );
		    }
		}

		Look <GridyTerrainMap> look = new GridyTerrainLook();
		return new EntityShell<GridyTerrainMap>( terrain, new GridyTerrainBehavior(), look );
	}

}
