package yarangi.game.harmonium.temple;

import yarangi.game.harmonium.Playground;
import yarangi.game.harmonium.battle.Damage;
import yarangi.game.harmonium.battle.Damageable;
import yarangi.game.harmonium.battle.ITemple;
import yarangi.game.harmonium.battle.Integrity;
import yarangi.game.harmonium.environment.resources.Port;
import yarangi.spatial.Area;


public class EnergyCore extends ObserverEntity implements Serviceable, ITemple, Damageable 
{

	private static final long serialVersionUID = 6893825029204201873L;

	private TempleStructure structure;
	
	
	ObserverEntity highlight;
	
	Port port;
	
	double transferRate = 5;
	
	double health;

	public EnergyCore(Playground playground)
	{
		super();
		
		// TODO: for now:
		port = Port.createEndlessPort();
		port.setTransferRate( transferRate );

	}
	

	public TempleStructure getStructure() { return structure; }

	@Override
	public Area getServiceArea()
	{
		return getArea();
	}

	@Override
	public Port getPort()
	{
		return port;
	}


	public void setHealth(double health)
	{
		this.health = health;
	}
	
	public double getHealth() { return health; }
	
	public double getAttractivity() { return 300; }


	@Override
	public int getGroupId()
	{
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Integrity getIntegrity()
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void hit(Damage damage)
	{
		// TODO Auto-generated method stub
		
	}

}
