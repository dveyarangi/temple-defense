package yarangi.game.harmonium.temple;

import yarangi.game.harmonium.environment.resources.Port;
import yarangi.spatial.Area;

public interface Serviceable
{
	public Area getServicePoint();
	
	/**
	 * Returns resource of specified type
	 * @param type
	 * @return
	 */
	public Port getPort();

}
