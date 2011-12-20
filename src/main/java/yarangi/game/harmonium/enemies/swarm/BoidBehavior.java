package yarangi.game.harmonium.enemies.swarm;

import java.util.Set;

import yarangi.game.harmonium.enemies.swarm.agents.SwarmAgent;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.graphics.quadraturin.objects.ISensor;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.math.Vector2D;

public class BoidBehavior implements IBehaviorState<SwarmAgent> 
{
	public static final double CROWDING_COEF = 1;
	public static final double SPREADING_COEF = 1;
	public static final double FLOCKING_COEF = 1;
///	public static final double FLOCKING_COEF = 1;
	@Override
	public double behave(double time, SwarmAgent boid) {
		ISensor<Entity> sensor = boid.getSensor();
		
		Set <Entity> neighbours = sensor.getEntities();
		
		Vector2D loc = boid.getArea().getRefPoint();
		
		Vector2D massCenter = Vector2D.ZERO();
		Vector2D spreadingForce = Vector2D.ZERO();
		Vector2D flockingForce = Vector2D.ZERO();
		Vector2D otherLoc;
		for(Entity entity : neighbours)
		{
			if(entity == boid)
				continue;
			otherLoc = entity.getArea().getRefPoint();
			massCenter.add(otherLoc);
			spreadingForce.add(otherLoc.minus(loc));
			flockingForce.add(entity.getBody().getForce());
		}
		
		massCenter.multiply(1./(neighbours.size()-1));
		Vector2D crowdingForce = massCenter.substract(loc).multiply(CROWDING_COEF);
		spreadingForce.mul(SPREADING_COEF);
		flockingForce.multiply(1./(neighbours.size()-1));
		
		boid.getBody().setForce(crowdingForce.plus(spreadingForce).plus(flockingForce));
		
		return 0;
	}
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 145645645;
	}

}
