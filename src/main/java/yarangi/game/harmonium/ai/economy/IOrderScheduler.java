package yarangi.game.harmonium.ai.economy;

import java.util.List;

import yarangi.game.harmonium.environment.resources.Resource;
import yarangi.game.harmonium.temple.IServiceable;
import yarangi.game.harmonium.temple.bots.Bot;
import yarangi.game.harmonium.temple.bots.IBotInterface;

public interface IOrderScheduler extends IBotInterface
{

	public abstract int requestResource(IServiceable requester, IServiceable provider, Resource.Type type, double amount, int priority);

	public abstract void add(Bot bot);

	public abstract List<Bot> getBotsList();

	public abstract double changeTransferRate(double d);

}