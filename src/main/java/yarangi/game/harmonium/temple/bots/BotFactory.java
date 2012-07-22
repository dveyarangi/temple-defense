package yarangi.game.harmonium.temple.bots;

import yarangi.game.harmonium.ai.economy.IOrder;
import yarangi.game.harmonium.environment.resources.Port;
import yarangi.game.harmonium.environment.resources.Resource;
import yarangi.game.harmonium.temple.Serviceable;
import yarangi.graphics.quadraturin.objects.IBehavior;
import yarangi.graphics.quadraturin.objects.behaviors.FSMBehavior;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorCondition;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.physics.Body;
import yarangi.spatial.PointArea;

public class BotFactory
{
	public static final double BOT_CAPACITY = 50;
	
	public static Bot createBot(final Serviceable host, IBotInterface botInterface)
	{
		Port port = Port.createEmptyPort();
		port.setTransferRate( 1 );
		port.setCapacity( Resource.Type.ENERGY, 0, BOT_CAPACITY );
		port.setCapacity( Resource.Type.MATTER, 0, BOT_CAPACITY );
		Bot bot = new Bot (port);
		bot.setArea( new PointArea(0, 0) );
		bot.setLook( new BotLook(100) );
		bot.setBehavior( createBotBehavoir( host, botInterface ) );
		bot.setBody( new Body(1, 10) );
		return bot;
	}
	
	public static IBehavior <Bot> createBotBehavoir(final Serviceable host, final IBotInterface botInterface)
	{
		final IBehaviorState <Bot> querying = new QueryingBehavior (botInterface);
		FSMBehavior <Bot> fsm = new FSMBehavior <Bot>(querying);
		
		fsm.link( querying.getId(), new IBehaviorCondition<Bot> (){
 			@Override public IBehaviorState<Bot> nextState(Bot bot) {

				if(bot.getOrder() == null) // querying returned no order:
					return new SatelliteBehavior<Bot>(host.getServiceArea(), Bot.ENGINE_POWER);
				
				IOrder order = bot.getOrder();
				switch(order.getState())
				{
				case TO_PROVIDER: return new ChasingBehavior (bot.getOrder().getProvider().getServiceArea(),  Bot.ENGINE_POWER);
				case AT_PROVIDER: return new ServicingBehavior (botInterface, bot.getOrder().getProvider());
				case TO_REQUESTER: return new ChasingBehavior (bot.getOrder().getRequester().getServiceArea(),  Bot.ENGINE_POWER);
				case AT_REQUESTER: return new ServicingBehavior (botInterface, bot.getOrder().getRequester());
				default: return new SatelliteBehavior<Bot>(host.getServiceArea(), Bot.ENGINE_POWER);
				}
			}});
		
		fsm.link( ChasingBehavior.getStateId(), new IBehaviorCondition<Bot> (){
 			@Override public IBehaviorState<Bot> nextState(Bot bot) { return querying; }});
		fsm.link( ServicingBehavior.getStateId(), new IBehaviorCondition<Bot> (){
 			@Override public IBehaviorState<Bot> nextState(Bot bot) { return querying; }});
		fsm.link( SatelliteBehavior.getStateId(), new IBehaviorCondition<Bot> (){
			@Override public IBehaviorState<Bot> nextState(Bot bot) { return querying; }});
		
		return fsm;
	}
}
