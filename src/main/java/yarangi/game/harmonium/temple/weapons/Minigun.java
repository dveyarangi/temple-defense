package yarangi.game.harmonium.temple.weapons;

import yarangi.game.harmonium.battle.Damage;
import yarangi.game.harmonium.battle.Integrity;
import yarangi.game.harmonium.environment.resources.Resource;
import yarangi.game.harmonium.temple.BattleInterface;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.AABB;

public class Minigun extends Weapon 
{
	private static final long serialVersionUID = -4267007085490446753L;
	
	public static final double BASE_TRACKING_SPEED = 3;
	public static final double CANNON_TRACKING_HALF_WIDTH = 360;
	public static final double projectileSpeed = 5;
	public static final double projectileHitRadius = 1;
	public static final double trackingSpeed = BASE_TRACKING_SPEED;
	public static final double effectiveRangeSquare = 40000;
	public static final double maxRangeSquare = 200;
	public static final double RELOADING_TIME = 20;
	public static final double ACCURACY = 0.2;
	public static final Damage DAMAGE = new Damage(0.1, 0.1, 0, 0);
//	public static final Damage DAMAGE = new Damage(15, 0.1, 0, 0);
	public static final double resourceCapacity = 1000;
	public static final double resourceConsumption = 50;
	public static final Resource.Type resourceType = Resource.Type.ENERGY;

	public static final WeaponProperties PROPS0 = new WeaponProperties(
			BASE_TRACKING_SPEED, 
			CANNON_TRACKING_HALF_WIDTH, 
			1, 
			1, // reloading time
			128, // projectile range square
			projectileSpeed,  // prjectile speed
			128, // tracking range
			ACCURACY, 
			projectileHitRadius, 
			new Damage(0.2, 0.1, 0, 0),
			resourceCapacity, 0, resourceType,
			128);
	
	public static final WeaponProperties PROP_SMALL = new WeaponProperties(
			BASE_TRACKING_SPEED, 
			CANNON_TRACKING_HALF_WIDTH, 
			1, 
			4, // reloading time
			32, // projectile range square
			projectileSpeed,  // prjectile speed
			32, // tracking range
			ACCURACY, 
			projectileHitRadius, 
			new Damage(15, 0.1, 0, 0),
			resourceCapacity, 0, resourceType,
			32);
	public static final WeaponProperties PROPS1 = new WeaponProperties(
			BASE_TRACKING_SPEED, 
			CANNON_TRACKING_HALF_WIDTH, 
			1, 
			4, // reloading time
			64, // projectile range square
			projectileSpeed,  // prjectile speed
			64, // tracking range
			ACCURACY, 
			projectileHitRadius, 
			new Damage(15, 0.1, 0, 0),
			resourceCapacity, 8, resourceType,
			256);
	public static final WeaponProperties PROPS2 = new WeaponProperties(
			BASE_TRACKING_SPEED, 
			CANNON_TRACKING_HALF_WIDTH, 
			1, 
			8, 
			128,
			4, 
			128, 
			ACCURACY, 
			projectileHitRadius, 
			new Damage(30, 0.1, 0, 0),
			resourceCapacity, 16, resourceType,
			256);
	public static final WeaponProperties PROPS3 = new WeaponProperties(
			BASE_TRACKING_SPEED, 
			CANNON_TRACKING_HALF_WIDTH, 
			1, 
			16, 
			256,
			8, 
			256, 
			ACCURACY, 
			projectileHitRadius, 
			new Damage(60, 0.1, 0, 0),
			resourceCapacity, 32, resourceType,
			256);

	private double nextProjectileSpeed;
	
	
	public Minigun(BattleInterface bi, AABB area, WeaponProperties props) {
		super(bi, area, props, Integrity.DUMMY());

	}

	@Override
	public Projectile fire() 
	{
		if(!isReloaded())
			return null;
		if(! this.powerUp())
			return null; // not enough resources

		reload();
		
		return WeaponFactory.createProjectile( this, nextProjectileSpeed );
	}

	/**
	 * {@inheritDoc}
	 */
	public void stop() {
	}

	@Override
	public double getAttractiveness() { return 100; }

	@Override
	public void updateState(double time)
	{
		nextProjectileSpeed = RandomUtil.STD( getProps().getProjectileSpeed(), 0.01);
	}

	@Override
	public double getProjectileSpeed()
	{
		
		return nextProjectileSpeed;
	}

	@Override
	public double getLeadership()
	{
		return 0;
	}
}
