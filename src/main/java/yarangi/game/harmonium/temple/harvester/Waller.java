package yarangi.game.harmonium.temple.harvester;

import yarangi.game.harmonium.ai.economy.IOrderScheduler;
import yarangi.game.harmonium.battle.ITemple;
import yarangi.game.harmonium.environment.resources.Port;
import yarangi.game.harmonium.temple.IObserverEntity;
import yarangi.game.harmonium.temple.Serviceable;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.spatial.Area;

public class Waller extends Entity implements Serviceable, ITemple, IObserverEntity
{
	
	private Port port;
	
	private IOrderScheduler scheduler;
	
	
	public Waller(Port port, IOrderScheduler scheduler)
	{
		this.port = port;
		this.scheduler = scheduler;
	}
	

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


	@Override
	public double getAttractiveness()
	{
		return 0;
	}

}
