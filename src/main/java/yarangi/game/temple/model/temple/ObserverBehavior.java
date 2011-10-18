package yarangi.game.temple.model.temple;

import java.util.HashSet;
import java.util.Set;

import yarangi.game.temple.ai.IFeedbackBeacon;
import yarangi.game.temple.model.weapons.Projectile;
import yarangi.game.temple.model.weapons.Weapon;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.graphics.quadraturin.objects.ISensor;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.math.Vector2D;

public class ObserverBehavior implements Behavior <ObserverEntity>
{
	
//	private MapSensor <ISpatialObject> sensor = new MapSensor<ISpatialObject>();
	
	private double radius;
	private Vector2D anchor;
	private double speed;
	
	private double a;
	
	public ObserverBehavior (double a, double radius, Vector2D anchor, double speed)
	{
		this.radius = radius;
		this.anchor = anchor;
		this.speed = speed;
		this.a = a;
	}

	public boolean behave(double time, ObserverEntity entity, boolean isVisible) 
	{
		Set <IFeedbackBeacon> trackList = new HashSet <IFeedbackBeacon> ();
		Set <IFeedbackBeacon> prevTrack = entity.getTrackedObjects();
		
		ISensor <IEntity> sensor = entity.getSensor();
//		System.out.println(getSensedObjects().size());
		IFeedbackBeacon feedback;
		
		// updating list of tracked objects:
		for(IEntity object : sensor.getEntities())
		{

//			System.out.println(object);
			if(object instanceof Projectile)
			{
			
				feedback = ((Projectile)object).getFeedback();
				if(feedback != null)
				{
	
					prevTrack.remove(feedback);
					trackList.add(feedback);
				
					Projectile prj = (Projectile) object;
				
//					System.out.println(object);
					prj.getFeedback().update(prj.getArea().getRefPoint());
				}
			}
			else
			{
				if(!(object instanceof ObserverEntity) && !(object instanceof TempleEntity) &&!(object instanceof Weapon))
				{
//					System.out.println(object);
					entity.getController().getBattleInterface().objectObserved(object);
				}
			}
		}
		
		if(!prevTrack.isEmpty())
		{
			for(IFeedbackBeacon capsule : prevTrack)
			{
				entity.getController().getBattleInterface().dispatchFeedback(capsule);
			}
		}
		
		entity.setTrackedObjects(trackList);
		
		if(anchor != null)
		{
			a += speed;
			entity.getArea().translate( anchor.x() + radius*Math.cos(a)-entity.getArea().getRefPoint().x(), 
										anchor.y() + radius*Math.sin(a)-entity.getArea().getRefPoint().y() );
		}
		return true;
	}

}
