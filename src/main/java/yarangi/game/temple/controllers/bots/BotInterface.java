package yarangi.game.temple.controllers.bots;

import java.util.ArrayList;

import yarangi.game.temple.model.Resource;
import yarangi.game.temple.model.temple.Serviceable;
import yarangi.game.temple.model.temple.bots.Bot;
import yarangi.numbers.RandomUtil;

public class BotInterface
{
	private ArrayList <ResourceRequest> requestsQueue = new ArrayList <ResourceRequest> ();
	
	public int requestResource(Serviceable requester, Serviceable provider, Resource resource, int priority)
	{
		ResourceRequest request = new ResourceRequest(requester, provider, resource.getType(), resource.getAmount());
		
		int handle = requestsQueue.size();
		for(int i = 0; i < priority; i ++)
			requestsQueue.add(request); // questionable way of prioritizing, but will do for now
		
		return handle;
	}
	
	public IBotOrder getOrder(Bot bot)
	{
		if(requestsQueue.size() == 0 )
			return null;
		ResourceRequest request;
		int dice = RandomUtil.getRandomInt( requestsQueue.size() );
		request = requestsQueue.get( dice );
		
		if(request.isApproved())
			return null;
			
		double amount = bot.getCapacity(request.getType());
		request.approve( amount );
		
		return new GatheringOrder(request, new Resource(request.getType(), amount));
	}
	
	public void fulfill(IBotOrder order)
	{
		if(order instanceof GatheringOrder)
		{
			GatheringOrder go = (GatheringOrder) order;
			ResourceRequest request = go.getRequest();
			request.supply( go.getRequestedResource().getAmount() );
			
			if(request.isFulfilled()) // TODO: a bit uneffective:
				for(int idx = 0; idx < requestsQueue.size(); idx ++)
					if(requestsQueue.get( idx ) == go.getRequest())
						requestsQueue.remove(idx);
		}
	}

	public void reject(IBotOrder order)
	{
		if(order instanceof GatheringOrder)
		{
			GatheringOrder go = (GatheringOrder) order;
			ResourceRequest request = go.getRequest();
			request.approve( -go.getRequestedResource().getAmount() );
		}

	}
}
