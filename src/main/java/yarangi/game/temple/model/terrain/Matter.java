package yarangi.game.temple.model.terrain;

import yarangi.game.temple.model.Damage;
import yarangi.game.temple.model.Damageable;
import yarangi.game.temple.model.Integrity;
import yarangi.graphics.quadraturin.objects.Dummy;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.spatial.Polygon;

public  class Matter extends Entity implements Damageable
{

	public Matter(Polygon area) 
	{
		super();
		
		setArea(area);
		
		setBehavior(Dummy.BEHAVIOR);
//		setLook(new MatterLook());

	}

	@Override
	public Integrity getIntegrity() {
		return null;
	}

	@Override
	public void hit(Damage damage) {
		
	}


}
