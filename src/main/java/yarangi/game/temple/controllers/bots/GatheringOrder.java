package yarangi.game.temple.controllers.bots;

import yarangi.game.temple.model.Resource;
import yarangi.game.temple.model.Resource.Type;
import yarangi.game.temple.model.temple.Serviceable;

public class GatheringOrder implements IBotOrder
{
	private ResourceRequest request;
	
	private Resource requestedResource;
	
	public EOrderState state = EOrderState.BEGIN;
	
	public GatheringOrder (ResourceRequest request, Resource requestedResource)
	{
		
		this.request = request;
		this.requestedResource = requestedResource;
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

	ResourceRequest getRequest()
	{
		 return request;
	}

	public Resource getRequestedResource() { return requestedResource; }
	
	
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

}
