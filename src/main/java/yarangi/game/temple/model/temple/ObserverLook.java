package yarangi.game.temple.model.temple;

import javax.media.opengl.GL;

import yarangi.game.temple.model.weapons.Projectile;
import yarangi.graphics.colors.Color;
import yarangi.graphics.lights.CircleLightLook;
import yarangi.graphics.quadraturin.RenderingContext;
import yarangi.math.Angles;
import yarangi.spatial.ISpatialFilter;
import yarangi.spatial.ISpatialObject;

public class ObserverLook extends CircleLightLook<ObserverEntity> 
{
	public ObserverLook(ISpatialFilter filter) {
		super(filter);
	}

	public void render(GL gl, double time, ObserverEntity entity, RenderingContext context) 
	{
		if(entity.getEntities() == null)
			return;
		super.render(gl, time, entity, context);
		
		Color color = entity.getColor();
		for(ISpatialObject e : entity.getEntities().keySet())
		{
			if(e instanceof Projectile)
				continue;
			double distance = entity.getEntities().get(e);
	//		gl.glPushMatrix();
//			gl.glTranslatef((float)(e.getAABB().x-entity.getAABB().x), (float)(e.getAABB().y-entity.getAABB().y), 0);
			gl.glBegin(GL.GL_LINE_STRIP);
			gl.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), (float)(1- Math.sqrt(distance)/entity.getSensorRadius()));
			for(double a = 0; a <= 6; a ++)
			{
				gl.glVertex3f((float)((e.getAABB().x-entity.getAABB().x) + e.getAABB().r*Math.cos(a*Angles.PI_div_3)), 
						      (float)((e.getAABB().y-entity.getAABB().y) + e.getAABB().r*Math.sin(a*Angles.PI_div_3)),1);
			}
			gl.glEnd();
		}
		
/*		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glColor4f(0.2f, 1.0f, 0.3f, 0.2f);
		for(double a = 0; a <= Angles.PI_2; a += Angles.PI_div_20)
		{
			gl.glVertex3f((float)(entity.getSensorRadius()*Math.cos(a)), 
					      (float)(entity.getSensorRadius()*Math.sin(a)),1);
		}
		gl.glEnd();*/

	}
}
