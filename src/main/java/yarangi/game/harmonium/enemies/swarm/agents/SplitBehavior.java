package yarangi.game.harmonium.enemies.swarm.agents;

import yar.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.numbers.RandomUtil;

public class SplitBehavior implements IBehaviorState<SwarmAgent>
{
	private static int id = RandomUtil.N(Integer.MAX_VALUE);

	@Override
	public int getId()
	{
		return id;
	}

	@Override
	public double behave(double time, SwarmAgent entity)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
