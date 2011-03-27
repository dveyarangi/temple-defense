package yarangi.game.spacefight.model.weapons;

import javax.media.opengl.GL;

import yarangi.graphics.quadraturin.RenderingContext;
import yarangi.graphics.quadraturin.interaction.spatial.AABB;
import yarangi.graphics.quadraturin.objects.Look;

public class MinigunBurstLook implements Look <Projectile> 
{
	static float UGLY = 0.0f;
	static float dir = 1;

	public void render(GL gl, double time, Projectile prj, RenderingContext context) 
	{

		float scale = (float)(prj.getRangeSquare() / ( prj.getMaxRange()-prj.getRangeSquare()));
		gl.glColor4f(UGLY, scale,1.0f-scale/2, 1.0f);
		AABB aabb = prj.getAABB();
		UGLY += dir*0.00001;
		if(UGLY >= 1)
		{
			dir = -1;
		}
		else
			if(UGLY <= 0)
				dir = 1;
		gl.glBegin(GL.GL_LINE_STRIP);
			gl.glVertex3f(5, 0, 0);
			gl.glVertex3f(-5, 0, 0);
		gl.glEnd();

	}

	public void init(GL gl, Projectile entity) {
//		System.out.println("MinigunBurstLook:initing");
	}

	public void destroy(GL gl, Projectile entity) {
		// TODO Auto-generated method stub
		
	}

}
