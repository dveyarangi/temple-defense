package yarangi.game.temple.model.enemies.swarm;

import yarangi.game.temple.model.Integrity;
import yarangi.game.temple.model.enemies.MetaCircleLook;
import yarangi.game.temple.model.enemies.swarm.agents.DroneBehavior;
import yarangi.game.temple.model.enemies.swarm.agents.Seeder;
import yarangi.game.temple.model.enemies.swarm.agents.SeederBehavior;
import yarangi.game.temple.model.enemies.swarm.agents.SeederLook;
import yarangi.game.temple.model.enemies.swarm.agents.SwarmAgent;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.graphics.quadraturin.objects.Sensor;
import yarangi.graphics.quadraturin.objects.behaviors.FSMBehavior;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorCondition;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.graphics.quadraturin.simulations.Body;
import yarangi.graphics.quadraturin.terrain.GridyTerrainMap;
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
	private GridyTerrainMap terrain;
	
//	public Integrity integrity = new Integrity(30, 0, new double [] {0,0,0,0});
	public SpawningBehavior(GridyTerrainMap terrain, double spawnInterval)
	{
		
		this.terrain = terrain;
		this.spawnInterval = spawnInterval;

	}
	@Override
	public double behave(double time, final Swarm swarm) 
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
			Vector2D source = swarm.getSource();
			final SwarmAgent agent = new SwarmAgent(swarm, new Integrity(10, 0, new double [] {0,0,0,0}), false);
			agent.setLook(agentLook);
			agent.setBehavior(createAgentBehavior());
	//		System.out.println("spawning agent at " + swarm.getArea().getRefPoint());
			agent.setArea(AABB.createSquare(source.x() + RandomUtil.getRandomDouble(SPAWNING_RADIUS*2)-SPAWNING_RADIUS, 
								   source.y() + RandomUtil.getRandomDouble(SPAWNING_RADIUS*2)-SPAWNING_RADIUS, size*4, angle));
			agent.setSensor(new Sensor(10, 5, new AgentSensorFilter(), true));
			agent.setBody(new Body());
			agent.getBody().setMaxSpeed( size/2);
			swarm.addAgent(agent);
//			System.out.println("Agent spawn at " + agent.getArea().getRefPoint());
			
			if(RandomUtil.oneOf( 1 )) {
				Seeder seeder = new Seeder(swarm, new Integrity(10, 0, new double [] {0,0,0,0}), 
									AABB.createSquare(source.x() + RandomUtil.getRandomDouble(SPAWNING_RADIUS*2)-SPAWNING_RADIUS, 
													  source.y() + RandomUtil.getRandomDouble(SPAWNING_RADIUS*2)-SPAWNING_RADIUS, size*4, angle));
				
				seeder.setBehavior(createSeederBehavior());
				seeder.setLook(new SeederLook());
				seeder.setBody(new Body());
				seeder.getBody().setMaxSpeed( size/2 );
				
				swarm.addAgent(seeder);
			}
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
		final IBehaviorState<SwarmAgent> droneState = new DroneBehavior(1);
//		final IBehaviorState<SwarmAgent> dangerState = new DangerBehavior();
		
		FSMBehavior <SwarmAgent> beh = new FSMBehavior<SwarmAgent>(droneState);
		
		beh.link(droneState.getId(), new IBehaviorCondition<SwarmAgent>()
		{
			@Override public IBehaviorState<SwarmAgent> nextState(SwarmAgent entity) {
				return droneState;
			}
		});
		
		return beh;
	}
	
	public Behavior <Seeder> createSeederBehavior()
	{
		final IBehaviorState<Seeder> seederState = new SeederBehavior(terrain);
//		final IBehaviorState<SwarmAgent> dangerState = new DangerBehavior();
		
		FSMBehavior <Seeder> beh = new FSMBehavior<Seeder>(seederState);
		
		beh.link(seederState.getId(), new IBehaviorCondition<Seeder>()
		{
			@Override public IBehaviorState<Seeder> nextState(Seeder entity) {
				return seederState;
			}
		});
		
		return beh;
	}
	public int getId() { return this.getClass().hashCode(); }

}
