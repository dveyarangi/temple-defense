package yarangi.game.harmonium.enemies.swarm;

import java.util.List;

import yarangi.game.harmonium.battle.IEnemy;
import yarangi.game.harmonium.battle.ITemple;
import yarangi.game.harmonium.enemies.swarm.agents.DroneBehavior;
import yarangi.game.harmonium.enemies.swarm.agents.SwarmAgent;
import yarangi.game.harmonium.temple.bots.SatelliteBehavior;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.graphics.quadraturin.objects.ISensor;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.math.Geometry;
import yarangi.math.IVector2D;
import yarangi.math.Vector2D;

/**
 * 
 * Boid  behavior implementation.
 * Regards both IEnemy and ITemple entities as fellow boids, though handles them a bit differently 
 *
 * @author dveyarangi
 *
 */
public class BoidBehavior implements IBehaviorState<SwarmAgent> 
{
	public static final double ATTRACTION_COEF = 1;
	public static final double SEPARATION_COEF = 0.2;
	public static final double FLOCKING_COEF = 0.03;
	
	private final DroneBehavior droning = new DroneBehavior(20);
	private SatelliteBehavior satellite;
	
	Vector2D tMassCenter = Vector2D.ZERO(); // temp var mass center of group (boids in sensor range)
	Vector2D tSeparationForce = Vector2D.ZERO(); // temp var total separation force from group
	Vector2D tFlockingVelocity = Vector2D.ZERO(); // temp var average group velocity
	Vector2D tAttractionForce = Vector2D.ZERO(); // temp var for group attraction weighted vector
	
	Vector2D tMomentum = Vector2D.ZERO(); // temp var relative location to particular boid in group
	Vector2D tBoidSeparation = Vector2D.ZERO(); // temp var separation force from particular boid (TODO: is this weak or electromagnetic forces?)  
	Vector2D tBoidLeadership = Vector2D.ZERO(); // temp var leadership factor of particular boid
	Vector2D tBoidAttraction = Vector2D.ZERO(); // temp var attraction factor of particular boid (em force?)

///	public static final double FLOCKING_COEF = 1;
	@Override
	public double behave(double time, SwarmAgent boid) {
		
		ISensor<Entity> sensor = boid.getEntitySensor();
		
		List <Entity> neighbours = sensor.getEntities();
		if(neighbours.size() <= 1)
		{
			droning.behave( time, boid);

			return CONTINUE;
		}
		
		// TODO: OPTIMIZE vector operations
		IVector2D loc = boid.getArea().getAnchor();

		
		IVector2D otherLoc;
		
		// reset vectors
		tMassCenter.setxy( 0, 0 );
		tSeparationForce.setxy(0, 0);
		tFlockingVelocity.setxy( 0, 0 );
		tAttractionForce.setxy( 0, 0 );
		

		double dN = 1./(neighbours.size()-1);  // detected neighbour boids count inverse
		IEnemy otherBoid; // temp var
		double leadership; // temp var for otherBoid leadership
		double attractivity; // temp var for otherBoid attractiveness
		boolean flocking = false; // temp var floking on/off for this boid
		double distance;
		double attractionModifier = Math.min( 100, boid.getLocalDanger() ) / 100 + 0.01;
		for(Entity neigh : neighbours)
		{
			if(neigh == boid)
				continue;
			

 			if(neigh instanceof ITemple)
			{
				attractivity = ((ITemple) neigh).getAttractiveness();
				leadership = 0; //
				flocking = false; //not one of our kind, we do not flock with him
				
//				if(boid.getTarget() == null) // lock on temple core, not used currently
				{
					boid.setTarget( neigh );
				}

			}
			else
			{
 				otherBoid = (IEnemy) neigh;
				leadership = otherBoid.getLeadership();
				attractivity = otherBoid.getAttractiveness() * attractionModifier;
				flocking = true;
			}
			
			
			
			otherLoc = neigh.getArea().getAnchor();
//			separationDistance = (neigh.getArea().getMaxRadius() + boid.getArea().getMaxRadius())/1.5;
			
			distance = Geometry.calcHypot( loc, otherLoc );
			
			tMomentum.set( otherLoc ).substract( loc ).multiply( 1 / distance );
			
			// separation:
			if(boid.getTarget() != neigh) 
			{
				tBoidSeparation.set( tMomentum ).multiply( -SEPARATION_COEF / distance); // its -momentum * S
				tSeparationForce.add(tBoidSeparation);//.multiply((separationDistance-distance)));
			}
			
			// attraction parameters:
//			tMassCenter.add(tMomentum);
			tBoidAttraction.set( tMomentum ).multiply( attractivity );
			tAttractionForce.add( tBoidAttraction ); //attraction forces sum
			
			
			// flocking:
			if(flocking) {
				tBoidLeadership.set(neigh.getBody().getVelocity()).multiply(leadership);
				tFlockingVelocity.add(tBoidLeadership);
			}
		}
		
//		tMassCenter.multiply( dN ).add( loc );
		tAttractionForce.multiply( dN );
//		double attDistance = Geometry.calcHypot(massCenter, loc);
		tAttractionForce.multiply(ATTRACTION_COEF);
		
//		flockingVelocity.multiply(dN);
//		flockingVelocity.substract( boid.getBody().getVelocity() );
//		flockingVelocity.multiply(FLOCKING_COEF);
		
//		Fsep.normalize().multiply( SEPARATION_COEF );
		
//		tFlockingVelocity.normalize().multiply(FLOCKING_COEF); 
		
//		droning.behave( time, boid);
		droning.behave( time, boid);
		boid.getBody().addForce( tAttractionForce );
		boid.getBody().addForce( tSeparationForce );
		boid.getBody().addVelocity( tFlockingVelocity.substract( boid.getBody().getVelocity()).multiply( FLOCKING_COEF ) );
		
		
//		System.out.println(Fatt + " : " + Fsep + " : " + Flok + ", res:" + boid.getBody().getForce());
		
		return CONTINUE;
	}
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 145645645;
	}

}
