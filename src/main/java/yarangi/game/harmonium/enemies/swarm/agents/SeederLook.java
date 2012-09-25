package yarangi.game.harmonium.enemies.swarm.agents;

import javax.media.opengl.GL;

import yarangi.graphics.curves.Bezier4Curve;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.objects.ILook;
import yarangi.graphics.veils.BlurVeil;
import yarangi.math.IVector2D;

public class SeederLook implements ILook <Seeder> 
{

	IVeil veil;
	
	@Override
	public void init(GL gl, IRenderingContext context) {
		// TODO Auto-generated method stub
		veil = context.getPlugin( BlurVeil.NAME );
		
		
	}

	@Override
	public void render(GL gl, Seeder seeder, IRenderingContext context) {
		
		Bezier4Curve left = seeder.getLeftEdge();
		Bezier4Curve right = seeder.getRightEdge();
		
		IVector2D point;
//		gl.glPushAttrib( GL.GL_ENABLE_BIT );
//		gl.glDisable(GL.GL_BLEND);
		gl.glColor4f(1f, 0.0f, 0.0f, 1.0f);
		
		gl.glBegin(GL.GL_POLYGON);
		for(float t = 0; t <= 1.09; t += 0.1) {
			point = left.at(t);
			gl.glVertex2f((float) point.x(), (float)point.y());
		}
//		gl.glEnd();
		
//		gl.glBegin(GL.GL_LINE_STRIP);
		for(float t = 1; t > -.09; t -= 0.1) {
			point = right.at(t);
			gl.glVertex2f((float) point.x(), (float)point.y());
		}
		gl.glEnd();
/*		gl.glColor4f(0, 1, 0, 1);
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex2f((float) right.p1().x(), (float)right.p1().y());
		gl.glVertex2f((float) right.p2().x(), (float)right.p2().y());
		gl.glVertex2f((float) right.p3().x(), (float)right.p3().y());
		gl.glVertex2f((float) right.p4().x(), (float)right.p4().y());
		gl.glEnd();
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex2f((float) left.p1().x(), (float)left.p1().y());
		gl.glVertex2f((float) left.p2().x(), (float)left.p2().y());
		gl.glVertex2f((float) left.p3().x(), (float)left.p3().y());
		gl.glVertex2f((float) left.p4().x(), (float)left.p4().y());
		gl.glEnd();*/
//		gl.glPopAttrib();
	}

	@Override
	public void destroy(GL gl,  IRenderingContext context) {}

	@Override
	public float getPriority() {
		return -0.1f;
	}

	@Override
	public boolean isCastsShadow() {
		return false; 
	}

	@Override
	public IVeil getVeil() {
		return veil;
	}

	@Override
	public boolean isOriented()
	{
		return true;
	}

}
