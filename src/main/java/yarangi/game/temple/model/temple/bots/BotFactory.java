package yarangi.game.temple.model.temple.bots;

import yarangi.game.temple.controllers.TempleController;
import yarangi.game.temple.controllers.bots.IBotOrder;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.graphics.quadraturin.objects.behaviors.FSMBehavior;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorCondition;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.graphics.quadraturin.simulations.Body;
import yarangi.spatial.Point;

public class BotFactory
{
	public static Bot createBot(final TempleController controller)
	{
		Bot bot = new Bot ();
		bot.setArea( new Point(controller.getTemple().getArea().getRefPoint()) );
		bot.setLook( new BotLook(5) );
		bot.setBehavior( createBotBehavoir( controller ) );
		bot.setBody( new Body() );
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
				
				IBotOrder order = bot.getOrder();
				switch(order.getState())
				{
				case TO_PROVIDER: return new ChasingBehavior (bot.getOrder().getRequester());
				case AT_PROVIDER: return new ServicingBehavior (controller.getBotInterface(), bot.getOrder().getRequester());
				case TO_REQUESTER: return new ChasingBehavior (bot.getOrder().getProvider());
				case AT_REQUESTER: return new ServicingBehavior (controller.getBotInterface(), bot.getOrder().getProvider());
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
