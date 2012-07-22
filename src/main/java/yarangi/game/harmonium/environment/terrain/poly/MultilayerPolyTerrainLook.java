package yarangi.game.harmonium.environment.terrain.poly;

import javax.media.opengl.GL;

import yarangi.graphics.grid.PolyGridLook;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.terrain.PolygonTerrainMap;
import yarangi.graphics.quadraturin.terrain.MultilayerTilePoly;
import yarangi.spatial.Tile;

import com.seisw.util.geom.Poly;

public class MultilayerPolyTerrainLook extends PolyGridLook<MultilayerTilePoly, PolygonTerrainMap>
{


	public MultilayerPolyTerrainLook(boolean depthtest, boolean blend)
	{
		super( depthtest, blend );
		// TODO Auto-generated constructor stub
	}

	@Override
	public IVeil getVeil()
	{
		return null;
	}

	@Override
	protected void renderTile(GL gl, Tile<MultilayerTilePoly> tile, PolygonTerrainMap grid, int scale)
	{
		// 
//		for(Poly poly : tile.get().getPolys())
//			renderPoly( gl, poly );
		Poly [] poly = tile.get().getPoly();
		if(poly[0] == null)
			return;
		for(int idx = 0; idx < poly.length; idx ++) {
			gl.glColor4f(idx*0.2f, (poly.length-idx)*0.2f, 0.0f, 0.5f);
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
		gl.glBegin( GL.GL_LINE_STRIP );
		for(int idx = 0; idx < poly.getNumPoints(); idx ++)
			gl.glVertex2f((float)poly.getX( idx ), (float)poly.getY( idx ));
		gl.glVertex2f((float)poly.getX( 0 ), (float)poly.getY( 0 ));
		gl.glEnd();
	}
}
