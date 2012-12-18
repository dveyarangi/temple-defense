package yarangi.game.harmonium.environment.resources;

import yarangi.game.harmonium.ai.economy.EOrderState;
import yarangi.game.harmonium.ai.economy.IOrder;
import yarangi.game.harmonium.environment.resources.Resource.Type;
import yarangi.game.harmonium.temple.IServiceable;

/**
 * This class represents a resource transaction order.
 * 
 * @author dveyarangi
 */
public class GatheringOrder implements IOrder
{
	/**
	 * Order origin request
	 */
	private ResourceRequest request;
	
	/**
	 * Amount of resource to be moved according to this order.
	 */
	private double orderedAmount;
	
	/**
	 * Type of resource to be moved according to this order.
	 */
	private Resource.Type orderedType;
	
	/**
	 * Order execution state
	 * TODO: consider making generic
	 */
	public EOrderState state = EOrderState.BEGIN;
	
	/**
	 * Amount of resource exported from provider by this order at current order state.
	 * Increases during export procedure
	 */
	public double exportedAmount;
	
	/**
	 * Amount of resource imported to requester by this order at current order state.
	 * Increases during import procedure
	 */
	public double importedAmount;
	
	public GatheringOrder(ResourceRequest request, Type type, double amount)
	{
		this.request = request;
		orderedAmount = amount;
		orderedType = type;
	}

	@Override
	public IServiceable getRequester()
	{
		return request.getRequester();
	}

	@Override
	public IServiceable getProvider()
	{
		return request.getProvider();
	}

	public ResourceRequest getRequest()
	{
		 return request;
	}

//	public Resource getRequestedResource() { return requestedResource; }
	
	
	public EOrderState getState() { return state; }

	@Override
	public void nextState()
	{
		switch(state)
		{
		case BEGIN:        state =  EOrderState.TO_PROVIDER;  break;
		case TO_PROVIDER:  state =  EOrderState.AT_PROVIDER;  break;
		case AT_PROVIDER:  state =  EOrderState.TO_REQUESTER; break;
		case TO_REQUESTER: state =  EOrderState.AT_REQUESTER; break;
		case AT_REQUESTER: state =  EOrderState.FINISH;       break;
		}
	}

	public boolean sealExport(IServiceable importer, double time)
	{
		Port exPort = getProvider().getPort();
		Port imPort = importer.getPort();
		
		double importerCapacity = imPort.getCapacity( orderedType );
		Resource importStock = imPort.get( orderedType );
		double importerSpace = importerCapacity - importStock.getAmount();
		
		Resource exportStock = exPort.get( orderedType );
		
		double transferedAmount = Math.min( // keep in mind that transfere rate is from importer
										Math.min( time*imPort.getTransferRate(), orderedAmount),
										Math.min( importerSpace, exportStock.getAmount() ));
		
		Resource exportedResource = exportStock.consume( transferedAmount, true );
		if(exportedResource == null)
			return false;
		
		importStock.supply( transferedAmount );

		exportedAmount += transferedAmount; 
		if(exportedAmount+0.0000001 < orderedAmount)
			return false;

		return true;
	}

	public double getOrderedAmount()
	{
		return orderedAmount;
	}

	public boolean sealImport(IServiceable importer, double time)
	{
		Port exPort = importer.getPort();
		Port imPort = getRequester().getPort();
		
		double importerCapacity = imPort.getCapacity( orderedType );
		Resource importStock = imPort.get( orderedType );
		double importerSpace = importerCapacity - importStock.getAmount();
		
		Resource exportStock = exPort.get( orderedType );
		double transferedAmount = Math.min( // keep in mind that transfer rate is from exporter
										Math.min( time*exPort.getTransferRate(), orderedAmount),
										Math.min( importerSpace, exportStock.getAmount() ));

		
		Resource exportedResource = exportStock.consume( transferedAmount, true );
		if(exportedResource == null)
			return false;
		
		importStock.supply( transferedAmount );
		
		importedAmount += transferedAmount; 
		if(importedAmount+0.0000001 < orderedAmount)
			return false;

		return true;
	}
	
	public boolean isClosed() { return state == EOrderState.FINISH; }

}
