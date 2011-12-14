package yarangi.game.harmonium.actions;

import yarangi.game.harmonium.temple.weapons.Projectile;

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
