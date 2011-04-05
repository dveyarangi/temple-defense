package yarangi.game.temple.model.temple.platforms;

import yarangi.game.temple.model.temple.Hexagon;
import yarangi.game.temple.model.temple.Platform;

public class EmptyPlatform extends Platform 
{

	
	public EmptyPlatform(Hexagon hexagon)
	{
		super(hexagon);
		setBehavior(new PlatformBehavior());
		setLook(new PlatformLook());
	}

}
