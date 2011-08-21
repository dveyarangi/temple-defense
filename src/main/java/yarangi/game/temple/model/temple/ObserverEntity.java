package yarangi.game.temple.model.temple;

import java.util.HashSet;
import java.util.Set;

import yarangi.game.temple.ai.IFeedbackBeacon;
import yarangi.game.temple.controllers.TempleController;
import yarangi.game.temple.model.temple.structure.Connectable;
import yarangi.game.temple.model.temple.structure.PowerConnector;
import yarangi.graphics.quadraturin.objects.WorldEntity;

public class ObserverEntity extends WorldEntity implements Connectable 
{

	private PowerConnector [] connectors;
	
	private TempleController ctrl;
	private Set <IFeedbackBeacon> trackedObjects = new HashSet <IFeedbackBeacon> ();

	public ObserverEntity(TempleController ctrl)
	{
		super();
		
		
		this.ctrl = ctrl;
/*		connectors = new PowerConnector[6];
		int idx = 0;
		for(double a = 0.01; a < Angles.PI_2; a += Angles.PI_div_3)
			connectors[idx++] = new PowerConnector(this.getArea().getRefPoint(), a);*/

	}

	public Set <IFeedbackBeacon> getTrackedObjects() {
		return trackedObjects;
	}
	
	public void setTrackedObjects(Set <IFeedbackBeacon> objects) { this.trackedObjects = objects; }


	@Override
	public PowerConnector[] getConnectors() { return connectors; }

	public TempleController getController() { return ctrl; }
}
