package yarangi.game.temple.model.enemies;

import yarangi.game.temple.model.Damage;
import yarangi.game.temple.model.Damageable;
import yarangi.game.temple.model.Integrity;
import yarangi.graphics.quadraturin.objects.NewtonialSceneEntity;
import yarangi.graphics.quadraturin.objects.SceneEntity;
import yarangi.spatial.AABB;

public abstract class GenericEnemy extends NewtonialSceneEntity implements Damageable
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
