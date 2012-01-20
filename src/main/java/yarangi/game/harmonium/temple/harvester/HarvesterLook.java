package yarangi.game.harmonium.temple.harvester;

import javax.media.opengl.GL;

import yarangi.graphics.lights.CircleLightLook;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.spatial.Tile;

public class HarvesterLook implements Look <Harvester>
{

	@Override
	public void init(GL gl, Harvester entity, IRenderingContext context)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GL gl, double time, Harvester entity, IRenderingContext context)
	{
		
		float x, y;
/*		gl.glEnable(GL.GL_BLEND);
		gl.glColor4f( 0f, 1f, 0f, 0.3f );
		gl.glBegin( GL.GL_LINE_STRIP );
		for(double a = 0; a <= Angles.PI_2; a += Angles.PI_div_40)
		{
			x = (float)(entity.getSensor().getRadius() * Math.cos( a ));
			y = (float)(entity.getSensor().getRadius() * Math.sin( a ));
			gl.glVertex2f( x, y );
		}
		gl.glEnd();*/
			
		gl.glBegin( GL.GL_LINE_STRIP );
		gl.glColor4f( 0f, 1f, 0f, 0.7f );
		for(double a = 0; a <= Angles.PI_2; a += Angles.PI_div_20)
		{
			x = (float)(entity.getArea().getMaxRadius() * Math.cos( a ));
			y = (float)(entity.getArea().getMaxRadius() * Math.sin( a ));
			gl.glVertex2f( x, y );
		}
		gl.glEnd();
		
	/*	ErrodingBehavior beh = (ErrodingBehavior)entity.getBehavior();
		Tile tile = beh.getErrodedTile();
		Vector2D c = entity.getArea().getRefPoint();
		if(tile != null) {
			gl.glBegin( GL.GL_LINE_STRIP );
			gl.glVertex2f( (float)(tile.getMinX()-c.x()), (float)(tile.getMinY()-c.y()));
			gl.glVertex2f( (float)(tile.getMinX()-c.x()), (float)(tile.getMaxY()-c.y()));
			gl.glVertex2f( (float)(tile.getMaxX()-c.x()), (float)(tile.getMaxY()-c.y()));
			gl.glVertex2f( (float)(tile.getMaxX()-c.x()), (float)(tile.getMinY()-c.y()));
			gl.glVertex2f( (float)(tile.getMinX()-c.x()), (float)(tile.getMinY()-c.y()));
			gl.glEnd();
		}*/
			
	}

	@Override
	public void destroy(GL gl, Harvester entity, IRenderingContext context)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getPriority()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isCastsShadow()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IVeil getVeil()
	{
		// TODO Auto-generated method stub
		return IVeil.ORIENTING;
	}

}
