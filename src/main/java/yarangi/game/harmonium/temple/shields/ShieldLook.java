package yarangi.game.harmonium.temple.shields;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLProfile;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import yar.quadraturin.IRenderingContext;
import yar.quadraturin.IVeil;
import yar.quadraturin.Q;
import yar.quadraturin.graphics.veils.IsoheightVeil;
import yar.quadraturin.objects.ILook;
import yarangi.numbers.RandomUtil;


public class ShieldLook implements ILook <Shield> 
{

	
	private float a1 = (float)RandomUtil.getRandomDouble( 0.1 );
	private float a2 = (float)RandomUtil.getRandomDouble( 0.1 );

//	private IsoheightVeil veil;
	private IVeil veil;
	
	private Texture texture;
//	private static int count = 0;

	@Override
	public void init(IRenderingContext ctx) {
		
		GL2 gl = ctx.gl();

		veil = ctx.<IsoheightVeil> getPlugin( IsoheightVeil.NAME );
		if(veil == null)
		{
			Q.rendering.warn( "Plugin [" + IsoheightVeil.NAME + "] requested by look [" + this.getClass() + "] is not available."  );
		}
		
//		Port port = entity.getPort();
//		double resourcePercent = port.get( Resource.Type.ENERGY ).getAmount() / port.getCapacity( Resource.Type.ENERGY );
		
		if(texture == null) // shared
		{
			BufferedImage image;
			try
			{
				image = ImageIO.read(getClass().getResourceAsStream("/textures/distort4_blue_512x512.png"));
	//			image = ImageIO.read(getClass().getResourceAsStream("/textures/hairy_gradient.png"));
			} catch ( IOException e )
			{
				throw new IllegalArgumentException("file not found");
			}
			texture = AWTTextureIO.newTexture(GLProfile.getGL2ES2(), image, false);
			texture.setTexParameteri(gl, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
			
			texture.setTexParameteri(gl, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
		}
//		count ++;
		
//		veil = context.getPlugin( IsoheightVeil.NAME );	}
	}
	@Override
	public void render(Shield entity, IRenderingContext ctx) 
	{
		GL2 gl = ctx.gl();
		
		gl.glPushAttrib( GL.GL_COLOR_BUFFER_BIT );
		gl.glBlendFunc(GL.GL_ONE, GL.GL_ONE);
		gl.glBlendEquation(GL.GL_FUNC_ADD);
		float radius = (float)entity.getArea().getMaxRadius();

//	System.out.println(force + " : " + speed + " : " + radius + " : " + time);
		texture.bind(gl);
		
		gl.glPushMatrix();
		gl.glRotatef( a1, 0, 0, 1 );
		a1 += 0.005*ctx.getFrameLength();
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f( 0.0f, 0.0f ); gl.glVertex2f(-radius, -radius);
		gl.glTexCoord2f( 0.0f, 1.0f ); gl.glVertex2f(-radius,  radius);
		gl.glTexCoord2f( 1.0f, 1.0f ); gl.glVertex2f( radius,  radius);
		gl.glTexCoord2f( 1.0f, 0.0f ); gl.glVertex2f( radius, -radius);
		gl.glEnd();
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glRotatef( a2, 0, 0, 1 );
		a2 -= 0.005*ctx.getFrameLength();
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f( 0.0f, 0.0f ); gl.glVertex2f(-radius, -radius);
		gl.glTexCoord2f( 0.0f, 1.0f ); gl.glVertex2f(-radius,  radius);
		gl.glTexCoord2f( 1.0f, 1.0f ); gl.glVertex2f( radius,  radius);
		gl.glTexCoord2f( 1.0f, 0.0f ); gl.glVertex2f( radius, -radius);
		gl.glEnd();
		gl.glPopMatrix();
		
		gl.glBindTexture( GL.GL_TEXTURE_2D, 0 );
		gl.glPopAttrib();
		ctx.setDefaultBlendMode( gl );
	}

	@Override
	public void destroy(IRenderingContext ctx) {
		texture.destroy(ctx.gl());
		texture = null; //

	}

	@Override
	public float getPriority() {
		return 0;
	}

	@Override
	public boolean isCastsShadow() {
		return false;
	}
	@Override
	public IVeil getVeil() { return veil; }

	@Override
	public boolean isOriented() { return true; }
}
