package yarangi.game.temple.controllers;

import java.util.HashMap;
import java.util.Map;

import yarangi.game.temple.model.terrain.Tile;
import yarangi.game.temple.model.weapons.Weapon;
import yarangi.graphics.colors.Color;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.actions.ActionController;
import yarangi.graphics.quadraturin.actions.IAction;
import yarangi.graphics.quadraturin.events.ICursorEvent;
import yarangi.graphics.quadraturin.events.UserActionEvent;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.graphics.quadraturin.terrain.Cell;
import yarangi.graphics.quadraturin.terrain.GridyTerrainMap;
import yarangi.math.Vector2D;
import yarangi.spatial.IAreaChunk;
import yarangi.spatial.ISpatialFilter;
import yarangi.spatial.ISpatialSensor;


public class OrdersActionController extends ActionController
{
	Map <String, IAction> actions = new HashMap <String, IAction> ();
	
	private IEntity dragged = null;
	private IEntity hovered = null;

	
	private Vector2D target;
	
	private Look <OrdersActionController> look = new OrdersActionLook();
	
	private GridyTerrainMap<Tile> terrain;
	
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
		
		terrain = (GridyTerrainMap<Tile>)scene.getWorldVeil().<Tile>getTerrain();
//		actions.put("cursor-moved", temple.getController());
		actions.put("mouse-left-drag", new IAction()
		{

			@Override
			public void act(UserActionEvent event)
			{
				ICursorEvent cursor = event.getCursor();
				target = cursor.getWorldLocation();
				// TODO: test olnly 
				terrain.query(new ConsumingSensor( false,target.x(), target.y(), 10  ), target.x(), target.y(), 10 );
				
				if(dragged != null || cursor.getEntity() == null)
					return;
				dragged = cursor.getEntity();
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
				terrain.query(new ConsumingSensor( true,target.x(), target.y(), 10  ), target.x(), target.y(), 10 );
				
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
	public class ConsumingSensor implements ISpatialSensor <Tile>
	{
		double ox, oy, radiusSquare;
		boolean draw = false;
		
		public ConsumingSensor (boolean draw, double ox, double oy, double radiusSquare)
		{
			this.ox = ox;
			this.oy = oy;
			this.radiusSquare = radiusSquare;
			this.draw = draw;
		}
		/**
		 * @param chunk - current cell
		 */
		@Override
		public boolean objectFound(IAreaChunk chunk, Tile tile)
		{
//			System.out.println(chunk + " : " + tile);
			int pixelsBefore = tile.getPixelCount();
			if(pixelsBefore == 0 && !draw)
				return false;
			for(int i = 0; i < tile.getSize(); i ++)
				for(int j = 0; j < tile.getSize(); j ++)
				{
					double dx = chunk.getMinX() + i * terrain.getPixelSize() - ox;
					double dy = chunk.getMinY() + j * terrain.getPixelSize() - oy;
					if((dx*dx) + (dy*dy) > radiusSquare)
						continue;

					if(draw)
						((Tile)tile).put((new Color(0,1,1,1)), i, j );
					else
						tile.remove( i, j );
				}
			
			if(tile.getPixelCount() != pixelsBefore)
				terrain.setModified( (Cell<Tile>)chunk );
			
			return false;
		}

		@Override
		public void clear() { }
		
	}

}
