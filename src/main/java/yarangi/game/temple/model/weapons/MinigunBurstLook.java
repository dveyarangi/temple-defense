package yarangi.game.temple.model.weapons;

import javax.media.opengl.GL;

import yarangi.graphics.colors.Color;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.spatial.Area;

public class MinigunBurstLook implements Look <Projectile> 
{
//	static float UGLY = 0.0f;
	static float dir = 1;
	
	private Color color;
	
	public MinigunBurstLook(Minigun cannon)
	{
		double resourcePercent = cannon.getPort().get( cannon.getWeaponProperties().getResourceType() ).getAmount() / cannon.getPort().getCapacity( cannon.getWeaponProperties().getResourceType() );
		this.color = new Color( (float)(0.3f),  (float)(1.0f), (float)(0.3f),1);
//		this.color = new Color( (float)((1-resourcePercent)+resourcePercent*0.1),  (float)(resourcePercent*0.5), (float)(resourcePercent),1);
	}

	public void render(GL gl, double time, Projectile prj, IRenderingContext context) 
	{
		color.apply( gl );
		gl.glBegin(GL.GL_POLYGON);
			gl.glVertex3f(0.7f, 0.7f, 0);
			gl.glVertex3f(-0.7f, 0.7f, 0);
			gl.glVertex3f(-0.7f, -0.7f, 0);
			gl.glVertex3f(0.7f, -0.7f, 0);
			gl.glVertex3f(0.7f, 0.7f, 0);
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
