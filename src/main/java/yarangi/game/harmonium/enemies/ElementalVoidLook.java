package yarangi.game.harmonium.enemies;

import javax.media.opengl.GL;

import yarangi.game.harmonium.enemies.swarm.agents.SwarmAgent;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.objects.ILook;
import yarangi.graphics.veils.BlurVeil;
import yarangi.math.Angles;
import yarangi.numbers.RandomUtil;

public class ElementalVoidLook implements ILook <SwarmAgent> 
{
	private final float blue = (float)(RandomUtil.getRandomDouble(0.5)+0.5);
	private final float green = (float)(RandomUtil.getRandomDouble(0.5)+0.5);
	private final float red = (float)(RandomUtil.getRandomDouble(0.5)+0.5);
	
	private IVeil veil;

	@Override
	public void render(GL gl, SwarmAgent entity, IRenderingContext context) {

//		gl.glBlendFunc(GL.GL_ONE, GL.GL_ZERO);
		
//		System.out.println("here");
//		Integrity integrity = entity.getIntegrity();
//		gl.glColor4f(0.2f, 0.1f, 0.1f, 1);
		gl.glColor4f(red, green, blue, 1);
		gl.glBegin(GL.GL_POLYGON);
		double rad = entity.getArea().getMaxRadius();
		for(double a = 0; a <= Angles.PI_2; a += Angles.PI_div_6)
			gl.glVertex3f((float)(rad*Math.cos(a)), (float)(rad*Math.sin(a)), 0);
		
		gl.glEnd();
//		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
	}

	@Override
	public void init(GL gl, SwarmAgent entity, IRenderingContext context) {
		veil = context.getPlugin( BlurVeil.NAME );

	}

	@Override
	public void destroy(GL gl, SwarmAgent entity, IRenderingContext context) {
	}

	@Override
	public boolean isCastsShadow() { return false; }
	@Override
	public float getPriority() { return 0; }
	@Override
	public IVeil getVeil() { return veil; }

	@Override
	public boolean isOriented() { return true; }
}
