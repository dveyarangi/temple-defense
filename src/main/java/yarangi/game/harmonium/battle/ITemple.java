package yarangi.game.harmonium.battle;

import yar.quadraturin.objects.IEntity;
import yarangi.spatial.AABB;


/**
 * Marker interface of temple parts, for enemy targeting
 * @author dveyarangi
 *
 */

public interface ITemple extends IEntity, IDamageable
{
	public double getAttractiveness();
	
	@Override
	public AABB getArea();
	
	public double getLeadership();
}
