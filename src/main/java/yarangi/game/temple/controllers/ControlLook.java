package yarangi.game.temple.controllers;

import javax.media.opengl.GL;

import yarangi.game.temple.model.temple.TempleEntity;
import yarangi.graphics.quadraturin.RenderingContext;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.graphics.quadraturin.objects.SceneEntity;
import yarangi.math.AABB;
import yarangi.math.DistanceUtils;
import yarangi.math.Vector2D;

public class ControlLook implements Look <ControlEntity> 
{

	public void render(GL gl, double time, ControlEntity entity, RenderingContext context) 
	{
		if(context.isForEffect())
			return;
		TempleEntity temple = entity.getTemple();
		
		Vector2D mousePoint = entity.getCursorLocation();
		if(mousePoint == null)
			return;
		
		double controlAngle = Math.atan2(mousePoint.y, mousePoint.x);
		double controlRadius = temple.getStructure().getShieldRadius()+1;
//		System.out.println("hui: " + mousePoint);
		gl.glColor4f(0.5f, 1.0f, 0.7f,0.2f);
//		double sin = Math.sin(controlAngle);
//		double cos = Math.cos(controlAngle);
		double maxDistance = 1000;//Math.sqrt(DistanceUtils.calcDistanceSquare(new Vector2D(0,0), mousePoint));
		double distance = Math.sqrt(DistanceUtils.calcDistanceSquare(new Vector2D(0,0), mousePoint));
		
		double startAngle = controlAngle - Math.PI/20;
		double endAngle = controlAngle + Math.PI/20;
		gl.glBegin(GL.GL_POLYGON);
		gl.glColor4f(0.5f, 1.0f, 0.7f,0.1f);
//			gl.glVertex3f((float)(controlRadius*cos),(float)(controlRadius*sin),0);
			gl.glVertex3f((float)(maxDistance*Math.cos(endAngle)),   (float)(maxDistance*Math.sin(endAngle)), 0);
			gl.glVertex3f((float)(maxDistance*Math.cos(startAngle)), (float)(maxDistance*Math.sin(startAngle)), 0);
		
			for(double a = startAngle; a < endAngle; a += 0.1)
				gl.glVertex3f((float)(controlRadius*Math.cos(a)),(float)(controlRadius*Math.sin(a)),0);
		gl.glEnd();
		
		gl.glColor4f(0.5f, 1.0f, 0.7f,0.3f);
		gl.glBegin(GL.GL_LINE_STRIP);
			gl.glVertex3f((float)(maxDistance*Math.cos(controlAngle)),(float)(maxDistance*Math.sin(controlAngle)),0);
			gl.glVertex3f((float)(controlRadius*Math.cos(controlAngle)),(float)(controlRadius*Math.sin(controlAngle)),0);
		gl.glEnd();
		
		gl.glBegin(GL.GL_LINE_STRIP);
		for(double a = startAngle; a < endAngle; a += 0.1)
			gl.glVertex3f((float)(distance*Math.cos(a)),(float)(distance*Math.sin(a)),0);
		gl.glEnd();
		
		
		SceneEntity highlighted = entity.getHighlighted(); 
		if(highlighted != null)
		{
			AABB haabb = highlighted.getAABB();
			gl.glColor4f(0.0f, 0.2f, 0.7f,1.0f);
			gl.glBegin(GL.GL_LINE_STRIP);
			gl.glVertex3f((float)(haabb.x-haabb.r),   (float)(haabb.y-haabb.r), 0);
			gl.glVertex3f((float)(haabb.x-haabb.r),   (float)(haabb.y+haabb.r), 0);
			gl.glVertex3f((float)(haabb.x+haabb.r),   (float)(haabb.y+haabb.r), 0);
			gl.glVertex3f((float)(haabb.x+haabb.r),   (float)(haabb.y-haabb.r), 0);
			gl.glVertex3f((float)(haabb.x-haabb.r),   (float)(haabb.y-haabb.r), 0);
			
			gl.glEnd();
			
		}
/*		double width = distance*Math.sin(Math.PI/20);
		gl.glBegin(GL.GL_LINE_STRIP);
			for(double a = 0; a < 2*Math.PI; a += 0.1)
				gl.glVertex3f((float)(mousePoint.x+width*Math.cos(a)),(float)(mousePoint.y+width*Math.sin(a)),0);
		gl.glEnd();*/

	}

	public void init(GL gl, ControlEntity entity) {
		// TODO Auto-generated method stub
		
	}

	public void destroy(GL gl, ControlEntity entity) {
		// TODO Auto-generated method stub
		
	}

}
