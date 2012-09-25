package yarangi.game.harmonium.temple.debug;

import javax.media.opengl.GL;

import yarangi.fragments.CircleSegmentList;
import yarangi.game.harmonium.temple.Shield;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.objects.ILook;
import yarangi.intervals.AngleInterval;
import yarangi.math.IVector2D;

public class ShieldDebugLook implements ILook <Shield>
{

	@Override
	public void init(GL gl, IRenderingContext context){}

	@Override
	public void render(GL gl, Shield entity, IRenderingContext context)
	{
		CircleSegmentList segments = entity.getExcludedSegments();
		
		for(AngleInterval segment : segments.getRegularList())
		{
			drawSegment(gl, segment, entity.getArea().getAnchor(), entity.getArea().getMaxRadius());
		}
		
	}
	
	private void drawSegment(GL gl, AngleInterval segment, IVector2D center, double radius) {
		
		double da = (segment.getMax()- segment.getMin())/10.;
		gl.glBegin( GL.GL_LINE_STRIP );
		for(double a = segment.getMin(); a < segment.getMax(); a += da)
			gl.glVertex2d( center.x() + radius*Math.cos( a ), center.x() + radius*Math.sin( a ) );
		gl.glEnd();
			
	}

	@Override
	public void destroy(GL gl,IRenderingContext context){}

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
	public boolean isOriented() { return true; }
}
