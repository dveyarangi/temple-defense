package yarangi.game.temple.model.weapons;

import yarangi.game.temple.model.Damage;
import yarangi.game.temple.model.Damageable;
import yarangi.graphics.quadraturin.interaction.PhysicalEntity;
import yarangi.graphics.quadraturin.objects.SceneEntity;
import yarangi.math.AABB;
import yarangi.math.Vector2D;

public class Projectile extends SceneEntity implements PhysicalEntity 
{

	private static final long serialVersionUID = -2909463886504124942L;

	private Damage damage;
	
	private double range;
	
	protected double maxRange;
	
	public Vector2D velocity;
	
	protected Vector2D impactPoint;
	
	public  Projectile(double x, double y, double a, Vector2D v, WeaponProperties props) 
	{
		super(new AABB(x, y, props.getProjectileHitRadius(), a));
		this.range = 0;
		this.velocity = v;
		this.maxRange = props.getProjectileRange();
		this.damage = props.getDamage();
	}
	public Damage getDamage() { return damage; }
	public double getMaxRange() { return maxRange; }
	public double getRangeSquare() { return range; }
	
	public boolean addRangeSquare(double dr)
	{
		range += dr;
		if(range >= maxRange)
			return true;
		
		return false;
	}
	
	public boolean isCollidable() { return true; }
	
	public boolean isPickable() { return false; }

	public void setImpactWith(SceneEntity e) 
	{
		
		if(e instanceof Damageable)
		{
			// TODO: impact something.
			impactPoint = new Vector2D(e.getAABB());
			((Damageable)e).hit(this.getDamage());
		}
		
	}
	
	public Vector2D getImpactPoint() { return impactPoint; }

	public Vector2D getVelocity() { return velocity; }
	
}
