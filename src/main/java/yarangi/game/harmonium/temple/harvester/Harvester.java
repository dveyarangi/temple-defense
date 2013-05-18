package yarangi.game.harmonium.temple.harvester;

import yar.quadraturin.objects.Entity;
import yarangi.game.harmonium.ai.economy.IOrderScheduler;
import yarangi.game.harmonium.battle.Damage;
import yarangi.game.harmonium.battle.ITemple;
import yarangi.game.harmonium.battle.Integrity;
import yarangi.game.harmonium.environment.resources.Port;
import yarangi.game.harmonium.temple.IObserverEntity;
import yarangi.game.harmonium.temple.IServiceable;
import yarangi.spatial.Area;

public class Harvester extends Entity implements IServiceable, ITemple, IObserverEntity
{
	
	private final Port port;
	
	private final IOrderScheduler scheduler;
	
	
	public Harvester(Port port, IOrderScheduler scheduler)
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
		return 0.05;
	}


	@Override
	public double getLeadership()
	{
		return 100;
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
