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
 * TODO: this motherfucker creates enormous amounts of vectors, 
 * @author dveyarangi
 *
 */
public class BoidBehavior implements IBehaviorState<SwarmAgent> 
{
	public static final double ATTRACTION_COEF = 0.25;
	public static final double SEPARATION_COEF = 0.02;
	public static final double FLOCKING_COEF = 0.3;
	
	private final DroneBehavior droning = new DroneBehavior(20);
	private SatelliteBehavior satellite;
	
	Vector2D massCenter = Vector2D.ZERO();
	Vector2D Fsep = Vector2D.ZERO();
	Vector2D flockingVelocity = Vector2D.ZERO();
	Vector2D separation = Vector2D.ZERO();
	Vector2D momentum = Vector2D.ZERO();
	Vector2D lead = Vector2D.ZERO();

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
		
		double separationDistance;
		
		IVector2D otherLoc;
		
		massCenter.setxy( 0, 0 );
		Fsep.setxy(0, 0);
		flockingVelocity.setxy( 0, 0 );
		
		
		double distance;
		double dN = 1./(neighbours.size()-1);
		IEnemy otherBoid;
		double leadership;
		double attractivity;
		boolean flocking = false;
		for(Entity neigh : neighbours)
		{
			if(neigh == boid)
				continue;
			
			if(neigh instanceof ITemple)
			{
				if(boid.getTarget() == null)
				{
					boid.setTarget( neigh );
				}
				
				if(neigh == boid.getTarget()) 
				{
				leadership = 1;
				attractivity = ((ITemple) neigh).getAttractivity();
				flocking = false;
				}
				else
					continue;
			}
			else
			{
 				otherBoid = (IEnemy) neigh;
				leadership = otherBoid.getLeadership();
				attractivity = otherBoid.getAttractiveness();
				flocking = true;
			}
			
			
			
			otherLoc = neigh.getArea().getAnchor();
			separationDistance = (neigh.getArea().getMaxRadius() + boid.getArea().getMaxRadius())/1.5;
			
			distance = Geometry.calcHypot( loc, otherLoc );
			
			// separation:
			separation.set( loc ).substract(otherLoc).normalize().multiply( SEPARATION_COEF );
			Fsep.add(separation);//.multiply((separationDistance-distance)));
			
			// attraction parameters:
			momentum.set( otherLoc ).substract( loc ).multiply( attractivity );
			massCenter.add(momentum);
			
			// flocking:
			if(flocking) {
				lead.set(neigh.getBody().getVelocity()).multiply(leadership);
				flockingVelocity.add(lead);
			}
		}
		
		massCenter.multiply(dN).add( loc );
//		double attDistance = Geometry.calcHypot(massCenter, loc);
		Vector2D Fatt = massCenter.substract(loc).normalize().multiply(ATTRACTION_COEF);
		
//		flockingVelocity.multiply(dN);
//		flockingVelocity.substract( boid.getBody().getVelocity() );
//		flockingVelocity.multiply(FLOCKING_COEF);
		
//		Fsep.normalize().multiply( SEPARATION_COEF );
		
		Vector2D Flok = flockingVelocity.normalize().multiply(FLOCKING_COEF); 
		
//		droning.behave( time, boid);
		droning.behave( time, boid);
		boid.getBody().addForce( Fatt );
		boid.getBody().addForce( Fsep );
		boid.getBody().addForce( Flok );
		
//		System.out.println(Fatt + " : " + Fsep + " : " + Flok + ", res:" + boid.getBody().getForce());
		
		return CONTINUE;
	}
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 145645645;
	}

}
