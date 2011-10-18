package yarangi.game.temple.model.temple.bots;

import yarangi.game.temple.controllers.orders.IOrder;
import yarangi.game.temple.model.resource.Port;
import yarangi.game.temple.model.temple.Serviceable;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.spatial.Area;

public class Bot extends Entity implements Serviceable
{

	private static final long serialVersionUID = -19945327419649387L;
	
	private Serviceable currTarget;
	private double enginePower = 3;
	
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
	public double getEnginePower() { return enginePower; }

	public void setOrder(IOrder order)
	{
		this.order = order;
	}

	public IOrder getOrder() { return order; }


	@Override
	public Area getServicePoint()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	public Port getPort() { return port; }
}
