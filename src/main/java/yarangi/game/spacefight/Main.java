package yarangi.game.spacefight;

import yarangi.graphics.quadraturin.QuadConstants.QuadMode;
import yarangi.graphics.quadraturin.QuadContainer;
import yarangi.graphics.quadraturin.Scene;

public class Main 
{
	
	public static void main(String ... args)
	{
		
		// creating frame: 
		QuadContainer frame = new QuadContainer(QuadMode.PRESENT_2D);
		
		// creating test scene:
		Scene playground = new Playground(frame.getEventManager());
	    int playgroundId = frame.addScene(playground);
	    
	    // starting engine
	    frame.start();
	    frame.setVisible(true);

	    // actualizing playground scene
	    frame.activateScene(playgroundId);
	    
	}
}
