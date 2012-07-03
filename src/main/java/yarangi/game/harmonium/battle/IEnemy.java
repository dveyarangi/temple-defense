package yarangi.game.harmonium.battle;

import yarangi.graphics.quadraturin.objects.IEntity;

/**
 * Marker interface for enemy units
 * @author dveyarangi
 *
 */
public interface IEnemy extends IEntity
{
	public double getLeadership();
	public double getAttractiveness();
}
