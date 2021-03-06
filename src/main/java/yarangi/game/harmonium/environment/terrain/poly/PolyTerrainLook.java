package yarangi.game.harmonium.environment.terrain.poly;

import javax.media.opengl.GL2;

import yar.quadraturin.IVeil;
import yar.quadraturin.graphics.grid.PolyGridLook;
import yar.quadraturin.terrain.ITilePoly;
import yar.quadraturin.terrain.PolygonGrid;
import yarangi.spatial.Tile;

import com.seisw.util.geom.Poly;

public class PolyTerrainLook extends PolyGridLook <ITilePoly, PolygonGrid>
{


	public PolyTerrainLook(PolygonGrid grid)
	{
		super( grid, false, true );
		// TODO Auto-generated constructor stub
	}
	@Override
	public IVeil getVeil() { return null; }

	@Override
	public boolean isOriented() { return true; }
	
	@Override
	protected void renderTile(GL2 gl, Tile<ITilePoly> tile, PolygonGrid grid, int scale)
	{
		// 
//		for(Poly poly : tile.get().getPolys())
//			renderPoly( gl, poly );
		Poly poly = tile.get().getPoly();
		if(poly == null || tile.get().isEmpty() )
			return;
		gl.glColor4f(1f, 1f, 0.1f, 0.05f);
//		renderPoly( gl, poly );
//		renderPoly( gl, poly );
		for(int idx = 0; idx < poly.getNumInnerPoly(); idx ++)
			renderPoly( gl, tile.get(), poly.getInnerPoly( idx ) );
	}
	
	private void renderPoly(GL2 gl, ITilePoly tile, Poly poly)
	{

		gl.glBegin( GL2.GL_POLYGON );

		for(int idx = 0; idx < poly.getNumPoints(); idx ++)
			gl.glVertex2f((float)poly.getX( idx ), (float)poly.getY( idx ));
		
	
		gl.glVertex2f((float)poly.getX( 0 ), (float)poly.getY( 0 ));
		gl.glEnd();
	}
}
