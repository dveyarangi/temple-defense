package yarangi.game.harmonium.temple;

import yarangi.game.harmonium.Playground;
import yarangi.game.harmonium.controllers.TempleController;
import yarangi.game.harmonium.environment.resources.Port;
import yarangi.spatial.Area;


public class TempleEntity extends ObserverEntity implements Serviceable 
{

	private static final long serialVersionUID = 6893825029204201873L;

	private TempleStructure structure;
	
	
	ObserverEntity highlight;
	
	Port port;
	
	double transferRate = 5;
	
	double health;

	public TempleEntity(Playground playground)
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
}
