package yarangi.game.harmonium.temple.weapons;

import yarangi.game.harmonium.ai.weapons.IntellectCore;
import yarangi.game.harmonium.battle.Damage;
import yarangi.game.harmonium.battle.IDamageable;
import yarangi.game.harmonium.battle.ITemple;
import yarangi.game.harmonium.battle.Integrity;
import yarangi.game.harmonium.environment.resources.Port;
import yarangi.game.harmonium.environment.resources.Resource;
import yarangi.game.harmonium.temple.BattleInterface;
import yarangi.game.harmonium.temple.IServiceable;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.spatial.AABB;
import yarangi.spatial.Area;

public abstract class Weapon extends Entity implements IServiceable, ITemple, IDamageable
{

	private static final long serialVersionUID = 1561840016371205291L;

//	private WeaponPlatform platform;
	
//	private Vector2D trackingPoint;
	
//	private double trackingSpeed;
	
	private WeaponProperties props;
	
	private IntellectCore core;
	
//	private double reloadTime;
	private double timeToReload = 0;
	
//	private double absoluteAngle = 0;
	private BattleInterface battleInterface; 
	private double requestedAmount = 0;
	private double arrivedAmount = 0;
	private Integrity integrity;
	
	private Port port;
	
	private AABB serviceArea;
	
	private boolean isPoweredUp = true;
	
	protected Weapon(BattleInterface battleInterface, AABB area, WeaponProperties props, Integrity integrity) {
		
		this.battleInterface = battleInterface;
		this.props = props;
		this.integrity = integrity;
		this.port = new Port();
		port.setCapacity( props.getResourceType(), props.getResourceCapacity(), props.getResourceCapacity() );
		core = battleInterface.getCore();
		setArea(area);
		
//		serviceArea = new AABB(area.get)
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
	
	public abstract Projectile fire();
	
	public void advanceReloading(double time)
	{
		this.timeToReload -= time;
	}
	
	public void reload()
	{
		this.timeToReload = props.getCannonReloadingTime();
	}
	
	public WeaponProperties getProps() { return props; }
	
	public IntellectCore getCore() { return core; }

	public BattleInterface getBattleInterface() {
		return battleInterface;
	}
	@Override
	public Area getServiceArea()
	{
		return getArea();
	}

	@Override
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
	

	@Override
	public Integrity getIntegrity()
	{
		return integrity;
	}

	@Override
	public void hit(Damage damage)
	{
		integrity.hit( damage );
		port.use(Resource.Type.ENERGY, damage.getDamage( Damage.ELECTRO_MAGNETIC ));
	}

	@Override
	public int getGroupId()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public abstract void updateState(double time);

	public abstract double getProjectileSpeed();

}
