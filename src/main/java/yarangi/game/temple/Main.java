package yarangi.game.temple;

import yarangi.graphics.quadraturin.Swing2DContainer;
import yarangi.graphics.shaders.ShaderFactory;

public class Main 
{
	
	public static void main(String ... args)
	{
		
		// creating frame: 
		Swing2DContainer container = new Swing2DContainer();
		
//	    container.registerPlugin(new ShaderFactory());
	    
	    // starting engine
	    container.start();
	    
	    // actualizing playground scene
	    container.activateScene("playground");
	    
	}
}
