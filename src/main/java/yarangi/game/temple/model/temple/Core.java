package yarangi.game.temple.model.temple;

import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.ui.Sensor;
import yarangi.spatial.ISpatialFilter;

public class Core extends Entity 
{
	private ISpatialFilter <IEntity> coreFilter = new ISpatialFilter<IEntity>() { @Override public boolean accept(IEntity entity) { return entity instanceof Core;	}};
	
	public Core()
	{
		setSensor(new Sensor(100, 1, coreFilter, false));
		setBehavior(new ShieldBehavior());
	}
}
