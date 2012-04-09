package yarangi.game.harmonium.battle;

import yarangi.graphics.colors.Color;
import yarangi.graphics.lights.CircleLightLook;
import yarangi.graphics.quadraturin.SceneLayer;
import yarangi.graphics.quadraturin.objects.DummySensor;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.math.Vector2D;
import yarangi.spatial.PointArea;

public class EffectUtils
{
	public static void makeExplosion(Vector2D location, SceneLayer veil, Color color, int size)
	{
/*		ExplodingBehavior dyingBehavior = new ExplodingBehavior( new Color(color), 8 );
		Look <IEntity> dyingLook = new CircleLightLook<IEntity>( dyingBehavior.getActiveColor() );
		Entity explosion = new Entity(){};
		explosion.setBehavior( dyingBehavior );
		explosion.setLook( dyingLook );
		explosion.setSensor( new DummySensor(size) );
		explosion.setArea( new PointArea( location.x(), location.y() ) );
		
		veil.addEntity( explosion );*/

	}
}
