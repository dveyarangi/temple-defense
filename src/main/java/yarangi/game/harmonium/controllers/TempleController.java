package yarangi.game.harmonium.controllers;

import javax.media.opengl.GL;

import yarangi.game.harmonium.ai.economy.IOrderScheduler;
import yarangi.game.harmonium.ai.economy.StupidScheduler;
import yarangi.game.harmonium.ai.weapons.IntellectCore;
import yarangi.game.harmonium.temple.BattleCommander;
import yarangi.game.harmonium.temple.BattleInterface;
import yarangi.game.harmonium.temple.EnergyCore;
import yarangi.game.harmonium.temple.ServiceInterface;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.events.CursorListener;
import yarangi.graphics.quadraturin.events.ICursorEvent;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.graphics.quadraturin.objects.ILayerObject;
import yarangi.graphics.quadraturin.terrain.ITerrain;
import yarangi.spatial.Area;
import yarangi.spatial.ISpatialSensor;
import yarangi.spatial.ITileMap;
 
public class TempleController extends Entity implements CursorListener
{

	private final EnergyCore temple;
	
	private final Scene scene;
	
	private ILayerObject highlighted;
	
	private final BattleInterface battleInterface;
	
	private final ServiceInterface structureInterface;
	
	private final IOrderScheduler botInterface;


	public TempleController(final Scene scene, final IntellectCore core, final EnergyCore temple) 
	{
		super();

		this.scene = scene;
		this.temple = temple;
		setArea(Area.EMPTY);
		setLook(new ControlLook());
		setBehavior(new ControlBehavior());

		
		battleInterface = new BattleCommander(this, core);
		structureInterface = new ServiceInterface();
		botInterface = new StupidScheduler();

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

	class LOSSensor implements ISpatialSensor <ITerrain> 
	{
		private boolean hasLOS = true;
		public boolean hasLOS() { return hasLOS; }

		@Override
		public boolean objectFound(final ITerrain object)
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
		if(scene.getWorldLayer().getTerrain() != null)
			scene.getWorldLayer().<ITileMap<ITerrain>>getTerrain().queryLine( sensor, (float)x, (float)y, (float)(x2-x), (float)(y2-y) );
		
		return sensor.hasLOS();
	}

	public ServiceInterface getStructureInterface()
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
