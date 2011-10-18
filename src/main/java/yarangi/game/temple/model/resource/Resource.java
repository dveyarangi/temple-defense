package yarangi.game.temple.model.resource;

public class Resource
{
	public static enum Type { MATTER, ENERGY }
	
	public Type type;
	
	public double amount;
	
	Resource(Type type, double amount)
	{
		if(amount < 0)
			throw new IllegalArgumentException("Cannot create negative resource.");
		this.type = type;
		this.amount = amount;
	}
	
	public Type getType() { return type; }
	public double getAmount() { return amount; }

	/**
	 * If nothing was consumed, null is returned
	 * 
	 * @param amount amount to consume
	 * @param consumeOnFailure either we should still consume when to enough resource available
	 * @return
	 */
	public Resource consume(double consumedAmount, boolean consumeOnFailure)
	{
		double consumed;
		if(this.amount >= consumedAmount)
			consumed = consumedAmount;
		else if(consumeOnFailure)
			consumed = this.amount;
		else
			consumed = 0;
		
		this.amount -= consumed;
		if(amount < 0) 
			amount = 0;
		
		return consumed > 0 ? new Resource(getType(), consumed) : null;
	}

	/**
	 * Creation of resources is by {@link GatheringOrder} only.
	 * @param suppliedAmount
	 */
	void supply(double suppliedAmount)
	{
		if(amount < 0)
			throw new IllegalArgumentException("Cannot supply negative resource.");
		this.amount += suppliedAmount;
	}
}
