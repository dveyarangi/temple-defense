package yarangi.game.harmonium.temple;

import yarangi.game.harmonium.Playground;
import yarangi.game.harmonium.battle.Damage;
import yarangi.game.harmonium.battle.IDamageable;
import yarangi.game.harmonium.battle.ITemple;
import yarangi.game.harmonium.battle.Integrity;
import yarangi.game.harmonium.environment.resources.Port;
import yarangi.game.harmonium.environment.resources.Resource;
import yarangi.game.harmonium.temple.structure.TempleStructure;
import yarangi.spatial.Area;


public class EnergyCore extends ObserverEntity implements IServiceable, ITemple, IDamageable 
{

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
	
	@Override
	public double getAttractiveness() { return 30; }


	@Override
	public double getLeadership()
	{
		// TODO Auto-generated method stub
		return 0;
	}

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
//		integrity.hit( damage );
		port.use(Resource.Type.ENERGY, damage.getDamage( Damage.ELECTRO_MAGNETIC ));
		
		transferRate -= damage.getDamage( Damage.KINETIC )/10;
		if(transferRate < 0) transferRate = 0;
//		System.out.println("transfer rate: " + transferRate);
	}


}
