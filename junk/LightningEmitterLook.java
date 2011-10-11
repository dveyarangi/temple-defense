package yarangi.game.temple.model.weapons;

import javax.media.opengl.GL;

import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.objects.Look;

public class LightningEmitterLook implements Look <LightningEmitter>
{

	public void render(GL gl, double time, LightningEmitter emitter, IRenderingContext context) {
		// TODO Add look
		
	}

	public void init(GL gl, LightningEmitter entity, IRenderingContext context) {
		// TODO Auto-generated method stub
		
	}

	public void destroy(GL gl, LightningEmitter entity, IRenderingContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getPriority()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isCastsShadow()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
