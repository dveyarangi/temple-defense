package yarangi.game.temple.model.weapons;

import javax.media.opengl.GL;

import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.spatial.Area;

public class MinigunLook implements Look<Minigun> 
{


	public void render(GL gl, double time, Minigun cannon, IRenderingContext context) 
	{
		if(context.isForEffect())
			return;
		
		double controlAngle = cannon.getArea().getOrientation();
		
/*		double width =  cannon.getWeaponProperties().getProjectileTrajectoryAccuracy();
		
		double maxDistance = 1000;//Math.sqrt(DistanceUtils.calcDistanceSquare(new Vector2D(0,0), mousePoint));
//		double distance = Math.sqrt(DistanceUtils.calcDistanceSquare(new Vector2D(0,0), mousePoint));
		
		double startAngle = controlAngle - width;
		double endAngle = controlAngle + width;
		gl.glBegin(GL.GL_POLYGON);
		gl.glColor4f(1.0f, 0.5f, 0.7f,0.1f);
//			gl.glVertex3f((float)(controlRadius*cos),(float)(controlRadius*sin),0);
			gl.glVertex3f((float)(maxDistance*Math.cos(endAngle)),   (float)(maxDistance*Math.sin(endAngle)), 0);
			gl.glVertex3f((float)(maxDistance*Math.cos(startAngle)), (float)(maxDistance*Math.sin(startAngle)), 0);
		
			for(double a = startAngle; a < endAngle; a += 0.1)
				gl.glVertex3f((float)(10*Math.cos(a)),(float)(10*Math.sin(a)),0);
		gl.glEnd();	*/
		
//		AABB target = cannon.getTrackingPoint()
		Area area = cannon.getArea();
		Vector2D loc = area.getRefPoint();
		gl.glColor4f(0.0f, 1.0f, 0f,0.5f);
		gl.glBegin(GL.GL_POLYGON);
		for(double a = 0; a <= 6; a ++)
		{
			gl.glVertex3f((float)(0. + cannon.getArea().getMaxRadius()*Math.cos(a*Angles.PI_div_3)), 
					(float)(0f + cannon.getArea().getMaxRadius()*Math.sin(a*Angles.PI_div_3)), 1);
		}
		gl.glEnd();
		
		gl.glColor4f(0.0f, 1.0f, 0f,0.2f);
		Vector2D trackPoint = cannon.getBattleInterface().acquireTrackPoint(cannon);
		
		if(trackPoint != null)
		{
			Vector2D relTrack = trackPoint.minus(loc);
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
	
	

	public void init(GL gl, Minigun entity, IRenderingContext context) {
		// TODO Auto-generated method stub
		
	}

	public void destroy(GL gl, Minigun entity, IRenderingContext context) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean isCastsShadow() { return true; }
	@Override
	public float getPriority() { return 0; }

}
