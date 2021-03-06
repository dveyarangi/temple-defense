package yarangi.game.harmonium.enemies;

import yar.quadraturin.IRenderingContext;
import yar.quadraturin.IVeil;
import yar.quadraturin.objects.ILook;
import yarangi.game.harmonium.enemies.swarm.agents.SwarmAgent;
import yarangi.numbers.RandomUtil;

public class ElementalDarknessLook implements ILook <SwarmAgent>
{
	private final double blue = RandomUtil.getRandomDouble(0.5)+0.5;
	private final double green = RandomUtil.getRandomDouble(0.5)+0.5;
	private final double red = RandomUtil.getRandomDouble(0.5)+0.5;

	@Override
	public void render(SwarmAgent entity, IRenderingContext ctx) 
	{
//		Integrity integrity = entity.getIntegrity();
////		gl.glColor3f(0.4f, 0.4f, 0.5f);
//		gl.glColor4f((float)red, (float)green, (float)blue, (float)(0.1*integrity.getHitPoints()/integrity.getMaxHitPoints()));
////		gl.glColor4f(1f, 1f, 1f, /*(float)(0.5*integrity.getHitPoints()/integrity.getMaxHitPoints())*/ 1f);
//		gl.glLineWidth(3);
//		gl.glBegin(GL.GL_LINE_STRIP);
//		Vector2D point;
//
//		for(float t = 0; t <= entity.getCurve().getMax(); t += 0.01 /* TODO: LOD */)
//		{
//			point = entity.getCurve().pointAt(t);
//			gl.glColor4f(1f, 1f, 1f, /*(float)(0.5*integrity.getHitPoints()/integrity.getMaxHitPoints())*/ 1f);
//			gl.glColor4f(1f, 1f, 1f, /*(float)(0.5*integrity.getHitPoints()/integrity.getMaxHitPoints())*/ 1f);
//			gl.glVertex3f((float)point.x(), (float) point.y(), 0);
//		}
//		gl.glEnd();
//		gl.glLineWidth(1);
		
/*		gl.glColor4f(1f, 0.5f, 0.5f,  1f);
		for(BezierNode node : entity.getCurve().getNodes())
		{
			gl.glBegin(GL.GL_LINE_STRIP);
			Vector2D pivot = node.anchor.plus(node.pivot);
			gl.glVertex2f((float)node.anchor.x, (float)node.anchor.y);
			gl.glVertex2f((float)pivot.x, (float)pivot.y);
			gl.glEnd();
		}*/
		
	}

	@Override
	public void init(IRenderingContext ctx) {}

	@Override
	public void destroy(IRenderingContext ctx) {}

	@Override
	public float getPriority()
	{
		return 0;
	}

	@Override
	public boolean isCastsShadow()
	{
		return false;
	}
	@Override
	public IVeil getVeil() { return null; }

	@Override
	public boolean isOriented() { return true; }
}
