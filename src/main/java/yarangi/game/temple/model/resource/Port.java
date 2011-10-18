package yarangi.game.temple.model.resource;

import java.util.HashMap;
import java.util.Map;

public class Port
{
	/**
	 * @deprecated Test only, not really infinite
	 * @return
	 */
	public static Port createEndlessPort()
	{
		Port port = new Port();
		for(Resource.Type type : Resource.Type.values())
		{
			port.capacity.put( type, Double.MAX_VALUE );
			port.stock.put(type, new Resource(type, Double.MAX_VALUE));
		}
		
		return port;
	}
	
	public static Port createEmptyPort()
	{
		Port port = new Port();
		for(Resource.Type type : Resource.Type.values())
		{
			port.capacity.put( type, 0d );
			port.stock.put(type, new Resource(type, 0));
		}
		
		return port;
	}
	
	private Map <Resource.Type, Double> capacity = new HashMap <Resource.Type, Double> ();
	private Map <Resource.Type, Resource> stock = new HashMap <Resource.Type, Resource> ();
	
	private double transferRate = 1;
	
	public void setTransferRate(double rate) { this.transferRate = rate; }
	
	void put(Resource resource) { stock.put( resource.type, resource ); }
	public Resource get(Resource.Type type) { return stock.get( type ); }
	
	public void setCapacity(Resource.Type type, double amount, double maxAmount) { 
		capacity.put( type, maxAmount );
		stock.put( type, new Resource(type, amount) );
	}
	public double getCapacity(Resource.Type type) { return capacity.get(type); }

	public double use(Resource.Type type, double amount)
	{
		Resource resource = get(type);
		if(resource == null)
			return 0;
		
		Resource consumedResource =  resource.consume( amount, false );
		return consumedResource == null ? 0 : consumedResource.getAmount(); 
	}
	
	public double getTransferRate() { return transferRate; }
	
}
