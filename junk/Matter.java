package yarangi.game.harmonium.environment.terrain;

import yarangi.game.harmonium.battle.Damage;
import yarangi.game.harmonium.battle.IDamageable;
import yarangi.game.harmonium.battle.Integrity;
import yarangi.graphics.quadraturin.objects.Dummy;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.spatial.PolygonArea;

public  class Matter extends Entity implements IDamageable
{

	public Matter(PolygonArea area) 
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
