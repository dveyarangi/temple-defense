package yarangi.game.temple.model.weapons;

import yarangi.game.temple.actions.Fireable;
import yarangi.game.temple.model.temple.platforms.WeaponPlatform;
import yarangi.graphics.quadraturin.objects.CompositeSceneEntity;
import yarangi.math.AABB;
import yarangi.math.Vector2D;

public abstract class Weapon extends CompositeSceneEntity implements Fireable
{

	private static final long serialVersionUID = 1561840016371205291L;

	private WeaponPlatform platform;
	
	private Vector2D trackingPoint;
	
//	private double trackingSpeed;
	
	private WeaponProperties props;
	
//	private double reloadTime;
	private double timeToReload = 0;
	
	private double absoluteAngle;
	
	protected Weapon(WeaponPlatform platform, double x, double y, double a, WeaponProperties props) {
		super(new AABB(x,y,a,0));

		this.platform = platform;
		this.props = props;
		setBehavior(new WeaponBehavior());
	}
	public WeaponPlatform getPlatform() { return platform; }
	public void setPlatform(WeaponPlatform platform) { this.platform = platform; }
	
	public Vector2D getTrackingPoint() { return trackingPoint; }
	public double getTrackingSpeed() { return props.getCannonTrackingSpeed(); }
	
	public void setA(double a) 
	{ 
		this.absoluteAngle = a + platform.getAABB().getA() + platform.getBattleInterface().getAbsoluteAngle();
	}
	
	public double getAbsoluteAngle() { return absoluteAngle; }
	
	public boolean isReloaded()
	{
		return timeToReload < 0;
	}
	
	public void advanceReloading(double time)
	{
		this.timeToReload -= time;
	}
	
	public void reload()
	{
		this.timeToReload = props.getCannonReloadingTime();
	}
	
	public WeaponProperties getWeaponProperties() { return props; }
	
	@Override
	public boolean isPickable() { return false; }
	

}
