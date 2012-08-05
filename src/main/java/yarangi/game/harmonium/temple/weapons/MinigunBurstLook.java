package yarangi.game.harmonium.temple.weapons;

import javax.media.opengl.GL;

import yarangi.game.harmonium.environment.resources.Port;
import yarangi.graphics.colors.Color;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.objects.ILook;

public class MinigunBurstLook implements ILook <Projectile> 
{
//	static float UGLY = 0.0f;
	static float dir = 1;
	
	private final Color color;

//	private static GLU glu = new GLU();
	private IVeil veil;
	
	public MinigunBurstLook(Port port, WeaponProperties weapon)
	{
		double resourcePercent = port.get( weapon.getResourceType() ).getAmount() / port.getCapacity( weapon.getResourceType() );
		this.color = new Color( 0.3f, 1.0f, (float)resourcePercent,1);
//		this.color = new Color( (float)((1-resourcePercent)+resourcePercent*0.1),  (float)(resourcePercent*0.5), (float)(resourcePercent),1);
	}

	@Override
	public void render(GL gl, Projectile prj, IRenderingContext context) 
	{
		color.apply( gl );
		gl.glBegin(GL.GL_POLYGON);
			gl.glVertex3f(0.2f, 0.2f, 0);
			gl.glVertex3f(-0.2f, 0.2f, 0);
			gl.glVertex3f(-0.2f, -0.2f, 0);
			gl.glVertex3f(0.2f, -0.2f, 0);
			gl.glVertex3f(0.2f, 0.2f, 0);
		gl.glEnd();
	}

	@Override
	public void init(GL gl, Projectile entity, IRenderingContext context) {
//		veil = context.getPlugin( BlurVeil.NAME );

	}

	@Override
	public void destroy(GL gl, Projectile entity, IRenderingContext context) {
	}

	@Override
	public boolean isCastsShadow() {
		return false;
	}
	@Override
	public float getPriority() { return 0; }
	@Override
	public IVeil getVeil() { return veil; }

	@Override
	public boolean isOriented() { return true; }

}
