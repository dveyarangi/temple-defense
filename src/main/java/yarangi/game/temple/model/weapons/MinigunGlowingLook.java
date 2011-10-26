package yarangi.game.temple.model.weapons;

import javax.media.opengl.GL;

import yarangi.game.temple.model.resource.Resource;
import yarangi.graphics.colors.Color;
import yarangi.graphics.lights.CircleLightLook;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.spatial.Area;

public class MinigunGlowingLook extends CircleLightLook<Minigun>
{


	public MinigunGlowingLook()
	{
		super( );
	}



	public void render(GL gl, double time, Minigun cannon, IRenderingContext context) 
	{
		Resource.Type type = cannon.getWeaponProperties().getResourceType();
		float P = (float)(cannon.getPort().get( type ).getAmount() / cannon.getPort().getCapacity( type ));
		this.setColor(new Color( (float)((1-P)/4+P*0.3),  (float)(P*0.3), (float)(P),1));
//		this.setColor(new Color( P*0.2f+(1-P)*0.8f, 0.5f+0.5f*P, 1f*P, 1.0f));
//		this.setColor(new Color( 1.0f, P, P, 1.0f));
				
		super.render( gl, time, cannon, context );		
		
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
			gl.glVertex3f((float)(relTrack.x()-3), (float)(relTrack.y()-3), 0);
			gl.glVertex3f((float)(relTrack.x()-3), (float)(relTrack.y()+3), 0);
			gl.glVertex3f((float)(relTrack.x()+3), (float)(relTrack.y()+3), 0);
			gl.glVertex3f((float)(relTrack.x()+3), (float)(relTrack.y()-3), 0);
			gl.glVertex3f((float)(relTrack.x()-3), (float)(relTrack.y()-3), 0);
			gl.glEnd();
		}

	}

	@Override
	public boolean isCastsShadow() { return false; }
	@Override
	public float getPriority() { return 0; }

}
