package yarangi.game.harmonium.temple.weapons;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import yar.quadraturin.IRenderingContext;
import yar.quadraturin.IVeil;
import yar.quadraturin.Q;
import yar.quadraturin.graphics.colors.Color;
import yar.quadraturin.graphics.lights.CircleLightLook;
import yar.quadraturin.graphics.veils.IsoheightVeil;
import yarangi.game.harmonium.environment.resources.Resource;
import yarangi.math.Angles;
import yarangi.math.Vector2D;

public class MinigunGlowingLook extends CircleLightLook<Minigun>
{

	private IVeil veil;
	
	private boolean showRadii = false;
	
	public MinigunGlowingLook(int size)
	{
		super( size );
	}

	@Override
	public void init(IRenderingContext ctx)
	{
		super.init( ctx );
//		veil = context.<IsoheightVeil> getPlugin( IsoheightVeil.NAME );
		
		if(veil == null)
		{
			Q.rendering.warn( "Plugin [" + IsoheightVeil.NAME + "] requested by look [" + this.getClass() + "] is not available."  );
		}

	}


	@Override
	public void render(Minigun cannon, IRenderingContext ctx) 
	{
		GL2 gl = ctx.gl();
		
		Resource.Type type = cannon.getProps().getResourceType();
		float P = (float)(cannon.getPort().get( type ).getAmount() / cannon.getPort().getCapacity( type ));
//		this.setColor(new Color( (float)((1-P)/4+P*0.3),  (float)(P*0.3), (P),1));
//		this.setColor(new Color( P*0.2f+(1-P)*0.8f, 0.5f+0.5f*P, 1f*P, 1.0f));
		this.setColor(new Color( 0f, 0, P, 1.0f));
				
		super.render( cannon, ctx );		
		
		if(showRadii)
		{
			gl.glPushAttrib(GL2.GL_ENABLE_BIT);
			gl.glEnable(GL.GL_BLEND);
			gl.glColor4f( 0f, 1f, 0f, 0.2f );
			gl.glBegin( GL.GL_LINE_STRIP );
			float x, y;
			for(double a = 0; a <= Angles.TAU+0.001; a += Angles.PI_div_40)
			{
				x = (float)(cannon.getEntitySensor().getRadius() * Math.cos( a ));
				y = (float)(cannon.getEntitySensor().getRadius() * Math.sin( a ));
				gl.glVertex2f( x, y );
			}
			gl.glEnd();
		}
//		gl.glColor4f((float)((1-P)/4+P*0.3),  (float)(P*0.3), (float)(P),1);
//		gl.glColor4f((float)((1-resourcePercent)*1.0), (float)(resourcePercent*1.0), 2f*(float)(0.5-Math.abs(resourcePercent-0.5)),0.5f);
		gl.glBegin(GL2.GL_POLYGON);
		for(double a = 0; a <= 6; a ++)
		{
			// TODO: markers!
			gl.glVertex3f((float)(0. + 1*Math.cos(a*Angles.PI_div_3)), 
						  (float)(0f + 1*Math.sin(a*Angles.PI_div_3)), -0.1f);
		}
		gl.glEnd();

		
//		gl.glEnable(GL.GL_BLEND);
		gl.glColor4f(0.0f, 1.0f, 0f, 0.2f);
		Vector2D trackPoint = cannon.getBattleInterface().acquireTrackPoint(cannon);
		
		if(trackPoint != null)
		{
//			Vector2D relTrack = trackPoint.minus(cannon.getArea().getAnchor());
			float rel_x = (float) (trackPoint.x() - cannon.getArea().getAnchor().x());
			float rel_y = (float) (trackPoint.y() - cannon.getArea().getAnchor().y());
			gl.glBegin(GL.GL_LINE_STRIP);
			gl.glVertex3f(0, 0, 0);
			gl.glVertex3f(rel_x, rel_y, 0);
			gl.glEnd();
			
			gl.glBegin(GL2.GL_QUADS);
			gl.glVertex3f(rel_x-0.3f, rel_y-0.3f, 0);
			gl.glVertex3f(rel_x-0.3f, rel_y+0.3f, 0);
			gl.glVertex3f(rel_x+0.3f, rel_y+0.3f, 0);
			gl.glVertex3f(rel_x+0.3f, rel_y-0.3f, 0);
			gl.glVertex3f(rel_x-0.3f, rel_y-0.3f, 0);
			gl.glEnd();
		}

		gl.glPopAttrib();
	}
	@Override
	public IVeil getVeil() { return veil; }

	@Override
	public boolean isCastsShadow() { return false; }
	@Override
	public float getPriority() { return 0; }

	@Override
	public boolean isOriented() { return true; }
}
