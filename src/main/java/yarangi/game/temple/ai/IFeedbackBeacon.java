package yarangi.game.temple.ai;

import yarangi.math.Vector2D;


public interface IFeedbackBeacon 
{
	
	
//	public IPhysicalObject getTarget();
	
//	public ISpatialObject getSource();
	
	/**
	 * @return true, if this beacon can be dispatched
	 */
	public boolean update(Vector2D location);
}
