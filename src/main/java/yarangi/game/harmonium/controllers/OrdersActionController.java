package yarangi.game.harmonium.controllers;

import java.util.HashMap;
import java.util.Map;

import yarangi.game.harmonium.environment.terrain.ConsumingSensor;
import yarangi.game.harmonium.temple.harvester.Harvester;
import yarangi.game.harmonium.temple.weapons.Weapon;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.ViewPoint2D;
import yarangi.graphics.quadraturin.actions.ActionController;
import yarangi.graphics.quadraturin.actions.CameraMover;
import yarangi.graphics.quadraturin.actions.IAction;
import yarangi.graphics.quadraturin.actions.ICameraMan;
import yarangi.graphics.quadraturin.events.ICursorEvent;
import yarangi.graphics.quadraturin.events.UserActionEvent;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.graphics.quadraturin.terrain.Bitmap;
import yarangi.graphics.quadraturin.terrain.GridyTerrainMap;
import yarangi.math.Vector2D;
import yarangi.spatial.ISpatialFilter;


public class OrdersActionController extends ActionController
{
	Map <String, IAction> actions = new HashMap <String, IAction> ();
	
	private IEntity dragged = null;
	private IEntity hovered = null;

	
	private Vector2D target;
	
	private Look look = new OrdersActionLook();
	
	private GridyTerrainMap terrain;
	
	private ICameraMan cameraMan;
	
	private ISpatialFilter <IEntity> filter = new ISpatialFilter <IEntity> ()
	{
		@Override
		public boolean accept(IEntity entity)
		{
			if(entity instanceof Weapon || entity instanceof Harvester)
			{
				return true;
			}
			return false;
		}
	
	};

	
	public OrdersActionController(final Scene scene)
	{
		super(scene);
		
		cameraMan = new CameraMover( (ViewPoint2D) scene.getViewPoint() );
		
		terrain = (GridyTerrainMap)scene.getWorldLayer().<Bitmap>getTerrain();
//		actions.put("cursor-moved", temple.getController());
		actions.put("mouse-left-drag", new IAction()
		{

			@Override
			public void act(UserActionEvent event)
			{
				ICursorEvent cursor = event.getCursor();
				target = cursor.getWorldLocation();
				// TODO: test olnly 
				terrain.query(new ConsumingSensor(terrain, false,target.x(), target.y(), 10  ), target.x(), target.y(), 10 );
				
				if(dragged != null || cursor.getEntity() == null || !(cursor.getEntity() instanceof IEntity))
					return;
				dragged = (IEntity)cursor.getEntity();
				target = cursor.getWorldLocation();
			}
			
		});
		actions.put("mouse-right-drag", new IAction()
		{

			@Override
			public void act(UserActionEvent event)
			{
				// TODO: test olnly
				target = event.getCursor().getWorldLocation();
				terrain.query(new ConsumingSensor(terrain, true,target.x(), target.y(), 10  ), target.x(), target.y(), 10 );
				
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


	public IEntity getDragged() { return dragged; }
	public Vector2D getTarget() { return target; }
	public IEntity getHovered() { return hovered; }


	@Override
	public ICameraMan getCameraManager()
	{
		return cameraMan;
	}


	@Override
	public Look<ActionController> getLook()
	{
		
		return look;
	}

/*	@Override
	public void display(GL gl, double time, RenderingContext context)
	{
		look.render( gl, time, this, context );
	}*/

}
