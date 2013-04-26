package yarangi.game.harmonium.temple.weapons;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import yarangi.game.harmonium.environment.resources.Port;
import yarangi.graphics.colors.Color;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.objects.ILook;
import yarangi.graphics.veils.BlurVeil;

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
	public void render(GL gl1, Projectile prj, IRenderingContext context) 
	{
		GL2 gl = gl1.getGL2();
		color.apply( gl );
		gl.glPushAttrib( GL2.GL_ENABLE_BIT );
		gl.glEnable(GL.GL_BLEND);
		gl.glBegin(GL.GL_LINE_STRIP);
			gl.glVertex2f(1.2f, 0.1f);
			gl.glVertex2f(-1.2f, -0.1f);
		gl.glEnd();
		gl.glPopAttrib();
	}

	@Override
	public void init(GL gl, IRenderingContext context) {
		veil = context.getPlugin( BlurVeil.NAME );

	}

	@Override
	public void destroy(GL gl, IRenderingContext context) {
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
