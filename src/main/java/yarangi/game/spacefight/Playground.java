package yarangi.game.spacefight;

import yarangi.game.spacefight.actions.PlaygroundActionProvider;
import yarangi.game.spacefight.model.enemies.bubbles.BubbleSwarm;
import yarangi.game.spacefight.model.temple.TempleEntity;
import yarangi.graphics.quadraturin.EventManager;
import yarangi.graphics.quadraturin.IViewPoint;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.ViewPoint2D;
import yarangi.graphics.quadraturin.actions.UIProvider;
import yarangi.graphics.quadraturin.util.scene.BackgroundEntity;

public class Playground extends Scene
{
	
	private TempleEntity temple;
	
	private BackgroundEntity background;
	
	public Playground(EventManager voices)
	{
		super("Playground", voices, new ArcadeWorldVeil(1000, 1000), new TestUIVeil(1000, 1000));
		
		temple = new TempleEntity(this);
		addEntity(temple);
		
		addEntity(new BubbleSwarm(getWorldVeil(), temple));
		
//		background = new BackgroundEntity(100, 100, 10);		addEntity(background);
	}
	
	public BackgroundEntity getBackground() { return background; }
	 
	public UIProvider createActionProvider(IViewPoint viewPoint) 
	{ 
		return new PlaygroundActionProvider(temple, (ViewPoint2D)viewPoint); 
	}
	


}
