package yarangi.game.harmonium.temple.debug;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import yar.quadraturin.IRenderingContext;
import yar.quadraturin.IVeil;
import yar.quadraturin.objects.ILook;
import yarangi.fragments.CircleSegmentList;
import yarangi.game.harmonium.temple.shields.Shield;
import yarangi.intervals.AngleInterval;
import yarangi.math.IVector2D;

public class ShieldDebugLook implements ILook <Shield>
{

	@Override
	public void init(IRenderingContext context){}

	@Override
	public void render(Shield entity, IRenderingContext ctx)
	{
		GL2 gl = ctx.gl();
		
		CircleSegmentList segments = entity.getExcludedSegments();
		
		for(AngleInterval segment : segments.getRegularList())
		{
			drawSegment(gl, segment, entity.getArea().getAnchor(), entity.getArea().getMaxRadius());
		}
		
	}
	
	private void drawSegment(GL gl1, AngleInterval segment, IVector2D center, double radius) {
		
		GL2 gl = gl1.getGL2();
		double da = (segment.getMax()- segment.getMin())/10.;
		gl.glBegin( GL.GL_LINE_STRIP );
		for(double a = segment.getMin(); a < segment.getMax(); a += da)
			gl.glVertex2d( center.x() + radius*Math.cos( a ), center.x() + radius*Math.sin( a ) );
		gl.glEnd();
			
	}

	@Override
	public void destroy(IRenderingContext ctx){}

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
