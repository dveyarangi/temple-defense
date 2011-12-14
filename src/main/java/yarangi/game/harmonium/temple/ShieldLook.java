package yarangi.game.harmonium.temple;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;

import yarangi.game.harmonium.environment.resources.Port;
import yarangi.game.harmonium.environment.resources.Resource;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.Q;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.graphics.veils.IsoheightVeil;
import yarangi.numbers.RandomUtil;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class ShieldLook implements Look <Shield> 
{

	
	private float a1 = (float)RandomUtil.getRandomDouble( 0.1 );
	private float a2 = (float)RandomUtil.getRandomDouble( 0.1 );

//	private IsoheightVeil veil;
	private IVeil veil;
	
	private static Texture texture;
	private static int count = 0;

	@Override
	public void init(GL gl, Shield entity, IRenderingContext context) {
		

		veil = context.<IsoheightVeil> getPlugin( IsoheightVeil.NAME );
		if(veil == null)
		{
			Q.rendering.warn( "Plugin [" + IsoheightVeil.NAME + "] requested by look [" + this.getClass() + "] is not available."  );
			veil = IVeil.ORIENTING;
		}
		
		Port port = entity.getPort();
		double resourcePercent = port.get( Resource.Type.ENERGY ).getAmount() / port.getCapacity( Resource.Type.ENERGY );
		
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
			texture = TextureIO.newTexture(image, false);
			texture.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
			
			texture.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
		}
		count ++;
		
//		veil = context.getPlugin( IsoheightVeil.NAME );	}
	}
	@Override
	public void render(GL gl, double time, Shield entity,
			IRenderingContext context) {
		gl.glPushAttrib( GL.GL_COLOR_BUFFER_BIT );
		gl.glBlendFunc(GL.GL_ONE, GL.GL_ONE);
		gl.glBlendEquation(GL.GL_FUNC_ADD);
		float radius = (float)entity.getArea().getMaxRadius();

//	System.out.println(force + " : " + speed + " : " + radius + " : " + time);
		texture.bind();
		
		gl.glPushMatrix();
		gl.glRotatef( a1, 0, 0, 1 );
		a1 += 0.005*time;
		gl.glBegin(GL.GL_QUADS);
		gl.glTexCoord2f( 0.0f, 0.0f ); gl.glVertex2f(-radius, -radius);
		gl.glTexCoord2f( 0.0f, 1.0f ); gl.glVertex2f(-radius,  radius);
		gl.glTexCoord2f( 1.0f, 1.0f ); gl.glVertex2f( radius,  radius);
		gl.glTexCoord2f( 1.0f, 0.0f ); gl.glVertex2f( radius, -radius);
		gl.glEnd();
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glRotatef( a2, 0, 0, 1 );
		a2 -= 0.005*time;
		gl.glBegin(GL.GL_QUADS);
		gl.glTexCoord2f( 0.0f, 0.0f ); gl.glVertex2f(-radius, -radius);
		gl.glTexCoord2f( 0.0f, 1.0f ); gl.glVertex2f(-radius,  radius);
		gl.glTexCoord2f( 1.0f, 1.0f ); gl.glVertex2f( radius,  radius);
		gl.glTexCoord2f( 1.0f, 0.0f ); gl.glVertex2f( radius, -radius);
		gl.glEnd();
		gl.glPopMatrix();
		
		gl.glBindTexture( GL.GL_TEXTURE_2D, 0 );
		gl.glPopAttrib();
	}

	@Override
	public void destroy(GL gl, Shield entity, IRenderingContext context) {
		count --;
		if(count == 0)
		{
			texture.dispose();
			texture = null; //
		}
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
}
