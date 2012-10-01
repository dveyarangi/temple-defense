package yarangi.game.harmonium.environment.terrain;

import java.util.Iterator;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.objects.ILook;
import yarangi.spatial.PolygonArea;
import yarangi.spatial.PolygonArea.PolyPoint;

public class MatterLook implements ILook <Matter> 
{

	@Override
	public void init(GL gl, IRenderingContext context) { }

	@Override
	public void render(GL gl1, Matter entity, IRenderingContext context) {
		GL2 gl = gl1.getGL2();
		PolygonArea polygon = (PolygonArea) entity.getArea();
		
		Iterator <PolyPoint> polyit =  polygon.getPoints().iterator();
		PolyPoint start, curr = start = polyit.next();
		
		gl.glBegin(GL2.GL_POLYGON);
		gl.glColor4f(0.5f,0.2f,0.2f, 1.0f);
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
	public void destroy(GL gl, IRenderingContext context) { }

	@Override
	public boolean isCastsShadow() { return true; }
	@Override
	public float getPriority() { return 1; }
	@Override
	public IVeil getVeil() { return null; }

	@Override
	public boolean isOriented() { return true; }}
