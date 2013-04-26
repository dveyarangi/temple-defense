package yarangi.game.harmonium.temple.bots;

import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.math.Angles;
import yarangi.math.Geometry;
import yarangi.math.IVector2D;
import yarangi.math.Vector2D;
import yarangi.spatial.Area;

/**
 * In this state bot wanders aimlessly near its host.
 * @author dveyarangi
 *TODO:OPTIMIZE - vector allocation
 */
public class SatelliteBehavior <K extends IEntity> implements IBehaviorState <K>
{
	private final Area host;
	
	private final double enginePower;
	
	public SatelliteBehavior(Area host, double enginePower)
	{
		this.host = host;
		this.enginePower = enginePower;
	}

	@Override
	public double behave(double time, K bot)
	{
		IVector2D botLocation = bot.getArea().getAnchor();
		IVector2D hostLocation = host.getAnchor();
		double satelliteDistanceSquare = 2*host.getMaxRadius()*host.getMaxRadius();
		
		
		Vector2D attractionDir = hostLocation.minus(botLocation).normalize();
		if (attractionDir.x() == 0 && attractionDir.y() == 0) // stale mate
		{
			attractionDir = Vector2D.UNIT(Math.random()*Angles.TAU);
		}
		double offset = Geometry.calcHypotSquare( botLocation, hostLocation ) - satelliteDistanceSquare;
		
		double rotationScalar = 1 / Math.log( Math.abs( offset )+1 );
//		System.out.println(rotationScalar);
		Vector2D attractionForce = attractionDir.multiply( (offset > 0 ? 1 : -1 ) * (1 - rotationScalar)*10*enginePower); 
		
//			System.out.println(rotationScalar);
		Vector2D rotationDir = attractionDir.left(); 
		rotationDir = bot.getBody().getVelocity().dot( attractionDir.left() ) > 0 ? rotationDir : rotationDir.minus();
		
		Vector2D force = rotationDir.multiply( rotationScalar ).add( attractionForce );
	
//		System.out.println(bot.getArea() + " : " + offset);
		bot.getBody().setForce( force.x(), force.y() );
		
		return 0;
	}
	
	@Override
	public int getId() { return getStateId(); }
	public static int getStateId() { return SatelliteBehavior.class.hashCode(); }
}
