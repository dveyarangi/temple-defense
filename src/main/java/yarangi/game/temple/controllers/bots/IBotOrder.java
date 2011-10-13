package yarangi.game.temple.controllers.bots;

import yarangi.game.temple.model.temple.Serviceable;

public interface IBotOrder
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
	
}
