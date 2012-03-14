package yarangi.game.harmonium.temple;

import javax.media.opengl.GL;

import yarangi.graphics.colors.Color;
import yarangi.graphics.lights.CircleLightLook;
import yarangi.graphics.quadraturin.IRenderingContext;

public class ObserverLook extends CircleLightLook<IObserverEntity> 
{
	public ObserverLook(Color color) {
		super(color);
	}

	public void render(GL gl, double time, IObserverEntity entity, IRenderingContext context) 
	{	
		super.render(gl, time, entity, context);

/*		Color color = getColor();
		Vector2D observerLoc = entity.getArea().getRefPoint();
		Vector2D targetLoc;
//		gl.glDisable( GL.GL_DEPTH_TEST );
		for(IWorldEntity e : sensor.getEntities())
		{
			if(e instanceof SwarmAgent)
			{
			
				targetLoc = e.getArea().getRefPoint();
				double distance = Geometry.calcHypotSquare(observerLoc, targetLoc);
				float alpha = (float)(1. - distance/entity.getSensor().getSensorRadiusSquare())/4;
				if(alpha < 0.01)
					continue;
		//		gl.glPushMatrix();
	//			gl.glTranslatef((float)(e.getAABB().x-entity.getAABB().x), (float)(e.getAABB().y-entity.getAABB().y), 0);
				gl.glBegin(GL.GL_LINE_STRIP);
				gl.glColor4f(1f, color.getGreen(), color.getBlue()/10, alpha);
				double size = e.getArea().getMaxRadius()+2;
				for(double a = 0; a <= 6; a ++)
				{
					gl.glVertex3f((float)((targetLoc.x()-observerLoc.x()) + size*Math.cos(a*Angles.PI_div_3)), 
							      (float)((targetLoc.y()-observerLoc.y()) + size*Math.sin(a*Angles.PI_div_3)), 0);
				}
				gl.glEnd();
			}
			else
				continue;
			
			if(e instanceof Projectile)
				continue;
			
			if(e instanceof Weapon)
				continue;
			if(e instanceof TempleEntity)
				continue;
			if(e instanceof ObserverEntity)
				continue;
			
		}*/
//		gl.glEnable( GL.GL_DEPTH_TEST );
		
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
