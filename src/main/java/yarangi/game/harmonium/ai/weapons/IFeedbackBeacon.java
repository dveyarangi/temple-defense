package yarangi.game.harmonium.ai.weapons;

import yarangi.math.IVector2D;


public interface IFeedbackBeacon 
{
	
	
//	public IPhysicalObject getTarget();
	
//	public ISpatialObject getSource();
	
	/**
	 * @return true, if this beacon can be dispatched
	 */
	public boolean update(IVector2D location);
}
