package yarangi.game.harmonium.temple.weapons;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import yar.quadraturin.IRenderingContext;
import yar.quadraturin.IVeil;
import yar.quadraturin.graphics.colors.Color;
import yar.quadraturin.graphics.veils.BlurVeil;
import yar.quadraturin.objects.ILook;
import yarangi.game.harmonium.environment.resources.Port;

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
	public void render(Projectile prj, IRenderingContext ctx) 
	{
		GL2 gl = ctx.gl();
		
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
	public void init(IRenderingContext ctx) {
		veil = ctx.getPlugin( BlurVeil.NAME );

	}

	@Override
	public void destroy(IRenderingContext ctx) {
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
