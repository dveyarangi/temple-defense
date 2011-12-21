package yarangi.game.harmonium.temple.weapons;

import javax.media.opengl.GL;

import yarangi.game.harmonium.environment.resources.Resource;
import yarangi.graphics.colors.Color;
import yarangi.graphics.lights.CircleLightLook;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.Q;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.graphics.veils.IsoheightVeil;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.spatial.Area;

public class MinigunGlowingLook extends CircleLightLook<Minigun>
{

	private IVeil veil;
	public MinigunGlowingLook()
	{
		super( );
	}

	public void init(GL gl, Minigun minigun, IRenderingContext context)
	{
		super.init( gl, minigun, context );
//		veil = context.<IsoheightVeil> getPlugin( IsoheightVeil.NAME );
		
		if(veil == null)
		{
			Q.rendering.warn( "Plugin [" + IsoheightVeil.NAME + "] requested by look [" + this.getClass() + "] is not available."  );
			veil = IVeil.ORIENTING;
		}

	}


	public void render(GL gl, double time, Minigun cannon, IRenderingContext context) 
	{
		Resource.Type type = cannon.getProps().getResourceType();
		float P = (float)(cannon.getPort().get( type ).getAmount() / cannon.getPort().getCapacity( type ));
		this.setColor(new Color( (float)((1-P)/4+P*0.3),  (float)(P*0.3), (float)(P),1));
//		this.setColor(new Color( P*0.2f+(1-P)*0.8f, 0.5f+0.5f*P, 1f*P, 1.0f));
//		this.setColor(new Color( 1.0f, P, P, 1.0f));
				
		super.render( gl, time, cannon, context );		
		
//		gl.glColor4f((float)((1-P)/4+P*0.3),  (float)(P*0.3), (float)(P),1);
//		gl.glColor4f((float)((1-resourcePercent)*1.0), (float)(resourcePercent*1.0), 2f*(float)(0.5-Math.abs(resourcePercent-0.5)),0.5f);
		gl.glBegin(GL.GL_POLYGON);
		for(double a = 0; a <= 6; a ++)
		{
			gl.glVertex3f((float)(0. + cannon.getArea().getMaxRadius()*Math.cos(a*Angles.PI_div_3)), 
					(float)(0f + cannon.getArea().getMaxRadius()*Math.sin(a*Angles.PI_div_3)), -0.1f);
		}
		gl.glEnd();

		
		gl.glEnable(GL.GL_BLEND);
		gl.glColor4f(0.0f, 1.0f, 0f,0.2f);
		Vector2D trackPoint = cannon.getBattleInterface().acquireTrackPoint(cannon);
		
		if(trackPoint != null)
		{
			Vector2D relTrack = trackPoint.minus(cannon.getArea().getRefPoint());
			gl.glBegin(GL.GL_LINE_STRIP);
			gl.glVertex3f(0, 0, 0);
			gl.glVertex3f((float)(relTrack.x()), (float)(relTrack.y()), 0);
			gl.glEnd();
			
			gl.glBegin(GL.GL_QUADS);
			gl.glVertex3f((float)(relTrack.x()-0.3), (float)(relTrack.y()-0.3), 0);
			gl.glVertex3f((float)(relTrack.x()-0.3), (float)(relTrack.y()+0.3), 0);
			gl.glVertex3f((float)(relTrack.x()+0.3), (float)(relTrack.y()+0.3), 0);
			gl.glVertex3f((float)(relTrack.x()+0.3), (float)(relTrack.y()-0.3), 0);
			gl.glVertex3f((float)(relTrack.x()-0.3), (float)(relTrack.y()-0.3), 0);
			gl.glEnd();
		}

	}
	public IVeil getVeil() { return veil; }

	@Override
	public boolean isCastsShadow() { return true; }
	@Override
	public float getPriority() { return 0; }

}