package yarangi.game.harmonium.environment.terrain;

import javax.media.opengl.GL;

import yarangi.graphics.grid.TileGridLook;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.terrain.Bitmap;
import yarangi.graphics.quadraturin.terrain.GridyTerrainMap;
import yarangi.graphics.textures.TextureUtils;
import yarangi.spatial.Tile;

public class GridyTerrainLook extends TileGridLook<Bitmap, GridyTerrainMap>
{

	@Override
	protected void renderTile(GL gl, Tile<Bitmap> tile, GridyTerrainMap grid)
	{
		Bitmap chunk = tile.get();
		if(chunk == null)
			return;
		
		if(chunk.hasTexture())
			TextureUtils.destroyTexture(gl, chunk.getTextureId());
		
		int texId = TextureUtils.createBitmapTexture2D( gl, grid.getCellSize(), grid.getCellSize(), chunk.getPixels(), false );
		chunk.setTextureId( texId );
			
		gl.glDisable( GL.GL_DEPTH_TEST );
		gl.glColor3f( 0,0,0 );
		gl.glBegin( GL.GL_QUADS );
		gl.glVertex2f( (float)tile.getMinX(), (float)tile.getMinY());
		gl.glVertex2f( (float)tile.getMaxX(), (float)tile.getMinY());
		gl.glVertex2f( (float)tile.getMaxX(), (float)tile.getMaxY());
		gl.glVertex2f( (float)tile.getMinX(), (float)tile.getMaxY());
		gl.glEnd();
		
		
		gl.glBindTexture( GL.GL_TEXTURE_2D, chunk.getTextureId() );
		
		// disabling filtering for tile texture to avoid  
		gl.glTexParameteri( GL.GL_TEXTURE_2D,  GL.GL_TEXTURE_MIN_FILTER,  GL.GL_NEAREST);
		gl.glTexParameteri( GL.GL_TEXTURE_2D,  GL.GL_TEXTURE_MAG_FILTER,  GL.GL_NEAREST);
		
		gl.glColor3f( 0,0,0 );
		gl.glBegin( GL.GL_QUADS );
//		System.out.println(cell.getMinX() + " : " + cell.getMaxX());
			gl.glTexCoord2f( 0, 0 ); gl.glVertex2f( (float)tile.getMinX(), (float)tile.getMinY());
			gl.glTexCoord2f( 1, 0 ); gl.glVertex2f( (float)tile.getMaxX(), (float)tile.getMinY());
			gl.glTexCoord2f( 1, 1 ); gl.glVertex2f( (float)tile.getMaxX(), (float)tile.getMaxY());
			gl.glTexCoord2f( 0, 1 ); gl.glVertex2f( (float)tile.getMinX(), (float)tile.getMaxY());
		gl.glEnd();
		
		gl.glBindTexture( GL.GL_TEXTURE_2D, 0 );
		gl.glEnable( GL.GL_DEPTH_TEST );
	}
	@Override
	public IVeil getVeil() { return IVeil.ORIENTING; }
	
}
