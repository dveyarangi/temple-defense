package yarangi.game.temple.model.enemies.swarm;

import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.objects.behaviors.FSMBehavior;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorCondition;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.math.Angles;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.Point;

public class SwarmFactory 
{
	public static int N = 10;
	
	public static double agentEnginePower = 0.01;
	
	public static Swarm createSwarm(int worldSize, Scene scene, int nodes)
	{
		
		Swarm swarm = new Swarm (worldSize, scene);
		swarm.setArea(new Point(400, 400));
		final IBehaviorState<Swarm> rotating = new RotatingBehavior();
		final IBehaviorState<Swarm> pathing = new SwarmPathingBehavior(1);
		final IBehaviorState<Swarm> spawning = new SwarmSpawningBehavior(3);
		final IBehaviorState<Swarm> shifting = new SwarmShiftBehavior();
		
		FSMBehavior <Swarm> behavior = new FSMBehavior <Swarm> (shifting);
		behavior.link(shifting, new IBehaviorCondition<Swarm>() {
			@Override
			public IBehaviorState<Swarm> nextState(Swarm entity) 
			{
				if(RandomUtil.oneOf(5))
					return pathing;
				
				return spawning;
				
			}
		});
		behavior.link(spawning, new IBehaviorCondition<Swarm>() {
			@Override
			public IBehaviorState<Swarm> nextState(Swarm entity) { return shifting; }
		});
		behavior.link(pathing, new IBehaviorCondition<Swarm>() {
			@Override
			public IBehaviorState<Swarm> nextState(Swarm entity) { return shifting; }
		});
		
		swarm.setBehavior(behavior);
		
		for(int i = 1; i <= nodes; i ++)
		{
			double r = RandomUtil.getRandomDouble(100)+500;
			double a = RandomUtil.getRandomDouble(Angles.PI_2);
			
			swarm.addSpawnNode(r*Math.cos(a), r*Math.sin(a));
		}

		return swarm;
	}
	
//	};
	
}
