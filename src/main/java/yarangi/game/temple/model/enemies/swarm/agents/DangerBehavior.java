package yarangi.game.temple.model.enemies.swarm.agents;

import yarangi.game.temple.model.enemies.swarm.IBeacon;
import yarangi.game.temple.model.enemies.swarm.Swarm;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.math.Vector2D;

public class DangerBehavior implements IBehaviorState <SwarmAgent>  
{

	@Override
	public double behave(double time, SwarmAgent agent) {
		Swarm swarm = agent.getSwarm();
		Vector2D flow = swarm.getFlow(agent.getArea().getRefPoint());
		
		IBeacon localBeacon = swarm.getBeacon(agent.getArea().getRefPoint());
//		localBeacon.getDangerFactor()
		
		if(flow != null)
			agent.getBody().setForce(flow.x(), flow.y());
		return 0;
	}
	public int getId() { return this.getClass().hashCode(); }

}
