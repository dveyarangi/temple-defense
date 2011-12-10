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
			40000, // projectile range square
			10,  // prjectile speed
			200, // tracking range
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
			40000,
			2, 
			200, 
			ACCURACY, 
			projectileHitRadius, 
			DAMAGE,
			resourceCapacity, 10, resourceType
			);
	
	
	private Behavior <Projectile> shellBehavior;
	
	public Minigun(BattleInterface bi, WeaponProperties props) {
		super(bi, props);
		shellBehavior = new SimpleProjectileBehavior(bi);
		

	}

	public Projectile fire() 
	{
		if(!isReloaded())
			return null;
		if(! this.powerUp())
			return null; // not enough resources

		
		Area area = getArea();
		
		double fireAngle = Angles.toRadians(RandomUtil.getRandomGaussian(
				getArea().getOrientation(), getWeaponProperties().getProjectileTrajectoryAccuracy())) + area.getOrientation();

		reload();
		
		return createProjectile( area.getRefPoint(), fireAngle );
	}

	/**
	 * {@inheritDoc}
	 */
	public void stop() {
	}

	
	private Projectile createProjectile(Vector2D location, double firingAngle)
	{
		double speedScalar = getWeaponProperties().getProjectileSpeed();
		
		Vector2D speed = new Vector2D(speedScalar, firingAngle, true);
		Projectile shell = new Projectile( speed, this.getWeaponProperties());
		shell.setLook(new MinigunBurstLook(this));
		shell.setBehavior(shellBehavior);
		shell.setArea(AABB.createSquare(location.x(), location.y(), getWeaponProperties().getProjectileHitRadius(), firingAngle));
		shell.setBody(new Body());
		shell.getBody().setMaxSpeed(speedScalar);
		shell.getBody().addVelocity(speed.x(), speed.y());
//		System.out.println("proj velocity " + v + " : " + getVelocity()+ " (" + a + ")");
		shell.getBody().setMass(0.001);
		return shell;
	}
}
