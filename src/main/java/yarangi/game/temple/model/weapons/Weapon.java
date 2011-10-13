package yarangi.game.temple.model.weapons;

import yarangi.game.temple.actions.Fireable;
import yarangi.game.temple.ai.NetCore;
import yarangi.game.temple.model.Resource;
import yarangi.game.temple.model.temple.BattleInterface;
import yarangi.game.temple.model.temple.Serviceable;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.math.Vector2D;
import yarangi.spatial.Area;

public abstract class Weapon extends Entity implements Fireable, Serviceable
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
	
	private Resource availableResource;
	
	private BattleInterface battleInterface; 
	private double requestedAmount = 0;
	private double arrivedAmount = 0;
	protected Weapon(BattleInterface battleInterface, WeaponProperties props) {
		
		this.battleInterface = battleInterface;
		this.props = props;
		// TODO: lets start with this:
		this.availableResource = new Resource( props.getResourceType(), props.getResourceCapacity() / 2);
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
	public Area getServicePoint()
	{
		return getArea();
	}

	public void supply(Resource resource)
	{
		if(this.availableResource.getType() == resource.getType())
			this.availableResource.supply(resource.getAmount());
		
		arrivedAmount += resource.getAmount();
		if(arrivedAmount >= requestedAmount )
		{
			requestedAmount = 0;
			arrivedAmount = 0;
		}
	}
	
	public Resource consume(Resource resource)
	{
		return availableResource.consume( resource.getAmount(), false );
	}
	
	protected double consume(double amountToConsume)
	{
		Resource consumed = availableResource.consume( amountToConsume, false );
		return consumed == null ? 0 : consumed.getAmount();
	}
	
	public void requestResource()
	{
		if(availableResource.getAmount() < getWeaponProperties().getResourceCapacity() / 2 && requestedAmount == 0)
		{
			this.requestedAmount = getWeaponProperties().getResourceCapacity();
			getBattleInterface().requestResource( this, 
					new Resource(getWeaponProperties().getResourceType(), requestedAmount) );
		}
	}
}
