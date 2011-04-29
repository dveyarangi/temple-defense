package yarangi.game.temple;

import java.util.HashMap;
import java.util.Map;

import yarangi.game.temple.model.enemies.ElementalVoid;
import yarangi.game.temple.model.temple.ObserverBehavior;
import yarangi.game.temple.model.temple.ObserverEntity;
import yarangi.game.temple.model.temple.TempleEntity;
import yarangi.game.temple.model.temple.structure.PowerGrid;
import yarangi.graphics.colors.Color;
import yarangi.graphics.quadraturin.IEventManager;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.actions.DefaultActionFactory;
import yarangi.graphics.quadraturin.actions.IAction;
import yarangi.graphics.scene.BackgroundEntity;
import yarangi.math.Angles;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.AABB;

public class Playground extends Scene
{
	
	private TempleEntity temple;
	
	private  Map <String, IAction> actions;
	
//	private BackgroundEntity background;
	
//	private int worldWidth = 1000;
//	private int worldHeight = 1000;
	
	public Playground(IEventManager voices)
	{
		super("Playground", new ArcadeWorldVeil(2000, 2000), new TestUIVeil(1000, 1000),  1000, 1000, 1.);
		
//		BackgroundEntity background = new BackgroundEntity(100, 100, 50);		
//		addEntity(background);
		
		PowerGrid grid = new PowerGrid(this.getWorldVeil());
		
		temple = new TempleEntity(this);
		addEntity(temple);
		
//		addEntity(new BubbleSwarm(getWorldVeil(), temple));

		actions = new HashMap <String, IAction> ();
		
	//		grid.connect(temple.getStructure(), sensor1);
		
		for(int i = 0; i < 6; i ++)
		{
			double angle = i * Angles.PI_div_3 + Angles.PI_div_6;
//			double angle = RandomUtil.getRandomDouble(Angles.PI_2);
			double radius = 400;//RandomUtil.getRandomGaussian(400, 100);
			ObserverEntity sensor2 = new ObserverEntity(
					new AABB(radius*Math.cos(angle), radius*Math.sin(angle), 5, 0),
					//new AABB(RandomUtil.getRandomGaussian(200, 50), RandomUtil.getRandomGaussian(200, 50), 5, 0),
					temple.getController(), 
					256, 
					(i % 2 == 0 ? new Color(0, 0.3f, 1, 1) : new Color(0, 0.3f, 1.f, 1)) 
					);
			sensor2.setBehavior(new ObserverBehavior(this.getWorldVeil().getEntityIndex()));
			addEntity(sensor2);
			grid.connect(temple.getStructure(), sensor2);
			
		}
		
		DefaultActionFactory.fillNavigationActions(actions, getViewPoint());
//		actions.put("cursor-moved", temple.getController());
		actions.put("fire-on", temple.getController().getFireOnAction());
		actions.put("fire-off", temple.getController().getFireOffAction());
			
//		voices.addCursorListener(temple.getController());
		voices.addCursorListener(temple.getController());
	}

	public Map <String, IAction> getActionsMap()
	{
		return actions;
	}
	
	public void animate(double time)
	{
		super.animate(time);
		
		if(RandomUtil.oneOf(2))
		{
			double angle = RandomUtil.getRandomDouble(Angles.PI_2);
			double radius = RandomUtil.getRandomGaussian(800, 0);
			addEntity(new ElementalVoid(radius*Math.cos(angle), radius*Math.sin(angle), 0, RandomUtil.getRandomInt(4)+4, temple));
		}
	}
	
	public String toString() { return "playground scene"; }
}
