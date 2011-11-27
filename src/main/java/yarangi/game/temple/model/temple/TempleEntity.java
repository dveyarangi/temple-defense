package yarangi.game.temple.model.temple;

import yarangi.game.temple.Playground;
import yarangi.game.temple.controllers.TempleController;
import yarangi.game.temple.model.resource.Port;
import yarangi.game.temple.model.weapons.Projectile;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.spatial.Area;
import yarangi.spatial.ISpatialFilter;
import yarangi.spatial.ISpatialObject;


public class TempleEntity extends ObserverEntity implements Serviceable 
{

	private static final long serialVersionUID = 6893825029204201873L;

	private TempleStructure structure;
	
	private TempleController controller;
	
	
	ObserverEntity highlight;
	
	Port port;
	
	double transferRate = 5;
	
	double health;

	public TempleEntity(Playground playground, TempleController ctrl)
	{
		super(ctrl);
		
		// TODO: for now:
		port = Port.createEndlessPort();
		port.setTransferRate( transferRate );
		this.controller = ctrl;
	}
	

	public TempleStructure getStructure() { return structure; }
	
	public TempleController getController() { return controller; }

	@Override
	public Area getServicePoint()
	{
		return getArea();
	}

	@Override
	public Port getPort()
	{
		return port;
	}


	public void setHealth(double health)
	{
		this.health = health;
	}
	
	public double getHealth() { return health; }
}
