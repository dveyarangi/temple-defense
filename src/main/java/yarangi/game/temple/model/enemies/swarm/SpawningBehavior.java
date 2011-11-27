package yarangi.game.temple.model.enemies.swarm;

import yarangi.game.temple.model.Integrity;
import yarangi.game.temple.model.enemies.MetaCircleLook;
import yarangi.game.temple.model.enemies.swarm.agents.DroneBehavior;
import yarangi.game.temple.model.enemies.swarm.agents.SwarmAgent;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.Look;
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
	public static final Integrity AGENT_INTEGRITY = new Integrity(10, 0, new double [] {0,0,0,0});
	public static final double AGENT_HEALTH = AGENT_INTEGRITY.getMaxHitPoints();
	
	public Look agentLook = new MetaCircleLook();
	
//	public Integrity integrity = new Integrity(30, 0, new double [] {0,0,0,0});
	public SpawningBehavior(double spawnInterval)
	{
		
		this.spawnInterval = spawnInterval;

	}
	@Override
	public double behave(double time, final Swarm swarm, boolean isVisible) 
	{
		timeToSpawn -= time;
		if(timeToSpawn > 0)
		{
			return 0;
		}
//		System.out.println(timeToSpawn);
		while(timeToSpawn < 0)
		{
			timeToSpawn += spawnInterval;
			double angle = RandomUtil.getRandomDouble(Angles.PI_2);
	//			double radius = RandomUtil.getRandomGaussian(800, 0);
			double size = RandomUtil.getRandomDouble(1)+1;
			final SwarmAgent agent = new SwarmAgent(swarm, new Integrity(10, 0, new double [] {0,0,0,0}), false);
			Vector2D source = swarm.getSource();
			agent.setLook(agentLook);
			agent.setBehavior(createAgentBehavior());
	//		System.out.println("spawning agent at " + swarm.getArea().getRefPoint());
			agent.setArea(new AABB(source.x() + RandomUtil.getRandomDouble(SPAWNING_RADIUS*2)-SPAWNING_RADIUS, 
								   source.y() + RandomUtil.getRandomDouble(SPAWNING_RADIUS*2)-SPAWNING_RADIUS, size*4, angle));
			agent.setSensor(new Sensor(10, 5, new AgentSensorFilter(), true));
			agent.setBody(new Body());
			agent.getBody().setMaxSpeed( size);
			swarm.addAgent(agent);
//			System.out.println("Agent spawn at " + agent.getArea().getRefPoint());
		}
		return 0;
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
//		final IBehaviorState<SwarmAgent> dangerState = new DangerBehavior();
		
		FSMBehavior <SwarmAgent> beh = new FSMBehavior<SwarmAgent>(droneState);
		
		beh.link(droneState.getId(), new IBehaviorCondition<SwarmAgent>()
		{
			@Override public IBehaviorState<SwarmAgent> nextState(SwarmAgent entity) {
				return droneState; // TODO: always the same for now
			}
		});
		
		return beh;
	}
	public int getId() { return this.getClass().hashCode(); }

}
