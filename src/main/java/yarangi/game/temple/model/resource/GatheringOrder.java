package yarangi.game.temple.model.resource;

import yarangi.game.temple.controllers.orders.EOrderState;
import yarangi.game.temple.controllers.orders.IOrder;
import yarangi.game.temple.model.resource.Resource.Type;
import yarangi.game.temple.model.temple.Serviceable;

public class GatheringOrder implements IOrder
{
	private ResourceRequest request;
	
	private double orderedAmount;
	private Resource.Type orderedType;
	
	public EOrderState state = EOrderState.BEGIN;
	
	public double exportedAmount;
	public double importedAmount;
	
	public GatheringOrder(ResourceRequest request, Type type, double amount)
	{
		this.request = request;
		orderedAmount = amount;
		orderedType = type;
	}

	@Override
	public Serviceable getRequester()
	{
		return request.getRequester();
	}

	@Override
	public Serviceable getProvider()
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

	public boolean sealExport(Serviceable importer, double time)
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

		importedAmount += transferedAmount; 
		if(importedAmount+0.0000001 < orderedAmount)
			return false;

		return true;
	}

	public double getOrderedAmount()
	{
		return orderedAmount;
	}

	public boolean sealImport(Serviceable importer, double time)
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
		
		exportedAmount += transferedAmount; 
		if(exportedAmount+0.0000001 < orderedAmount)
			return false;

		return true;
	}
	
	public boolean isClosed() { return state == EOrderState.FINISH; }

}
