package yarangi.game.temple.model.weapons;

import yarangi.game.temple.model.Damage;
import yarangi.game.temple.model.resource.Resource;

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
	
	private double cannonEffectiveRange;
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
	
	// TODO: following three parameters should be somehow generalized:
	private double resourceCapacity;
	private double resourceConsumption;
	private Resource.Type resourceType;
	
	public WeaponProperties(double cannonTrackingSpeed, double cannonTrackingHalfWidth,
			double cannonTrackingAccuracy, double cannonReloadingTime, double cannonEffectiveRange,
			
			double projectileSpeed, double projectileRange,
			double projectileTrajectoryAccuracy,
			double projectileHitRadius,
			Damage damage, 
			double resourceCapacity, double resourceConsumption, Resource.Type resourceType) 
	{
		
		super();
		this.cannonTrackingSpeed = cannonTrackingSpeed;
		this.cannonTrackingHalfWidth = cannonTrackingHalfWidth;
		this.cannonTrackingAccuracy = cannonTrackingAccuracy;
		this.cannonReloadingTime = cannonReloadingTime;
		this.cannonEffectiveRange = cannonEffectiveRange;
		this.projectileSpeed = projectileSpeed;
		this.projectileRange = projectileRange;
		this.projectileHitRadius = projectileHitRadius;
		this.projectileTrajectoryAccuracy = projectileTrajectoryAccuracy;
		this.damage = damage;
		
		this.resourceCapacity = resourceCapacity;
		this.resourceConsumption = resourceConsumption;
		this.resourceType = resourceType;
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

	public double getEffectiveRange() {
		return cannonEffectiveRange;
	}
	public double getResourceCapacity() { return resourceCapacity; }
	public double getResourceConsumption() { return resourceConsumption; }
	public Resource.Type getResourceType() { return resourceType; }
	
}
