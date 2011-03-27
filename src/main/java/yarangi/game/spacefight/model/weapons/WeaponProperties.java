package yarangi.game.spacefight.model.weapons;

import yarangi.game.spacefight.model.Damage;

/**
 * Unites the characteristics of a cannon and the projectile properties.
 * 
 * @author Dve Yarangi
 */
public class WeaponProperties 
{
	/**
	 * Cannon rotation speed.
	 */
	private double cannonTrackingSpeed;
	
	/** 
	 * Cannon aiming accuracy.
	 */
	private double cannonTrackingAccuracy;
	
	/**
	 * Time between shells.
	 */
	private double cannonReloadingTime;
	
	/**
	 * The rotation angle boundary. 
	 */
	private double cannonTrackingHalfWidth;
	
	/**
	 * Shell speed.
	 */
	private double projectileSpeed;
	
	/**
	 * Shell range.
	 */
	private double projectileRange;
	
	private double projectileHitRadius;
	/**
	 * Shell accuracy.
	 * TODO: replace with trajectory function
	 */
	private double projectileTrajectoryAccuracy;
	
	private Damage damage;
	
	public WeaponProperties(double cannonTrackingSpeed, double cannonTrackingHalfWidth,
			double cannonTrackingAccuracy, double cannonReloadingTime,
			
			double projectileSpeed, double projectileRange,
			double projectileTrajectoryAccuracy,
			double projectileHitRadius,
			Damage damage) 
	{
		
		super();
		this.cannonTrackingSpeed = cannonTrackingSpeed;
		this.cannonTrackingHalfWidth = cannonTrackingHalfWidth;
		this.cannonTrackingAccuracy = cannonTrackingAccuracy;
		this.cannonReloadingTime = cannonReloadingTime;
		this.projectileSpeed = projectileSpeed;
		this.projectileRange = projectileRange;
		this.projectileHitRadius = projectileHitRadius;
		this.projectileTrajectoryAccuracy = projectileTrajectoryAccuracy;
		this.damage = damage;
	}
	
	public double getCannonTrackingSpeed() {
		return cannonTrackingSpeed;
	}
	public double getCannonTrackingHalfWidth()
	{
		return cannonTrackingHalfWidth;
	}
	public double getCannonTrackingAccuracy() {
		return cannonTrackingAccuracy;
	}
	public double getCannonReloadingTime() {
		return cannonReloadingTime;
	}
	public double getProjectileSpeed() {
		return projectileSpeed;
	}
	public double getProjectileRange() {
		return projectileRange;
	}
	public double getProjectileHitRadius() {
		return projectileHitRadius;
	}
	public double getProjectileTrajectoryAccuracy() {
		return projectileTrajectoryAccuracy;
	}	
	public Damage getDamage() { return damage; }
	
	
}
