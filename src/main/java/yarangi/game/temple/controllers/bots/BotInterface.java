package yarangi.game.temple.controllers.bots;

import java.util.ArrayList;
import java.util.List;


import yarangi.game.temple.controllers.orders.IOrder;
import yarangi.game.temple.model.resource.GatheringOrder;
import yarangi.game.temple.model.resource.Resource;
import yarangi.game.temple.model.resource.ResourceRequest;
import yarangi.game.temple.model.temple.Serviceable;
import yarangi.game.temple.model.temple.bots.Bot;
import yarangi.numbers.RandomUtil;

public class BotInterface
{
	
	public static double MAX_TRANSFER_RATE = 10;
	public static double MIN_TRANSFER_RATE = 0.1;
	
	
	private ArrayList <ResourceRequest> requestsQueue = new ArrayList <ResourceRequest> ();
	
	
	private List <Bot> bots = new ArrayList <Bot> ();
	
	public int requestResource(Serviceable requester, Serviceable provider, Resource.Type type, double amount, int priority)
	{
		ResourceRequest request = new ResourceRequest(requester, provider, type, amount);
		
		int handle = requestsQueue.size();
		for(int i = 0; i < priority; i ++)
			requestsQueue.add(request); // questionable way of prioritizing, but will do for now
		
		return handle;
	}
	
	public IOrder getOrder(Bot bot)
	{
		if(requestsQueue.size() == 0 )
			return null;
		ResourceRequest request;
		int dice = RandomUtil.getRandomInt( requestsQueue.size() );
		request = requestsQueue.get( dice );
		
		if(request.isApproved())
		{
			
			return null;
		}	
		double amount = bot.getPort().getCapacity( request.getType() );
		request.approve( amount );
		
		return new GatheringOrder(request, request.getType(), amount);
	}
	
	public void fulfill(IOrder order)
	{
		if(order.isClosed())
			throw new IllegalArgumentException("CAnnot fulfill closed order");
		if(order instanceof GatheringOrder)
		{
			GatheringOrder go = (GatheringOrder) order;
			ResourceRequest request = go.getRequest();
			request.supply( go.getOrderedAmount() );
			
			if(request.isFulfilled()) // TODO: a bit uneffective:
			{
				for(int idx = 0; idx < requestsQueue.size(); idx ++)
					if(requestsQueue.get( idx ) == go.getRequest())
						requestsQueue.remove(idx);
			}
		}
	}

	public void reject(IOrder order)
	{
		if(order instanceof GatheringOrder)
		{
			GatheringOrder go = (GatheringOrder) order;
			ResourceRequest request = go.getRequest();
			request.approve( -go.getOrderedAmount() );
		}

	}

	public void add(Bot bot)
	{
		bots.add( bot );
	}
	
	public List <Bot> getBotsList() { return bots; }

	public double changeTransferRate(double d)
	{
		double transferRate = 1;
		for(Bot bot : this.getBotsList())
		{
			transferRate = bot.getPort().getTransferRate();
			
			transferRate += d;
			
			if(transferRate < MIN_TRANSFER_RATE)
				transferRate = MIN_TRANSFER_RATE;
			if(transferRate > MAX_TRANSFER_RATE)
				transferRate = MAX_TRANSFER_RATE;
			
			bot.getPort().setTransferRate( transferRate );
		}
		
		return transferRate;
	}
}
