package yarangi.game.harmonium.enemies.swarm;

import yarangi.game.harmonium.battle.Integrity;
import yarangi.game.harmonium.enemies.EnemyFactory;
import yarangi.game.harmonium.enemies.MetaCircleLook;
import yarangi.game.harmonium.enemies.swarm.agents.Seeder;
import yarangi.game.harmonium.enemies.swarm.agents.SeederBehavior;
import yarangi.game.harmonium.enemies.swarm.agents.SeederLook;
import yarangi.game.harmonium.enemies.swarm.agents.SwarmAgent;
import yarangi.game.harmonium.temple.bots.ChasingBehavior;
import yarangi.game.harmonium.temple.bots.SatelliteBehavior;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.graphics.quadraturin.objects.behaviors.FSMBehavior;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorCondition;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.graphics.quadraturin.simulations.Body;
import yarangi.graphics.quadraturin.terrain.GridyTerrainMap;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.AABB;

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
			double size = Math.abs(RandomUtil.getRandomGaussian(0.5, 0.2))+0.1;
			Vector2D source = swarm.getSource();
			final SwarmAgent agent = new SwarmAgent(swarm, new Integrity(100*size, 0, new double [] {0,0,0,0}), size/100000, 10*size);
			agent.setLook(agentLook);
			agent.setBehavior(createBoidBehavior());
	//		System.out.println("spawning agent at " + swarm.getArea().getRefPoint());
			agent.setArea(AABB.createSquare(source.x() + RandomUtil.getRandomDouble(SPAWNING_RADIUS*2)-SPAWNING_RADIUS, 
								   source.y() + RandomUtil.getRandomDouble(SPAWNING_RADIUS*2)-SPAWNING_RADIUS, size*4, angle));
			agent.setSensor(EnemyFactory.SHORT_SENSOR());
			agent.setBody(new Body(10*size+RandomUtil.getRandomGaussian(0, 1), 0.5+RandomUtil.getRandomGaussian(0, 0.1)));
			swarm.addAgent(agent);
//			System.out.println("Agent spawn at " + agent.getArea().getRefPoint());
			
			if(RandomUtil.oneOf( 4 )) {
				Seeder seeder = new Seeder(swarm, 
										new Integrity(20*size*10, 0, new double [] {0,0,0,0}), 
										AABB.createSquare(source.x() + RandomUtil.getRandomDouble(SPAWNING_RADIUS*2)-SPAWNING_RADIUS, 
													  source.y() + RandomUtil.getRandomDouble(SPAWNING_RADIUS*2)-SPAWNING_RADIUS, size*4, angle),
													  size*10, size*100);
				
				seeder.setBehavior(createSeederBehavior());
				seeder.setLook(new SeederLook());
				seeder.setBody(new Body(size, 0.5));
//				seeder.getBody().setMaxSpeed( 1 );
				
				swarm.addAgent(seeder);
			}
		}
		return 0;
	}
	
	
	public Behavior <SwarmAgent> createBoidBehavior()
	{
		final IBehaviorState<SwarmAgent> boidState = new BoidBehavior();
//		final IBehaviorState<SwarmAgent> attackState = new SatelliteBehavior();
		
		FSMBehavior <SwarmAgent> beh = new FSMBehavior<SwarmAgent>(boidState);
		
/*		beh.link(boidState.getId(), new IBehaviorCondition<SwarmAgent>()
		{
			@Override public IBehaviorState<SwarmAgent> nextState(SwarmAgent entity) {
				return boidState;
			}
		});*/
		beh.link(boidState.getId(), new IBehaviorCondition<SwarmAgent>()
				{
					@Override public IBehaviorState<SwarmAgent> nextState(SwarmAgent entity) {
						return new ChasingBehavior<SwarmAgent>(entity.getTarget().getArea(), 4);
					}
				});
		beh.link(ChasingBehavior.getStateId(), new IBehaviorCondition<SwarmAgent>()
				{
					@Override public IBehaviorState<SwarmAgent> nextState(SwarmAgent entity) {
						return new ChasingBehavior<SwarmAgent>(entity.getTarget().getArea(), 4);
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
