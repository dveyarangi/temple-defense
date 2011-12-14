package yarangi.game.temple.model.enemies;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;

import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.Q;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.graphics.veils.BlurVeil;
import yarangi.graphics.veils.IsoheightVeil;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class MetaCircleLook implements Look <Entity> 
{

//	private IsoheightVeil veil;
	private IVeil veil;
	
	private static Texture texture;
	
	@Override
	public void init(GL gl, Entity entity, IRenderingContext context) {
		
		if(texture != null)
			return;
		BufferedImage image;
		try
		{
			image = ImageIO.read(getClass().getResourceAsStream("/textures/red_gradient.jpg"));
//			image = ImageIO.read(getClass().getResourceAsStream("/textures/distort_orange_512x512.png"));
//			image = ImageIO.read(getClass().getResourceAsStream("/textures/hairy_gradient.png"));
		} catch ( IOException e )
		{
			throw new IllegalArgumentException("file not found");
		}
		texture = TextureIO.newTexture(image, false);
		texture.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
		
		texture.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);		
		
//		veil = context.getPlugin( IsoheightVeil.NAME );
		veil = context.<IVeil>getPlugin( IsoheightVeil.NAME );
		if(veil == null)
		{
			Q.rendering.warn( "Plugin [" + BlurVeil.NAME + "] requested by look [" + this.getClass() + "] is not available."  );
			veil = IVeil.ORIENTING;
		}
	}

	@Override
	public void render(GL gl, double time, Entity entity,
			IRenderingContext context) {
		
		gl.glPushAttrib( GL.GL_COLOR_BUFFER_BIT );
		gl.glBlendFunc(GL.GL_ONE, GL.GL_ONE);
		gl.glBlendEquation(GL.GL_FUNC_ADD);
		
		float radius = (float)(entity.getArea().getMaxRadius());
		texture.bind();
		
		
		gl.glBegin(GL.GL_QUADS);
		gl.glTexCoord2f( 0.0f, 0.0f ); gl.glVertex2f(-radius, -radius);
		gl.glTexCoord2f( 0.0f, 1.0f ); gl.glVertex2f(-radius,  radius);
		gl.glTexCoord2f( 1.0f, 1.0f ); gl.glVertex2f( radius,  radius);
		gl.glTexCoord2f( 1.0f, 0.0f ); gl.glVertex2f( radius, -radius);
		gl.glEnd();
		
		gl.glBindTexture( GL.GL_TEXTURE_2D, 0 );
		gl.glPopAttrib();
	}

	@Override
	public void destroy(GL gl, Entity entity, IRenderingContext context) {
	//	texture.dispose();
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
