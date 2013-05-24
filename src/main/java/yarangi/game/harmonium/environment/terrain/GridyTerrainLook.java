package yarangi.game.harmonium.environment.terrain;

import java.awt.Point;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import yar.quadraturin.IRenderingContext;
import yar.quadraturin.IVeil;
import yar.quadraturin.graphics.grid.TileGridLook;
import yar.quadraturin.graphics.textures.TextureUtils;
import yar.quadraturin.terrain.Bitmap;
import yar.quadraturin.terrain.GridyTerrainMap;
import yarangi.math.BitUtils;
import yarangi.spatial.Tile;

public class GridyTerrainLook extends TileGridLook<Bitmap, GridyTerrainMap>
{

	
	public GridyTerrainLook(GridyTerrainMap grid, boolean depthtest, boolean blend)
	{
		super( grid, depthtest, blend );
	}

	@Override
	public void init(IRenderingContext ctx)
	{
		super.init( ctx );
	}
	
	
	@Override
	protected void renderTile(IRenderingContext context, Tile<Bitmap> tile, GridyTerrainMap grid, int scale)
	{
		GL2 gl = context.gl();
		Bitmap chunk = tile.get();
		if(chunk == null)
			return;
		
		if(chunk.hasTexture())
			TextureUtils.destroyTexture(gl, chunk.getTextureId());
		chunk.setTextureId( TextureUtils.createBitmapTexture2D( gl, chunk.getSize(), chunk.getSize(), chunk.getPixels(), false ) );
			
		gl.glPushAttrib( GL2.GL_ENABLE_BIT );
		gl.glDisable( GL.GL_DEPTH_TEST );
		gl.glDisable( GL.GL_BLEND );
		gl.glColor3f( 0,0,0 );
		gl.glBegin( GL2.GL_QUADS );
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
		gl.glBegin( GL2.GL_QUADS );
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
