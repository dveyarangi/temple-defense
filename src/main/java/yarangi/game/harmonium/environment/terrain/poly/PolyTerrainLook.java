package yarangi.game.harmonium.environment.terrain.poly;

import javax.media.opengl.GL;

import yarangi.graphics.grid.PolyGridLook;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.terrain.PolygonTerrainMap;
import yarangi.graphics.quadraturin.terrain.TilePoly;
import yarangi.spatial.Tile;

import com.seisw.util.geom.Poly;

public class PolyTerrainLook extends PolyGridLook<TilePoly, PolygonTerrainMap>
{
	public PolyTerrainLook()
	{
	}

	@Override
	public IVeil getVeil()
	{
		return null;
	}

	@Override
	protected void renderTile(GL gl, Tile<TilePoly> tile, PolygonTerrainMap grid, int scale)
	{
		// 
//		for(Poly poly : tile.get().getPolys())
//			renderPoly( gl, poly );
		Poly poly = tile.get().getPoly();
		if(poly == null)
			return;
		renderPoly( gl, poly );
		for(int idx = 0; idx < poly.getNumInnerPoly(); idx ++)
		{
			// TODO: recursion may be needed:
			renderPoly(gl, poly.getInnerPoly( idx ));
		}
	}
	
	private void renderPoly(GL gl, Poly poly)
	{
		gl.glColor4f(0.2f, 0.1f, 0.5f, 0.5f);
		gl.glBegin( GL.GL_LINE_STRIP );
		for(int idx = 0; idx < poly.getNumPoints(); idx ++)
			gl.glVertex2f((float)poly.getX( idx ), (float)poly.getY( idx ));
		gl.glVertex2f((float)poly.getX( 0 ), (float)poly.getY( 0 ));
		gl.glEnd();
	}

}
