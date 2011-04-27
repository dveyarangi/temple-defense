package yarangi.game.temple.ai;

import yarangi.graphics.quadraturin.simulations.IPhysicalObject;
import yarangi.spatial.ISpatialObject;

public interface IFeedbackBeacon 
{
	
	
	public IPhysicalObject getTarget();
	
//	public ISpatialObject getSource();
	
	public void update();
}
