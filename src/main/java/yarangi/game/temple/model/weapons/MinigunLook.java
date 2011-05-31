package yarangi.game.temple.model.weapons;

import javax.media.opengl.GL;

import yarangi.game.temple.controllers.BattleInterface;
import yarangi.game.temple.model.temple.platforms.WeaponPlatform;
import yarangi.graphics.quadraturin.RenderingContext;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.spatial.Area;

public class MinigunLook implements Look<Minigun> 
{


	public void render(GL gl, double time, Minigun cannon, RenderingContext context) 
	{
		if(context.isForEffect())
			return;
		
		double controlAngle = 0;
		
		double width =  Angles.toRadians(cannon.getWeaponProperties().getProjectileTrajectoryAccuracy());
		
		double maxDistance = 1000;//Math.sqrt(DistanceUtils.calcDistanceSquare(new Vector2D(0,0), mousePoint));
//		double distance = Math.sqrt(DistanceUtils.calcDistanceSquare(new Vector2D(0,0), mousePoint));
		
		double startAngle = controlAngle - width;
		double endAngle = controlAngle + width;
/*		gl.glBegin(GL.GL_POLYGON);
		gl.glColor4f(1.0f, 0.5f, 0.7f,0.1f);
//			gl.glVertex3f((float)(controlRadius*cos),(float)(controlRadius*sin),0);
			gl.glVertex3f((float)(maxDistance*Math.cos(endAngle)),   (float)(maxDistance*Math.sin(endAngle)), 0);
			gl.glVertex3f((float)(maxDistance*Math.cos(startAngle)), (float)(maxDistance*Math.sin(startAngle)), 0);
		
			for(double a = startAngle; a < endAngle; a += 0.1)
				gl.glVertex3f((float)(10*Math.cos(a)),(float)(10*Math.sin(a)),0);
		gl.glEnd();	*/
		
//		AABB target = cannon.getTrackingPoint()
		WeaponPlatform platform = cannon.getPlatform();
		BattleInterface bif = platform.getBattleInterface();
		
		Vector2D trackPoint = bif.acquireTrackPoint(cannon);
		Area area = cannon.getArea();
		Vector2D loc = area.getRefPoint();
		gl.glColor4f(1.0f, 0.5f, 0.7f,0.5f);
		
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex3f((float)(loc.x()), (float)(loc.y()), 0);
		if(trackPoint != null)
			gl.glVertex3f((float)(trackPoint.x), (float)(trackPoint.y), 0);
		else
			gl.glVertex3f((float)(loc.x() + maxDistance*Math.cos(area.getOrientation())), 
						  (float)(loc.y() + maxDistance*Math.sin(area.getOrientation())), 0);
		gl.glEnd();

		if(trackPoint != null)
		{
			gl.glBegin(GL.GL_LINE_STRIP);
			gl.glVertex3f((float)(trackPoint.x-10), (float)(trackPoint.y-10), 0);
			gl.glVertex3f((float)(trackPoint.x-10), (float)(trackPoint.y+10), 0);
			gl.glVertex3f((float)(trackPoint.x+10), (float)(trackPoint.y+10), 0);
			gl.glVertex3f((float)(trackPoint.x+10), (float)(trackPoint.y-10), 0);
			gl.glVertex3f((float)(trackPoint.x-10), (float)(trackPoint.y-10), 0);
			gl.glEnd();
		}
	}
	
	

	public void init(GL gl, Minigun entity) {
		// TODO Auto-generated method stub
		
	}

	public void destroy(GL gl, Minigun entity) {
		// TODO Auto-generated method stub
		
	}

}
