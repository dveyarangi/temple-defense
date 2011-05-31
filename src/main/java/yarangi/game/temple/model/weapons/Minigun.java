package yarangi.game.temple.model.weapons;

import yarangi.game.temple.controllers.BattleInterface;
import yarangi.game.temple.model.Damage;
import yarangi.game.temple.model.temple.platforms.WeaponPlatform;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.Area;

public class Minigun extends Weapon 
{
	private static final long serialVersionUID = -4267007085490446753L;
	
	public static final double BASE_TRACKING_SPEED = 3;
	public static final double CANNON_TRACKING_HALF_WIDTH = 360;
	public static final double projectileSpeed = 6;
	public static final double projectileHitRadius = 1;
	public static final double trackingSpeed = BASE_TRACKING_SPEED;
	public static final double maxRangeSquare = 5000;
	public static final double RELOADING_TIME = 1;
	public static final double ACCURACY = 0.1;
	
	public static final Damage DAMAGE = new Damage(10, 0.01, 0, 0);
	private static final WeaponProperties PROPS = new WeaponProperties(
			BASE_TRACKING_SPEED, 
			CANNON_TRACKING_HALF_WIDTH, 
			1, 
			RELOADING_TIME, 
			projectileSpeed, 
			maxRangeSquare, 
			ACCURACY, 
			projectileHitRadius, 
			DAMAGE);
	
	
	private Look <Projectile> shellLook = /*new SpriteLook <Projectile> (*/new MinigunBurstLook()/*, 8, 8, false)*/;
	private Behavior <Projectile> shellBehavior = new SimpleProjectileBehavior();
	
	public Minigun(WeaponPlatform platfrom, double x, double y, double a) {
		super(platfrom, x, y, a, PROPS);
		
		setLook(new MinigunLook());
	}
	
	private double shotSpeed = -2;

	public Projectile fire() 
	{
		if(!isReloaded())
			return null;
		
		Area area = getArea();
		BattleInterface bi = getPlatform().getBattleInterface();
		Vector2D weaponLoc = bi.toWorldCoordinates(getPlatform(), area.getRefPoint().x(), area.getRefPoint().y());
		
		double fireAngle = Angles.toRadians(RandomUtil.getRandomGaussian(
				getAbsoluteAngle(), getWeaponProperties().getProjectileTrajectoryAccuracy()));

		
		double s = getWeaponProperties().getProjectileSpeed() + shotSpeed;
		shotSpeed += 0.01;
		if(shotSpeed > 2)
			shotSpeed = -2;
		Vector2D shellSpeed = new Vector2D(s, fireAngle, true);
		Projectile shell = new Projectile(weaponLoc.x, weaponLoc.y, 
				Angles.toDegrees(fireAngle),
						shellSpeed, 
				this.getWeaponProperties());
		
		shell.setLook(shellLook);
		shell.setBehavior(shellBehavior);

		
		reload();
		return shell;
	}

	/**
	 * {@inheritDoc}
	 */
	public void stop() {
	}

}
