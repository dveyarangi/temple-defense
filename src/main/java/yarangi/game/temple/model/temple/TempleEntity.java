package yarangi.game.temple.model.temple;

import yarangi.game.temple.Playground;
import yarangi.game.temple.controllers.ControlEntity;
import yarangi.graphics.quadraturin.objects.DummyEntity;
import yarangi.graphics.quadraturin.simulations.IPhysicalObject;
import yarangi.math.AABB;
import yarangi.math.Vector2D;


public class TempleEntity extends DummyEntity implements IPhysicalObject
{

	private static final long serialVersionUID = 6893825029204201873L;

	private TempleStructure structure;
	
	private ControlEntity controller;
	
	private Playground playground;
	
	private Vector2D velocity = new Vector2D(0,0);

	public TempleEntity(Playground playground)
	{
		super(new AABB(0,0,0,0));
		controller = new ControlEntity(playground, this);
		addChild(controller);
		
		structure = new TempleStructure(this, controller);
		addChild(structure);
		
		this.playground = playground;
/*		for(int idx = 0; idx < 100; idx ++)
		{
			Bot bot = new Bot(structure.getCenter(), new Vector2D(0,0), 10);
			playground.addEntity(bot, true);
			
		}*/
	}
	

	public TempleStructure getStructure() { return structure; }
	
	public ControlEntity getController() { return controller; }
	

	public Vector2D getVelocity() { return velocity; }


	public void setImpactWith(IPhysicalObject e) {
//		System.out.println("temple hit: " + e);
	}


	public Vector2D getForce() {
		// TODO Auto-generated method stub
		return new Vector2D(0,0);
	}


	public double getMass() {
		// TODO Auto-generated method stub
		return 1000;
	}
}
