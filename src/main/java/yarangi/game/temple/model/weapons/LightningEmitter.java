package yarangi.game.temple.model.weapons;

import yarangi.game.temple.controllers.BattleInterface;
import yarangi.game.temple.model.Damage;
import yarangi.game.temple.model.temple.platforms.WeaponPlatform;
import yarangi.graphics.quadraturin.objects.SceneEntity;
import yarangi.math.AABB;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;

public class LightningEmitter extends Weapon 
{

	private static final long serialVersionUID = -1437318912599089233L;
	
	private static final WeaponProperties FLACK_PROPS = 
		new WeaponProperties(1, 100, 1, 2, 5, 1000, 1, 1, new Damage(0, 1, 0.1, 0));
	
	private LightningBeam last;
	
	private boolean fired = false;
	
	private double distance = 0;
	
	private LightningBeamLook shellLook = new LightningBeamLook();
	private LightningBeamBehavior shellBehavior = new LightningBeamBehavior();
	
	public LightningEmitter(WeaponPlatform platform, double x, double y, double a) 
	{
		super(platform, x, y, a, FLACK_PROPS);
		setLook(new LightningEmitterLook());

	}

	public SceneEntity fire() {
		if(!isReloaded())
			return null;
		
		AABB aabb = getAABB();
		
		BattleInterface bi = getPlatform().getBattleInterface();
		Vector2D weaponLoc = bi.toWorldCoordinates(getPlatform(), aabb.x, aabb.y);
		
//		Vector2D explodePoint = bi.getTrackingPoint(this);
//		double distanceToExplosion = DistanceUtils.calcDistanceSquare(explodePoint, weaponLoc);
		
		double fireAngle = Angles.toRadians(RandomUtil.getRandomGaussian(
				getAbsoluteAngle(), getWeaponProperties().getProjectileTrajectoryAccuracy()));
		double s = getWeaponProperties().getProjectileSpeed();
		
		LightningBeam shell = new LightningBeam(weaponLoc.x+distance*Math.cos(fireAngle), distance*Math.sin(fireAngle), 
				fireAngle, new Vector2D(s*Math.cos(fireAngle), s*Math.sin(fireAngle)), this.getWeaponProperties(), last);
		
		this.last = shell;
//		System.out.println(last);
		distance += this.getWeaponProperties().getProjectileSpeed();	
		reload();
		shell.setLook(shellLook);
		shell.setBehavior(shellBehavior);

		return shell;
	}

	public void stop() {
		last = null;
	}

}
