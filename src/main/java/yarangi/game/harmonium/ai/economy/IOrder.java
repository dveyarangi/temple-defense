package yarangi.game.harmonium.ai.economy;

import yarangi.game.harmonium.temple.IServiceable;

public interface IOrder
{
	/**
	 * Requester of service that relies on this order
	 * @return
	 */
	public IServiceable getRequester();
	
	/**
	 * Provider of service that relies on this order
	 * @return
	 */
	public IServiceable getProvider();

	public EOrderState getState();

	public void nextState();
	
	public boolean isClosed(); 
}
