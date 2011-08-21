package yarangi.game.temple.actions;

import yarangi.game.temple.model.weapons.Projectile;
import yarangi.spatial.ISpatialObject;

/**
 * Interface for entities that spawn other entities in response to mouse click.
 * TODO: there should be entities that create entities on their own... 
 * 
 */
public interface Fireable
{
	/**
	 * Creates a new entity,
	 * @return entity object, or null
	 */
	public Projectile fire();
	
	/**
	 * Informs the fireable that fire is stopped.
	 */
	public void stop();
}
