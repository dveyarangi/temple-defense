package yarangi.game.temple.model.temple;

import yarangi.game.temple.Playground;
import yarangi.game.temple.controllers.TempleController;
import yarangi.game.temple.model.Resource;
import yarangi.game.temple.model.weapons.Projectile;
import yarangi.graphics.colors.Color;
import yarangi.graphics.quadraturin.objects.Dummy;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.Sensor;
import yarangi.graphics.quadraturin.simulations.Body;
import yarangi.math.Vector2D;
import yarangi.spatial.AABB;
import yarangi.spatial.Area;
import yarangi.spatial.ISpatialFilter;
import yarangi.spatial.ISpatialObject;


public class TempleEntity extends Dummy implements Serviceable 
{

	private static final long serialVersionUID = 6893825029204201873L;

	private TempleStructure structure;
	
	private TempleController controller;
	
	
	ObserverEntity highlight;
	
	HighLightFilter filter = new HighLightFilter();

	public TempleEntity(Playground playground, TempleController ctrl)
	{
		super();
		
		highlight = new ObserverEntity(ctrl);
		highlight.setBehavior(new ObserverBehavior(0,0,null,0));
		
		highlight.setArea(new AABB(0, 0, 30, 0));
		highlight.setSensor(new Sensor(512, 3, filter, true));
		highlight.setLook(new ObserverLook(new Color(0.6f, 0.5f, 1.0f, 1)));
		highlight.setBody(new Body());
		playground.addEntity(highlight);
		
//				structure = new TempleStructure(this, controller);
//		addChild(structure);
		
//		this.getAABB().r = structure.getShieldRadius();
//		this.playground = playground;
/*		for(int idx = 0; idx < 100; idx ++)
		{
			Bot bot = new Bot(structure.getCenter(), new Vector2D(0,0), 10);
			playground.addEntity(bot, true);
			
		}*/
	}
	

	public TempleStructure getStructure() { return structure; }
	
	public TempleController getController() { return controller; }


	public BattleInterface getCommandPlatform() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public class HighLightFilter implements ISpatialFilter <IEntity>
	{
		private ISpatialObject highlighted;
		public void setHighlighted(ISpatialObject object)
		{
			this.highlighted = object;
		}
		@Override
		public boolean accept(IEntity entity) {
			return !(entity instanceof Projectile) && entity != highlighted;
		}
		
	}

	@Override
	public Area getServicePoint()
	{
		return getArea();
	}


	@Override
	public void supply(Resource resource)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public Resource consume(Resource resource)
	{
		// TODO: infinite for now:
		return new Resource(resource.getType(), resource.getAmount());
	}

}
