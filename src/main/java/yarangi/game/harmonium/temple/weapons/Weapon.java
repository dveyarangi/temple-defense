package yarangi.game.harmonium.temple.weapons;

import yarangi.game.harmonium.actions.Fireable;
import yarangi.game.harmonium.ai.weapons.NetCore;
import yarangi.game.harmonium.environment.resources.Port;
import yarangi.game.harmonium.temple.BattleInterface;
import yarangi.game.harmonium.temple.Serviceable;
import yarangi.graphics.quadraturin.objects.Entity;
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
	private BattleInterface battleInterface; 
	private double requestedAmount = 0;
	private double arrivedAmount = 0;
	private Port port;
	
	private boolean isPoweredUp = true;
	
	protected Weapon(BattleInterface battleInterface, WeaponProperties props) {
		
		this.battleInterface = battleInterface;
		this.props = props;
		this.port = new Port();
		port.setCapacity( props.getResourceType(), props.getResourceCapacity()/2, props.getResourceCapacity() );
		// TODO: lets start with this:
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
	
	public WeaponProperties getProps() { return props; }
	
	public NetCore getCore() { return core; }

	public BattleInterface getBattleInterface() {
		return battleInterface;
	}
	@Override
	public Area getServiceArea()
	{
		return getArea();
	}

	public Port getPort() { return port; }
	
	/** consumes resource amount required for single shot
     * TODO: resource requesting logic shouldn't be here.
	 * @return power up success
	 */
	public boolean powerUp() 
	{
		if(!isPoweredUp())
			return false;
		
		double amountRemaining = port.get(props.getResourceType()).getAmount();
		if(requestedAmount <= 0 && amountRemaining < props.getResourceCapacity()/2)
		{
			double resourceToRequest = 1.0*(props.getResourceCapacity()-amountRemaining);
			if(resourceToRequest > 0)
			{
				this.requestedAmount += resourceToRequest;
//				System.out.println(requestedAmount + " : " + amountRemaining + " : " + resourceToRequest);
				getBattleInterface().requestResource( this, props.getResourceType(), resourceToRequest);
			}
			
		}
		double consumed = port.use(props.getResourceType(), props.getResourceConsumption());
		requestedAmount -= consumed;
		if(requestedAmount < 0)
			requestedAmount = 0;
		return true;
	}
	
	public boolean isPoweredUp() { 
		double amountRemaining = port.get(props.getResourceType()).getAmount();
		double amountToConsume = props.getResourceConsumption();
		return amountRemaining >= amountToConsume;
	}
}
