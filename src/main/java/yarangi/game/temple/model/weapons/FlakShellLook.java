package yarangi.game.temple.model.weapons;

import javax.media.opengl.GL;

import yarangi.graphics.quadraturin.RenderingContext;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.math.Vector2D;

public class FlakShellLook implements Look <FlakShell> 
{
	public void render(GL gl, double time, FlakShell shell, RenderingContext context) 
	{
		if(!shell.isAtExplosionRange())
		{
			gl.glColor4f(0.0f, 1.0f, 0.7f, 0.7f);
			gl.glBegin(GL.GL_LINE_STRIP);
			gl.glVertex3f(1, 0, 0);
			gl.glVertex3f(-1, 0, 0);
			gl.glEnd();
		}
		else 
		{
			double width = shell.getMaxRange() - shell.getExplosionRange();
			double offset = shell.getMaxRange() - shell.getRangeSquare();
			float scale = (float)(offset/width);
			gl.glColor4f(1f-scale/2f, 0.5f+scale/2f, 0.5f-scale/2f, (float)(scale));
			Vector2D [] frags = shell.getFragLocations();
			for(Vector2D frag : frags)
			{
				gl.glBegin(GL.GL_LINE_STRIP);
				gl.glVertex3f((float)frag.x, (float)frag.y, 0);
				gl.glVertex3f(0, 0, 0);
				gl.glEnd();
			}
		}
	}

	public void init(GL gl, FlakShell entity) {
		// TODO Auto-generated method stub
		
	}

	public void destroy(GL gl, FlakShell entity) {
		// TODO Auto-generated method stub
		
	}

}
