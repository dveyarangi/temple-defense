package yarangi.game.harmonium.enemies;

import yar.quadraturin.objects.Entity;
import yar.quadraturin.objects.ILayerObject;
import yarangi.game.harmonium.battle.Damage;
import yarangi.game.harmonium.battle.IDamageable;
import yarangi.game.harmonium.battle.Integrity;
import yarangi.spatial.AABB;

public abstract class GenericEnemy extends Entity implements IDamageable
{

	private Integrity integrity;
	
	private ILayerObject target;
	
	
	public GenericEnemy(AABB area, ILayerObject target, Integrity integrity) {
		super();
		this.target = target;
		this.integrity = integrity;
		
		setArea(area);
	}
	
	public ILayerObject getTarget() { return target; }

	public boolean isCollidable() {
		return true;
	}

	public boolean isPickable() {
		return true;
	}

	@Override
	public Integrity getIntegrity() {
		return integrity;
	}

	@Override
	public void hit(Damage damage) 
	{
		integrity.hit(damage);
	}
}
