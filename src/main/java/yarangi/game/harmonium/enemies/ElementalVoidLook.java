package yarangi.game.harmonium.enemies;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import yarangi.game.harmonium.enemies.swarm.agents.SwarmAgent;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.objects.ILook;
import yarangi.graphics.veils.BlurVeil;
import yarangi.math.Angles;
import yarangi.math.IVector2D;

public class ElementalVoidLook implements ILook <SwarmAgent> 
{
//	private final float blue = (float)(RandomUtil.getRandomDouble(0.5)+0.5);
//	private final float green = (float)(RandomUtil.getRandomDouble(0.5)+0.5);
//	private final float red = (float)(RandomUtil.getRandomDouble(0.5)+0.5);
//	private final float blue = 0;//(float)(RandomUtil.getRandomDouble(0.5)+0.5);
//	private final float green = 0;//(float)(RandomUtil.getRandomDouble(0.5)+0.5);
//	private final float red = 0;//(float)(RandomUtil.getRandomDouble(0.5)+0.5);
	
	private IVeil veil;
	
	@Override
	public void init(IRenderingContext ctx) {
		veil = ctx.getPlugin( BlurVeil.NAME );
	}

	@Override
	public void render(SwarmAgent entity, IRenderingContext ctx) {
	
		GL2 gl = ctx.gl();
//		gl.glBlendFunc(GL.GL_ONE, GL.GL_ZERO);
		
//		System.out.println("here");
//		Integrity integrity = entity.getIntegrity();
//		gl.glColor4f(0.2f, 0.1f, 0.1f, 1);
//		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		double dangerModifier = entity.getLocalDanger();
		
		IVector2D anchor = entity.getArea().getAnchor();
		gl.glDisable(GL.GL_DEPTH_TEST);
		gl.glBegin(GL2.GL_POLYGON);
		int count = 0;
		double rad = entity.getArea().getMaxRadius();
		for(double a = 0; a <= Angles.TAU; a += Angles.TRIG_STEP*120) 
		{
//			gl.glColor3f((float)(dangerModifier/100f), 0, 0);
			if(count ++ % 2 == 0)
				entity.getColor().apply( gl );
			else
				entity.getOtherColor().apply( gl );
			gl.glVertex2f((float)(anchor.x() + rad*Angles.COS( a )), (float)(anchor.y() + rad*Angles.SIN( a )));
		}
		gl.glEnd();
	}


	@Override
	public void destroy(IRenderingContext context) { }

	@Override
	public boolean isCastsShadow() { return false; }
	@Override
	public float getPriority() { return 0; }
	@Override
	public IVeil getVeil() { return veil; }

	@Override
	public boolean isOriented() { return false; }
}
