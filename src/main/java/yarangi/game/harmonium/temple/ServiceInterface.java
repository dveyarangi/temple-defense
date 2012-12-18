package yarangi.game.harmonium.temple;

import java.util.ArrayList;
import java.util.List;

import yarangi.game.harmonium.temple.bots.Bot;
import yarangi.numbers.RandomUtil;

public class ServiceInterface
{
	private List <IServiceable> serviceables = new ArrayList <IServiceable> (); 
	
	public IServiceable getServiceTarget(Bot bot)
	{
		return serviceables.get( RandomUtil.N( serviceables.size() ) );
	}
	
	public void addServiceable(IServiceable serv)
	{
		if(serviceables.contains( serv ))
			throw new IllegalArgumentException(serv + " is already registered as serviceable entity.");
		
		serviceables.add( serv );
	}

}
