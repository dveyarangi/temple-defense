package yarangi.game.temple.model.terrain;

import java.nio.IntBuffer;

import javax.media.opengl.GL;

import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.graphics.quadraturin.terrain.GridyTerrainMap;
import yarangi.graphics.textures.TextureUtils;
import yarangi.graphics.textures.TextureUtils.FBOHandle;
import yarangi.math.BitUtils;

public class GridyTerrainLook implements Look <GridyTerrainMap<TerrainChunk>>
{
	private FBOHandle fbo;
	@Override
	public void init(GL gl, GridyTerrainMap<TerrainChunk> entity, IRenderingContext context)
	{
		// rounding texture size to power of 2:
		int textureWidth  = BitUtils.po2Ceiling((int)(entity.getMaxX()-entity.getMinX()));
		int textureHeight = BitUtils.po2Ceiling((int)(entity.getMaxY()-entity.getMinY()));
		int texture = TextureUtils.createEmptyTexture2D( gl, textureWidth, textureHeight, false );
		
		fbo = TextureUtils.createFBO( gl, texture, TextureUtils.ILLEGAL_ID );
		gl.glBindFramebufferEXT(GL.GL_FRAMEBUFFER_EXT, fbo.getFboId());
		
		// retrieving viewport:
		IntBuffer viewport = IntBuffer.allocate(4);
		gl.glGetIntegerv(GL.GL_VIEWPORT, viewport);
		
		// transforming the rendering plane to fit the terrain grid:
		gl.glPushAttrib(GL.GL_VIEWPORT_BIT | GL.GL_ENABLE_BIT);	
		gl.glMatrixMode(GL.GL_MODELVIEW); gl.glPushMatrix();  gl.glLoadIdentity();
		gl.glMatrixMode(GL.GL_PROJECTION); gl.glPushMatrix(); gl.glLoadIdentity();
		gl.glViewport(0,0,textureWidth, textureHeight);
//		gl.glOrtho(-viewport.get(2)/2, viewport.get(2)/2, -viewport.get(3)/2, viewport.get(3)/2, -1, 1);
		gl.glOrtho(entity.getMinX(), entity.getMaxX(), 
	               entity.getMinY(), entity.getMaxY(), -1, 1);
		
		TerrainChunk chunk;
		float cellsize = entity.getCellSize();
		float minx = entity.getMinX();
		float maxx = entity.getMaxX();
		float miny = entity.getMinY();
		float maxy = entity.getMaxY();
		
		for(float x = minx; x < maxx; x += cellsize)
			for(float y = miny; y < maxy; y += cellsize)
			{
				if(entity.getCell(x, y) == null)
					continue;
				chunk = entity.getCell(x, y).getProperties();
				if(chunk == null)
					continue;
//				if(chunk.getColor().isVoid())
//					continue;
				int texId = TextureUtils.createBitmapTexture2D( gl, (int)cellsize, (int)cellsize, chunk.getPixels(), false );
				
				gl.glBindTexture( GL.GL_TEXTURE_2D, texId );
				gl.glBegin( GL.GL_QUADS );
					gl.glTexCoord2f( 0, 0 ); gl.glVertex2f( x, y);
					gl.glTexCoord2f( 1, 0 ); gl.glVertex2f( x+cellsize, y);
					gl.glTexCoord2f( 1, 1 ); gl.glVertex2f( x+cellsize, y+cellsize);
					gl.glTexCoord2f( 0, 1 ); gl.glVertex2f( x, y+cellsize);
				gl.glEnd();
				
				gl.glBindTexture( GL.GL_TEXTURE_2D, 0 );
			}
		gl.glBindFramebufferEXT(GL.GL_FRAMEBUFFER_EXT, 0);
		
		gl.glMatrixMode(GL.GL_PROJECTION); gl.glPopMatrix();
		gl.glMatrixMode(GL.GL_MODELVIEW); gl.glPopMatrix(); 
		gl.glPopAttrib();	

	}

	@Override
	public void render(GL gl, double time, GridyTerrainMap<TerrainChunk> entity, IRenderingContext context)
	{
		float minx = entity.getMinX();
		float maxx = entity.getMaxX();
		float miny = entity.getMinY();
		float maxy = entity.getMaxY();
		
		gl.glBindTexture( GL.GL_TEXTURE_2D, fbo.getTextureId() );
		gl.glBegin(GL.GL_QUADS);
		gl.glTexCoord2f(0,0); gl.glVertex2f( minx, miny );
		gl.glTexCoord2f(0,1); gl.glVertex2f( minx, maxy );
		gl.glTexCoord2f(1,1); gl.glVertex2f( maxx, maxy );
		gl.glTexCoord2f(1,0); gl.glVertex2f( maxx, miny );
		gl.glEnd();
		gl.glBindTexture( GL.GL_TEXTURE_2D, 0 );
/*		float cellsize = entity.getCellSize();
		for(float x = minx; x < maxx; x += cellsize)
		{
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex2f( x, entity.getTerrainMinY() );
		gl.glVertex2f( x, entity.getTerrainMaxY() );
		gl.glEnd();
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex2f( entity.getTerrainMinX(), x );
		gl.glVertex2f( entity.getTerrainMaxX(), x );
		gl.glEnd();
		}*/
	}

	@Override
	public void destroy(GL gl, GridyTerrainMap<TerrainChunk> entity, IRenderingContext context)
	{
		TextureUtils.destroyFBO( gl, fbo );
	}

	@Override
	public boolean isCastsShadow()
	{
		return false;
	}
	@Override
	public float getPriority() { return 1f; }

}
