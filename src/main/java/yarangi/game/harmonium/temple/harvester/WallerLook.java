package yarangi.game.harmonium.temple.harvester;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import yar.quadraturin.IRenderingContext;
import yar.quadraturin.graphics.colors.Color;
import yar.quadraturin.graphics.lights.CircleLightLook;
import yarangi.game.harmonium.environment.resources.Resource;
import yarangi.math.Angles;

public class WallerLook extends CircleLightLook <Waller>
{

	boolean showRadii = false;

	public WallerLook(int size)
	{
		super( size );
	}


	@Override
	public void render(Waller entity, IRenderingContext ctx)
	{
		GL2 gl = ctx.gl();
		
		float P = entity.getPort().getSaturation( Resource.Type.MATTER );
		this.setColor(new Color( 0, P, 0, 1.0f));
		super.render( entity, ctx );
		
		float x, y;
		if(showRadii)
		{

			gl.glPushAttrib( GL2.GL_ENABLE_BIT );
			gl.glEnable(GL.GL_BLEND);
			gl.glColor4f( 0f, 1f, 0f, 0.1f );
			gl.glBegin( GL.GL_LINE_STRIP );
			for(double a = 0; a <= Angles.TAU+0.001; a += Angles.PI_div_40)
			{
				x = (float)(entity.getTerrainSensor().getRadius() * Math.cos( a ));
				y = (float)(entity.getTerrainSensor().getRadius() * Math.sin( a ));
				gl.glVertex2f( x, y );
			}
			gl.glEnd();
			gl.glPopAttrib();
		}
		gl.glBegin( GL2.GL_POLYGON );
		gl.glColor4f( 0f, 1f, 0f, 0.3f );
		for(double a = 0; a <= Angles.TAU; a += Angles.PI_div_20)
		{
			x = (float)(entity.getArea().getMaxRadius() * Math.cos( a ));
			y = (float)(entity.getArea().getMaxRadius() * Math.sin( a ));
			gl.glVertex2f( x, y );
		}
		gl.glEnd();
		
	/*	ErrodingBehavior beh = (ErrodingBehavior)entity.getBehavior();
		Tile tile = beh.getErrodedTile();
		Vector2D c = entity.getArea().getRefPoint();
		if(tile != null) {
			gl.glBegin( GL.GL_LINE_STRIP );
			gl.glVertex2f( (float)(tile.getMinX()-c.x()), (float)(tile.getMinY()-c.y()));
			gl.glVertex2f( (float)(tile.getMinX()-c.x()), (float)(tile.getMaxY()-c.y()));
			gl.glVertex2f( (float)(tile.getMaxX()-c.x()), (float)(tile.getMaxY()-c.y()));
			gl.glVertex2f( (float)(tile.getMaxX()-c.x()), (float)(tile.getMinY()-c.y()));
			gl.glVertex2f( (float)(tile.getMinX()-c.x()), (float)(tile.getMinY()-c.y()));
			gl.glEnd();
		}*/
			
	}


	@Override
	public float getPriority()
	{
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public boolean isOriented() { return true; }
}
