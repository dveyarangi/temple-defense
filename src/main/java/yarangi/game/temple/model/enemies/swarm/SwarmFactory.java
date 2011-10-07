package yarangi.game.temple.model.enemies.swarm;

import yarangi.game.temple.model.Damage;
import yarangi.game.temple.model.EffectUtils;
import yarangi.game.temple.model.enemies.swarm.agents.SwarmAgent;
import yarangi.game.temple.model.temple.TempleEntity;
import yarangi.game.temple.model.terrain.Matter;
import yarangi.game.temple.model.weapons.Projectile;
import yarangi.graphics.colors.Color;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.objects.behaviors.FSMBehavior;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorCondition;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.graphics.quadraturin.simulations.ICollisionHandler;
import yarangi.graphics.quadraturin.simulations.IPhysicalObject;
import yarangi.graphics.quadraturin.terrain.Tile;
import yarangi.math.Angles;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.Point;

public class SwarmFactory 
{
	public static int N = 10;
	
	public static double agentEnginePower = 0.01;
	private final static Damage MATTER_DAMAGE = new Damage(12, 0, 0, 0);
	
	public static Swarm createSwarm(int worldSize, final Scene scene, int nodes)
	{
		
		final Swarm swarm = new Swarm (worldSize, scene);
		swarm.setArea(new Point(0, 0));
		final IBehaviorState<Swarm> rotating = new RotatingBehavior();
		final IBehaviorState<Swarm> pathing = new PathingBehavior(1);
		final IBehaviorState<Swarm> spawning = new SpawningBehavior(15);
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
		
		swarm.setBehavior(behavior);
		
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
		
		
		ICollisionHandler <SwarmAgent> agentCollider = new ICollisionHandler <SwarmAgent> (){

			@Override
			public boolean setImpactWith(SwarmAgent source, IPhysicalObject target)
			{
				if(target instanceof Projectile)
					{
						Projectile p = (Projectile) target;
						
						swarm.setDanger(source, source.getIntegrity().hit(p.getDamage()));
						
						if(source.getIntegrity().getHitPoints() <= 0)
						{
							source.markDead();
							EffectUtils.makeExplosion(source.getArea().getRefPoint(), scene.getWorldVeil(), new Color(0,1,0,1), 32);
							return true;
						}

					}
					else
					if(target instanceof TempleEntity)
					{
						source.markDead();
						EffectUtils.makeExplosion(source.getArea().getRefPoint(), scene.getWorldVeil(), new Color(1,0,0,1), 64);
						return true;
					}
					else
					if( target instanceof Tile || target instanceof Matter)
					{
//						terrain.consume( source.getArea().getRefPoint().x(), 
//								source.getArea().getRefPoint().y(), 10 );
//						swarm.setUnpassable(target.getArea().getRefPoint().x(), target.getArea().getRefPoint().y());
						
						swarm.setDanger(source, source.getIntegrity().hit(MATTER_DAMAGE));
//						if(source.getIntegrity().getHitPoints() <= 0)
						{
							source.markDead();
							EffectUtils.makeExplosion(source.getArea().getRefPoint(), scene.getWorldVeil(), new Color(0,1,0,1), 32);
							return true;
						}
					}
				
					return false;
				}

			};	
			scene.getCollisionManager().registerHandler(SwarmAgent.class, agentCollider);


		return swarm;
	}
	
//	};
	
}
