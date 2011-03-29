package yarangi.game.spacefight;

import yarangi.game.spacefight.model.enemies.bubbles.BubbleSwarm;
import yarangi.game.spacefight.model.temple.TempleEntity;
import yarangi.graphics.quadraturin.EventManager;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.util.scene.BackgroundEntity;

public class Playground extends Scene
{
	
	private TempleEntity temple;
	
	private BackgroundEntity background;
	
	private int worldWidth = 1000;
	private int worldHeight = 1000;
	
	public Playground(EventManager voices)
	{
		super("Playground", 1000, 1000, new ArcadeWorldVeil(1000, 1000), new TestUIVeil(1000, 1000));
		
		temple = new TempleEntity(this);
		addEntity(temple);
		
		addEntity(new BubbleSwarm(getWorldVeil(), temple));

		
//		background = new BackgroundEntity(100, 100, 10);		addEntity(background);
	}
	
	public BackgroundEntity getBackground() { return background; }

	@Override
	public void bindSceneActions(EventManager voices) 
	{
		bindNavigationActions();

		bindAction("fire-on", temple.getController().getFireOnAction());
		bindAction("fire-off", temple.getController().getFireOffAction());
		
		voices.addCursorListener(temple.getController());
	}

}
