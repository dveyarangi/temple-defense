package yarangi.game.harmonium.temple;

import java.util.HashSet;
import java.util.Set;

import yarangi.game.harmonium.ai.weapons.IFeedbackBeacon;
import yarangi.game.harmonium.controllers.TempleController;
import yarangi.game.harmonium.temple.weapons.Projectile;
import yarangi.game.harmonium.temple.weapons.Weapon;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.ISensor;

public class ObserverBehavior implements Behavior <ObserverEntity>
{
	

	
	private TempleController controller;
	
	public ObserverBehavior (TempleController controller)
	{

		this.controller = controller;

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
				if(!(object instanceof ObserverEntity) && !(object instanceof EnergyCore) &&!(object instanceof Weapon))
				{
//					System.out.println(object);
					controller.getBattleInterface().objectObserved(object);
				}
			}
		}
		
		if(!prevTrack.isEmpty())
		{
			for(IFeedbackBeacon capsule : prevTrack)
			{
				controller.getBattleInterface().dispatchFeedback(capsule);
			}
		}
		
		entity.setTrackedObjects(trackList);
		
		return true;
	}

}
