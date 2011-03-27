package yarangi.game.spacefight.model;

/**
 * 
 * 
 */
public interface Damageable {
	public Integrity getIntegrity();
	public void hit(Damage damage);
}
