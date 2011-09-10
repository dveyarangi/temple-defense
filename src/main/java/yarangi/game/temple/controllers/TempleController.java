package yarangi.game.temple.controllers;

import javax.media.opengl.GL;

import yarangi.game.temple.actions.DefaultActionFactory;
import yarangi.game.temple.ai.IntellectCore;
import yarangi.game.temple.model.temple.BattleCommander;
import yarangi.game.temple.model.temple.BattleInterface;
import yarangi.game.temple.model.temple.ObserverBehavior;
import yarangi.game.temple.model.temple.ObserverEntity;
import yarangi.game.temple.model.temple.ObserverLook;
import yarangi.game.temple.model.temple.StructureInterface;
import yarangi.game.temple.model.temple.TempleEntity;
import yarangi.game.temple.model.terrain.Matter;
import yarangi.graphics.colors.Color;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.actions.IActionController;
import yarangi.graphics.quadraturin.events.CursorEvent;
import yarangi.graphics.quadraturin.events.CursorListener;
import yarangi.graphics.quadraturin.objects.IVeilEntity;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.graphics.quadraturin.objects.Sensor;
import yarangi.math.Vector2D;
import yarangi.spatial.AABB;
import yarangi.spatial.Area;
import yarangi.spatial.IAreaChunk;
import yarangi.spatial.ISpatialSensor;

public class TempleController extends Entity implements CursorListener
{

	private static final long serialVersionUID = -2094957235603223096L;

	private TempleEntity temple;
	
	private Scene scene;
	
	
	private Vector2D cursorLocation;
	
	private IVeilEntity highlighted;


	private ObserverEntity cursor;
	
	private BattleInterface battleInterface;
	
	private StructureInterface structureInterface;
	
	private IActionController actionController;

	public TempleController(Scene scene, IntellectCore core) 
	{
		super();

		this.scene = scene;
		
		setArea(Area.EMPTY);
		setLook(new ControlLook());
		setBehavior(new ControlBehavior());

		cursor = new ObserverEntity(this);		
		cursor.setBehavior(new ObserverBehavior(0, 1, null, 0));
		cursor.setSensor(new Sensor(64, 1, null, true));
		cursor.setLook(new ObserverLook(new Color(1.0f,1.0f,0.5f,1f)));
		cursor.setArea(new AABB(0, 0, 10, 0));
		
		scene.addEntity(cursor);
		
		battleInterface = new BattleCommander(this, core);
		structureInterface = new StructureInterface();
		
		actionController = new OrdersActionController();
		DefaultActionFactory.fillNavigationActions(scene, actionController);
		
		// TODO: control modes
		scene.setActionController(actionController);

	}



	public Scene getScene() { return scene; }

	public TempleEntity getTemple() { return temple; }

	public void onCursorMotion(CursorEvent event) 
	{
		cursorLocation = event.getWorldLocation();
		highlighted = event.getEntity();
		
		if(cursorLocation != null)
		{
			cursor.getArea().getRefPoint().setxy(cursorLocation.x(), cursorLocation.y());
		}
		else
		{
			// TODO: beee
			cursor.getArea().getRefPoint().setxy(100000, 100000);
		}
	}
	
	public Vector2D getCursorLocation()
	{
		return cursorLocation;
	}


	public IVeilEntity getHighlighted() { return highlighted; }
	

//	public IPhysicalObject getTarget(Fireable fireable) {
//		System.out.println(observedEntities.size());
//		return observedEntities.isEmpty() ? null : observedEntities.iterator().next();
//	}

	
	public void destroy(GL gl)
	{
		battleInterface.destroy(gl);
	}


	public BattleInterface getBattleInterface() {
		return battleInterface;
	}


	public void objectObserved(Entity object) { battleInterface.objectObserved(object); }

	class LOSSensor implements ISpatialSensor <IEntity> 
	{
		private boolean hasLOS = true;
		public boolean hasLOS() { return hasLOS; }

		@Override
		public boolean objectFound(IAreaChunk chunk, IEntity object)
		{
			if(object instanceof Matter)
			{
				hasLOS = false;
				return true;
			}
			return false;
		}
		
		@Override
		public void clear() { hasLOS = true; }
		
	}

	public boolean testLOS(double x, double y, double x2, double y2)
	{
		LOSSensor sensor = new LOSSensor();
//		scene.getEntityIndex().query( sensor, x, y, x2-x, y2-y );
		scene.getWorldVeil().getTerrain().query( sensor, x, y, x2-x, y2-y );
		return sensor.hasLOS();
	}

	public IActionController getActionController()
	{
		return actionController;
	}



	public StructureInterface getStructureInterface()
	{
		return structureInterface; 
	}	

}
