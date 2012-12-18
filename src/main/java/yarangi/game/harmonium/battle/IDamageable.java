package yarangi.game.harmonium.battle;

/**
 * 
 * 
 */
public interface IDamageable {
	public Integrity getIntegrity();
	public void hit(Damage damage);
}
