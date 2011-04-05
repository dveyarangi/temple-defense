package yarangi.game.temple;

import yarangi.graphics.quadraturin.Swing2DContainer;

public class Main 
{
	
	public static void main(String ... args)
	{
		
		// creating frame: 
		Swing2DContainer container = new Swing2DContainer();
		
		// creating test scene:
	    int playgroundId = container.addScene(new Playground(container.getEventManager()));
	    
	    // starting engine
	    container.start();
	    
	    // actualizing playground scene
	    container.activateScene(playgroundId);
	    
	}
}
