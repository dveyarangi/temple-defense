package yarangi.game.temple.model.temple.bots;

import yarangi.game.temple.controllers.bots.IBotOrder;
import yarangi.game.temple.model.Resource;
import yarangi.game.temple.model.temple.Serviceable;
import yarangi.game.temple.model.temple.StructureInterface;
import yarangi.graphics.quadraturin.objects.Entity;

public class Bot extends Entity
{

	private static final long serialVersionUID = -19945327419649387L;
	
	private Serviceable currTarget;
	private double enginePower = 0.2;
	
	private IBotOrder order;
	
	private Resource carriedResource;
	
	public Bot()
	{
		super();
	}
	
	public void setTarget(Serviceable target) 
	{ 
		this.currTarget = target; 
	}
	public double getEnginePower() { return enginePower; }

	public double getCapacity(Resource.Type type)
	{
		return 10;
	}

	public void setOrder(IBotOrder order)
	{
		this.order = order;
	}

	public IBotOrder getOrder() { return order; }

	public void give(Resource resource)
	{
		this.carriedResource = resource;
	}
	
	public Resource take(double amount) {
		Resource taken = carriedResource.consume(amount , false );
		if(carriedResource.getAmount() == 0)
			carriedResource = null;
		return taken;
	}

	public boolean isFull()
	{
		return carriedResource.getAmount() >= getCapacity( carriedResource.getType() );
	}
	public boolean isEmpty()
	{
		return carriedResource.getAmount() <= 0;
	}
}
