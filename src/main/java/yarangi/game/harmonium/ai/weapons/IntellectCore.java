package yarangi.game.harmonium.ai.weapons;

import yarangi.math.IVector2D;
import yarangi.math.Vector2D;

/**
 * 
 * Targeting AI interface
 * 
 * @author dveyarangi
 *
 */
public interface IntellectCore 
{
	/**
	 * Picks targeting point for cannon. 
	 * TODO: shall return time to allow target scheduling and more realistic cannon behavior.
	 * @param cannonLocation
	 * @param projectileVelocity
	 * @param target
	 * @return
	 */
	public Vector2D pickTrackPoint(IVector2D cannonLocation, double projectileVelocity, IVector2D targetLocation, IVector2D targetVelocity);
	
	/**
	 * 
	 * @param capsule
	 * @return
	 */
	public boolean processFeedback(IFeedbackBeacon capsule);
	
//	core = new NetCore("netcore", playground.getWorldVeil().getWidth(), playground.getWorldVeil().getHeight());

	public void shutdown(); 

}
