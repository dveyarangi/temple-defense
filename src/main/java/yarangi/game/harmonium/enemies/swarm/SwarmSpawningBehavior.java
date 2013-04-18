package yarangi.game.harmonium.enemies.swarm;

import yarangi.game.harmonium.battle.MazeInterface;
import yarangi.game.harmonium.battle.Integrity;
import yarangi.game.harmonium.enemies.ElementalVoidLook;
import yarangi.game.harmonium.enemies.EnemyFactory;
import yarangi.game.harmonium.enemies.swarm.agents.DroneBehavior;
import yarangi.game.harmonium.enemies.swarm.agents.Seeder;
import yarangi.game.harmonium.enemies.swarm.agents.SeederBehavior;
import yarangi.game.harmonium.enemies.swarm.agents.SeederLook;
import yarangi.game.harmonium.enemies.swarm.agents.SplitBehavior;
import yarangi.game.harmonium.enemies.swarm.agents.SwarmAgent;
import yarangi.graphics.quadraturin.objects.IBehavior;
import yarangi.graphics.quadraturin.objects.ILook;
import yarangi.graphics.quadraturin.objects.behaviors.FSMBehavior;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorCondition;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;
import yarangi.physics.Body;
import yarangi.spatial.AABB;

class SwarmSpawningBehavior implements IBehaviorState<Swarm> 
{
	
	private final double spawnInterval;
	private double timeToSpawn = 0;
	public static final int SPAWNING_RADIUS = 50;
	public static final Integrity AGENT_INTEGRITY = new Integrity(10, 0, new double [] {0,0,0,0});
	public static final double AGENT_HEALTH = AGENT_INTEGRITY.getMaxHitPoints();
	public static final double  AGENT_VELOCITY = 1;
	public ILook agentLook = /*new MetaCircleLook();//*/new ElementalVoidLook();
	public ILook seederLook = new SeederLook();
	private final MazeInterface maze;
	
	private static int getSpawnAmount() { return RandomUtil.N( 6 )+ 6; }
	
//	public Integrity integrity = new Integrity(30, 0, new double [] {0,0,0,0});
	public SwarmSpawningBehavior(MazeInterface maze, double spawnInterval)
	{
		
		this.maze = maze;
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
			for(int idx = 0; idx < getSpawnAmount(); idx ++) {
				double angle = RandomUtil.getRandomDouble(Angles.TAU);
		//			double radius = RandomUtil.getRandomGaussian(800, 0);
				double size = 1*Math.abs(RandomUtil.STD(0.5, 0.01))+0.1;
				
				double flavor = Math.abs(RandomUtil.STD(0.1, 0.01))+0.1;
				double health = 5;
				double mass = flavor;
				
				Vector2D source = swarm.getSource();
				double spawnX = source.x();// + RandomUtil.getRandomDouble(SPAWNING_RADIUS*2)-SPAWNING_RADIUS;
				double spawnY = source.y();// + RandomUtil.getRandomDouble(SPAWNING_RADIUS*2)-SPAWNING_RADIUS;
				
				SwarmAgent agent = null;
				switch(RandomUtil.N( 20 )) {
				case 0: case 1: case 2: case 3: case 4: case 5: case 6:
				case 7: case 8: case 9: case 10: case 11: case 12: case 13: case 14: 
					agent = new SwarmAgent(swarm, new Integrity(10*flavor, 0, new double [] {0,0,0,0}), flavor/100, 5*flavor);
					agent.setLook(agentLook);
					agent.setBehavior(createBoidBehavior());
		//			agent.setBehavior(new DroneBehavior( 1 ));
			//		System.out.println("spawning agent at " + swarm.getArea().getRefPoint());
					agent.setArea(AABB.createSquare(spawnX, spawnY, size, angle));
					agent.setEntitySensor(EnemyFactory.SHORT_SENSOR());
					agent.setBody(new Body(10*mass, AGENT_VELOCITY+RandomUtil.STD(0, 0.02)));
					break;
	//			System.out.println("Agent spawn at " + agent.getArea().getRefPoint());
				case 16: case 17: case 18: case 15:
					agent = new Seeder(swarm, 
											new Integrity(5*flavor, 0, new double [] {0,0,0,0.99}), 
											AABB.createSquare(spawnX, spawnY, 4*size, angle),
											flavor, 30*flavor);
					
					
					agent.setBehavior(createSeederBehavior(maze));
//					agent.setBehavior(new DroneBehavior(20));
					agent.setEntitySensor(EnemyFactory.SHORT_SENSOR());
					agent.setLook(seederLook);
					agent.setBody(new Body(mass, AGENT_VELOCITY+RandomUtil.STD(0, 0.01)));
					
					break;
	//				seeder.getBody().setMaxSpeed( 1 );
				case 19:
					agent = new SwarmAgent(swarm, new Integrity(100*flavor, 0, new double [] {0,0,0,0}), 2*flavor, flavor*10 );
					agent.setLook(agentLook);
					agent.setBehavior(createDroneBehavior());
		//			agent.setBehavior(new DroneBehavior( 1 ));
			//		System.out.println("spawning agent at " + swarm.getArea().getRefPoint());
					agent.setArea(AABB.createSquare(spawnX, spawnY, 2*size, angle));
					agent.setEntitySensor(EnemyFactory.SHORT_SENSOR());
					agent.setBody(new Body(10*mass, AGENT_VELOCITY+RandomUtil.STD(0, 0.01)));
					break;
				}
				if(agent != null)
					swarm.addAgent(agent);
			}
		}
		
		return 0;
	}
	
	
	private IBehavior<?> createDroneBehavior()
	{
		return new DroneBehavior( 0 );
	}
	public IBehavior <SwarmAgent> createBoidBehavior()
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
/*		beh.link(boidState.getId(), new IBehaviorCondition<SwarmAgent>()
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
				});*/
		
		return beh;
	}

	
	public IBehavior <Seeder> createSeederBehavior(MazeInterface maze)
	{
		
		
		final IBehaviorState<Seeder> seederState = new SeederBehavior(maze);
//		final IBehaviorState<SwarmAgent> dangerState = new DangerBehavior();
		final IBehaviorState<SwarmAgent> splitState = new SplitBehavior();
		
		FSMBehavior <Seeder> beh = new FSMBehavior<Seeder>(seederState);
		
		beh.link(seederState.getId(), new IBehaviorCondition<Seeder>()
		{
			@Override public IBehaviorState<Seeder> nextState(Seeder entity) {
				return seederState;
			}
		});
		
		return beh;
	}
	@Override
	public int getId() { return this.getClass().hashCode(); }

}
