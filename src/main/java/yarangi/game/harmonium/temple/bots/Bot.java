package yarangi.game.harmonium.temple.bots;

import yar.quadraturin.objects.Entity;
import yarangi.game.harmonium.ai.economy.IOrder;
import yarangi.game.harmonium.environment.resources.Port;
import yarangi.game.harmonium.temple.IServiceable;
import yarangi.spatial.Area;

public class Bot extends Entity implements IServiceable
{

	private static final long serialVersionUID = -19945327419649387L;

	public static final int MAX_SPEED = 5;
	
	private IServiceable currTarget;
	public static double ENGINE_POWER = 3;
	
	private IOrder order;

	private Port port;
	
	public Bot(Port port)
	{
		super();

		this.port = port;
	}
	
	public void setTarget(IServiceable target) 
	{ 
		this.currTarget = target; 
	}
	public double getEnginePower() { return ENGINE_POWER; }

	public void setOrder(IOrder order)
	{
		this.order = order;
	}

	public IOrder getOrder() { return order; }


	@Override
	public Area getServiceArea()
	{
		return getArea();
	}
	
	@Override
	public Port getPort() { return port; }
}
