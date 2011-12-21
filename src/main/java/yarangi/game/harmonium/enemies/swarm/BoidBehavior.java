package yarangi.game.harmonium.enemies.swarm;

import java.util.Set;

import yarangi.game.harmonium.enemies.swarm.agents.DroneBehavior;
import yarangi.game.harmonium.enemies.swarm.agents.SwarmAgent;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.graphics.quadraturin.objects.ISensor;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.math.Geometry;
import yarangi.math.Vector2D;

public class BoidBehavior implements IBehaviorState<SwarmAgent> 
{
	public static final double ATTRACTION_COEF = 0.5;
	public static final double SEPARATION_COEF = 0.25;
	public static final double FLOCKING_COEF = 0.5;
	
	private DroneBehavior droning = new DroneBehavior(2);
///	public static final double FLOCKING_COEF = 1;
	@Override
	public double behave(double time, SwarmAgent boid) {
		
		ISensor<Entity> sensor = boid.getSensor();
		
		Set <Entity> neighbours = sensor.getEntities();
		droning.behave( time, boid);
		if(neighbours.size() <= 1)
		{
//			droning.behave( time, boid);

			return 0;
		}
		Vector2D loc = boid.getArea().getRefPoint();
		
		double separationDistance;
		
		Vector2D massCenter = Vector2D.ZERO();
		Vector2D Fsep = Vector2D.ZERO();
		Vector2D flockingVelocity = Vector2D.ZERO();
		Vector2D otherLoc;
		
		double distance;
		double dN = 1./(neighbours.size()-1);
		for(Entity otherBoid : neighbours)
		{
			if(otherBoid == boid)
				continue;
			
			otherLoc = otherBoid.getArea().getRefPoint();
			separationDistance = 2*(otherBoid.getArea().getMaxRadius() + boid.getArea().getMaxRadius());
			
			distance = Geometry.calcHypot( loc, otherLoc );
			
			// separation:
//			if(distance <= separationDistance)
			{
				Fsep.add(loc.minus(otherLoc).multiply(separationDistance-distance));
			}
			
			// attraction parameters:
			massCenter.add(otherLoc);
			
			// flocking:
			flockingVelocity.add(otherBoid.getBody().getVelocity().mul(otherBoid.getArea().getMaxRadius()));
		}
		
		massCenter.multiply(dN);
		double attDistance = Geometry.calcHypot(massCenter, loc);
		Vector2D Fatt = massCenter.substract(loc).normalize().multiply(ATTRACTION_COEF);
		
//		flockingVelocity.multiply(dN);
//		flockingVelocity.substract( boid.getBody().getVelocity() );
//		flockingVelocity.multiply(FLOCKING_COEF);
		
		Fsep.normalize().multiply( SEPARATION_COEF );
		
		Vector2D Flok = flockingVelocity.normalize().multiply(FLOCKING_COEF); 
		
		boid.getBody().addForce( Fatt );
		boid.getBody().addForce( Fsep );
		boid.getBody().addForce( Flok );
		
//		System.out.println(Fatt + " : " + Fsep + " : " + Flok + ", res:" + boid.getBody().getForce());
		
		return -1;
	}
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 145645645;
	}

}
