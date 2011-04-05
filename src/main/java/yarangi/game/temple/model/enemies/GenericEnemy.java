package yarangi.game.temple.model.enemies;

import yarangi.game.temple.model.Damage;
import yarangi.game.temple.model.Damageable;
import yarangi.game.temple.model.Integrity;
import yarangi.graphics.quadraturin.interaction.PhysicalEntity;
import yarangi.graphics.quadraturin.objects.SceneEntity;
import yarangi.math.AABB;

public abstract class GenericEnemy extends SceneEntity implements PhysicalEntity, Damageable
{

	private Integrity integrity;
	
	private SceneEntity target;
	
	public GenericEnemy(AABB aabb, SceneEntity target, Integrity integrity) {
		super(aabb);

		this.target = target;
		this.integrity = integrity;
	}
	
	public SceneEntity getTarget() { return target; }

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
