package yarangi.game.temple.model.temple.structure;

import javax.media.opengl.GL;

import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.math.Vector2D;
import yarangi.spatial.AABB;

public class PowerConnectorLook implements Look <PowerConnector> 
{

	@Override
	public void init(GL gl, PowerConnector entity, IRenderingContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GL gl, double time, PowerConnector entity, IRenderingContext context) 
	{
		
/*		AABB aabb = entity.getAABB();
		gl.glColor4f(0.0f, 1.0f, 0.0f, 0.5f);
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex2f((float)aabb.x-1, (float)aabb.y-1);
		gl.glVertex2f((float)aabb.x-1, (float)aabb.y+1);
		gl.glVertex2f((float)aabb.x+1, (float)aabb.y+1);
		gl.glVertex2f((float)aabb.x+1, (float)aabb.y-1);
		gl.glVertex2f((float)aabb.x-1, (float)aabb.y-1);
		gl.glEnd();
		
		for(PowerLine line : entity.getPaths())
		{
			gl.glBegin(GL.GL_LINE_STRIP);
//			System.out.println("===");
			for(Vector2D loc : line)
			{
				gl.glVertex2f((float)loc.x, (float)loc.y);
//				System.out.println(loc);
			}
			gl.glEnd();
		}*/
	}

	@Override
	public void destroy(GL gl, PowerConnector entity, IRenderingContext context) {
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
