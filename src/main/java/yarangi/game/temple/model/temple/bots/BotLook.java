package yarangi.game.temple.model.temple.bots;

import javax.media.opengl.GL;

import yarangi.graphics.quadraturin.RenderingContext;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.math.Vector2D;
import static yarangi.math.Angles.*;


public class BotLook implements Look<Bot> {

	public void render(GL gl, double time, Bot entity, RenderingContext context) 
	{
		
/*		if(entity.isHighlighted())
			gl.glColor3f(1.0f, 1.0f, 1.0f);
		else
			gl.glColor3f(0.0f, 1.0f, 0.2f);*/
		
		gl.glBegin(GL.GL_LINE_STRIP);
		Vector2D [] tail = entity.getTail();
		for(int idx = 0; idx < tail.length; idx ++)
			gl.glVertex3f((float)tail[idx].x, (float)tail[idx].y, 0f);
		gl.glEnd();

		if(entity.getCurrTarget() == null)
			return;
		Vector2D seg = entity.getCommandPlatform().getTempleStructure().toTempleCoordinates(entity.getCurrTarget().getLocation());
		gl.glBegin(GL.GL_POLYGON);
		for(double angle = 0; angle < PI_2; angle += PI_div_10)
		{
			gl.glVertex3f((float)(seg.x + 0.1 * Math.cos(angle)), 
						  (float)(seg.y + 0.1 * Math.sin(angle)), 0);
		}
		gl.glEnd();

	}

	public void init(GL gl, Bot entity) {
		// TODO Auto-generated method stub
		
	}

	public void destroy(GL gl, Bot entity) {
		// TODO Auto-generated method stub
		
	}

}
