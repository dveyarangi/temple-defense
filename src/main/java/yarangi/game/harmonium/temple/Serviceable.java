package yarangi.game.harmonium.temple;

import yarangi.game.harmonium.environment.resources.Port;
import yarangi.spatial.Area;

/**
 * A generic "serviceable" represents a beacon for bot interaction.
 * @author dveyarangi
 *
 */
public interface Serviceable
{
	/**
	 * Marks servicing area for animation.
	 * May be scheduled to return different areas
	 * @return
	 */
	public Area getServiceArea();
	
	/**
	 * Returns resource of specified type
	 * @param type
	 * @return
	 */
	public Port getPort();

}
