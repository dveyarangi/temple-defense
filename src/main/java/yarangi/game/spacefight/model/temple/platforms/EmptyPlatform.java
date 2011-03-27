package yarangi.game.spacefight.model.temple.platforms;

import yarangi.game.spacefight.model.temple.Hexagon;
import yarangi.game.spacefight.model.temple.Platform;

public class EmptyPlatform extends Platform 
{

	
	public EmptyPlatform(Hexagon hexagon)
	{
		super(hexagon);
		setBehavior(new PlatformBehavior());
		setLook(new PlatformLook());
	}

}
