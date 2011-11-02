package yarangi.game.temple.model.enemies.swarm.agents;

import yarangi.game.temple.model.enemies.swarm.Swarm;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;

public class DroneBehavior implements IBehaviorState <SwarmAgent>  
{
	double da = RandomUtil.getRandomGaussian(0, 0.1);

	@Override
	public double behave(double time, SwarmAgent agent, boolean isVisible) {
		agent.getArea().setOrientation( agent.getArea().getOrientation()+da );
		Swarm swarm = agent.getSwarm();
		Vector2D flow = swarm.getFlow(agent.getArea().getRefPoint());
		
		if(flow != null)
			agent.getBody().setForce(flow.x(), flow.y());
		return 0;
	}
	public int getId() { return this.getClass().hashCode(); }

}
