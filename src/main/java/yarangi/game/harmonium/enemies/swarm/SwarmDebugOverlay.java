package yarangi.game.harmonium.enemies.swarm;

import java.awt.Point;

import javax.media.opengl.GL;

import yarangi.graphics.grid.TileGridLook;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.math.Vector2D;
import yarangi.spatial.Tile;

public class SwarmDebugOverlay extends TileGridLook <Beacon, Swarm>
{
	public SwarmDebugOverlay(boolean depthtest, boolean blend, Swarm swarm) {
		super( swarm, depthtest, blend );
	}

	@Override
	public boolean isCastsShadow() { return false; }
	
	@Override
	public float getPriority() { return 1; }
	


	@Override
	protected void renderTile(GL gl, IRenderingContext context, Tile<Beacon> tile, Swarm swarm, int scale)
	{
        if(tile == null)
        	return;
        
		float size = swarm.getCellSize();
		float hsize = swarm.getCellSize()/2;
        Beacon beacon = tile.get();
        double x = tile.getX();
        double y = tile.getY();
		float curr = toAlpha(beacon);
//		float topLeft     = toAlpha(entity.getBeacons()[i-1][j+1]);
//		float top         = toAlpha(entity.getBeacons()[i]  [j+1]);
//		float topRight    = toAlpha(entity.getBeacons()[i+1][j+1]);
//		float right       = toAlpha(swarm.getBeacons()[i+1][j]  );
//		float bottomRight = toAlpha(swarm.getBeacons()[i+1][j-1]);
//		float bottom      = toAlpha(swarm.getBeacons()[i]  [j-1]);
//		float bottomLeft  = toAlpha(swarm.getBeacons()[i-1][j-1]);
//		float left        = toAlpha(swarm.getBeacons()[i-1][j]  );
//		float red = beacon.isUnpassable() ? 1.0f : 0.3f; 
		curr = beacon.isUnpassable() ? 1.f : curr;
//		if(beacon1.getDangerFactor() != 0)
		{
			
/*					gl.glBegin(GL.GL_POLYGON);
			gl.glColor4f(0.3f, 0, 0.7f+curr, curr*0.75f);
			gl.glVertex2f(x, y);
			gl.glColor4f(0.3f, 0, 0.7f+top, top*0.75f);
			gl.glVertex2f(x, y+size);
			gl.glColor4f(0.3f, 0, 0.7f+topLeft, topLeft*0.75f);
			gl.glVertex2f(x-size, y+size);
			gl.glColor4f(0.3f, 0, 0.7f+left, left*0.75f);
			gl.glVertex2f(x-size, y);
			gl.glEnd();
			gl.glBegin(GL.GL_POLYGON);
			gl.glColor4f(0.3f, 0, 0.7f+curr, curr*0.75f);
			gl.glVertex2f(x, y);
			gl.glColor4f(0.3f, 0, 0.7f+right, right*0.75f);
			gl.glVertex2f(x+size, y);
			gl.glColor4f(0.3f, 0, 0.7f+topRight, topRight*0.75f);
			gl.glVertex2f(x+size, y+size);
			gl.glColor4f(0.3f, 0, 0.7f+top, top*0.75f);
			gl.glVertex2f(x, y+ size);
			gl.glEnd();*/
/*			gl.glBegin(GL.GL_QUADS);
			gl.glColor4f(0.7f+curr, 0, 0.3f, curr);
			gl.glVertex2f(x, y);
			gl.glColor4f(0.7f+bottom, 0, 0.3f, bottom);
			gl.glVertex2f(x, y-size);
			gl.glColor4f(0.7f+bottomRight, 0, 0.3f, bottomRight);
			gl.glVertex2f(x+size, y-size);
			gl.glColor4f(0.7f+right, 0, 0.3f, right);
			gl.glVertex2f(x+size, y);
			gl.glEnd();
			gl.glBegin(GL.GL_QUADS);
			gl.glColor4f(0.7f+curr, 0, 0.3f, curr);
			gl.glVertex2f(x, y);
			gl.glColor4f(0.7f+left, 0, 0.3f, left);
			gl.glVertex2f(x-size, y);
			gl.glColor4f(0.7f+bottomLeft, 0, 0.3f, bottomLeft);
			gl.glVertex2f(x-size, y-size);
			gl.glColor4f(0.7f+bottom, 0, 0.3f, bottom);
			gl.glVertex2f(x, y-size);
			gl.glEnd();*/
			Vector2D flow = beacon.getFlow().mul(5);
			Vector2D l = beacon.getFlow().left();
			
			gl.glPushAttrib( GL.GL_COLOR_BUFFER_BIT );
			gl.glBlendEquation( GL.GL_MIN );
			gl.glBlendFunc( GL.GL_ONE, GL.GL_ONE );
			gl.glColor4f(0f, 0, 0f, 1f);
			gl.glBegin(GL.GL_QUADS);
			gl.glVertex3f((float)(x), (float)(y), 0);
			gl.glVertex3f((float)(x), (float)(y+size), 0);
			gl.glVertex3f((float)(x+size), (float)(y+size), 0);
			gl.glVertex3f((float)(x+size), (float)(y), 0);
			gl.glEnd();
			gl.glBlendEquation( GL.GL_FUNC_ADD );
//			gl.glBlendEquationSeparate(GL.GL_FUNC_ADD, GL.GL_FUNC_ADD);
			gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
			
			gl.glColor4f(0,1,1,0.65f);
			gl.glBegin(GL.GL_TRIANGLES);
			gl.glVertex3f((float)(x+hsize+l.x()-flow.x()), (float)(y+hsize+l.y()-flow.y()), 0);
			gl.glVertex3f((float)(x+hsize+flow.x()), (float)(y+hsize+flow.y()), 0);
			gl.glVertex3f((float)(x+hsize-l.x()-flow.x()), (float)(y+hsize-l.y()-flow.y()), 0);
			gl.glEnd();
			
			gl.glColor4f(0.3f, 0, 0.7f+curr, curr*1f);
//			System.out.println(curr);
			gl.glBegin(GL.GL_QUADS);
			gl.glVertex3f((float)(x), (float)(y), 0);
			gl.glVertex3f((float)(x), (float)(y+size), 0);
			gl.glVertex3f((float)(x+size), (float)(y+size), 0);
			gl.glVertex3f((float)(x+size), (float)(y), 0);
			gl.glEnd();
			
/*			if(beacon.isUnpassable())
			{
				gl.glColor4f(0.3f, 0, 0.7f, 0.75f);
				gl.glBegin(GL.GL_QUADS);
				gl.glVertex3f((float)(x), (float)(y), 0);
				gl.glVertex3f((float)(x), (float)(y+size), 0);
				gl.glVertex3f((float)(x+size), (float)(y+size), 0);
				gl.glVertex3f((float)(x+size), (float)(y), 0);
				gl.glEnd();
			}*/
//			gl.glEnable( GL.GL_DEPTH_TEST );
			gl.glPopAttrib();
		}
		context.setDefaultBlendMode( gl );
	}
	
	private float toAlpha(IBeacon beacon)
	{
		return beacon.getDangerFactor() >= Swarm.MAX_DANGER_FACTOR ? 1 :(float)((beacon.getDangerFactor()))/(float)Swarm.MAX_DANGER_FACTOR;

	}
	@Override
	public IVeil getVeil() { return null; }

	@Override
	public boolean isOriented() { return true; }
	
	@Override
	protected Point getFBODimensions(IRenderingContext context, Swarm grid)
	{
		return new Point(grid.getWorldSize(), grid.getWorldSize());
	}


}
