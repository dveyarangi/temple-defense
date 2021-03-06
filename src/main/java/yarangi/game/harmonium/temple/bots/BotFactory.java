package yarangi.game.harmonium.temple.bots;

import yar.quadraturin.objects.IBehavior;
import yar.quadraturin.objects.behaviors.FSMBehavior;
import yar.quadraturin.objects.behaviors.IBehaviorCondition;
import yar.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.game.harmonium.ai.economy.IOrder;
import yarangi.game.harmonium.environment.resources.Port;
import yarangi.game.harmonium.environment.resources.Resource;
import yarangi.game.harmonium.temple.IServiceable;
import yarangi.physics.Body;
import yarangi.spatial.AABB;
import yarangi.spatial.PointArea;

public class BotFactory
{
	public static final double BOT_CAPACITY = 90;
	
	public static Bot createBot(final IServiceable host, IBotInterface botInterface)
	{
		Port port = Port.createEmptyPort();
		port.setTransferRate( 0.5 );
		port.setCapacity( Resource.Type.ENERGY, 0, BOT_CAPACITY );
		port.setCapacity( Resource.Type.MATTER, 0, BOT_CAPACITY );
		Bot bot = new Bot (port);
		bot.setArea( AABB.createFromCenter(0, 0, 1, 1, 0) );
		bot.setLook( new BotLook(20) );
		bot.setBehavior( createBotBehavoir( host, botInterface ) );
		bot.setBody( new Body(1, Bot.MAX_SPEED) );
		return bot;
	}
	
	public static IBehavior <Bot> createBotBehavoir(final IServiceable host, final IBotInterface botInterface)
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
