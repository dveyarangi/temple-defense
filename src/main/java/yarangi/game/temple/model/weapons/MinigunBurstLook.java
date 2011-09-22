package yarangi.game.temple.model.weapons;

import javax.media.opengl.GL;

import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.spatial.Area;

public class MinigunBurstLook implements Look <Projectile> 
{
//	static float UGLY = 0.0f;
	static float dir = 1;

	public void render(GL gl, double time, Projectile prj, IRenderingContext context) 
	{

		float scale = (float)(prj.getRangeSquare() / ( prj.getMaxRange()-prj.getRangeSquare()));
		gl.glColor4f(0.1f, 1.0f, 0.1f, 1.0f);
		Area area = prj.getArea();
//		System.out.println(area.getOrientation());
/*		UGLY += dir*0.00001;
		if(UGLY >= 1)
		{
			dir = -1;
		}
		else
			if(UGLY <= 0)
				dir = 1;*/
		gl.glBegin(GL.GL_POLYGON);
			gl.glVertex3f(0.5f, 0.5f, 0);
			gl.glVertex3f(-0.5f, 0.5f, 0);
			gl.glVertex3f(-0.5f, -0.5f, 0);
			gl.glVertex3f(0.5f, -0.5f, 0);
			gl.glVertex3f(0.5f, 0.5f, 0);
		gl.glEnd();

	}

	public void init(GL gl, Projectile entity, IRenderingContext context) {
//		System.out.println("MinigunBurstLook:initing");
	}

	public void destroy(GL gl, Projectile entity, IRenderingContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCastsShadow() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public float getPriority() { return 0; }

}
