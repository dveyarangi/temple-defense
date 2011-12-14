package yarangi.game.harmonium.temple.bots;

import yarangi.game.harmonium.ai.economy.EOrderState;
import yarangi.game.harmonium.ai.economy.IOrder;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;

/**
 * Requests for orders and transits order states.
 * @author dveyarangi
 *
 */
public class QueryingBehavior implements IBehaviorState <Bot>
{
	private IBotInterface controller;
	
	public QueryingBehavior (IBotInterface controller)
	{
		this.controller = controller;
	}

	@Override
	public double behave(double time, Bot bot)
	{
		IOrder order = bot.getOrder();
		
		if(order == null)
		{
			order = controller.getOrder(bot);
			bot.setOrder(order);
		}
		
		if(order == null)
			return time;
		
		if(order.getState() == EOrderState.AT_REQUESTER)
		{
			controller.fulfill( order );
			bot.setOrder( null );
		}
		
		order.nextState();
		
		
		return time; // takes to time
	}
	public int getId() { return this.getClass().hashCode(); }

}
