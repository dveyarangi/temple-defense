package yarangi.game.harmonium.ai.economy;

import yarangi.game.harmonium.temple.Serviceable;

public interface IOrder
{
	/**
	 * Requester of service that relies on this order
	 * @return
	 */
	public Serviceable getRequester();
	
	/**
	 * Provider of service that relies on this order
	 * @return
	 */
	public Serviceable getProvider();

	public EOrderState getState();

	public void nextState();
	
	public boolean isClosed(); 
}
