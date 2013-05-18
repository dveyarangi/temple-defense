package yarangi.game.harmonium.battle;

import yar.quadraturin.SceneLayer;
import yar.quadraturin.graphics.colors.Color;
import yar.quadraturin.graphics.lights.CircleLightLook;
import yar.quadraturin.objects.DummySensor;
import yar.quadraturin.objects.Entity;
import yar.quadraturin.objects.IEntity;
import yar.quadraturin.objects.ILook;
import yarangi.math.IVector2D;
import yarangi.spatial.PointArea;

/**
 * TODO: effects
 * 
 * @author dveyarangi
 */
public class EffectUtils
{
	
	public static final Color EXPLOSION_COLOR = new Color(0,0,1,1);
	
	public static void makeExplosion(IVector2D location, SceneLayer veil, Color color, int size)
	{
/*		ExplodingBehavior dyingBehavior = new ExplodingBehavior( new Color(color), 8 );
		ILook <IEntity> dyingLook = new CircleLightLook <IEntity>( size, dyingBehavior.getActiveColor() );
		Entity explosion = new Entity(){};
		explosion.setBehavior( dyingBehavior );
		explosion.setLook( dyingLook );
//		explosion.setEntitySensor( new DummySensor(size) );
		explosion.setArea( new PointArea( location.x(), location.y() ) );
		
		veil.addEntity( explosion );*/

	}
}
