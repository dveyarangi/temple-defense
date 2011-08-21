package yarangi.game.temple.model.enemies.swarm;

import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.math.Vector2D;

public class DroneBehavior implements IBehaviorState <SwarmAgent>  
{

	@Override
	public boolean behave(double time, SwarmAgent agent, boolean isVisible) {
		Swarm swarm = agent.getSwarm();
		Vector2D flow = swarm.getFlow(agent.getArea().getRefPoint());
		
		if(flow != null)
			agent.getBody().setForce(flow.x(), flow.y());
		return false;
	}

}
