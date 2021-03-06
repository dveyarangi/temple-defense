package yarangi.game.harmonium.temple.structure;

import yar.quadraturin.IRenderingContext;
import yar.quadraturin.IVeil;
import yar.quadraturin.objects.ILook;
import yarangi.game.harmonium.environment.resources.Port;
import yarangi.spatial.Area;

public class PowerConnectorLook implements ILook <PowerConnector> 
{

	@Override
	public void init(IRenderingContext context) {}

	@Override
	public void render(PowerConnector entity, IRenderingContext ctx) 
	{
		
		Area sourceArea = entity.getSource().getServiceArea();
		Area targetArea = entity.getTarget().getServiceArea();
		Port sourcePort = entity.getSource().getPort();
		Port targetPort = entity.getTarget().getPort();
		
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
	public void destroy(IRenderingContext ctx) {}

	@Override
	public float getPriority()
	{
		return 0;
	}

	@Override
	public boolean isCastsShadow()
	{
		return false;
	}
	@Override
	public IVeil getVeil() { return null; }

	@Override
	public boolean isOriented()
	{
		return true;
	}

}
