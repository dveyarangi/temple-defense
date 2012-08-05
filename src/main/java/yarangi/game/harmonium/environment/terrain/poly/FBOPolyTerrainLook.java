package yarangi.game.harmonium.environment.terrain.poly;

import java.awt.Point;

import javax.media.opengl.GL;

import yarangi.graphics.grid.TileGridLook;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.terrain.MultilayerTilePoly;
import yarangi.graphics.quadraturin.terrain.PolygonTerrainMap;
import yarangi.spatial.Tile;

import com.seisw.util.geom.Poly;

public class FBOPolyTerrainLook extends TileGridLook<MultilayerTilePoly, PolygonTerrainMap>
{
	private final int layers;
	
	public FBOPolyTerrainLook(boolean depthtest, boolean blend, int layers)
	{
		super(depthtest, blend);
		this.layers = layers;
	}

	@Override
	public IVeil getVeil() { return null; }

	@Override
	public boolean isOriented() { return true; }
	
	public void init(GL gl, Tile<MultilayerTilePoly> tile, PolygonTerrainMap grid, IRenderingContext context)
	{
		super.init( gl, grid, context );
	}

	@Override
	protected void renderTile(GL gl, IRenderingContext context, Tile<MultilayerTilePoly> tile, PolygonTerrainMap grid, int scale)
	{
		// 
//		for(Poly poly : tile.get().getPolys())
//			renderPoly( gl, poly );
		gl.glDisable( GL.GL_DEPTH_TEST );
		gl.glDisable( GL.GL_BLEND );
		
		gl.glColor4f( 0,0,0,0 );
		gl.glBegin( GL.GL_QUADS );
			gl.glVertex2f( (float)tile.getMinX(), (float)tile.getMinY());
			gl.glVertex2f( (float)tile.getMaxX(), (float)tile.getMinY());
			gl.glVertex2f( (float)tile.getMaxX(), (float)tile.getMaxY());
			gl.glVertex2f( (float)tile.getMinX(), (float)tile.getMaxY());
		gl.glEnd();		
		
		Poly [] poly = tile.get().getPoly();
		float gradient;
		if(poly[0] == null)
			return;
		for(int idx = 0; idx < poly.length; idx ++) {
			gradient = (idx)/(float)layers;
			gl.glColor4f(0.3f*gradient, 0.1f, gradient, gradient/2);
			if(poly[idx] == null || poly[idx].isEmpty())
				break;
			renderPoly( gl, poly[idx] );
			for(int pidx = 0; pidx < poly[idx].getNumInnerPoly(); pidx ++)
			{
				// TODO: recursion may be needed:
				renderPoly(gl, poly[idx].getInnerPoly( pidx ));
			}
		}
	}
	
	private void renderPoly(GL gl, Poly poly)
	{
		gl.glBegin( GL.GL_POLYGON );
		for(int idx = 0; idx < poly.getNumPoints(); idx ++)
			gl.glVertex2f((float)poly.getX( idx ), (float)poly.getY( idx ));
		gl.glVertex2f((float)poly.getX( 0 ), (float)poly.getY( 0 ));
		gl.glEnd();
	}

	@Override
	protected Point getFBODimensions(IRenderingContext context, PolygonTerrainMap grid)
	{
		return new Point(2048, 2048);
	}

	@Override
	public float getPriority()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
