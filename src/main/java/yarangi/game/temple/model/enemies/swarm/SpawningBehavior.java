package yarangi.game.temple.model.enemies.swarm;

import yarangi.game.temple.model.Integrity;
import yarangi.game.temple.model.enemies.ElementalVoidLook;
import yarangi.game.temple.model.enemies.swarm.agents.DroneBehavior;
import yarangi.game.temple.model.enemies.swarm.agents.SwarmAgent;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.Sensor;
import yarangi.graphics.quadraturin.objects.behaviors.FSMBehavior;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorCondition;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.graphics.quadraturin.simulations.Body;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.AABB;
import yarangi.spatial.ISpatialFilter;

class SpawningBehavior implements IBehaviorState<Swarm> 
{
	
	private double spawnInterval;
	private double timeToSpawn = 0;
	public static final int SPAWNING_RADIUS = 50;
	
//	public Integrity integrity = new Integrity(30, 0, new double [] {0,0,0,0});
	public SpawningBehavior(double spawnInterval)
	{
		
		this.spawnInterval = spawnInterval;

	}
	@Override
	public boolean behave(double time, final Swarm swarm, boolean isVisible) 
	{
		timeToSpawn -= time;
		if(timeToSpawn > 0)
		{
			return false;
		}
		
		while(timeToSpawn < 0)
		{
			timeToSpawn += spawnInterval;
			double angle = RandomUtil.getRandomDouble(Angles.PI_2);
	//			double radius = RandomUtil.getRandomGaussian(800, 0);
			double size = RandomUtil.getRandomDouble(1)+1;
			final SwarmAgent agent = new SwarmAgent(swarm, new Integrity(10*size, 0, new double [] {0,0,0,0}), false);
			Vector2D source = swarm.getSource();
			agent.setLook(new ElementalVoidLook());
			agent.setBehavior(createAgentBehavior());
	//		System.out.println("spawning agent at " + swarm.getArea().getRefPoint());
			agent.setArea(new AABB(source.x() + RandomUtil.getRandomDouble(SPAWNING_RADIUS*2)-SPAWNING_RADIUS, 
								   source.y() + RandomUtil.getRandomDouble(SPAWNING_RADIUS*2)-SPAWNING_RADIUS, size*4, angle));
			agent.setSensor(new Sensor(10, 5, new AgentSensorFilter(), true));
			agent.setBody(new Body());
			agent.getBody().setMaxSpeed( size);
			swarm.addAgent(agent);
		}
		return false;
	}
	
	public class AgentSensorFilter implements ISpatialFilter <IEntity>
	{
		@Override
		public boolean accept(IEntity entity)
		{
			return entity instanceof SwarmAgent;
		}
		
	}
	
	public Behavior <SwarmAgent> createAgentBehavior()
	{
		final IBehaviorState<SwarmAgent> droneState = new DroneBehavior();
		
		FSMBehavior <SwarmAgent> beh = new FSMBehavior<SwarmAgent>(droneState);
		
		beh.link(droneState, new IBehaviorCondition<SwarmAgent>()
		{
			@Override public IBehaviorState<SwarmAgent> nextState(SwarmAgent entity) {
				return droneState; // TODO: always the same for now
			}
		});
		
		return beh;
	}

}
