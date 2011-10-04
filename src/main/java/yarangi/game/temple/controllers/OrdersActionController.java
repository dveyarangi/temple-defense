package yarangi.game.temple.controllers;

import java.util.HashMap;
import java.util.Map;

import yarangi.game.temple.model.weapons.Weapon;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.actions.ActionController;
import yarangi.graphics.quadraturin.actions.IAction;
import yarangi.graphics.quadraturin.events.ICursorEvent;
import yarangi.graphics.quadraturin.events.UserActionEvent;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.graphics.quadraturin.terrain.GridyTerrainMap;
import yarangi.graphics.quadraturin.terrain.Tile;
import yarangi.math.Vector2D;
import yarangi.spatial.ISpatialFilter;


public class OrdersActionController extends ActionController
{
	Map <String, IAction> actions = new HashMap <String, IAction> ();
	
	private IEntity dragged = null;
	private IEntity hovered = null;

	
	private Vector2D target;
	
	private Look <OrdersActionController> look = new OrdersActionLook();
	
	private ISpatialFilter <IEntity> filter = new ISpatialFilter <IEntity> ()
	{
		@Override
		public boolean accept(IEntity entity)
		{
			if(entity instanceof Weapon)
			{
				return true;
			}
			return false;
		}
	
	};

	
	public OrdersActionController(final Scene scene)
	{
		super(scene);
//		actions.put("cursor-moved", temple.getController());
		actions.put("mouse-left-drag", new IAction()
		{

			@Override
			public void act(UserActionEvent event)
			{
				target = event.getCursor().getWorldLocation();
				// TODO: test olnly
				((GridyTerrainMap<Tile,?>)
						scene.getWorldVeil().getTerrain()).consume( target.x(), target.y(), 10 );
				
				if(dragged != null || event.getEntity() == null)
					return;
				dragged = event.getEntity();
				target = event.getCursor().getWorldLocation();
			}
			
		});
		actions.put("mouse-right-drag", new IAction()
		{

			@Override
			public void act(UserActionEvent event)
			{
				// TODO: test olnly
				target = event.getCursor().getWorldLocation();
				((GridyTerrainMap<Tile,?>)
						scene.getWorldVeil().getTerrain()).produce( target.x(), target.y(), 10 );
				
			}
			
		});
		actions.put("mouse-release", new IAction()
		{

			@Override
			public void act(UserActionEvent event)
			{
				if(dragged == null)
					return;
				Vector2D location = event.getCursor().getWorldLocation();
				if(location == null)
					return;
				dragged.getArea().getRefPoint().setxy( location.x(), location.y() );
				dragged = null;
			}});
//		actions.put( "drag", value )
/*		actions.put("hold-child", new IAction() {
			
			@Override
			public void act(UserActionEvent event) {
				ObserverEntity sensor2 = new ObserverEntity(
						new AABB(event.get, 5, 0),
						//new AABB(RandomUtil.getRandomGaussian(200, 50), RandomUtil.getRandomGaussian(200, 50), 5, 0),
						temple.getController(), 
						256, 
						(i % 2 == 0 ? new Color(0, 0.3f, 1, 1) : new Color(0, 0.3f, 1.f, 1)) 
						);
				sensor2.setBehavior(new ObserverBehavior(this.getWorldVeil().getEntityIndex()));
				addEntity(sensor2);
			}
		})*/
	}
	
	
	@Override
	public Map<String, IAction> getActions()
	{
		return actions;
	}

	@Override
	public ISpatialFilter<IEntity> getPickingFilter() { return filter; }


	@Override
	public void onCursorMotion(ICursorEvent event)
	{
		hovered = event.getEntity();
	}

	public IEntity getDragged() { return dragged; }
	public Vector2D getTarget() { return target; }
	public IEntity getHovered() { return hovered; }

/*	@Override
	public void display(GL gl, double time, RenderingContext context)
	{
		look.render( gl, time, this, context );
	}*/

}
