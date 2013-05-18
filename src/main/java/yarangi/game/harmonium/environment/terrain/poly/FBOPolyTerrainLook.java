package yarangi.game.harmonium.environment.terrain.poly;

import java.awt.Point;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import yar.quadraturin.IRenderingContext;
import yar.quadraturin.IVeil;
import yar.quadraturin.graphics.grid.TileGridLook;
import yar.quadraturin.terrain.ITilePoly;
import yar.quadraturin.terrain.MultilayerTilePoly;
import yar.quadraturin.terrain.PolygonGrid;
import yarangi.spatial.Tile;

import com.seisw.util.geom.Poly;

public class FBOPolyTerrainLook extends TileGridLook<ITilePoly, PolygonGrid>
{
	private final int layers;
	private IVeil veil;

	public FBOPolyTerrainLook(PolygonGrid grid, boolean depthtest, boolean blend, int layers)
	{
		super(grid, depthtest, blend);
		this.layers = layers;
	}

	@Override
	public IVeil getVeil() { return veil; }

	@Override
	public boolean isOriented() { return true; }
	
	@Override
	public void init(IRenderingContext ctx)
	{
		super.init( ctx );
//		veil = ctx.getPlugin( BlurVeil.NAME );
	}

	@Override
	protected void renderTile(IRenderingContext context, Tile<ITilePoly> tile, PolygonGrid grid, int scale)
	{
		GL2 gl = context.gl();
		// 
//		for(Poly poly : tile.get().getPolys())
//			renderPoly( gl, poly );
		gl.glDisable( GL.GL_DEPTH_TEST );
		gl.glDisable( GL.GL_BLEND );
		
		gl.glColor4f( 0,0,0,0 );
		gl.glBegin( GL2.GL_QUADS );
			gl.glVertex2f( (float)tile.getMinX(), (float)tile.getMinY());
			gl.glVertex2f( (float)tile.getMaxX(), (float)tile.getMinY());
			gl.glVertex2f( (float)tile.getMaxX(), (float)tile.getMaxY());
			gl.glVertex2f( (float)tile.getMinX(), (float)tile.getMaxY());
		gl.glEnd();		
		
		MultilayerTilePoly p = (MultilayerTilePoly) tile.get();
		Poly [] poly = p.getPolys();
		float gradient;
		if(poly[0] == null)
			return;
		for(int idx = 0; idx < poly.length; idx ++) {
			gradient = (idx)/(float)layers;
			gl.glColor4f((poly.length-idx)*0.8f, 0, 0, 1f);
//			gl.glColor4f(gradient, gradient, gradient, 1);
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
	
	private void renderPoly(GL2 gl, Poly poly)
	{
		gl.glBegin( GL2.GL_POLYGON );
		for(int idx = 0; idx < poly.getNumPoints(); idx ++)
			gl.glVertex2f((float)poly.getX( idx ), (float)poly.getY( idx ));
		gl.glVertex2f((float)poly.getX( 0 ), (float)poly.getY( 0 ));
		gl.glEnd();
	}

	@Override
	protected Point getFBODimensions(IRenderingContext context, PolygonGrid grid)
	{
		return new Point(2048, 2048);
	}

	@Override
	public float getPriority()
	{
		return 0;
	}

}
