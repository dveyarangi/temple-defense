package yarangi.game.temple.model.enemies.bubbles;


import javax.media.opengl.GL;

import yarangi.game.temple.model.Integrity;
import yarangi.graphics.quadraturin.RenderingContext;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.graphics.quadraturin.util.colors.Color;
import yarangi.math.Vector2D;

public class BubbleLook implements Look <SimpleBubble>
{

	public void render(GL gl, double time, SimpleBubble entity, RenderingContext context) 
	{
		Color color = entity.getColor();
		Integrity integrity = entity.getIntegrity();
//		gl.glColor3f(0.4f, 0.4f, 0.5f);
		gl.glColor4f((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), (float)(0.5*integrity.getHitPoints()/integrity.getMaxHitPoints()));
//		gl.glColor4f(1f, 1f, 1f, /*(float)(0.5*integrity.getHitPoints()/integrity.getMaxHitPoints())*/ 1f);
		gl.glLineWidth(2);
		gl.glBegin(GL.GL_LINE_STRIP);
		Vector2D point;

		for(float t = 0; t <= entity.getCurve().getMax(); t += 0.01 /* TODO: LOD */)
		{
			point = entity.getCurve().pointAt(t);
//			gl.glColor4f(1f, 1f, 1f, /*(float)(0.5*integrity.getHitPoints()/integrity.getMaxHitPoints())*/ 1f);
//			gl.glColor4f(1f, 1f, 1f, /*(float)(0.5*integrity.getHitPoints()/integrity.getMaxHitPoints())*/ 1f);
			gl.glVertex3f((float)point.x, (float) point.y, 0);
		}
		gl.glEnd();
		gl.glLineWidth(1);
		
/*		if(!context.isForEffect())
		{
			gl.glColor4f(1f, 0.5f, 0.5f,  1f);
			for(BezierNode node : entity.getCurve().getNodes())
			{
				gl.glBegin(GL.GL_LINE_STRIP);
				Vector2D pivot = node.anchor.plus(node.pivot);
				gl.glVertex2f((float)node.anchor.x, (float)node.anchor.y);
				gl.glVertex2f((float)pivot.x, (float)pivot.y);
				gl.glEnd();
			}
		}*/
	}

	public void init(GL gl, SimpleBubble entity) {
		// TODO Auto-generated method stub
		
	}

	public void destroy(GL gl, SimpleBubble entity) {
		// TODO Auto-generated method stub
		
	}

}
