package yarangi.game.temple.model.weapons;

import yarangi.game.temple.model.Damage;
import yarangi.game.temple.model.temple.BattleInterface;
import yarangi.spatial.ISpatialObject;
import yarangi.spatial.SpatialIndexer;

public class FlakCannon extends Weapon 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3710249126189305664L;
	private static final WeaponProperties FLACK_PROPS = 
		new WeaponProperties(1, 180, 5, 5, 1000, 2, 600, 1, 2, new Damage(0.1, 0.1, 0, 0));
	
	private FlakShellBehavior shellBehavior;
	private FlakShellLook shellLook;// = new FlakShellLook();
	
	public FlakCannon(BattleInterface platform, double x, double y, double a, SpatialIndexer <ISpatialObject> indexer) {
		super(platform, x, y, a, FLACK_PROPS);
		setLook(new FlakCannonLook());
		
		shellBehavior = new FlakShellBehavior(indexer);
	}

	/**
	 * {@inheritDoc}
	 */
	public Projectile fire() 
	{
		if(!isReloaded())
			return null;
/*		AABB aabb = getAABB();
		BattleInterface bi = getPlatform().getBattleInterface();
		Vector2D weaponLoc = bi.toWorldCoordinates(getPlatform(), aabb.getX(), aabb.getY());
		
		Vector2D explodePoint = bi.getTrackingPoint(this);
		if (explodePoint == null)
			return null;
		double distanceToExplosion = DistanceUtils.calcDistanceSquare(explodePoint, weaponLoc);
		double fireAngle = Angles.toRadians(RandomUtil.getRandomGaussian(
				getAbsoluteAngle(), getWeaponProperties().getProjectileTrajectoryAccuracy()));
		double s = getWeaponProperties().getProjectileSpeed();
		
		FlakShell shell = new FlakShell(weaponLoc.x, weaponLoc.y, 
				Angles.toDegrees(fireAngle), new Vector2D(s*Math.cos(fireAngle), s*Math.sin(fireAngle)),
				Math.sqrt(distanceToExplosion)*2,this.getWeaponProperties());
		
		reload();
		shell.setLook(shellLook);
		shell.setBehavior(shellBehavior);*/

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void stop() {
	}


}
