package yarangi.game.harmonium.temple;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLProfile;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.Q;
import yarangi.graphics.quadraturin.objects.ILook;
import yarangi.graphics.veils.IsoheightVeil;
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
	public void init(GL gl,IRenderingContext context) {
		

		veil = context.<IsoheightVeil> getPlugin( IsoheightVeil.NAME );
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
	public void render(GL gl1, Shield entity, IRenderingContext context) {
		GL2 gl = gl1.getGL2();
		gl.glPushAttrib( GL.GL_COLOR_BUFFER_BIT );
		gl.glBlendFunc(GL.GL_ONE, GL.GL_ONE);
		gl.glBlendEquation(GL.GL_FUNC_ADD);
		float radius = (float)entity.getArea().getMaxRadius();

//	System.out.println(force + " : " + speed + " : " + radius + " : " + time);
		texture.bind(gl);
		
		gl.glPushMatrix();
		gl.glRotatef( a1, 0, 0, 1 );
		a1 += 0.005*context.getFrameLength();
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f( 0.0f, 0.0f ); gl.glVertex2f(-radius, -radius);
		gl.glTexCoord2f( 0.0f, 1.0f ); gl.glVertex2f(-radius,  radius);
		gl.glTexCoord2f( 1.0f, 1.0f ); gl.glVertex2f( radius,  radius);
		gl.glTexCoord2f( 1.0f, 0.0f ); gl.glVertex2f( radius, -radius);
		gl.glEnd();
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glRotatef( a2, 0, 0, 1 );
		a2 -= 0.005*context.getFrameLength();
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f( 0.0f, 0.0f ); gl.glVertex2f(-radius, -radius);
		gl.glTexCoord2f( 0.0f, 1.0f ); gl.glVertex2f(-radius,  radius);
		gl.glTexCoord2f( 1.0f, 1.0f ); gl.glVertex2f( radius,  radius);
		gl.glTexCoord2f( 1.0f, 0.0f ); gl.glVertex2f( radius, -radius);
		gl.glEnd();
		gl.glPopMatrix();
		
		gl.glBindTexture( GL.GL_TEXTURE_2D, 0 );
		gl.glPopAttrib();
		context.setDefaultBlendMode( gl );
	}

	@Override
	public void destroy(GL gl,  IRenderingContext context) {
		texture.destroy(gl);
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
