package yarangi.game.temple.model.temple.debug;

import javax.media.opengl.GL;

import yarangi.fragments.CircleSegmentList;
import yarangi.game.temple.model.temple.Shield;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.intervals.AngleInterval;
import yarangi.math.Vector2D;

public class ShieldDebugLook implements Look <Shield>
{

	@Override
	public void init(GL gl, Shield entity, IRenderingContext context)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GL gl, double time, Shield entity, IRenderingContext context)
	{
		CircleSegmentList segments = entity.getExcludedSegments();
		
		for(AngleInterval segment : segments.getRegularList())
		{
			drawSegment(gl, segment, entity.getArea().getRefPoint(), entity.getArea().getMaxRadius());
		}
		
		// TODO Auto-generated method stub
		
	}
	
	private void drawSegment(GL gl, AngleInterval segment, Vector2D center, double radius) {
		
		double da = (segment.getMax()- segment.getMin())/10.;
		gl.glBegin( GL.GL_LINE_STRIP );
		for(double a = segment.getMin(); a < segment.getMax(); a += da)
			gl.glVertex2d( center.x() + radius*Math.cos( a ), center.x() + radius*Math.sin( a ) );
		gl.glEnd();
			
	}

	@Override
	public void destroy(GL gl, Shield entity, IRenderingContext context)
	{
	}

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
	public IVeil getVeil()
	{
		return IVeil.ORIENTING;
	}

}
