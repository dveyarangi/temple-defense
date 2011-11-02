package yarangi.game.temple.model.temple.platforms;

import javax.media.opengl.GL;

import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.objects.Look;


public class WeaponPlatformLook implements Look <WeaponPlatform>
{

	public void render(GL gl, double time, WeaponPlatform platform, IRenderingContext context) {
		// TODO Add look
		
	}

	public void init(GL gl, WeaponPlatform entity, IRenderingContext context) {
		// TODO Auto-generated method stub
		
	}

	public void destroy(GL gl, WeaponPlatform entity, IRenderingContext context) {
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
	@Override
	public IVeil getVeil() { return IVeil.ORIENTING; }

}
