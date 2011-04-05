package yarangi.game.temple;

import java.util.HashMap;
import java.util.Map;

import yarangi.game.temple.model.enemies.bubbles.BubbleSwarm;
import yarangi.game.temple.model.temple.TempleEntity;
import yarangi.graphics.quadraturin.EventManager;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.actions.Action;
import yarangi.graphics.quadraturin.actions.DefaultActionFactory;

public class Playground extends Scene
{
	
	private TempleEntity temple;
	
//	private BackgroundEntity background;
	
	private int worldWidth = 1000;
	private int worldHeight = 1000;
	
	public Playground(EventManager voices)
	{
		super("Playground", new ArcadeWorldVeil(1000, 1000), new TestUIVeil(1000, 1000),  1000, 1000, 1.);
		
		temple = new TempleEntity(this);
		addEntity(temple);
		
		addEntity(new BubbleSwarm(getWorldVeil(), temple));

		voices.addCursorListener(temple.getController());
//		background = new BackgroundEntity(100, 100, 10);		addEntity(background);
	}

	public Map <String, Action> getActionsMap()
	{
		Map <String, Action> actions = new HashMap <String, Action> ();
		
		DefaultActionFactory.fillNavigationActions(actions, getViewPoint());
//		actions.put("cursor-moved", temple.getController());
		actions.put("fire-on", temple.getController().getFireOnAction());
		actions.put("fire-off", temple.getController().getFireOffAction());
			
//		voices.addCursorListener(temple.getController());
		
		return actions;
	}

}
