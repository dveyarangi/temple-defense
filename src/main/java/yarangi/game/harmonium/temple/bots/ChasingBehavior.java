package yarangi.game.harmonium.temple.bots;

import yarangi.game.harmonium.temple.Serviceable;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.math.Angles;
import yarangi.math.Geometry;
import yarangi.math.Vector2D;

public class ChasingBehavior implements IBehaviorState <Bot> 
{
	private Serviceable target;
	private SatelliteBehavior satelliting;
	
	public ChasingBehavior(Serviceable target)
	{
		
		this.target = target;
		satelliting = new SatelliteBehavior( target.getServiceArea() );
	}
	public double behave(double time, Bot bot) 
	{
	

		Vector2D targetLocation = target.getServiceArea().getRefPoint();
		
		double distanceToTarget = Geometry.calcHypotSquare(targetLocation, bot.getArea().getRefPoint());
		
		Vector2D botLocation = bot.getArea().getRefPoint();
		Vector2D hostLocation = target.getServiceArea().getRefPoint();
		double satelliteDistanceSquare = 2*target.getServiceArea().getMaxRadius()*target.getServiceArea().getMaxRadius();
		
			
		Vector2D attractionDir = hostLocation.minus(botLocation).normalize();
		if (attractionDir.x() == 0 && attractionDir.y() == 0) // stale mate
		{
			double randomAngle = Math.random()*Angles.PI_2;
			attractionDir = Vector2D.POLAR(1, randomAngle);
		}
		double offset = Geometry.calcHypotSquare( botLocation, hostLocation ) - satelliteDistanceSquare;
		
		double rotationScalar = 1 / Math.log( Math.abs( offset )+1 );
//			System.out.println(rotationScalar);
		Vector2D attractionForce = attractionDir.multiply( (offset > 0 ? 1 : -1 ) * (1 - rotationScalar)*bot.getEnginePower()); 
			
//				System.out.println(rotationScalar);
		Vector2D rotationDir = attractionDir.left(); 
		rotationDir = bot.getBody().getVelocity().dot( attractionDir.left() ) > 0 ? rotationDir : rotationDir.minus();
		
		Vector2D force = rotationDir.multiply( rotationScalar * bot.getEnginePower() ).add( attractionForce.mul( bot.getEnginePower() ));
		
//			System.out.println(bot.getArea() + " : " + offset);
		bot.getBody().setForce( force.x(), force.y() );
		
		if (distanceToTarget < 8 * target.getServiceArea().getMaxRadius()*target.getServiceArea().getMaxRadius())
		{
			return 0; // proceed to next state
		}
			
		return -1;
	}
	public int getId() { return getStateId(); }
	public static int getStateId() {return ChasingBehavior.class.hashCode(); }

}
