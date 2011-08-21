package yarangi.game.temple.model.temple.platforms;

import yarangi.game.temple.model.temple.Platform;
import yarangi.game.temple.model.temple.structure.Hexagon;

public class EmptyPlatform extends Platform 
{

	
	public EmptyPlatform(Hexagon hexagon)
	{
		super(hexagon);
		setBehavior(new PlatformBehavior());
		setLook(new PlatformLook());
	}

}
