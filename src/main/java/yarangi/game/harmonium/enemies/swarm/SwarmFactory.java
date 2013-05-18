package yarangi.game.harmonium.enemies.swarm;

import yar.quadraturin.Scene;
import yar.quadraturin.objects.IBehavior;
import yar.quadraturin.objects.behaviors.FSMBehavior;
import yar.quadraturin.objects.behaviors.IBehaviorCondition;
import yar.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.game.harmonium.battle.MazeInterface;
import yarangi.math.Angles;
import yarangi.numbers.RandomUtil;

public class SwarmFactory 
{
	public static int N = 10;
	
	public static double agentEnginePower = 0.01;

	public static Swarm createSwarm(int worldSize, final Scene scene, int nodes)
	{
		
		final Swarm swarm = new Swarm (worldSize, 8, scene);
		
/*		PickingSensor <IEntity> sensor = new PickingSensor<IEntity>( null );
		double r , a;
		for(int i = 1; i <= nodes; i ++)
		{
			do {
				r = RandomUtil.getRandomDouble(100)+400;
				a = RandomUtil.getRandomDouble(Angles.PI_2);
				sensor.clear();
//				System.out.println(sensor.getObject());
				scene.getWorldVeil().getTerrain().query( sensor,
						new AABB(r*Math.cos(a), r*Math.sin(a), 1, 0));
			}
			while(sensor.getObject() != null);
			swarm.addSpawnNode(r*Math.cos(a), r*Math.sin(a));
		}*/
		for(int i = 1; i <= nodes; i ++)
		{
			double r = RandomUtil.getRandomDouble(100)+400;
			double a = RandomUtil.getRandomDouble(Angles.TAU);
			swarm.addSpawnNode(r*Math.cos(a), r*Math.sin(a));
		}
		
		return swarm;
	}
	
//	};
	public static IBehavior <Swarm> createDefaultBehavior(MazeInterface maze)
	{
//		swarm.setArea(new Point(0, 0));
		final IBehaviorState<Swarm> rotating = new RotatingBehavior();
		final IBehaviorState<Swarm> pathing = new PathingBehavior(3);
		final IBehaviorState<Swarm> spawning = new SwarmSpawningBehavior(maze, Swarm.SPAWNING_INTERVAL);
		final IBehaviorState<Swarm> shifting = new ShiftBehavior();
		
		FSMBehavior <Swarm> behavior = new FSMBehavior <Swarm> (shifting);
		behavior.link(shifting, new IBehaviorCondition<Swarm>() {
			@Override
			public IBehaviorState<Swarm> nextState(Swarm entity) 
			{
				if(RandomUtil.oneOf(3))
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
		
		return behavior;
	}
	

}
