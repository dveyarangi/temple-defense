package yarangi.game.temple.model.temple.bots;

import yarangi.game.temple.controllers.TempleController;
import yarangi.game.temple.controllers.orders.IOrder;
import yarangi.game.temple.model.resource.Port;
import yarangi.game.temple.model.resource.Resource;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.graphics.quadraturin.objects.behaviors.FSMBehavior;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorCondition;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.graphics.quadraturin.simulations.Body;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.PointArea;

public class BotFactory
{
	public static final double BOT_CAPACITY = 240;
	
	public static Bot createBot(final TempleController controller)
	{
		Port port = Port.createEmptyPort();
		port.setTransferRate( 1 );
		port.setCapacity( Resource.Type.ENERGY, 0, BOT_CAPACITY );
		port.setCapacity( Resource.Type.MATTER, 0, BOT_CAPACITY );
		Bot bot = new Bot (port);
		bot.setArea( new PointArea(controller.getTemple().getArea().getRefPoint()) );
		bot.setLook( new BotLook(20) );
		bot.setBehavior( createBotBehavoir( controller ) );
		bot.setBody( new Body(1, 7+RandomUtil.getRandomGaussian( 0, 1 )) );
		return bot;
	}
	
	public static Behavior <Bot> createBotBehavoir(final TempleController controller)
	{
		final IBehaviorState <Bot> querying = new QueryingBehavior (controller.getBotInterface());
		FSMBehavior <Bot> fsm = new FSMBehavior <Bot>(querying);
		
		fsm.link( querying.getId(), new IBehaviorCondition<Bot> (){
 			@Override public IBehaviorState<Bot> nextState(Bot bot) {

				if(bot.getOrder() == null) // querying returned no order:
					return new SatelliteBehavior(controller.getTemple().getArea());
				
				IOrder order = bot.getOrder();
				switch(order.getState())
				{
				case TO_PROVIDER: return new ChasingBehavior (bot.getOrder().getProvider());
				case AT_PROVIDER: return new ServicingBehavior (controller.getBotInterface(), bot.getOrder().getProvider());
				case TO_REQUESTER: return new ChasingBehavior (bot.getOrder().getRequester());
				case AT_REQUESTER: return new ServicingBehavior (controller.getBotInterface(), bot.getOrder().getRequester());
				default: return new SatelliteBehavior(controller.getTemple().getArea());
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
