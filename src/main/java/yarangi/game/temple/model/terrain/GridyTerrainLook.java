package yarangi.game.temple.model.terrain;

import java.util.List;

import javax.media.opengl.GL;

import yarangi.graphics.colors.Color;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.graphics.quadraturin.terrain.Cell;
import yarangi.graphics.quadraturin.terrain.GridyTerrainMap;
import yarangi.graphics.quadraturin.terrain.Tile;
import yarangi.graphics.textures.TextureUtils;
import yarangi.graphics.textures.TextureUtils.FBOHandle;
import yarangi.math.BitUtils;
import yarangi.spatial.IGridListener;

public class GridyTerrainLook implements Look <GridyTerrainMap<Tile, Color>>, IGridListener<Cell<Tile>>
{
	private FBOHandle fbo;
	
	private int gridTextureWidth, gridTextureHeight;
	
	private List <Cell<Tile>> pendingCells;
	
	@Override
	public void init(GL gl, GridyTerrainMap<Tile, Color> grid, IRenderingContext context)
	{
		// rounding texture size to power of 2:
		gridTextureWidth  = BitUtils.po2Ceiling((int)(grid.getMaxX()-grid.getMinX()));
		gridTextureHeight = BitUtils.po2Ceiling((int)(grid.getMaxY()-grid.getMinY()));
		int texture = TextureUtils.createEmptyTexture2D( gl, gridTextureWidth, gridTextureHeight, false );
		
		fbo = TextureUtils.createFBO( gl, texture, TextureUtils.ILLEGAL_ID );
		
/*		beginFrameBufferSpace( gl, grid );
		
		Tile chunk;
		float cellsize = grid.getCellSize();
		float minx = grid.getMinX();
		float maxx = grid.getMaxX();
		float miny = grid.getMinY();
		float maxy = grid.getMaxY();
		
		for(float x = minx; x < maxx; x += cellsize)
			for(float y = miny; y < maxy; y += cellsize)
			{
				if(grid.getCell(x, y) == null)
					continue;
				chunk = grid.getCell(x, y).getProperties();
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
		endFrameBufferSpace( gl );*/
	}

	@Override
	public void render(GL gl, double time, GridyTerrainMap<Tile, Color> grid, IRenderingContext context)
	{
		if(pendingCells != null && pendingCells.size() > 0)
		{
			Tile chunk;
			float cellsize = grid.getCellSize();
//			System.out.println("modifying!!!");
			// redrawing changed tiles:
			beginFrameBufferSpace( gl, grid );
			for(Cell <Tile> cell : pendingCells)
			{
				chunk = cell.getProperties();
				if(chunk == null)
					continue;
				
				if(chunk.hasTexture())
					TextureUtils.destroyTexture(gl, chunk.getTextureId());
				
				int texId = TextureUtils.createBitmapTexture2D( gl, (int)cellsize, (int)cellsize, chunk.getPixels(), false );
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
				gl.glColor3f( 0,0,0 );
				gl.glBegin( GL.GL_QUADS );
//				System.out.println(cell.getMinX() + " : " + cell.getMaxX());
					gl.glTexCoord2f( 0, 0 ); gl.glVertex2f( (float)cell.getMinX(), (float)cell.getMinY());
					gl.glTexCoord2f( 1, 0 ); gl.glVertex2f( (float)cell.getMaxX(), (float)cell.getMinY());
					gl.glTexCoord2f( 1, 1 ); gl.glVertex2f( (float)cell.getMaxX(), (float)cell.getMaxY());
					gl.glTexCoord2f( 0, 1 ); gl.glVertex2f( (float)cell.getMinX(), (float)cell.getMaxY());
				gl.glEnd();
				
				gl.glBindTexture( GL.GL_TEXTURE_2D, 0 );
				gl.glEnable( GL.GL_DEPTH_TEST );
				
			}
			
			endFrameBufferSpace( gl );
			
			pendingCells = null;
		}
		
		
		float minx = grid.getMinX();
		float maxx = grid.getMaxX();
		float miny = grid.getMinY();
		float maxy = grid.getMaxY();
		
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
	public void destroy(GL gl, GridyTerrainMap<Tile, Color> entity, IRenderingContext context)
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

	
	@Override
	public void cellsModified(List<Cell<Tile>> cells)
	{
		pendingCells = cells;
	}

	private void beginFrameBufferSpace(GL gl, GridyTerrainMap<Tile, Color> grid)
	{
		gl.glBindFramebufferEXT(GL.GL_FRAMEBUFFER_EXT, fbo.getFboId());
		
		// retrieving viewport:
		
		// transforming the rendering plane to fit the terrain grid:
		gl.glPushAttrib(GL.GL_VIEWPORT_BIT | GL.GL_ENABLE_BIT);	
		gl.glMatrixMode(GL.GL_MODELVIEW); gl.glPushMatrix();  gl.glLoadIdentity();
		gl.glMatrixMode(GL.GL_PROJECTION); gl.glPushMatrix(); gl.glLoadIdentity();
		gl.glViewport(0,0,gridTextureWidth, gridTextureHeight);
		gl.glOrtho(grid.getMinX(), grid.getMaxX(), 
				   grid.getMinY(), grid.getMaxY(), -1, 1);

	}
	private void endFrameBufferSpace(GL gl)
	{
		gl.glBindFramebufferEXT(GL.GL_FRAMEBUFFER_EXT, 0);
		
		gl.glMatrixMode(GL.GL_PROJECTION); gl.glPopMatrix();
		gl.glMatrixMode(GL.GL_MODELVIEW); gl.glPopMatrix(); 
		gl.glPopAttrib();	
	}
}
