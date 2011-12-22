package yarangi.game.harmonium.battle;

/**
 * 
 * 
 */
public interface Damageable {
	public Integrity getIntegrity();
	public void hit(Damage damage);
}
