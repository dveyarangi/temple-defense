package yarangi.game.harmonium.battle;

import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.spatial.Area;


/**
 * Marker interface of temple parts, for enemy targeting
 * @author dveyarangi
 *
 */

public interface ITemple extends IEntity
{
	public double getAttractiveness();
	public Area getArea();
}
