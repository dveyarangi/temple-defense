package yarangi.game.temple.model.weapons;

import java.awt.Color;

import javax.media.opengl.GL;

import yarangi.graphics.quadraturin.RenderingContext;
import yarangi.graphics.quadraturin.objects.Look;

public class LightningBeamLook implements Look <LightningBeam> 
{
	public static Color beamColor = new Color(0,255,127, 255);


	public void render(GL gl, double time, LightningBeam beam, RenderingContext context) 
	{
//		TODO: not working
/*		LightningBeamNode beamNode = null;
		LightningBeamNode prevNode = null;
		for(Iterator<LightningBeamNode>it = beam.getNodes().iterator();
			it.hasNext(); prevNode = beamNode, beamNode = it.next())
			if(prevNode != null)
			Lines.drawGlowingLine(gl, prevNode.x, prevNode.y, beamNode.x, beamNode.y, 0.1, 1, beamColor);*/
/*		if(beam.prev != null)
		{
			gl.glColor4f(0.0f, 0.4f, 0.9f, 1.0f);
			gl.glBegin(GL.GL_LINE_STRIP);
			
//			System.out.println(beam + " ::: " + beam.prev);
			System.out.println(beam.curr.y-beam.prev.y);
			gl.glVertex3f((float)(beam.curr.x - beam.prev.x), (float)(beam.curr.y-beam.prev.y), 0);
			gl.glVertex3f(0, 0, 0);
			gl.glEnd();
		}*/
	}


	public void init(GL gl, LightningBeam entity) {
		// TODO Auto-generated method stub
		
	}


	public void destroy(GL gl, LightningBeam entity) {
		// TODO Auto-generated method stub
		
	}

}
