package yarangi.game.temple.model.enemies.swarm;

import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;

public class SwarmShiftBehavior implements IBehaviorState<Swarm> 
{

	@Override
	public boolean behave(double time, Swarm swarm, boolean isVisible) {
		swarm.nextNode();
		return false;
	}

}
