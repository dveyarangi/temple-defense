package yarangi.game.temple.model.enemies;

import javax.media.opengl.GL;

import yarangi.game.temple.model.Integrity;
import yarangi.graphics.quadraturin.RenderingContext;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.math.Angles;

public class ElementalVoidLook implements Look <ElementalVoid> 
{
	private double red = 0;//0.5+RandomUtil.getRandomDouble(0.5);
	private double blue = 0;//0.5+RandomUtil.getRandomDouble(0.5);
	private double green = 0;//0.5+RandomUtil.getRandomDouble(0.5);
	public void render(GL gl, double time, ElementalVoid entity, RenderingContext context) {

		gl.glBlendFunc(GL.GL_ONE, GL.GL_ZERO);
//		System.out.println("here");
		Integrity integrity = entity.getIntegrity();
		gl.glColor4f((float)red, (float)green, (float)blue, 1);
		gl.glBegin(GL.GL_POLYGON);
		for(double a = 0; a < Angles.PI_2; a += Angles.PI_div_6)
			gl.glVertex3f((float)(10*Math.cos(a)), (float)(10*Math.sin(a)), 0);
		
		gl.glEnd();
	}

	public void init(GL gl, ElementalVoid entity) {
		// TODO Auto-generated method stub
		
	}

	public void destroy(GL gl, ElementalVoid entity) {
		// TODO Auto-generated method stub
		
	}

}
