package yarangi.game.temple.model.enemies.swarm;

import javax.media.opengl.GL;

import yarangi.graphics.quadraturin.RenderingContext;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.math.Vector2D;

public class SwarmDebugOverlay implements Look <Swarm> 
{

	@Override
	public void init(GL gl, Swarm entity) {
		// TODO Auto-generated method stub
		
	}
	
	private float toAlpha(IBeacon beacon)
	{
		return beacon.getDangerFactor() >= Swarm.MAX_DANGER_FACTOR ? 1 :(float)((beacon.getDangerFactor()))/(float)Swarm.MAX_DANGER_FACTOR;

	}

	@Override
	public void render(GL gl, double time, Swarm entity,
			RenderingContext context) {
		
		if(context.isForEffect())
			return;
		float size = entity.getCellSize();
		float hsize = entity.getCellSize()/2;
		float min = -entity.getWorldSize()/2 * entity.getCellSize();
		float max = entity.getWorldSize()-1;
//		gl.glShadeModel(GL.GL_FLAT);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		// shadow blending setting:
//		gl.glBlendFunc(GL.GL_ONE, GL.GL_ONE);
//		gl.glBlendFunc(GL.GL_SRC_COLOR, GL.GL_DST_COLOR);
//		gl.glBlendEquation( GL.GL_MAX );
		float x, y;
		float prevTop = 0, prevBottom = 0;
		float currTop, currBottom;
		for(int j = 1; j < max; j ++)
		{
			y = (float)entity.toBeaconCoord(j);// + 2f*size;
				for(int i = 1; i < max; i ++)
				{
					x = (float)entity.toBeaconCoord(i);//+2f*size;
				
				IBeacon beacon = entity.getBeacons()[i][j];  
				                                                 
				float curr = toAlpha(beacon);
//				float topLeft     = toAlpha(entity.getBeacons()[i-1][j+1]);
//				float top         = toAlpha(entity.getBeacons()[i]  [j+1]);
//				float topRight    = toAlpha(entity.getBeacons()[i+1][j+1]);
				float right       = toAlpha(entity.getBeacons()[i+1][j]  );
				float bottomRight = toAlpha(entity.getBeacons()[i+1][j-1]);
				float bottom      = toAlpha(entity.getBeacons()[i]  [j-1]);
				float bottomLeft  = toAlpha(entity.getBeacons()[i-1][j-1]);
				float left        = toAlpha(entity.getBeacons()[i-1][j]  );
//				float red = beacon.isUnpassable() ? 1.0f : 0.3f; 
//				curr = beacon.isUnpassable() ? 1.f : curr;
//				if(beacon1.getDangerFactor() != 0)
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
					gl.glBegin(GL.GL_QUADS);
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
					gl.glEnd();
					Vector2D flow = beacon.getFlow().mul(10);
					Vector2D l = beacon.getFlow().left();
					gl.glColor4f(0,1,1,0.25f);
					gl.glBegin(GL.GL_TRIANGLES);
					gl.glVertex3f((float)(x+l.x()), (float)(y+l.y()), 0);
					gl.glVertex3f((float)(x+flow.x()), (float)(y+flow.y()), 0);
					gl.glVertex3f((float)(x-l.x()), (float)(y-l.y()), 0);
					gl.glEnd();
					
					if(beacon.isUnpassable())
					{
						gl.glColor4f(0.3f, 0, 0.7f, 0.7f);
						gl.glBegin(GL.GL_QUADS);
						gl.glVertex3f((float)(x-hsize), (float)(y-hsize), 0);
						gl.glVertex3f((float)(x-hsize), (float)(y+hsize), 0);
						gl.glVertex3f((float)(x+hsize), (float)(y+hsize), 0);
						gl.glVertex3f((float)(x+hsize), (float)(y-hsize), 0);
						gl.glEnd();
					}
				}
//				else
//					currTop = currBottom = 0;
			}
		}
		gl.glBlendEquation(GL.GL_FUNC_ADD);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
						
		
		gl.glPopMatrix();
	}

	@Override
	public void destroy(GL gl, Swarm entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCastsShadow() { return false; }
	@Override
	public float getPriority() { return 1; }

}
