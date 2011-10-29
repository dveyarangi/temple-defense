package yarangi.game.temple.model.temple;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;

import yarangi.game.temple.model.resource.Port;
import yarangi.game.temple.model.resource.Resource;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.QServices;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.graphics.veils.IsoheightVeil;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class ShieldLook implements Look <ShieldEntity> 
{


//	private IsoheightVeil veil;
	private IVeil veil;
	
	private static Texture texture;
	
	@Override
	public void init(GL gl, ShieldEntity entity, IRenderingContext context) {
		

		veil = context.<IsoheightVeil> getPlugin( IsoheightVeil.NAME );
		if(veil == null)
		{
			QServices.rendering.warn( "Plugin [" + IsoheightVeil.NAME + "] requested by look [" + this.getClass() + "] is not available."  );
			veil = IVeil.ORIENTING;
		}
		
		if(texture != null)
			return;
		BufferedImage image;
		try
		{
			image = ImageIO.read(getClass().getResourceAsStream("/textures/radial_blue_512x512.png"));
//			image = ImageIO.read(getClass().getResourceAsStream("/textures/hairy_gradient.png"));
		} catch ( IOException e )
		{
			throw new IllegalArgumentException("file not found");
		}
		texture = TextureIO.newTexture(image, false);
		texture.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
		
		texture.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);		
		
//		veil = context.getPlugin( IsoheightVeil.NAME );	}
	}
	@Override
	public void render(GL gl, double time, ShieldEntity entity,
			IRenderingContext context) {
		gl.glBlendFunc(GL.GL_ONE, GL.GL_ONE);
		gl.glBlendEquation(GL.GL_FUNC_ADD);
		
		Port port = entity.getPort();
		double resourcePercent = port.get( Resource.Type.ENERGY ).getAmount() / port.getCapacity( Resource.Type.ENERGY );
		float radius = 1*(float)(entity.getArea().getMaxRadius() * (resourcePercent + 0.6f));
		texture.bind();
		
		
		gl.glBegin(GL.GL_QUADS);
		gl.glTexCoord2f( 0.0f, 0.0f ); gl.glVertex2f(-radius, -radius);
		gl.glTexCoord2f( 0.0f, 1.0f ); gl.glVertex2f(-radius,  radius);
		gl.glTexCoord2f( 1.0f, 1.0f ); gl.glVertex2f( radius,  radius);
		gl.glTexCoord2f( 1.0f, 0.0f ); gl.glVertex2f( radius, -radius);
		gl.glEnd();
		
		gl.glBindTexture( GL.GL_TEXTURE_2D, 0 );

	}

	@Override
	public void destroy(GL gl, ShieldEntity entity, IRenderingContext context) {
	//	texture.dispose();
	}

	@Override
	public float getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isCastsShadow() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public IVeil getVeil() { return veil; }
}
