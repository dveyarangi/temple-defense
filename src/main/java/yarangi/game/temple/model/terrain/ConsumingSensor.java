package yarangi.game.temple.model.terrain;

import yarangi.graphics.colors.Color;
import yarangi.graphics.quadraturin.terrain.Cell;
import yarangi.graphics.quadraturin.terrain.GridyTerrainMap;
import yarangi.spatial.IAreaChunk;
import yarangi.spatial.ISpatialSensor;

public class ConsumingSensor implements ISpatialSensor <Tile>
{
	double ox, oy, radiusSquare;
	boolean draw = false;
	GridyTerrainMap<Tile> terrain;
	public ConsumingSensor (GridyTerrainMap<Tile> terrain, boolean draw, double ox, double oy, double radiusSquare)
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
	public boolean objectFound(IAreaChunk chunk, Tile tile)
	{
//		System.out.println(chunk + " : " + tile);
		int pixelsBefore = tile.getPixelCount();
		if(pixelsBefore == 0 && !draw)
			return false;
		for(int i = 0; i < tile.getSize(); i ++)
			for(int j = 0; j < tile.getSize(); j ++)
			{
				double dx = chunk.getMinX() + i * tile.getPixelSize() - ox;
				double dy = chunk.getMinY() + j * tile.getPixelSize() - oy;
				if((dx*dx) + (dy*dy) > radiusSquare)
					continue;

				if(draw)
					((Tile)tile).put((new Color(0,1,1,1)), i, j );
				else
					tile.remove( i, j );
			}
		
		if(tile.getPixelCount() != pixelsBefore)
			terrain.setModified( (Cell<Tile>)chunk );
		
		return false;
	}

	@Override
	public void clear() { }
	
}
