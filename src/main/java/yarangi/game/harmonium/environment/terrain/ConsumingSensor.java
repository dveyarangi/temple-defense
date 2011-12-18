package yarangi.game.harmonium.environment.terrain;

import yarangi.graphics.colors.Color;
import yarangi.graphics.quadraturin.terrain.Bitmap;
import yarangi.graphics.quadraturin.terrain.GridyTerrainMap;
import yarangi.spatial.ISpatialSensor;
import yarangi.spatial.Tile;

public class ConsumingSensor implements ISpatialSensor <Tile <Bitmap>, Bitmap>
{
	double ox, oy, radiusSquare;
	boolean draw = false;
	GridyTerrainMap terrain;
	public ConsumingSensor (GridyTerrainMap terrain, boolean draw, double ox, double oy, double radiusSquare)
	{
		this.terrain = terrain;
		this.ox = ox;
		this.oy = oy;
		this.radiusSquare = radiusSquare;
		this.draw = draw;
	}
	/**
	 * @param chunk - current cell
	 */
	@Override
	public boolean objectFound(Tile <Bitmap> tile, Bitmap bitmap)
	{
//		System.out.println(chunk + " : " + tile);
		int pixelsBefore = bitmap.getPixelCount();
		if(pixelsBefore == 0 && !draw)
			return false;
/*		for(int i = 0; i < bitmap.getSize(); i ++)
			for(int j = 0; j < bitmap.getSize(); j ++)
			{
				double dx = tile.getMinX() + i * bitmap.getPixelSize() - ox;
				double dy = tile.getMinY() + j * bitmap.getPixelSize() - oy;
				if((dx*dx) + (dy*dy) > radiusSquare)
					continue;

				if(draw)
					bitmap.put((new Color(0,1,1,1)), i, j );
				else
					bitmap.remove( i, j );
			}*/
		
		if(bitmap.getPixelCount() != pixelsBefore)
			terrain.setModified( tile );
		
		return false;
	}

	@Override
	public void clear() { }
	
}
