package yarangi.game.temple.model.temple;

import javax.media.opengl.GL;

import yarangi.graphics.quadraturin.RenderingContext;
import yarangi.graphics.quadraturin.objects.Look;

public class TempleLook implements Look <TempleStructure> 
{
	
	
	public void render(GL gl, double time,TempleStructure temple, RenderingContext context) 
	{
		
//		temple.getCenter().getHexagon().search(new DrawingObserver(gl, temple));
//		System.out.println("drawing temple");
		double perimeterOffset = 1;//temple.getHexRadius();
		gl.glColor4f(0.5f, 0.2f, 1.0f,0.5f);
/*		gl.glBegin(GL.GL_LINE_STRIP);
		for(MeshNode node : temple.getPerimeter())
			gl.glVertex3f((float)(perimeterOffset*node.getLocation().x), (float)(perimeterOffset*node.getLocation().y), 0);
		gl.glEnd();*/

		
	}

	public void init(GL gl, TempleStructure entity) {
		// TODO Auto-generated method stub
		
	}

	public void destroy(GL gl, TempleStructure entity) {
		// TODO Auto-generated method stub
		
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

}
