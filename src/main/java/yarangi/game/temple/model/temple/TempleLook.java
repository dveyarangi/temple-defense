package yarangi.game.temple.model.temple;

import javax.media.opengl.GL;

import yarangi.graphics.colors.Color;
import yarangi.graphics.lights.CircleLightLook;
import yarangi.graphics.quadraturin.IRenderingContext;

public class TempleLook extends CircleLightLook<TempleEntity>
{
	
	
	public void render(GL gl, double time,TempleEntity temple, IRenderingContext context) 
	{
		
		float P = (float)temple.getHealth();
//		this.setColor(new Color( P*0.2f+(1-P)*0.8f, 0.5f+0.5f*P, 1f*P, 1.0f));
//		this.setColor(new Color( P*0.3f+(1-P)*0.7f, (1-P)*0.5f+0.5f*P, 1f*P, 1.0f));
		this.setColor(new Color( (float)((1-P)/4+P*0.3),  (float)(P*0.3), (float)(P),1));
//		this.setColor(new Color( 1.0f, P, P, 1.0f));
		super.render( gl, time, temple, context );
		
	}

	@Override
	public boolean isCastsShadow() {
		// TODO Auto-generated method stub
		return false;
	}
	
/*	public class DrawingObserver implements HexagonObserver
	{
		private GL gl;
		private TempleStructure temple;
		public DrawingObserver(GL gl, TempleStructure temple) 
		{ 
			this.gl = gl;
			this.temple = temple;
		}
		
		public void hexagonFound(Hexagon hexagon, int x, int y) 
		{
//			gl.glColor3f(0.5f, 0.2f, 1.0f);
			
			gl.glBegin(GL.GL_LINE_STRIP);
			for(int idx = 0; idx <= 6; idx ++)
			{
				if (hexagon.getNeighbor(idx) != null)
					gl.glColor3f(0.5f, 0.2f, 1.0f);
				
				MeshNode node = hexagon.getMeshNode(idx);
//				Vector2D l = temple.toTempleCoordinates(node.getLocation());
				gl.glVertex3f((float)node.getLocation().x, (float)node.getLocation().y, 1);

			}			
			gl.glEnd();
//			System.out.println("drAawing");
			
			gl.glColor3f(0.0f, 1.0f, 0.2f);
			for(int idx = 0; idx < 6; idx ++)
			{
				MeshNode node = hexagon.getMeshNode(idx);
				Vector2D l = attr.toTempleCoordinates(node.getLocation());
				
				gl.glBegin(GL.GL_POLYGON);
				for(double angle = 0; angle < Math.PI*2; angle += Math.PI/10)
					gl.glVertex3f((float)(l.x + 0.1 * Math.cos(attr.getMainAngle()+angle)), 
								  (float)(l.y + 0.1 * Math.sin(attr.getMainAngle()+angle)), 1);
				
				gl.glEnd();
			}
		}
		
	}*/
	@Override
	public float getPriority() { return 0; }

}
