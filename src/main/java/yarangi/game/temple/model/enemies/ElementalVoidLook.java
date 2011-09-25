package yarangi.game.temple.model.enemies;

import javax.media.opengl.GL;

import yarangi.game.temple.model.enemies.swarm.agents.SwarmAgent;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.math.Angles;

public class ElementalVoidLook implements Look <SwarmAgent> 
{

	public void render(GL gl, double time, SwarmAgent entity, IRenderingContext context) {

//		gl.glBlendFunc(GL.GL_ONE, GL.GL_ZERO);
		
//		System.out.println("here");
//		Integrity integrity = entity.getIntegrity();
//		gl.glColor4f(0.2f, 0.1f, 0.1f, 1);
		gl.glColor4f(0.5f, 0.25f, 0.0f, 1);
		gl.glBegin(GL.GL_POLYGON);
		double rad = entity.getArea().getMaxRadius();
		for(double a = 0; a <= Angles.PI_2; a += Angles.PI_div_6)
			gl.glVertex3f((float)(rad*Math.cos(a)), (float)(rad*Math.sin(a)), 0);
		
		gl.glEnd();
//		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
	}

	public void init(GL gl, SwarmAgent entity, IRenderingContext context) {
		// TODO Auto-generated method stub
		
	}

	public void destroy(GL gl, SwarmAgent entity, IRenderingContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCastsShadow() { return true; }
	@Override
	public float getPriority() { return 0; }

}
