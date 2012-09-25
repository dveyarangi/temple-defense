package yarangi.game.harmonium.environment.terrain.poly;

import javax.media.opengl.GL;

import yarangi.graphics.grid.PolyGridLook;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.terrain.PolygonGrid;
import yarangi.graphics.quadraturin.terrain.TilePoly;
import yarangi.spatial.Tile;

import com.seisw.util.geom.Poly;

public class PolyTerrainLook extends PolyGridLook<TilePoly, PolygonGrid<TilePoly>>
{


	public PolyTerrainLook(PolygonGrid<TilePoly> grid)
	{
		super( grid, false, true );
		// TODO Auto-generated constructor stub
	}
	@Override
	public IVeil getVeil() { return null; }

	@Override
	public boolean isOriented() { return true; }
	
	@Override
	protected void renderTile(GL gl, Tile<TilePoly> tile, PolygonGrid<TilePoly> grid, int scale)
	{
		// 
//		for(Poly poly : tile.get().getPolys())
//			renderPoly( gl, poly );
		Poly poly = tile.get().getPoly();
		if(poly == null || poly.isEmpty())
			return;
		gl.glColor4f(1f, 1f, 0.1f, 0.25f);
//		renderPoly( gl, poly );
		renderPoly( gl, poly );
//		for(int idx = 0; idx < poly.getNumInnerPoly(); idx ++)
//			renderPoly( gl, poly.getInnerPoly( idx ) );
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
