package yarangi.game.harmonium.temple.weapons;

import javax.media.opengl.GL;

import yarangi.game.harmonium.ai.weapons.IntellectCore;
import yarangi.game.harmonium.environment.resources.Resource;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.ILook;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.spatial.Area;

public class MinigunLook implements ILook<Minigun> 
{


	@Override
	public void render(GL gl, Minigun cannon, IRenderingContext context) 
	{

			Area area = cannon.getArea();
		Vector2D loc = area.getAnchor();
		Resource.Type type = cannon.getProps().getResourceType();
		double resourcePercent = cannon.getPort().get( type ).getAmount() / cannon.getPort().getCapacity( type );
		gl.glColor4f((float)((1-resourcePercent)/4+resourcePercent*0.3),  (float)(resourcePercent*0.3), (float)(resourcePercent),1);
//		gl.glColor4f((float)((1-resourcePercent)*1.0), (float)(resourcePercent*1.0), 2f*(float)(0.5-Math.abs(resourcePercent-0.5)),0.5f);
		gl.glBegin(GL.GL_POLYGON);
		for(double a = 0; a <= 6; a ++)
		{
			gl.glVertex3f((float)(0. + cannon.getArea().getMaxRadius()*Math.cos(a*Angles.PI_div_3)), 
					(float)(0f + cannon.getArea().getMaxRadius()*Math.sin(a*Angles.PI_div_3)), 1);
		}
		gl.glEnd();
		
/*		gl.glEnable(GL.GL_BLEND);
		gl.glColor4f(0.0f, 1.0f, 0f,0.2f);
		gl.glBegin( GL.GL_LINE_STRIP );
		float x, y;
		for(double a = 0; a <= Angles.PI_2+0.001; a += Angles.PI_div_40)
		{
			x = (float)(cannon.getSensor().getRadius() * Math.cos( a ));
			y = (float)(cannon.getSensor().getRadius() * Math.sin( a ));
			gl.glVertex2f( x, y );
		}
		gl.glEnd();*/
		Vector2D trackPoint = cannon.getBattleInterface().acquireTrackPoint(cannon);
		
		if(trackPoint != null)
		{
			Vector2D relTrack = trackPoint.minus(loc);
			gl.glBegin(GL.GL_LINE_STRIP);
			gl.glVertex3f(0, 0, 0);
			gl.glVertex3f((float)(relTrack.x()), (float)(relTrack.y()), 0);
			gl.glEnd();
			
			gl.glBegin(GL.GL_QUADS);
			gl.glVertex3f((float)(relTrack.x()-0.5), (float)(relTrack.y()-0.5), 0);
			gl.glVertex3f((float)(relTrack.x()-0.5), (float)(relTrack.y()+0.5), 0);
			gl.glVertex3f((float)(relTrack.x()+0.5), (float)(relTrack.y()+0.5), 0);
			gl.glVertex3f((float)(relTrack.x()+0.5), (float)(relTrack.y()-0.5), 0);
			gl.glVertex3f((float)(relTrack.x()-0.5), (float)(relTrack.y()-0.5), 0);
			gl.glEnd();
		}
		
		IntellectCore core = cannon.getCore();
		

		IEntity target = cannon.getBattleInterface().getTargets().get( cannon );
		if(target != null) {
			double baseSpeed = cannon.getProps().getProjectileSpeed();
			gl.glBegin(GL.GL_LINE_STRIP);
			for(int i = -5; i < 5; i ++)
			{
//				System.out.println(core + " : " + target + " : " + cannon);
				Vector2D prediction = core.pickTrackPoint( cannon.getArea().getAnchor(), baseSpeed-0.5*i, target.getArea().getAnchor(), target.getBody().getVelocity() );
				prediction.substract( loc );
				gl.glVertex3f((float)(prediction.x()), (float)(prediction.y()), 0);
				
			}
			gl.glEnd();
		}
	}
	
	

	@Override
	public void init(GL gl, Minigun entity, IRenderingContext context) {
	}

	@Override
	public void destroy(GL gl, Minigun entity, IRenderingContext context) {
	}



	@Override
	public boolean isCastsShadow() { return true; }
	@Override
	public float getPriority() { return 0; }
	@Override
	public IVeil getVeil() { return IVeil.ORIENTING; }

}
