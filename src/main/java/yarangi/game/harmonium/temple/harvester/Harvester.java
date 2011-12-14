package yarangi.game.harmonium.temple.harvester;

import yarangi.game.harmonium.ai.economy.IOrderScheduler;
import yarangi.game.harmonium.environment.resources.Port;
import yarangi.game.harmonium.temple.Serviceable;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.spatial.Area;

public class Harvester extends Entity implements Serviceable
{
	
	private Port port;
	
	private IOrderScheduler scheduler;
	
	
	public Harvester(Port port, IOrderScheduler scheduler)
	{
		this.port = port;
		this.scheduler = scheduler;
	}
	

	@Override
	public Area getServicePoint()
	{
		return getArea(); 
	}

	@Override
	public Port getPort()
	{
		return port;
	}

}
