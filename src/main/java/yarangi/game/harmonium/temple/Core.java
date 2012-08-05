package yarangi.game.harmonium.temple;

import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.graphics.quadraturin.objects.IBeing;
import yarangi.graphics.quadraturin.objects.Sensor;
import yarangi.spatial.ISpatialFilter;

public class Core extends Entity 
{
	private final ISpatialFilter <IBeing> coreFilter = new ISpatialFilter<IBeing>() { @Override public boolean accept(IBeing entity) { return entity instanceof Core;	}};
	
	public Core()
	{
		setSensor(new Sensor(100, 1, coreFilter, false));
		setBehavior(new ShieldBehavior());
	}
}
