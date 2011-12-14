package yarangi.game.temple.model.enemies.swarm.agents;

import yarangi.game.temple.model.enemies.swarm.Swarm;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;

public class DroneBehavior implements IBehaviorState <SwarmAgent>  
{
	double da = RandomUtil.getRandomGaussian(0, 0.1);
	
	double inertion;
	
	public DroneBehavior(double inertion) {
		this.inertion = inertion;
	}

	@Override
	public double behave(double time, SwarmAgent agent) {
		Swarm swarm = agent.getSwarm();
		Vector2D flow = swarm.getFlow(agent.getArea().getRefPoint());
		agent.getArea().setOrientation( Angles.TO_DEG * agent.getBody().getVelocity().getAngle() );
		
//		System.out.println(flow);
		if(flow != null) {
//			if(agent.getBody().getForce().x() == 0 && agent.getBody().getForce().y() == 0)
				agent.getBody().setForce( flow.x()/inertion, flow.y()/inertion );
//			else
//				agent.getBody().addForce(flow.x(), flow.y());
		}
		return 0;
	}
	public int getId() { return this.getClass().hashCode(); }

}
