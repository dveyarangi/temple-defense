package yarangi.game.harmonium.temple;

import java.util.ArrayList;
import java.util.List;

import yarangi.game.harmonium.temple.bots.Bot;
import yarangi.numbers.RandomUtil;

public class StructureInterface
{
	private List <Serviceable> serviceables = new ArrayList <Serviceable> (); 
	
	public Serviceable getServiceTarget(Bot bot)
	{
		return serviceables.get( RandomUtil.N( serviceables.size() ) );
	}
	
	public void addServiceable(Serviceable serv)
	{
		if(serviceables.contains( serv ))
			throw new IllegalArgumentException(serv + " is already registered as serviceable entity.");
		
		serviceables.add( serv );
	}

}
