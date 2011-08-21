package yarangi.game.temple.model.terrain;

import java.util.Iterator;

import javax.media.opengl.GL;

import yarangi.graphics.quadraturin.RenderingContext;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.math.Vector2D;
import yarangi.spatial.Polygon;
import yarangi.spatial.Polygon.PolyPoint;

public class MatterLook implements Look <Matter> 
{

	@Override
	public void init(GL gl, Matter entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GL gl, double time, Matter entity,
			RenderingContext context) {
		Polygon polygon = (Polygon) entity.getArea();
		
		Iterator <PolyPoint> polyit =  polygon.getPoints().iterator();
		PolyPoint start, curr = start = polyit.next();
		
		gl.glBegin(GL.GL_POLYGON);
		gl.glColor4f(0.1f,0.2f,0.1f, 1.0f);
		gl.glVertex3f((float)curr.x(), (float)curr.y(), 0);
		while(polyit.hasNext())
		{
			curr = polyit.next();
			gl.glVertex3f((float)curr.x(), (float)curr.y(), 0);
		}
//		gl.glVertex3f((float)start.x(), (float)start.y());
		
		gl.glEnd();
	}

	@Override
	public void destroy(GL gl, Matter entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCastsShadow() { return true; }
	
}
