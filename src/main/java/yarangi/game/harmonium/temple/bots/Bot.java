package yarangi.game.harmonium.temple.bots;

import yarangi.game.harmonium.ai.economy.IOrder;
import yarangi.game.harmonium.environment.resources.Port;
import yarangi.game.harmonium.temple.Serviceable;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.spatial.Area;

public class Bot extends Entity implements Serviceable
{

	private static final long serialVersionUID = -19945327419649387L;
	
	private Serviceable currTarget;
	public static double ENGINE_POWER = 10;
	
	private IOrder order;

	private Port port;
	
	public Bot(Port port)
	{
		super();

		this.port = port;
	}
	
	public void setTarget(Serviceable target) 
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
	
	public Port getPort() { return port; }
}
