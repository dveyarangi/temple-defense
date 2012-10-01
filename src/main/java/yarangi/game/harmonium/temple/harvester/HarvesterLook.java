package yarangi.game.harmonium.temple.harvester;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import yarangi.graphics.colors.Color;
import yarangi.graphics.lights.CircleLightLook;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.objects.ILook;
import yarangi.math.Angles;
import yarangi.game.harmonium.environment.resources.Resource;

public class HarvesterLook extends CircleLightLook <Harvester>
{


	public HarvesterLook(int size)
	{
		super( size );
	}


	@Override
	public void render(GL gl1, Harvester entity, IRenderingContext context)
	{
		GL2 gl = gl1.getGL2();
		float P = entity.getPort().getSaturation( Resource.Type.MATTER );
		this.setColor(new Color( 1f, 0, 0, 1.0f));
		
		super.render( gl, entity, context );		

		float x, y;
		gl.glPushAttrib( GL2.GL_ENABLE_BIT );
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
			
		gl.glBegin( GL2.GL_POLYGON );
		gl.glColor4f( 1f, 0f, 0f, 0.3f );
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
	public boolean isOriented() { return true; }
}
