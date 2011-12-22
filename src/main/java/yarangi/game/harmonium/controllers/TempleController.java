package yarangi.game.harmonium.controllers;

import javax.media.opengl.GL;

import yarangi.game.harmonium.Debug;
import yarangi.game.harmonium.Playground;
import yarangi.game.harmonium.ai.economy.IOrderScheduler;
import yarangi.game.harmonium.ai.economy.StupidScheduler;
import yarangi.game.harmonium.ai.weapons.IntellectCore;
import yarangi.game.harmonium.temple.BattleCommander;
import yarangi.game.harmonium.temple.BattleInterface;
import yarangi.game.harmonium.temple.StructureInterface;
import yarangi.game.harmonium.temple.EnergyCore;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.actions.ActionController;
import yarangi.graphics.quadraturin.actions.DefaultActionFactory;
import yarangi.graphics.quadraturin.events.CursorListener;
import yarangi.graphics.quadraturin.events.ICursorEvent;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.graphics.quadraturin.objects.ILayerObject;
import yarangi.graphics.quadraturin.terrain.Bitmap;
import yarangi.spatial.Area;
import yarangi.spatial.ISpatialSensor;
import yarangi.spatial.Tile;
 
public class TempleController extends Entity implements CursorListener
{

	private EnergyCore temple;
	
	private Scene scene;
	
	private ILayerObject highlighted;
	
	private BattleInterface battleInterface;
	
	private StructureInterface structureInterface;
	
	private IOrderScheduler botInterface;
	
	private ActionController actionController;

	public TempleController(Scene scene, IntellectCore core, EnergyCore temple) 
	{
		super();

		this.scene = scene;
		this.temple = temple;
		setArea(Area.EMPTY);
		setLook(new ControlLook());
		setBehavior(new ControlBehavior());

		
		battleInterface = new BattleCommander(this, core);
		structureInterface = new StructureInterface();
		botInterface = new StupidScheduler();
		
		actionController = new OrdersActionController(scene);
		DefaultActionFactory.appendNavActions(scene, actionController);
		Debug.appendDebugActions( actionController.getActions(), (Playground) scene );
		
		// TODO: control modes
		scene.setActionController(actionController);

	}



	public Scene getScene() { return scene; }

	public EnergyCore getTemple() { return temple; }

	public void onCursorMotion(ICursorEvent event) 
	{
		highlighted = event.getEntity();

	}


	public ILayerObject getHighlighted() { return highlighted; }
	

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

	class LOSSensor implements ISpatialSensor <Tile<Bitmap>, Bitmap> 
	{
		private boolean hasLOS = true;
		public boolean hasLOS() { return hasLOS; }

		@Override
		public boolean objectFound(Tile<Bitmap> chunk, Bitmap object)
		{
			if(object.isAlive())
//			if(object instanceof Tile)
			{
//				hasLOS = false;
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
		if(scene.getWorldLayer().<Bitmap>getTerrain() != null)
			scene.getWorldLayer().<Bitmap>getTerrain().query( sensor, x, y, x2-x, y2-y );
		
		return sensor.hasLOS();
	}

	public ActionController getActionController()
	{
		 return actionController;
	}



	public StructureInterface getStructureInterface()
	{
		return structureInterface; 
	}



	public IOrderScheduler getOrderScheduler()
	{
		return botInterface;
	}	

}
