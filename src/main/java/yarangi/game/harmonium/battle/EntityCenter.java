package yarangi.game.harmonium.battle;

import java.util.HashSet;
import java.util.Set;

import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.spatial.AABB;
import yarangi.spatial.ISpatialSensor;
import yarangi.spatial.SpatialHashMap;

/** 
 * This class controls generated game entities
 * Probably should be part of the scene.
 * 
 * @author dveyarangi
*/
public class EntityCenter
{
	
	private static EntityCenter instance;
	
	public static void init(Scene scene) {
		instance = new EntityCenter(scene);
	}
	
	private final Scene scene;
	
	private final SpatialHashMap <IEntity> index;
	
	private final Set <ITemple> templeTargets = new HashSet<ITemple> ();
	private final Set <IEnemy> enemyTargets = new HashSet<IEnemy> ();
	
	private EntityCenter(Scene scene)
	{
		this.scene = scene;
		index = (SpatialHashMap <IEntity>)scene.getEntityIndex();
	}
	
	public static void addTempleTarget(ITemple temple)
	{
		instance.templeTargets.add( temple );
		instance.scene.addEntity( temple );
	}
	
	public static void addEnemyTarget(IEnemy enemy)
	{
		instance.enemyTargets.add( enemy );
		instance.scene.addEntity( enemy );
	}
	
	public static void removeTempleTarget(ITemple temple)
	{
		instance.templeTargets.remove( temple );
		instance.scene.removeEntity( temple );
	}
	public static void removeEnemyTarget(IEnemy enemy)
	{
		instance.enemyTargets.remove( enemy );
		instance.scene.removeEntity( enemy );
}
	
	public static Set <ITemple> getTempleTargets() 
	{
		return instance.templeTargets;
	}
	
	public Set <ITemple> getTempleTargets(AABB area) {
		TempleSetSensor sensor = new TempleSetSensor ();
		index.queryAABB( sensor, area.getCenterX(), area.getCenterY(), area.getRX(), area.getRY() );
		
		return sensor;
	}
	
	public static Set <IEnemy> getEnemyTargets()
	{
		return instance.enemyTargets;
	}
	
	public final class TempleSetSensor extends HashSet <ITemple> implements ISpatialSensor <IEntity>
	{

		@Override
		public boolean objectFound(IEntity object)
		{
			if(object instanceof ITemple)
				add((ITemple) object);	
			
			return false;
		}
		
	}

	
	public final class TempleSensor implements ISpatialSensor <IEntity>
	{
		private ITemple entity;
		
		@Override
		public boolean objectFound(IEntity object)
		{
			if(object instanceof ITemple) {
				entity = (ITemple) object;
				return true;
			}
			return false;
		}
		
		public ITemple getTarget() { return entity; }

		@Override
		public void clear()
		{
			entity = null;
		}
		
	}

}
