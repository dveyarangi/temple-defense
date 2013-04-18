package yarangi.game.harmonium.environment.terrain.poly;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import yarangi.graphics.grid.PolyGridLook;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.terrain.ITilePoly;
import yarangi.graphics.quadraturin.terrain.MultilayerTilePoly;
import yarangi.graphics.quadraturin.terrain.PolygonGrid;
import yarangi.graphics.veils.BlurVeil;
import yarangi.spatial.Tile;

import com.seisw.util.geom.Poly;

public class MultilayerPolyTerrainLook extends PolyGridLook<ITilePoly, PolygonGrid>
{
	private IVeil veil;

	public MultilayerPolyTerrainLook(PolygonGrid grid, boolean depthtest, boolean blend)
	{
		super( grid, depthtest, blend );
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init(GL gl, IRenderingContext context)
	{
		super.init( gl, context );
		veil = context.getPlugin( BlurVeil.NAME );
	}

	@Override
	public IVeil getVeil() { return veil; }

	@Override
	public boolean isOriented() { return true; }
	
	@Override
	protected void renderTile(GL2 gl, Tile<ITilePoly> tile, PolygonGrid grid, int scale)
	{
		
		MultilayerTilePoly p = (MultilayerTilePoly) tile.get();

		// 
//		for(Poly poly : tile.get().getPolys())
//			renderPoly( gl, poly );
		Poly [] poly = p.getPolys();
		if(poly[0] == null)
			return;
		for(int idx = 0; idx < poly.length; idx ++) {
			gl.glColor4f(idx*0.2f, (poly.length-idx)*0.2f, (poly.length-idx)*0.2f, 0.5f);
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
		gl.glBegin( GL.GL_LINE_STRIP );
		for(int idx = 0; idx < poly.getNumPoints(); idx ++)
			gl.glVertex2f((float)poly.getX( idx ), (float)poly.getY( idx ));
		gl.glVertex2f((float)poly.getX( 0 ), (float)poly.getY( 0 ));
		gl.glEnd();
	}
}
