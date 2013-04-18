package yarangi.game.harmonium.battle;

import yarangi.graphics.quadraturin.objects.IEntity;


/**
 * Marker interface for enemy units
 * @author dveyarangi
 *
 */
public interface IEnemy extends IEntity, IDamageable, ICollidable
{
	
	public double getLeadership();
	public double getAttractiveness();
	
	@Override
	public void hit(Damage damage);
}
