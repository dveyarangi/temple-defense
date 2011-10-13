package yarangi.game.temple.model.temple;

import yarangi.game.temple.model.Resource;
import yarangi.spatial.Area;

public interface Serviceable
{
	public Area getServicePoint();
	
	public void supply(Resource resource);
	
	public Resource consume(Resource resource);
}
