package yarangi.game.harmonium.battle;

import java.util.HashSet;
import java.util.Set;

import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.spatial.AABB;
import yarangi.spatial.IAreaChunk;
import yarangi.spatial.ISpatialIndex;
import yarangi.spatial.ISpatialSensor;
import yarangi.spatial.SpatialHashMap;

public class EntityCenter
{
	
	private static EntityCenter instance;
	
	public static void init(Scene scene) {
		instance = new EntityCenter(scene);
	}
	
	private Scene scene;
	
	private SpatialHashMap <IEntity> index;
	
	private Set <ITemple> templeTargets = new HashSet<ITemple> ();
	private Set <IEnemy> enemyTargets = new HashSet<IEnemy> ();
	
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
		index.query( sensor, area );
		
		return sensor;
	}
	
	public static Set <IEnemy> getEnemyTargets()
	{
		return instance.enemyTargets;
	}
	
	public final class TempleSetSensor extends HashSet <ITemple> implements ISpatialSensor <IAreaChunk, IEntity>
	{

		@Override
		public boolean objectFound(IAreaChunk tile, IEntity object)
		{
			if(object instanceof ITemple)
				add((ITemple) object);	
			
			return false;
		}
		
	}

	
	public final class TempleSensor implements ISpatialSensor <IAreaChunk, IEntity>
	{
		private ITemple entity;
		
		@Override
		public boolean objectFound(IAreaChunk tile, IEntity object)
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
