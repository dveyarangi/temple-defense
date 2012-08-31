package yarangi.game.harmonium.temple.harvester;

import javax.media.opengl.GL;

import yarangi.game.harmonium.environment.resources.Resource;
import yarangi.graphics.colors.Color;
import yarangi.graphics.lights.CircleLightLook;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.objects.ILook;
import yarangi.math.Angles;

public class WallerLook extends CircleLightLook <Waller>
{



	@Override
	public void render(GL gl, Waller entity, IRenderingContext context)
	{
		float P = entity.getPort().getSaturation( Resource.Type.MATTER );
		this.setColor(new Color( 0, P, 0, 1.0f));
		super.render( gl, entity, context );
		
		float x, y;
		gl.glPushAttrib( GL.GL_ENABLE_BIT );
		gl.glEnable(GL.GL_BLEND);
		gl.glColor4f( 0f, 1f, 0f, 0.1f );
		gl.glBegin( GL.GL_LINE_STRIP );
		for(double a = 0; a <= Angles.PI_2+0.001; a += Angles.PI_div_40)
		{
			x = (float)(entity.getTerrainSensor().getRadius() * Math.cos( a ));
			y = (float)(entity.getTerrainSensor().getRadius() * Math.sin( a ));
			gl.glVertex2f( x, y );
		}
		gl.glEnd();
			
		gl.glBegin( GL.GL_POLYGON );
		gl.glColor4f( 0f, 1f, 0f, 0.3f );
		for(double a = 0; a <= Angles.PI_2; a += Angles.PI_div_20)
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
			
		gl.glPopAttrib();
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
