package yarangi.game.temple.model.temple;

import yarangi.game.temple.model.resource.Port;
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
