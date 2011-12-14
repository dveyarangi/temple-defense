package yarangi.game.harmonium.model;

/**
 * 
 * 
 */
public interface Damageable {
	public Integrity getIntegrity();
	public void hit(Damage damage);
}
