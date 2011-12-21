package yarangi.game.harmonium.enemies;

import yarangi.game.harmonium.model.Damage;
import yarangi.game.harmonium.model.Damageable;
import yarangi.game.harmonium.model.Integrity;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.graphics.quadraturin.objects.ILayerObject;
import yarangi.spatial.Area;

public abstract class GenericEnemy extends Entity implements Damageable
{

	private Integrity integrity;
	
	private ILayerObject target;
	
	
	public GenericEnemy(Area area, ILayerObject target, Integrity integrity) {
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

	public Integrity getIntegrity() {
		return integrity;
	}

	public void hit(Damage damage) 
	{
		integrity.hit(damage);
	}
}