package yarangi.game.harmonium.controllers;

import javax.media.opengl.GL;

import yarangi.game.harmonium.Debug;
import yarangi.game.harmonium.Playground;
import yarangi.game.harmonium.ai.economy.IOrderScheduler;
import yarangi.game.harmonium.ai.economy.StupidScheduler;
import yarangi.game.harmonium.ai.weapons.IntellectCore;
import yarangi.game.harmonium.temple.BattleCommander;
import yarangi.game.harmonium.temple.BattleInterface;
import yarangi.game.harmonium.temple.EnergyCore;
import yarangi.game.harmonium.temple.StructureInterface;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.actions.ActionController;
import yarangi.graphics.quadraturin.actions.DefaultActionFactory;
import yarangi.graphics.quadraturin.events.CursorListener;
import yarangi.graphics.quadraturin.events.ICursorEvent;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.graphics.quadraturin.objects.EntityShell;
import yarangi.graphics.quadraturin.objects.ILayerObject;
import yarangi.graphics.quadraturin.terrain.MultilayerTilePoly;
import yarangi.spatial.Area;
import yarangi.spatial.ISpatialSensor;
 
public class TempleController extends Entity implements CursorListener
{

	private final EnergyCore temple;
	
	private final Scene scene;
	
	private ILayerObject highlighted;
	
	private final BattleInterface battleInterface;
	
	private final StructureInterface structureInterface;
	
	private final IOrderScheduler botInterface;
	
	private final ActionController actionController;

	public TempleController(final Scene scene, final IntellectCore core, final EnergyCore temple) 
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
		
		EntityShell <ActionController> shell = new EntityShell<ActionController>( actionController, null, new OrdersActionLook() );
		
		// TODO: control modes
		scene.setActionController(shell);

	}



	public Scene getScene() { return scene; }

	public EnergyCore getTemple() { return temple; }

	@Override
	public void onCursorMotion(final ICursorEvent event) 
	{
		highlighted = event.getEntity();

	}


	public ILayerObject getHighlighted() { return highlighted; }
	

//	public IPhysicalObject getTarget(Fireable fireable) {
//		System.out.println(observedEntities.size());
//		return observedEntities.isEmpty() ? null : observedEntities.iterator().next();
//	}

	
	public void destroy(final GL gl)
	{
		battleInterface.destroy(gl);
	}


	public BattleInterface getBattleInterface() {
		return battleInterface;
	}


	public void objectObserved(final Entity object) { battleInterface.objectObserved(object); }

	class LOSSensor implements ISpatialSensor <MultilayerTilePoly> 
	{
		private boolean hasLOS = true;
		public boolean hasLOS() { return hasLOS; }

		@Override
		public boolean objectFound(final MultilayerTilePoly object)
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

	public boolean testLOS(final double x, final double y, final double x2, final double y2)
	{
		final LOSSensor sensor = new LOSSensor();
//		scene.getEntityIndex().query( sensor, x, y, x2-x, y2-y );
		if(scene.getWorldLayer().<MultilayerTilePoly>getTerrain() != null)
			scene.getWorldLayer().<MultilayerTilePoly>getTerrain().queryLine( sensor, x, y, x2-x, y2-y );
		
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
	
	@Override
	public boolean isIndexed() { return false; }

}
