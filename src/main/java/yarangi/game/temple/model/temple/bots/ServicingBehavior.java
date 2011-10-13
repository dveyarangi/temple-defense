package yarangi.game.temple.model.temple.bots;

import yarangi.game.temple.controllers.bots.BotInterface;
import yarangi.game.temple.controllers.bots.GatheringOrder;
import yarangi.game.temple.controllers.bots.IBotOrder;
import yarangi.game.temple.model.Resource;
import yarangi.game.temple.model.temple.Serviceable;

public class ServicingBehavior extends SatelliteBehavior
{
	private double TRANSFER_RATE = 10;
	private BotInterface controller;
	public ServicingBehavior(BotInterface controller, Serviceable serviceable)
	{
		super(serviceable.getServicePoint());
		this.controller = controller;
	}
	
	@Override
	public double behave(double time, Bot bot, boolean isVisible)
	{
		super.behave( time, bot, isVisible );
		IBotOrder order = bot.getOrder();
		boolean finished = false;
		if(order instanceof GatheringOrder)
		{
			GatheringOrder go = (GatheringOrder) order;
			
			switch(go.getState())
			{
			case AT_PROVIDER:
				Resource givenResource = go.getProvider().consume(new Resource(go.getRequestedResource().getType(), TRANSFER_RATE*time));
				
				bot.give( givenResource);
				
				if(bot.isFull())
					return 0;
				
				break;
			case AT_REQUESTER:
				Resource takenResource = bot.take(TRANSFER_RATE*time);
			
				if(bot.isEmpty())
				{
					 controller.fulfill( go );
					 return 0;
				}
				
				go.getRequester().supply( takenResource ); 
			}
		}
		
		return -1;
	}

	@Override
	public int getId() { return getStateId(); }

	public static int getStateId() { return ServicingBehavior.class.hashCode(); }

}
