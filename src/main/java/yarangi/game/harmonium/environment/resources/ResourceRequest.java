package yarangi.game.harmonium.environment.resources;

import yarangi.game.harmonium.temple.Serviceable;

public class ResourceRequest
{
	private Serviceable requester;
	private Serviceable provider;
	
	private Resource.Type type;
	
	private double requiredAmount;
	private double unapprovedAmount;
	
	public ResourceRequest(Serviceable requester, Serviceable provider, Resource.Type type, double amount)
	{
		this.requester = requester;
		this.provider = provider;
		this.type = type;
		this.requiredAmount = unapprovedAmount = amount;
		unapprovedAmount = requiredAmount;
	}
	
	/**
	 * Marks that specified amount of resource is going to be delivered.
	 * @param a
	 */
	public void approve(double amount)
	{
		this.unapprovedAmount -= amount;
		if(unapprovedAmount < 0)
			unapprovedAmount = 0;
	}
	
	public void supply(double amount)
	{
		this.requiredAmount -= amount;
		if(requiredAmount <= 0)
			requiredAmount = 0;
	}
	
	public boolean isApproved()
	{
		return unapprovedAmount <= 0;
	}
	
	public boolean isFulfilled()
	{
		return requiredAmount == 0;
	}

	public Resource.Type getType()
	{
		return type;
	}

	public Serviceable getRequester()
	{
		return requester;
	}
	public Serviceable getProvider()
	{
		return provider;
	}
	
}
