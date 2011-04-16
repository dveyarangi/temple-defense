package yarangi.game.temple;

import java.util.HashMap;
import java.util.Map;

import yarangi.game.temple.model.enemies.ElementalVoid;
import yarangi.game.temple.model.temple.ObserverEntity;
import yarangi.game.temple.model.temple.TempleEntity;
import yarangi.graphics.lights.CircleLightEntity;
import yarangi.graphics.quadraturin.EventManager;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.actions.DefaultActionFactory;
import yarangi.graphics.quadraturin.actions.IAction;
import yarangi.graphics.quadraturin.objects.SensorBehavior;
import yarangi.graphics.utils.colors.Color;
import yarangi.graphics.utils.scene.BackgroundEntity;
import yarangi.math.Angles;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.AABB;

public class Playground extends Scene
{
	
	private TempleEntity temple;
	
	private  Map <String, IAction> actions;
	
//	private BackgroundEntity background;
	
	private int worldWidth = 1000;
	private int worldHeight = 1000;
	
	public Playground(EventManager voices)
	{
		super("Playground", new ArcadeWorldVeil(2000, 2000), new TestUIVeil(1000, 1000),  1000, 1000, 1.);
		
		BackgroundEntity background = new BackgroundEntity(100, 100, 50);		
//		addEntity(background);
		
		temple = new TempleEntity(this);
		addEntity(temple);
		
//		addEntity(new BubbleSwarm(getWorldVeil(), temple));

		actions = new HashMap <String, IAction> ();
		
		ObserverEntity sensor1 = new ObserverEntity(new AABB(0, 0, 10, 0), 512, new Color(1.0f,0.0f,0.0f,1f));
		sensor1.setBehavior(new SensorBehavior<CircleLightEntity>(this.getWorldVeil().getEntityIndex()));
		addEntity(sensor1);
		
		for(int i = 0; i < 6; i ++)
		{
			double angle = i * Angles.PI_div_3;
			double radius = 256;//RandomUtil.getRandomGaussian(400, 100);
			ObserverEntity sensor2 = new ObserverEntity(
					new AABB(radius*Math.cos(angle), radius*Math.sin(angle), 5, 0), 
					256, 
					(i % 2 == 0 ? new Color(0, 0.3f, 1, 1) : new Color(0, 0.3f, 1.f, 1)) 
					);
			sensor2.setBehavior(new SensorBehavior<CircleLightEntity>(this.getWorldVeil().getEntityIndex()));
			addEntity(sensor2);
		}
		
		for(int i = 0; i < 300; i ++)
		{
			double angle = RandomUtil.getRandomDouble(Angles.PI_2);
			double radius = RandomUtil.getRandomGaussian(600, 250);
			addEntity(new ElementalVoid(radius*Math.cos(angle), radius*Math.sin(angle), 0, RandomUtil.getRandomInt(10)+5, temple));
			
		}
		voices.addCursorListener(sensor1);
		
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

}
