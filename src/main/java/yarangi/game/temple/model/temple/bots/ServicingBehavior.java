package yarangi.game.temple.model.temple.bots;

import yarangi.game.temple.controllers.bots.BotInterface;
import yarangi.game.temple.controllers.orders.IOrder;
import yarangi.game.temple.model.resource.GatheringOrder;
import yarangi.game.temple.model.resource.Resource;
import yarangi.game.temple.model.temple.Serviceable;
import yarangi.numbers.RandomUtil;

public class ServicingBehavior extends SatelliteBehavior
{
	private double TRANSFER_RATE = 5;
	private BotInterface controller;
	public ServicingBehavior(BotInterface controller, Serviceable serviceable)
	{
		super(serviceable.getServicePoint());
		this.controller = controller;
	}
	
	@Override
	public double behave(double time, Bot bot, boolean isVisible)
	{
		;
//		bot.getBody().setMaxSpeed(7+RandomUtil.getRandomGaussian( 0, 1 ) );
		super.behave( time, bot, isVisible );
		IOrder order = bot.getOrder();
		boolean finished = false;
		if(order instanceof GatheringOrder)
		{
			GatheringOrder go = (GatheringOrder) order;
			
			switch(go.getState())
			{
			case AT_PROVIDER:
				if(go.sealExport(bot, time))
				{
					return 0;
				}
				break;
			case AT_REQUESTER:
				if(go.sealImport(bot, time)) {
					 return 0;
				}
				
			}
		}
		
		return -1;
	}

	@Override
	public int getId() { return getStateId(); }

	public static int getStateId() { return ServicingBehavior.class.hashCode(); }

}
