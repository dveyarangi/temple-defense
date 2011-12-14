package yarangi.game.harmonium.temple.weapons;

import yarangi.game.harmonium.environment.resources.Resource;
import yarangi.game.harmonium.model.Damage;
import yarangi.game.harmonium.temple.BattleInterface;

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
