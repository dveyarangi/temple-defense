package yarangi.game.harmonium.temple.bots;

import yar.quadraturin.objects.IEntity;
import yar.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.math.Angles;
import yarangi.math.Geometry;
import yarangi.math.IVector2D;
import yarangi.math.Vector2D;
import yarangi.physics.Body;
import yarangi.spatial.Area;

public class ChasingBehavior <H extends IEntity>implements IBehaviorState <H> 
{
	private final Area target;

	
	private final double enginePower;
	
	public ChasingBehavior(Area target, double enginePower)
	{
		
		this.target = target;
		this.enginePower = enginePower;
	}
	@Override
	public double behave(double time, IEntity bot) 
	{
		Body body = bot.getBody();
		if(body.getMaxSpeed() < Bot.MAX_SPEED)
			body.setMaxSpeed( body.getMaxSpeed()+time*1 );
		
		IVector2D targetLocation = target.getAnchor();
		
		double distanceToTarget = Geometry.calcHypotSquare(targetLocation, bot.getArea().getAnchor());
		
		IVector2D botLocation = bot.getArea().getAnchor();
		IVector2D hostLocation = target.getAnchor();
		double satelliteDistanceSquare = 2*target.getMaxRadius()*target.getMaxRadius();
		
			
		Vector2D attractionDir = hostLocation.minus(botLocation).normalize();
		if (attractionDir.x() == 0 && attractionDir.y() == 0) // stale mate
		{
			double randomAngle = Math.random()*Angles.TAU;
			attractionDir = Vector2D.POLAR(1, randomAngle);
		}
		double offset = Geometry.calcHypotSquare( botLocation, hostLocation ) - satelliteDistanceSquare;
		
		double rotationScalar = 1 / Math.log( Math.abs( offset )+1 );
//			System.out.println(rotationScalar);
		Vector2D attractionForce = attractionDir.multiply( (offset > 0 ? 1 : -1 ) * (1 - rotationScalar)*enginePower); 
			
//				System.out.println(rotationScalar);
		Vector2D rotationDir = attractionDir.left(); 
		rotationDir = bot.getBody().getVelocity().dot( attractionDir.left() ) > 0 ? rotationDir : rotationDir.minus();
		
		Vector2D force = rotationDir.multiply( rotationScalar * enginePower ).add( attractionForce.mul( enginePower ));
		
//			System.out.println(bot.getArea() + " : " + offset);
		bot.getBody().setForce( force.x(), force.y() );
		
		if (distanceToTarget < 10 * target.getMaxRadius()*target.getMaxRadius())
		{
			return 0; // proceed to next state
		}
			
		return -1;
	}
	@Override
	public int getId() { return getStateId(); }
	public static int getStateId() {return ChasingBehavior.class.hashCode(); }

}
