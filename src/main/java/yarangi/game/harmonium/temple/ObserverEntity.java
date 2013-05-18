package yarangi.game.harmonium.temple;

import java.util.HashSet;
import java.util.Set;

import yar.quadraturin.objects.Entity;
import yarangi.game.harmonium.ai.weapons.IFeedbackBeacon;

public class ObserverEntity extends Entity// implements Connectable 
{

	
	private Set <IFeedbackBeacon> trackedObjects = new HashSet <IFeedbackBeacon> ();

	public ObserverEntity()
	{
		super();

/*		connectors = new PowerConnector[6];
		int idx = 0;
		for(double a = 0.01; a < Angles.PI_2; a += Angles.PI_div_3)
			connectors[idx++] = new PowerConnector(this.getArea().getRefPoint(), a);*/

	}

	public Set <IFeedbackBeacon> getTrackedObjects() {
		return trackedObjects;
	}
	
	public void setTrackedObjects(Set <IFeedbackBeacon> objects) { this.trackedObjects = objects; }


}
