package yarangi.game.temple.model.weapons;

import yarangi.game.temple.model.Damage;
import yarangi.game.temple.model.resource.Resource;
import yarangi.game.temple.model.temple.BattleInterface;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.graphics.quadraturin.simulations.Body;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.AABB;
import yarangi.spatial.Area;

public class Minigun extends Weapon 
{
	private static final long serialVersionUID = -4267007085490446753L;
	
	public static final double BASE_TRACKING_SPEED = 3;
	public static final double CANNON_TRACKING_HALF_WIDTH = 360;
	public static final double projectileSpeed = 2;
	public static final double projectileHitRadius = 3;
	public static final double trackingSpeed = BASE_TRACKING_SPEED;
	public static final double effectiveRangeSquare = 40000;
	public static final double maxRangeSquare = 200;
	public static final double RELOADING_TIME = 20;
	public static final double ACCURACY = 0.2;
	public static final Damage DAMAGE = new Damage(10, 0.1, 0, 0);
	public static final double resourceCapacity = 1000;
	public static final double resourceConsumption = 50;
	public static final Resource.Type resourceType = Resource.Type.ENERGY;

	
	public static final WeaponProperties PROPS1 = new WeaponProperties(
			BASE_TRACKING_SPEED, 
			CANNON_TRACKING_HALF_WIDTH, 
			1, 
			10, // reloading time
			10000, // projectile range square
			1,  // prjectile speed
			100, // tracking range
			ACCURACY, 
			projectileHitRadius, 
			DAMAGE,
			resourceCapacity, 20, resourceType
			);
	public static final WeaponProperties PROPS2 = new WeaponProperties(
			BASE_TRACKING_SPEED, 
			CANNON_TRACKING_HALF_WIDTH, 
			1, 
			10, 
			10000,
			2, 
			100, 
			ACCURACY, 
			projectileHitRadius, 
			DAMAGE,
			resourceCapacity, 10, resourceType
			);
	
	
	public Minigun(BattleInterface bi, WeaponProperties props) {
		super(bi, props);

	}

	public Projectile fire() 
	{
		if(!isReloaded())
			return null;
		if(! this.powerUp())
			return null; // not enough resources

		reload();
		
		return WeaponFactory.createProjectile( this );
	}

	/**
	 * {@inheritDoc}
	 */
	public void stop() {
	}

	
}
