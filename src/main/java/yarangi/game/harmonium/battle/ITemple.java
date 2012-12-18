package yarangi.game.harmonium.battle;

import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.spatial.Area;


/**
 * Marker interface of temple parts, for enemy targeting
 * @author dveyarangi
 *
 */

public interface ITemple extends IEntity, IDamageable
{
	public double getAttractiveness();
	
	@Override
	public Area getArea();
	
	public double getLeadership();
}
