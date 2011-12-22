package yarangi.game.harmonium.enemies.swarm;

import yarangi.game.harmonium.battle.Damage;
import yarangi.game.harmonium.battle.Damageable;
import yarangi.game.harmonium.battle.ITemple;
import yarangi.game.harmonium.enemies.swarm.agents.Seeder;
import yarangi.game.harmonium.enemies.swarm.agents.SwarmAgent;
import yarangi.game.harmonium.temple.EnergyCore;
import yarangi.game.harmonium.temple.weapons.Projectile;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.graphics.quadraturin.objects.behaviors.FSMBehavior;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorCondition;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.graphics.quadraturin.simulations.ICollisionHandler;
import yarangi.graphics.quadraturin.simulations.IPhysicalObject;
import yarangi.graphics.quadraturin.terrain.Bitmap;
import yarangi.graphics.quadraturin.terrain.GridyTerrainMap;
import yarangi.math.Angles;
import yarangi.numbers.RandomUtil;

public class SwarmFactory 
{
	public static int N = 10;
	
	public static double agentEnginePower = 0.01;
	private final static Damage MATTER_DAMAGE = new Damage(12, 0, 0, 0);

	
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
			double a = RandomUtil.getRandomDouble(Angles.PI_2);
			swarm.addSpawnNode(r*Math.cos(a), r*Math.sin(a));
		}
		
		final GridyTerrainMap terrain = (GridyTerrainMap)scene.getWorldLayer().<Bitmap>getTerrain();
		
		scene.getCollisionManager().registerHandler(SwarmAgent.class, new AgentCollisionHandler<SwarmAgent>(swarm));
		scene.getCollisionManager().registerHandler(Seeder.class, new AgentCollisionHandler<Seeder>(swarm));


		return swarm;
	}
	
//	};
	public static Behavior <Swarm> createDefaultBehavior(GridyTerrainMap terrain)
	{
//		swarm.setArea(new Point(0, 0));
		final IBehaviorState<Swarm> rotating = new RotatingBehavior();
		final IBehaviorState<Swarm> pathing = new PathingBehavior(3);
		final IBehaviorState<Swarm> spawning = new SpawningBehavior(terrain, 3);
		final IBehaviorState<Swarm> shifting = new ShiftBehavior();
		
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
		
		return behavior;
	}
	
	static class AgentCollisionHandler <E extends SwarmAgent> implements ICollisionHandler <E>
	{
		private Swarm swarm;
		public AgentCollisionHandler(Swarm swarm)
		{
			this.swarm = swarm; 
		}
		@Override
		public boolean setImpactWith(E source, IPhysicalObject target)
		{

				if(target instanceof Damageable && target instanceof ITemple)
				{
					((Damageable) target).hit( new Damage(0, 0, 0.01, 0) );
					source.hit( new Damage(0.05, 0, 0, 0) );
//					source.markDead();
//					EffectUtils.makeExplosion(source.getArea().getRefPoint(), scene.getWorldLayer(), new Color(1,0,0,1), 64);
					return true;
				}
				/*				else
				if( target instanceof Bitmap || target instanceof Matter)
				{
					terrain.query( new ConsumingSensor(terrain, false,
							source.getArea().getRefPoint().x(), source.getArea().getRefPoint().y(), 30*source.getArea().getMaxRadius() ), 
							AABB.createSquare(source.getArea().getRefPoint().x(), 
									source.getArea().getRefPoint().y(), 
									30*source.getArea().getMaxRadius(), 0));
//					swarm.setUnpassable(target.getArea().getRefPoint().x(), target.getArea().getRefPoint().y());
					
					swarm.setDanger(source, source.getIntegrity().hit(MATTER_DAMAGE));
//					if(source.getIntegrity().getHitPoints() <= 0)
					{
						source.markDead();
						EffectUtils.makeExplosion(source.getArea().getRefPoint(), scene.getWorldLayer(), new Color(0,1,0,1), 32);
						return true;
					}
				}*/
			
				return false;
			}

		};	

}
