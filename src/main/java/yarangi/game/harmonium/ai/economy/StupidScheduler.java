package yarangi.game.harmonium.ai.economy;

import java.util.ArrayList;
import java.util.List;

import yarangi.game.harmonium.environment.resources.GatheringOrder;
import yarangi.game.harmonium.environment.resources.Resource;
import yarangi.game.harmonium.environment.resources.ResourceRequest;
import yarangi.game.harmonium.temple.Serviceable;
import yarangi.game.harmonium.temple.bots.Bot;
import yarangi.numbers.RandomUtil;

public class StupidScheduler implements IOrderScheduler
{
	
	public static double MAX_TRANSFER_RATE = 10;
	public static double MIN_TRANSFER_RATE = 0.1;
	
	
	private ArrayList <ResourceRequest> requestsQueue = new ArrayList <ResourceRequest> ();
	
	
	private List <Bot> bots = new ArrayList <Bot> ();
	
	public StupidScheduler() 
	{

	}
	
	/* (non-Javadoc)
	 * @see yarangi.game.temple.controllers.bots.ITempleNode#requestResource(yarangi.game.temple.model.temple.Serviceable, yarangi.game.temple.model.temple.Serviceable, yarangi.game.temple.model.resource.Resource.Type, double, int)
	 */
	@Override
	public int requestResource(Serviceable requester, Serviceable provider, Resource.Type type, double amount, int priority)
	{
		ResourceRequest request = new ResourceRequest(requester, provider, type, amount);
		
		int handle = requestsQueue.size();
		for(int i = 0; i < priority; i ++)
			requestsQueue.add(request); // questionable way of prioritizing, but will do for now
		
		return handle;
	}
	
	/* (non-Javadoc)
	 * @see yarangi.game.temple.controllers.bots.ITempleNode#getOrder(yarangi.game.temple.model.temple.bots.Bot)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see yarangi.game.temple.controllers.bots.ITempleNode#fulfill(yarangi.game.temple.controllers.orders.IOrder)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see yarangi.game.temple.controllers.bots.ITempleNode#reject(yarangi.game.temple.controllers.orders.IOrder)
	 */
	@Override
	public void reject(IOrder order)
	{
		if(order instanceof GatheringOrder)
		{
			GatheringOrder go = (GatheringOrder) order;
			ResourceRequest request = go.getRequest();
			request.approve( -go.getOrderedAmount() );
		}

	}

	/* (non-Javadoc)
	 * @see yarangi.game.temple.controllers.bots.ITempleNode#add(yarangi.game.temple.model.temple.bots.Bot)
	 */
	@Override
	public void add(Bot bot)
	{
		bots.add( bot );
	}
	
	/* (non-Javadoc)
	 * @see yarangi.game.temple.controllers.bots.ITempleNode#getBotsList()
	 */
	@Override
	public List <Bot> getBotsList() { return bots; }

	/* (non-Javadoc)
	 * @see yarangi.game.temple.controllers.bots.ITempleNode#changeTransferRate(double)
	 */
	@Override
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
