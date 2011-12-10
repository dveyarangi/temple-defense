package yarangi.game.temple.model.temple.bots;

import yarangi.game.temple.model.temple.Serviceable;
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
		satelliting = new SatelliteBehavior( target.getServicePoint() );
	}
	public double behave(double time, Bot bot) 
	{
	

		Vector2D targetLocation = target.getServicePoint().getRefPoint();
		
		double distanceToTarget = Geometry.calcHypotSquare(targetLocation, bot.getArea().getRefPoint());
		if (distanceToTarget < 8 * target.getServicePoint().getMaxRadius()*target.getServicePoint().getMaxRadius())
		{
			return 0; // proceed to next state
		}
		
		Vector2D botLocation = bot.getArea().getRefPoint();
		Vector2D hostLocation = target.getServicePoint().getRefPoint();
		double satelliteDistanceSquare = 2*target.getServicePoint().getMaxRadius()*target.getServicePoint().getMaxRadius();
		
			
		Vector2D attractionDir = hostLocation.minus(botLocation).normalize();
		if (attractionDir.x() == 0 && attractionDir.y() == 0) // stale mate
		{
			double randomAngle = Math.random()*Angles.PI_2;
			attractionDir = new Vector2D(Math.cos(randomAngle), Math.sin(randomAngle));
		}
		double offset = Geometry.calcHypotSquare( botLocation, hostLocation ) - satelliteDistanceSquare;
		
		double rotationScalar = 1 / Math.log( Math.abs( offset )+1 );
//			System.out.println(rotationScalar);
		Vector2D attractionForce = attractionDir.multiply( (offset > 0 ? 1 : -1 ) * (1 - rotationScalar)*2*bot.getEnginePower()); 
			
//				System.out.println(rotationScalar);
		Vector2D rotationDir = attractionDir.left(); 
		rotationDir = bot.getBody().getVelocity().dot( attractionDir.left() ) > 0 ? rotationDir : rotationDir.minus();
		
		Vector2D force = rotationDir.multiply( rotationScalar *2* bot.getEnginePower() ).add( attractionForce );
		
//			System.out.println(bot.getArea() + " : " + offset);
		bot.getBody().setForce( force.x(), force.y() );
			
		return -1;
	}
	public int getId() { return getStateId(); }
	public static int getStateId() {return ChasingBehavior.class.hashCode(); }

}
