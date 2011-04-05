package yarangi.game.temple.model.temple.platforms;

import javax.media.opengl.GL;

import yarangi.game.temple.model.temple.Hexagon;
import yarangi.game.temple.model.temple.MeshNode;
import yarangi.game.temple.model.temple.Platform;
import yarangi.graphics.quadraturin.RenderingContext;
import yarangi.graphics.quadraturin.objects.Look;

public class PlatformLook implements Look <Platform>
{


	public void render(GL gl, double time, Platform platform, RenderingContext context) 
	{
		Hexagon hexagon = platform.getHexagon();
//		gl.glColor3f(0.5f, 0.2f, 1.0f);
		gl.glColor3f(1.0f, 0.8f, 0.0f);
		gl.glBegin(GL.GL_POLYGON);
		for(int idx = 0; idx <= 6; idx ++)
		{
			
			MeshNode node = hexagon.getMeshNode(idx);
//			Vector2D l = temple.toTempleCoordinates(node.getLocation());
			gl.glVertex3f((float)node.getLocation().x, (float)node.getLocation().y, 0);

		}			
		gl.glEnd();
	}

	public void init(GL gl, Platform entity) {
	}

	public void destroy(GL gl, Platform entity) {
		// TODO Auto-generated method stub
		
	}

}
