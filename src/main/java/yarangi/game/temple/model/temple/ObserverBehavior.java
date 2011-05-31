package yarangi.game.temple.model.temple;

import java.util.HashSet;
import java.util.Set;

import yarangi.game.temple.ai.IFeedbackBeacon;
import yarangi.game.temple.model.weapons.Projectile;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.graphics.quadraturin.simulations.IPhysicalObject;
import yarangi.spatial.ISpatialIndex;
import yarangi.spatial.ISpatialObject;
import yarangi.spatial.MapSensor;

public class ObserverBehavior implements Behavior <ObserverEntity>
{
	
	private ISpatialIndex <ISpatialObject> indexer;
	
	private MapSensor <ISpatialObject> sensor = new MapSensor<ISpatialObject>();
	
	public ObserverBehavior (ISpatialIndex <ISpatialObject> indexer)
	{
		this.indexer = indexer;
	}

	public boolean behave(double time, ObserverEntity entity, boolean isVisible) 
	{
		sensor.clear();

		indexer.query(sensor, entity.getArea().getRefPoint().x, entity.getArea().getRefPoint().x, entity.getSensorRadius());
		
		Set <IFeedbackBeacon> trackList = new HashSet <IFeedbackBeacon> ();
		Set <IFeedbackBeacon> prevTrack = entity.getTrackedObjects();
		
		IFeedbackBeacon feedback;
		for(ISpatialObject object : sensor.values())
		{

			if(object instanceof Projectile)
			{
			
				feedback = ((Projectile)object).getFeedback();
				if(feedback != null)
				{
	
					prevTrack.remove(feedback);
					trackList.add(feedback);
				
					Projectile prj = (Projectile) object;
				
					prj.getFeedback().update();
				}
			}
			else
			if(object instanceof IPhysicalObject &&
			  !(object instanceof ObserverEntity) && !(object instanceof TempleEntity))
				entity.getController().objectObserved((IPhysicalObject) object);
		}
		
		if(!prevTrack.isEmpty())
		{
			for(IFeedbackBeacon capsule : prevTrack)
			{
				entity.getController().dispatchFeedback(capsule);
			}
		}
		
		entity.setTrackedObjects(trackList);

		
		entity.setEntities(sensor);
		return true;
	}

}
