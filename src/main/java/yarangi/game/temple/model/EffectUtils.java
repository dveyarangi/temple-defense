package yarangi.game.temple.model;

import yarangi.graphics.colors.Color;
import yarangi.graphics.lights.CircleLightLook;
import yarangi.graphics.quadraturin.SceneVeil;
import yarangi.graphics.quadraturin.objects.DummySensor;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.graphics.quadraturin.objects.WorldEntity;
import yarangi.math.Vector2D;
import yarangi.spatial.Area;
import yarangi.spatial.Point;

public class EffectUtils
{
	public static void makeExplosion(Vector2D location, SceneVeil veil, Color color, int size)
	{
		ExplodingBehavior dyingBehavior = new ExplodingBehavior( new Color(color), 8 );
		Look <WorldEntity> dyingLook = new CircleLightLook<WorldEntity>( dyingBehavior.getActiveColor() );
		WorldEntity explosion = new WorldEntity(){};
		explosion.setBehavior( dyingBehavior );
		explosion.setLook( dyingLook );
		explosion.setSensor( new DummySensor(size) );
		explosion.setArea( new Point( location.x(), location.y() ) );
		
		veil.addEntity( explosion );

	}
}
