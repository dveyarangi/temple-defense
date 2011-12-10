package yarangi.game.temple.model.enemies.swarm;

import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;

class ShiftBehavior implements IBehaviorState<Swarm> 
{

	@Override
	public double behave(double time, Swarm swarm) {
		swarm.nextNode();
		return 0;
	}
	public int getId() { return this.getClass().hashCode(); }

}
