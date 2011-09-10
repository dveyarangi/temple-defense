package yarangi.game.temple.model.weapons;

import yarangi.game.temple.model.Damage;
import yarangi.game.temple.model.temple.BattleInterface;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.graphics.quadraturin.objects.Look;
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
	public static final double projectileSpeed = 6;
	public static final double projectileHitRadius = 3;
	public static final double trackingSpeed = BASE_TRACKING_SPEED;
	public static final double effectiveRangeSquare = 40000;
	public static final double maxRangeSquare = 200;
	public static final double RELOADING_TIME = 5;
	public static final double ACCURACY = 0.2;
	public static final Damage DAMAGE = new Damage(5, 0.1, 0, 0);
	private static final WeaponProperties PROPS = new WeaponProperties(
			BASE_TRACKING_SPEED, 
			CANNON_TRACKING_HALF_WIDTH, 
			1, 
			RELOADING_TIME, 
			effectiveRangeSquare,
			projectileSpeed, 
			maxRangeSquare, 
			ACCURACY, 
			projectileHitRadius, 
			DAMAGE);
	
	
	private Look <Projectile> shellLook = /*new SpriteLook <Projectile> (*/new MinigunBurstLook()/*, 8, 8, false)*/;
	private Behavior <Projectile> shellBehavior;
	
	public Minigun(BattleInterface bi) {
		super(bi, PROPS);
		shellBehavior = new SimpleProjectileBehavior(bi);
	}
	
	private double shotSpeed = -2;

	public Projectile fire() 
	{
		if(!isReloaded())
			return null;
		
		Area area = getArea();
//		Vector2D weaponLoc = battleInterface.toWorldCoordinates(getPlatform(), area.getRefPoint().x(), area.getRefPoint().y());
		
		double fireAngle = Angles.toRadians(RandomUtil.getRandomGaussian(
				getArea().getOrientation(), getWeaponProperties().getProjectileTrajectoryAccuracy())) + area.getOrientation();


		double s = getWeaponProperties().getProjectileSpeed() + shotSpeed;
//		shotSpeed += 0.01;
//		if(shotSpeed > 2)
//			shotSpeed = -2;
		Vector2D shellSpeed = new Vector2D(s, fireAngle, true);
//		System.out.println(shellSpeed);
		Projectile shell = new Projectile( shellSpeed, this.getWeaponProperties());
		
/*		ExplodingBehavior dyingBehavior = new ExplodingBehavior( new Color(1,1,1,1), 5 );
		Look <SceneEntity> dyingLook = new CircleLightLook<SceneEntity>( dyingBehavior.getActiveColor() );
		SceneEntity explosion = new SceneEntity(){};
		explosion.setBehavior( dyingBehavior );
		explosion.setLook( dyingLook );
		explosion.setSensor( new DummySensor(32) );
		explosion.setArea( new Area.PointArea( getArea().getRefPoint() ) );*/
//		shell.setLook(new CircleLightLook<SceneEntity>( new Color(0,1,0,1)));
//		shell.setBehavior(shellBehavior);
//		shell.setArea(new AABB(area.getRefPoint().x(), area.getRefPoint().y(), getWeaponProperties().getProjectileHitRadius(), fireAngle));
//		shell.setSensor( new DummySensor(4) );
		shell.setLook(shellLook);
		shell.setBehavior(shellBehavior);
		shell.setArea(new AABB(area.getRefPoint().x(), area.getRefPoint().y(), getWeaponProperties().getProjectileHitRadius(), fireAngle));
		shell.setBody(new Body());
		shell.getBody().setMaxSpeed(s);
		shell.getBody().addVelocity(shellSpeed.x(), shellSpeed.y());
//		System.out.println("proj velocity " + v + " : " + getVelocity()+ " (" + a + ")");
		shell.getBody().setMass(0.001);
		
		reload();
		return shell;
	}

	/**
	 * {@inheritDoc}
	 */
	public void stop() {
	}

}
