package yarangi.game.temple.model.weapons;

import javax.media.opengl.GL;

import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.math.Angles;

public class FlakCannonLook implements Look <FlakCannon>
{

	public void render(GL gl, double time, FlakCannon cannon, IRenderingContext context) 
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
		gl.glEnd();*/
	}

	public void init(GL gl, FlakCannon entity) {
		// TODO Auto-generated method stub
		
	}

	public void destroy(GL gl, FlakCannon entity) {
		// TODO Auto-generated method stub
		
	}

}
