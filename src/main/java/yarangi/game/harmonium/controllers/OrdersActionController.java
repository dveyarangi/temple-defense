package yarangi.game.harmonium.controllers;

import java.util.HashMap;
import java.util.Map;

import yarangi.game.harmonium.temple.harvester.Harvester;
import yarangi.game.harmonium.temple.harvester.Waller;
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
import yarangi.graphics.quadraturin.terrain.MultilayerTilePoly;
import yarangi.graphics.quadraturin.terrain.PolygonGrid;
import yarangi.graphics.quadraturin.terrain.PolygonTerrainMap;
import yarangi.graphics.quadraturin.terrain.TilePoly;
import yarangi.math.Angles;
import yarangi.math.IVector2D;
import yarangi.math.Vector2D;
import yarangi.spatial.ISpatialFilter;
import yarangi.spatial.Tile;

import com.seisw.util.geom.Poly;
import com.seisw.util.geom.PolyDefault;


public class OrdersActionController extends ActionController
{
	Map <String, IAction> actions = new HashMap <String, IAction> ();
	
	private IEntity dragged = null;
	private final IEntity hovered = null;

	
	private IVector2D target;

	
	private PolygonTerrainMap terrain;
	
	private PolygonGrid<TilePoly> reinforcementMap;
	
	private ICameraMan cameraMan;
	
	private final boolean isDrawing = false;
	
	private final ISpatialFilter <IEntity> filter = new ISpatialFilter <IEntity> ()
	{
		@Override
		public boolean accept(IEntity entity)
		{
			if(entity instanceof Weapon || entity instanceof Harvester || entity instanceof Waller)
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
		
		terrain = (PolygonTerrainMap)scene.getWorldLayer().<MultilayerTilePoly>getTerrain();
//		actions.put("cursor-moved", temple.getController());
		actions.put("mouse-left-drag", new IAction()
		{

			@Override
			public void act(UserActionEvent event)
			{
				ICursorEvent cursor = event.getCursor();
				target = cursor.getWorldLocation();
				// TODO: test olnly 
				
//				reinforcementMap.query(new ConsumingSensor(terrain, false,target.x(), target.y(), 10  ), target.x(), target.y(), 10 );
				
				if(dragged != null)
					return;
				if(cursor.getEntity() != null && (cursor.getEntity() instanceof IEntity) )
				{
					dragged = (IEntity)cursor.getEntity();
					target = cursor.getWorldLocation();
				}
				if(dragged == null) {
					System.out.println(target);
					drawTerrain(terrain, target, false);
				}
			}
			
		});
		actions.put("mouse-right-drag", new IAction()
		{

			@Override
			public void act(UserActionEvent event)
			{
				// TODO: test olnly
				target = event.getCursor().getWorldLocation();
				
				drawTerrain(terrain, target, true );
			}

			
		});
		actions.put("mouse-release", new IAction()
		{

			@Override
			public void act(UserActionEvent event)
			{
				if(dragged == null)
					return;
				IVector2D location = event.getCursor().getWorldLocation();
				if(location == null)
					return;
				dragged.getArea().move( location.x(), location.y() );
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
		
		if(terrain != null) {
			reinforcementMap = new PolygonGrid<TilePoly>(32, terrain.getWidth(), terrain.getHeight());
	
	//		System.out.println(reinforcementMap.getGridWidth() + " : " + terrain.getGridHeight());
			for (int i = 0; i < reinforcementMap.getGridWidth(); i ++)
				for (int j = 0; j < reinforcementMap.getGridHeight(); j ++)
				{
					Tile <TilePoly> tile = reinforcementMap.getTileAt( i, j );
	//				System.out.println(tile);
					tile.put( new TilePoly(tile.getMinX(), tile.getMinY(), tile.getMaxX(), tile.getMaxY() ) );
				}
		}
//		ILook <PolygonTerrainMap> look = new FBOPolyTerrainLook(false, false, POLY_DEPTH);
//		return new EntityShell<PolygonTerrainMap>( terrain, new ModifiableGridBehavior(), look );

	}
	
	
	@Override
	public Map<String, IAction> getActions()
	{
		return actions;
	}

	@Override
	public ISpatialFilter<IEntity> getPickingFilter() { return filter; }
	
	private final double drawRadius = 15;

	
	private void drawTerrain(PolygonTerrainMap terrain, IVector2D target, boolean draw)
	{
		Poly poly = new PolyDefault();

		for(double ang = 0 ; ang < Angles.PI_2; ang += Angles.PI_div_12)
			poly.add( target.x() + drawRadius * Math.cos( ang ), target.y() + drawRadius * Math.sin( ang) );
		
		if(terrain == null)
			return;
		reinforcementMap.apply( target.x(), target.y(), drawRadius, drawRadius, draw, poly );
	}


	public IEntity getDragged() { return dragged; }
	public IVector2D getTarget() { return target; }
	public IEntity getHovered() { return hovered; }


	@Override
	public ICameraMan getCameraManager()
	{
		return cameraMan;
	}

	public PolygonGrid<TilePoly> getReinforcementMap()
	{
		return reinforcementMap;
	}

/*	@Override
	public void display(GL gl, double time, RenderingContext context)
	{
		look.render( gl, time, this, context );
	}*/

}
