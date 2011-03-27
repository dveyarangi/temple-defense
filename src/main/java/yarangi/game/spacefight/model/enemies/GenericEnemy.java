package yarangi.game.spacefight.model.enemies;

import yarangi.game.spacefight.model.Damage;
import yarangi.game.spacefight.model.Damageable;
import yarangi.game.spacefight.model.Integrity;
import yarangi.graphics.quadraturin.interaction.PhysicalEntity;
import yarangi.graphics.quadraturin.interaction.spatial.AABB;
import yarangi.graphics.quadraturin.objects.SceneEntity;
import yarangi.math.Vector2D;

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
