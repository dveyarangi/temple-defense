package yarangi.game.harmonium.environment.terrain.stone;

import yar.quadraturin.graphics.colors.Color;
import yar.quadraturin.objects.EntityShell;
import yar.quadraturin.objects.ILook;
import yar.quadraturin.terrain.Bitmap;
import yar.quadraturin.terrain.GridyTerrainMap;
import yar.quadraturin.terrain.ITerrainFactory;
import yarangi.game.harmonium.environment.terrain.GridyTerrainLook;
import yarangi.spatial.Tile;

public class StoneFactory implements ITerrainFactory <GridyTerrainMap>
{

	@Override
	public EntityShell<GridyTerrainMap> generateTerrain(float width, float height, int cellsize)
	{
		GridyTerrainMap terrain = new GridyTerrainMap( width, height, cellsize, 0.5f );

		for (int i = 0; i < terrain.getGridWidth(); i ++)
		{
	    	double tileX = terrain.toXCoord( i );
//			xoffset = xstart + toXCoord * i * cellsize;
		    for(int j = 0; j <  terrain.getGridHeight(); j ++)
		    {
//				yoffset = ystart + toYCoord * j * cellsize;
		    	double tileY = terrain.toYCoord( j );

		    	
		    	Tile<Bitmap> tile = terrain.createTileAt( i, j );
		    	Bitmap bitmap = tile.get();
		    	for(int x = 0; x < bitmap.getSize(); x ++)
			    	for(int y = 0; y < bitmap.getSize(); y ++)
			    	{
			    		if((tileX+x*terrain.getPixelSize())*(tileX+x*terrain.getPixelSize()) +
			    		   (tileY+y*terrain.getPixelSize())*(tileY+y*terrain.getPixelSize()) > 10000) // TODO: variety :)
			    			bitmap.put( new Color(0.0f, 0.0f, 0.0f, 1), x, y );
			    	}
		    	terrain.setModifiedByCoord( tileX, tileY );
		    }
		}

		ILook <GridyTerrainMap> look = new GridyTerrainLook(terrain, false, false);
		return new EntityShell<GridyTerrainMap>( terrain, null, look );
	}

}
