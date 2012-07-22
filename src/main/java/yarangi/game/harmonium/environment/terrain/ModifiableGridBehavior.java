package yarangi.game.harmonium.environment.terrain;

import yarangi.graphics.quadraturin.objects.IBehavior;
import yarangi.spatial.GridMap;

/**
 * Constantly fires grid modified events
 * @author dveyarangi
 *
 * @param <G>
 */
public class ModifiableGridBehavior <G extends GridMap<?,?>> implements IBehavior<G>
{

	@Override
	public boolean behave(double time, G grid, boolean isVisible)
	{
		grid.fireGridModified();
		return false;
	}

}
