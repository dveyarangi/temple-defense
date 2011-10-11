package yarangi.game.temple.controllers;

import java.util.Map;

import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.actions.ActionController;
import yarangi.graphics.quadraturin.actions.IAction;
import yarangi.graphics.quadraturin.events.ICursorEvent;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.spatial.ISpatialFilter;

public class TerrainPainter extends ActionController
{

public TerrainPainter(Scene scene)
	{
		super( scene );
		// TODO Auto-generated constructor stub
	}

/*	@Override
	public Look<Cursor> getCursorLook()
	{
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public void onCursorMotion(ICursorEvent event)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, IAction> getActions()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISpatialFilter<IEntity> getPickingFilter()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}
