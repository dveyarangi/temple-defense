package yarangi.game.temple.model.enemies;

import javax.media.opengl.GL;

import yarangi.game.temple.model.Integrity;
import yarangi.graphics.quadraturin.RenderingContext;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.math.Angles;
import yarangi.numbers.RandomUtil;

public class ElementalVoidLook implements Look <ElementalVoid> 
{
	private double red = RandomUtil.getRandomDouble(1);
	private double blue = RandomUtil.getRandomDouble(1);
	private double green = RandomUtil.getRandomDouble(1);
	public void render(GL gl, double time, ElementalVoid entity, RenderingContext context) {

		Integrity integrity = entity.getIntegrity();
		gl.glBegin(GL.GL_POLYGON);
		gl.glColor4f((float)red, (float)green, (float)blue, (float)(0.1*integrity.getHitPoints()/integrity.getMaxHitPoints()));
		for(double a = 0; a < Angles.PI_2; a += Angles.PI_div_6)
			gl.glVertex3f((float)(10.*Math.cos(a)), (float)(10*Math.sin(a)), 0);
		
		gl.glEnd();
	}

	public void init(GL gl, ElementalVoid entity) {
		// TODO Auto-generated method stub
		
	}

	public void destroy(GL gl, ElementalVoid entity) {
		// TODO Auto-generated method stub
		
	}

}
