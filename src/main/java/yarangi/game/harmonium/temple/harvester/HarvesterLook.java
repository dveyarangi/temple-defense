package yarangi.game.harmonium.temple.harvester;

import javax.media.opengl.GL;

import yarangi.graphics.lights.CircleLightLook;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.math.Angles;

public class HarvesterLook implements Look <Harvester>
{

	@Override
	public void init(GL gl, Harvester entity, IRenderingContext context)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GL gl, double time, Harvester entity, IRenderingContext context)
	{
		float x, y;
		gl.glBegin( GL.GL_LINE_STRIP );
		for(double a = 0; a <= Angles.PI_2; a += Angles.PI_div_40)
		{
			x = (float)(entity.getSensor().getRadius() * Math.cos( a ));
			y = (float)(entity.getSensor().getRadius() * Math.sin( a ));
			gl.glVertex2f( x, y );
		}
		gl.glEnd();
			
		gl.glBegin( GL.GL_LINE_STRIP );
		for(double a = 0; a <= Angles.PI_2; a += Angles.PI_div_20)
		{
			x = (float)(entity.getArea().getMaxRadius() * Math.cos( a ));
			y = (float)(entity.getArea().getMaxRadius() * Math.sin( a ));
			gl.glVertex2f( x, y );
		}
		gl.glEnd();
	}

	@Override
	public void destroy(GL gl, Harvester entity, IRenderingContext context)
	{
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
	public IVeil getVeil()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
