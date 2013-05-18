package yarangi.game.harmonium.enemies.swarm;

import yar.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.game.harmonium.enemies.swarm.agents.SwarmAgent;

class TargetingBehavior implements IBehaviorState<SwarmAgent> {

	@Override
	public double behave(double time, SwarmAgent entity) 
	{
		return 0;
		
	}

	public int getId() { return this.getClass().hashCode(); }
}
