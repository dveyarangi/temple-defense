package yarangi.game.temple.model.temple;

import javax.media.opengl.GL;

import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.objects.Look;

public class ShieldLook implements Look <ShieldEntity> 
{


	public void render(GL gl, double time, ShieldEntity shield, IRenderingContext context) 
	{

	
/*		for(ForcePoint forcePoint : shield.getForcePoints())
		{
			if(forcePoint == null)
				continue;
			
			double shieldRadius = shield.getAABB().getBoundingRadius();
			
			gl.glColor4f(0.7f, 0.5f, 1.0f,(float)forcePoint.strength);
			gl.glBegin(GL.GL_LINE_STRIP);
			for(double a = forcePoint.angle-0.2; a < forcePoint.angle+0.2; a += 0.005)
				gl.glVertex3f((float)(shieldRadius*Math.cos(a)), (float)(shieldRadius*Math.sin(a)), 0);
			gl.glEnd();
			
		}*/

	}

	public void init(GL gl, ShieldEntity entity, IRenderingContext context) {
		// TODO Auto-generated method stub
		
	}

	public void destroy(GL gl, ShieldEntity entity, IRenderingContext context) {
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
