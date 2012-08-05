package yarangi.game.harmonium.controllers;

import javax.media.opengl.GL;

import yarangi.game.harmonium.environment.terrain.poly.PolyTerrainLook;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.ILook;
import yarangi.graphics.quadraturin.terrain.PolygonGrid;
import yarangi.graphics.quadraturin.terrain.TilePoly;
import yarangi.math.Angles;
import yarangi.math.Vector2D;

public class OrdersActionLook implements ILook <OrdersActionController>
{
	ILook <PolygonGrid<TilePoly>> look = new PolyTerrainLook();

	@Override
	public void init(GL gl, OrdersActionController entity, IRenderingContext context)
	{
		look.init( gl, entity.getReinforcementMap(), context );
	}

	@Override
	public void render(GL gl, OrdersActionController entity, IRenderingContext context)
	{
		
		look.render( gl, entity.getReinforcementMap(), context );
		
		IEntity dragged = entity.getDragged();
		Vector2D target = entity.getTarget();
		IEntity hovered = entity.getHovered();
		if(hovered != null)
		{
			gl.glColor4f( 0, 1, 0, 0.4f );
			gl.glBegin(GL.GL_LINE_STRIP);
			double rad = hovered.getArea().getMaxRadius() + 2;
			for(double a = 0; a <= Angles.PI_2+0.001; a += Angles.PI_div_6)
				gl.glVertex3f((float)(hovered.getArea().getAnchor().x() + rad*Math.cos(a)), 
						      (float)(hovered.getArea().getAnchor().y() + rad*Math.sin(a)), 1);
			
			gl.glEnd();

		}
		if(dragged == null || target == null)
			return;
		
		Vector2D source = dragged.getArea().getAnchor();
		Vector2D radius = target.minus(dragged.getArea().getAnchor()).normalize().multiply( dragged.getArea().getMaxRadius() );
		Vector2D left = radius.left().add( source );
		Vector2D right = radius.right().add( source );
		gl.glColor4f( 0, 1, 0, 0.4f );
		gl.glBegin( GL.GL_POLYGON );
		gl.glVertex3f((float)left.x(), (float)left.y(), 0);
		gl.glVertex3f((float)right.x(), (float)right.y(), 0);
		gl.glVertex3f((float)target.x(), (float)target.y(), 0);
		gl.glEnd();
			
	}

	@Override
	public void destroy(GL gl, OrdersActionController entity, IRenderingContext context)
	{
		look.destroy( gl, entity.getReinforcementMap(), context );
	}

	@Override
	public boolean isCastsShadow()
	{
		return false;
	}
	@Override
	public float getPriority() { return 0; }

	@Override
	public IVeil getVeil() { return null; }

	@Override
	public boolean isOriented() { return false; }
}
