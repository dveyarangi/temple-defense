package yarangi.game.harmonium.temple.bots;

import yarangi.game.harmonium.ai.economy.IOrder;

public interface IBotInterface
{


	public abstract IOrder getOrder(Bot bot);

	public abstract void fulfill(IOrder order);

	public abstract void reject(IOrder order);
}