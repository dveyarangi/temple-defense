package yarangi.game.temple.controllers;

import javax.media.opengl.GL;

import yarangi.game.temple.Debug;
import yarangi.game.temple.Playground;
import yarangi.game.temple.ai.IntellectCore;
import yarangi.game.temple.controllers.bots.BotInterface;
import yarangi.game.temple.model.temple.BattleCommander;
import yarangi.game.temple.model.temple.BattleInterface;
import yarangi.game.temple.model.temple.StructureInterface;
import yarangi.game.temple.model.temple.TempleEntity;
import yarangi.game.temple.model.terrain.Tile;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.actions.ActionController;
import yarangi.graphics.quadraturin.actions.DefaultActionFactory;
import yarangi.graphics.quadraturin.events.CursorListener;
import yarangi.graphics.quadraturin.events.ICursorEvent;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.graphics.quadraturin.objects.ILayerObject;
import yarangi.spatial.Area;
import yarangi.spatial.IAreaChunk;
import yarangi.spatial.ISpatialSensor;
 
public class TempleController extends Entity implements CursorListener
{

	private TempleEntity temple;
	
	private Scene scene;
	
	private ILayerObject highlighted;
	
	private BattleInterface battleInterface;
	
	private StructureInterface structureInterface;
	
	private BotInterface botInterface;
	
	private ActionController actionController;

	public TempleController(Scene scene, IntellectCore core) 
	{
		super();

		this.scene = scene;
		
		setArea(Area.EMPTY);
		setLook(new ControlLook());
		setBehavior(new ControlBehavior());

		
		battleInterface = new BattleCommander(this, core);
		structureInterface = new StructureInterface();
		botInterface = new BotInterface();
		
		actionController = new OrdersActionController(scene);
		DefaultActionFactory.appendNavActions(scene, actionController);
		Debug.appendDebugActions( actionController.getActions(), (Playground) scene );
		
		// TODO: control modes
		scene.setActionController(actionController);

	}



	public Scene getScene() { return scene; }

	public TempleEntity getTemple() { return temple; }
	public void setTemple(TempleEntity temple) { this.temple = temple; }

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

	class LOSSensor implements ISpatialSensor <Tile> 
	{
		private boolean hasLOS = true;
		public boolean hasLOS() { return hasLOS; }

		@Override
		public boolean objectFound(IAreaChunk chunk, Tile object)
		{
			if(object.isAlive())
//			if(object instanceof Tile)
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
		if(scene.getWorldLayer().<Tile>getTerrain() != null)
			scene.getWorldLayer().<Tile>getTerrain().query( sensor, x, y, x2-x, y2-y );
		
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



	public BotInterface getBotInterface()
	{
		return botInterface;
	}	

}
