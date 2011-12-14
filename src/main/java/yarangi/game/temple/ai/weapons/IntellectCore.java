package yarangi.game.temple.ai.weapons;

import yarangi.graphics.quadraturin.objects.IEntity;
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
	public Vector2D pickTrackPoint(Vector2D cannonLocation, Vector2D projectileVelocity, IEntity target);
	
	/**
	 * 
	 * @param capsule
	 * @return
	 */
	public boolean processFeedback(IFeedbackBeacon capsule);
	
//	core = new NetCore("netcore", playground.getWorldVeil().getWidth(), playground.getWorldVeil().getHeight());

	public void shutdown(); 

}
