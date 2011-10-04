package yarangi.game.temple.model.terrain;

import javax.media.opengl.GL;

import yarangi.graphics.colors.Color;
import yarangi.graphics.grid.TileGridLook;
import yarangi.graphics.quadraturin.terrain.Cell;
import yarangi.graphics.quadraturin.terrain.GridyTerrainMap;
import yarangi.graphics.quadraturin.terrain.Tile;
import yarangi.graphics.textures.TextureUtils;

public class GridyTerrainLook extends TileGridLook<Tile, GridyTerrainMap<Tile, Color>>
{

	@Override
	protected void renderTile(GL gl, Cell<Tile> cell, GridyTerrainMap<Tile, Color> grid)
	{
		Tile chunk = cell.getProperties();
		if(chunk == null)
			return;
		
		if(chunk.hasTexture())
			TextureUtils.destroyTexture(gl, chunk.getTextureId());
		
		int texId = TextureUtils.createBitmapTexture2D( gl, grid.getCellSize(), grid.getCellSize(), chunk.getPixels(), false );
		chunk.setTextureId( texId );
			
		gl.glDisable( GL.GL_DEPTH_TEST );
		gl.glColor3f( 0,0,0 );
		gl.glBegin( GL.GL_QUADS );
		gl.glVertex2f( (float)cell.getMinX(), (float)cell.getMinY());
		gl.glVertex2f( (float)cell.getMaxX(), (float)cell.getMinY());
		gl.glVertex2f( (float)cell.getMaxX(), (float)cell.getMaxY());
		gl.glVertex2f( (float)cell.getMinX(), (float)cell.getMaxY());
		gl.glEnd();
		
		
		gl.glBindTexture( GL.GL_TEXTURE_2D, chunk.getTextureId() );
		
		// disabling filtering for tile texture to avoid  
		gl.glTexParameteri( GL.GL_TEXTURE_2D,  GL.GL_TEXTURE_MIN_FILTER,  GL.GL_NEAREST);
		gl.glTexParameteri( GL.GL_TEXTURE_2D,  GL.GL_TEXTURE_MAG_FILTER,  GL.GL_NEAREST);
		
		gl.glColor3f( 0,0,0 );
		gl.glBegin( GL.GL_QUADS );
//		System.out.println(cell.getMinX() + " : " + cell.getMaxX());
			gl.glTexCoord2f( 0, 0 ); gl.glVertex2f( (float)cell.getMinX(), (float)cell.getMinY());
			gl.glTexCoord2f( 1, 0 ); gl.glVertex2f( (float)cell.getMaxX(), (float)cell.getMinY());
			gl.glTexCoord2f( 1, 1 ); gl.glVertex2f( (float)cell.getMaxX(), (float)cell.getMaxY());
			gl.glTexCoord2f( 0, 1 ); gl.glVertex2f( (float)cell.getMinX(), (float)cell.getMaxY());
		gl.glEnd();
		
		gl.glBindTexture( GL.GL_TEXTURE_2D, 0 );
		gl.glEnable( GL.GL_DEPTH_TEST );
	}
	
}
