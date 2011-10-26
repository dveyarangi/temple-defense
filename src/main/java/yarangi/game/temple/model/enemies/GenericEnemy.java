package yarangi.game.temple.model.enemies;

import yarangi.game.temple.model.Damage;
import yarangi.game.temple.model.Damageable;
import yarangi.game.temple.model.Integrity;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.graphics.quadraturin.objects.ILayerEntity;
import yarangi.spatial.Area;

public abstract class GenericEnemy extends Entity implements Damageable
{

	private Integrity integrity;
	
	private ILayerEntity target;
	
	
	public GenericEnemy(Area area, ILayerEntity target, Integrity integrity) {
		super();
		this.target = target;
		this.integrity = integrity;
		
		setArea(area);
	}
	
	public ILayerEntity getTarget() { return target; }

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
