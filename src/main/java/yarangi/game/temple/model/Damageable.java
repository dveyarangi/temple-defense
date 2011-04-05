package yarangi.game.temple.model;

/**
 * 
 * 
 */
public interface Damageable {
	public Integrity getIntegrity();
	public void hit(Damage damage);
}
