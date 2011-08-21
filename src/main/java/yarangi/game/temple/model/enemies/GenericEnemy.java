package yarangi.game.temple.model.enemies;

import yarangi.game.temple.model.Damage;
import yarangi.game.temple.model.Damageable;
import yarangi.game.temple.model.Integrity;
import yarangi.graphics.quadraturin.objects.Body;
import yarangi.graphics.quadraturin.objects.IVeilEntity;
import yarangi.spatial.AABB;
import yarangi.spatial.Area;

public abstract class GenericEnemy implements Damageable
{

	private Integrity integrity;
	
	private IVeilEntity target;
	
	
	public GenericEnemy(Area area, IVeilEntity target, Integrity integrity) {
		super();
		this.target = target;
		this.integrity = integrity;
		
		setArea(area);
	}
	
	public IVeilEntity getTarget() { return target; }

	public boolean isCollidable() {
		return true;
	}

	public boolean isPickable() {
		return true;
	}

	public Integrity getIntegrity() {
		return integrity;
	}

	public void hit(Damage damage) 
	{
		integrity.hit(damage);
	}
}
