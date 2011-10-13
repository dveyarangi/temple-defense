package yarangi.game.temple.model.temple.bots;

import yarangi.game.temple.controllers.bots.BotInterface;
import yarangi.game.temple.controllers.bots.EOrderState;
import yarangi.game.temple.controllers.bots.IBotOrder;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;

/**
 * Requests for orders and transits order states.
 * @author dveyarangi
 *
 */
public class QueryingBehavior implements IBehaviorState <Bot>
{
	private BotInterface controller;
	
	public QueryingBehavior (BotInterface controller)
	{
		this.controller = controller;
	}

	@Override
	public double behave(double time, Bot bot, boolean isVisible)
	{
		IBotOrder order = bot.getOrder();
		
		if(order == null)
		{
			order = controller.getOrder(bot);
			bot.setOrder(order);
		}
		
		if(order == null)
			return time;
		
		order.nextState();
		
		if(order.getState() == EOrderState.FINISH)
		{
			controller.fulfill( order );
			bot.setOrder( null );
		}
		
		return time; // takes to time
	}
	public int getId() { return this.getClass().hashCode(); }

}
