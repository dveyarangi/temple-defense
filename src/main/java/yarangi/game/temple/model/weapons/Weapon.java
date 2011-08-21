package yarangi.game.temple.model.weapons;

import yarangi.game.temple.actions.Fireable;
import yarangi.game.temple.ai.NetCore;
import yarangi.game.temple.model.temple.BattleInterface;
import yarangi.game.temple.model.temple.Serviceable;
import yarangi.graphics.quadraturin.objects.WorldEntity;
import yarangi.math.Vector2D;

public abstract class Weapon extends WorldEntity implements Fireable, Serviceable
{

	private static final long serialVersionUID = 1561840016371205291L;

//	private WeaponPlatform platform;
	
//	private Vector2D trackingPoint;
	
//	private double trackingSpeed;
	
	private WeaponProperties props;
	
	private NetCore core;
	
//	private double reloadTime;
	private double timeToReload = 0;
	
//	private double absoluteAngle = 0;
	
	private BattleInterface battleInterface; 

	protected Weapon(BattleInterface battleInterface, WeaponProperties props) {
		
		this.battleInterface = battleInterface;
		this.props = props;
	}
//	public WeaponPlatform getPlatform() { return platform; }
//	public void setPlatform(WeaponPlatform platform) { this.platform = platform; }
	
//	public Vector2D getTrackingPoint() { return trackingPoint; }
	public double getTrackingSpeed() { return props.getCannonTrackingSpeed(); }
	
//	public void setA(double a) 
//	{ 
//		getArea().setOrientation(a);
//		this.absoluteAngle = a + platform.getArea().getOrientation() + platform.getBattleInterface().getAbsoluteAngle();
//	}
	
//	public double getA() { return getArea().getOrientation(); }
	
//	public double getAbsoluteAngle() { return absoluteAngle; }

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
	
	public NetCore getCore() { return core; }

	public BattleInterface getBattleInterface() {
		return battleInterface;
	}
	@Override
	public Vector2D getServicePoint()
	{
		return getArea().getRefPoint();
	}

}
