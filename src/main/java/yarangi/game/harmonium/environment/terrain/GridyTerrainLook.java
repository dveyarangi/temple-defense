package yarangi.game.harmonium.environment.terrain;

import java.awt.Point;

import javax.media.opengl.GL;

import yarangi.graphics.grid.TileGridLook;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.terrain.Bitmap;
import yarangi.graphics.quadraturin.terrain.GridyTerrainMap;
import yarangi.graphics.textures.TextureUtils;
import yarangi.math.BitUtils;
import yarangi.spatial.Tile;

public class GridyTerrainLook extends TileGridLook<Bitmap, GridyTerrainMap>
{

	
	public GridyTerrainLook(GridyTerrainMap grid, boolean depthtest, boolean blend)
	{
		super( grid, depthtest, blend );
	}

	@Override
	public void init(GL gl, IRenderingContext context)
	{
		super.init( gl, context );
	}
	
	
	@Override
	protected void renderTile(GL gl, IRenderingContext context, Tile<Bitmap> tile, GridyTerrainMap grid, int scale)
	{
		Bitmap chunk = tile.get();
		if(chunk == null)
			return;
		
		if(chunk.hasTexture())
			TextureUtils.destroyTexture(gl, chunk.getTextureId());
		chunk.setTextureId( TextureUtils.createBitmapTexture2D( gl, chunk.getSize(), chunk.getSize(), chunk.getPixels(), false ) );
			
		gl.glPushAttrib( GL.GL_ENABLE_BIT );
		gl.glDisable( GL.GL_DEPTH_TEST );
		gl.glDisable( GL.GL_BLEND );
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
		
		gl.glColor4f( 1,1,1,0 );
		gl.glBegin( GL.GL_QUADS );
//		System.out.println(cell.getMinX() + " : " + cell.getMaxX());
			gl.glTexCoord2f( 0, 0 ); gl.glVertex2f( (float)tile.getMinX(), (float)tile.getMinY());
			gl.glTexCoord2f( 0, 1 ); gl.glVertex2f( (float)tile.getMinX(), (float)tile.getMaxY());
			gl.glTexCoord2f( 1, 1 ); gl.glVertex2f( (float)tile.getMaxX(), (float)tile.getMaxY());
			gl.glTexCoord2f( 1, 0 ); gl.glVertex2f( (float)tile.getMaxX(), (float)tile.getMinY());
		gl.glEnd();
		
		gl.glBindTexture( GL.GL_TEXTURE_2D, 0 );
		
		gl.glPopAttrib();
		
	}
	
	@Override
	protected Point getFBODimensions(IRenderingContext context, GridyTerrainMap grid)
	{
		return new Point(
				BitUtils.po2Ceiling(grid.getGridWidth()  * grid.getBitmapSize()), 
				BitUtils.po2Ceiling(grid.getGridHeight() * grid.getBitmapSize())
			);
	}


	@Override
	public float getPriority()
	{
		return 0;
	}
	@Override
	public IVeil getVeil() { return null; }

	@Override
	public boolean isOriented() { return true; }	
}
