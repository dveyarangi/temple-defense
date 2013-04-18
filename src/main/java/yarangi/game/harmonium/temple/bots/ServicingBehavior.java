package yarangi.game.harmonium.temple.bots;

import yarangi.game.harmonium.ai.economy.IOrder;
import yarangi.game.harmonium.environment.resources.GatheringOrder;
import yarangi.game.harmonium.temple.IServiceable;
import yarangi.numbers.RandomUtil;
import yarangi.physics.Body;

public class ServicingBehavior extends SatelliteBehavior <Bot>
{
	private double TRANSFER_RATE = 5;
	private IBotInterface controller;
	public ServicingBehavior(IBotInterface controller, IServiceable serviceable)
	{
		super(serviceable.getServiceArea(), Bot.ENGINE_POWER);
		this.controller = controller;
	}
	
	@Override
	public double behave(double time, Bot bot)
	{
		Body body = bot.getBody();
		if(body.getMaxSpeed() > Bot.MAX_SPEED/2)
			body.setMaxSpeed( body.getMaxSpeed()-time*1 );
//		bot.getBody().setMaxSpeed(7+RandomUtil.STD( 0, 1 ) );
		super.behave( time, bot);
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
