package yarangi.game.temple.model.enemies.swarm;

import yarangi.game.temple.model.enemies.swarm.agents.SwarmAgent;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;

class TargetingBehavior implements IBehaviorState<SwarmAgent> {

	@Override
	public double behave(double time, SwarmAgent entity) 
	{
		return 0;
		
	}

	public int getId() { return this.getClass().hashCode(); }
}
