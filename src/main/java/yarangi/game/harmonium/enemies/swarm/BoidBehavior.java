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
	public static final double CROWDING_COEF = 0.6;
	public static final double SPREADING_COEF = 0.5;
	public static final double FLOCKING_COEF = 1.6;
	
	private DroneBehavior droning = new DroneBehavior(1);
///	public static final double FLOCKING_COEF = 1;
	@Override
	public double behave(double time, SwarmAgent boid) {
		
//		droning.behave( time, boid);
		
		ISensor<Entity> sensor = boid.getSensor();
		
		Set <Entity> neighbours = sensor.getEntities();
		if(neighbours.size() == 1)
			return 0;
		
		Vector2D loc = boid.getArea().getRefPoint();
		
		Vector2D massCenter = Vector2D.ZERO();
		Vector2D spreadingVelocity = Vector2D.ZERO();
		Vector2D flockingVelocity = Vector2D.ZERO();
		Vector2D otherLoc;
		
		double distance;
		for(Entity entity : neighbours)
		{
			if(entity == boid)
				continue;
			
			otherLoc = entity.getArea().getRefPoint();
			distance = Geometry.calcHypot( loc, otherLoc );
			
			massCenter.add(otherLoc);
			spreadingVelocity.substract(otherLoc.minus(loc).multiply(distance));
			flockingVelocity.add(entity.getBody().getVelocity());
		}
		
		massCenter.multiply(1./(neighbours.size()-1));
		Vector2D crowdingVelocity = massCenter.substract(loc).multiply(CROWDING_COEF);
		flockingVelocity.multiply(1./(neighbours.size()-1));
		flockingVelocity.substract( boid.getBody().getVelocity() );
		flockingVelocity.multiply(FLOCKING_COEF);
		
		spreadingVelocity.multiply( 1./(neighbours.size()-1)*SPREADING_COEF );
		
		boid.getBody().setForce( crowdingVelocity.plus(spreadingVelocity).plus(flockingVelocity));
		
		return 0;
	}
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 145645645;
	}

}
