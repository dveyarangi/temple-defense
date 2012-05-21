package yarangi.game.harmonium.temple.bots;

import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.math.Angles;
import yarangi.math.Geometry;
import yarangi.math.Vector2D;
import yarangi.spatial.Area;

/**
 * In this state bot wanders aimlessly near its host.
 * @author dveyarangi
 *
 */
public class SatelliteBehavior <K extends IEntity> implements IBehaviorState <K>
{
	private Area host;
	
	private double enginePower;
	
	public SatelliteBehavior(Area host, double enginePower)
	{
		this.host = host;
		this.enginePower = enginePower;
	}

	@Override
	public double behave(double time, K bot)
	{
		Vector2D botLocation = bot.getArea().getAnchor();
		Vector2D hostLocation = host.getAnchor();
		double satelliteDistanceSquare = 2*host.getMaxRadius()*host.getMaxRadius();
		
		
		Vector2D attractionDir = hostLocation.minus(botLocation).normalize();
		if (attractionDir.x() == 0 && attractionDir.y() == 0) // stale mate
		{
			attractionDir = Vector2D.UNIT(Math.random()*Angles.PI_2);
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
	
	public int getId() { return getStateId(); }
	public static int getStateId() { return SatelliteBehavior.class.hashCode(); }
}
