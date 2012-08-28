package yarangi.game.harmonium.enemies.swarm;

import java.awt.geom.Point2D;

import yarangi.game.harmonium.battle.Damage;
import yarangi.game.harmonium.battle.Damageable;
import yarangi.game.harmonium.battle.IDamager;
import yarangi.game.harmonium.battle.ITemple;
import yarangi.game.harmonium.enemies.swarm.agents.Seeder;
import yarangi.game.harmonium.enemies.swarm.agents.SwarmAgent;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.objects.IBehavior;
import yarangi.graphics.quadraturin.objects.behaviors.FSMBehavior;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorCondition;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.graphics.quadraturin.simulations.ICollisionHandler;
import yarangi.graphics.quadraturin.terrain.ITerrain;
import yarangi.graphics.quadraturin.terrain.PolygonTerrainMap;
import yarangi.math.Angles;
import yarangi.numbers.RandomUtil;
import yarangi.physics.IPhysicalObject;

import com.seisw.util.geom.PolyDefault;

public class SwarmFactory 
{
	public static int N = 10;
	
	public static double agentEnginePower = 0.01;
	private final static Damage MATTER_DAMAGE = new Damage(0.5, 0, 0, 0);

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
		
		
		scene.getCollisionManager().registerHandler(SwarmAgent.class, new AgentCollisionHandler<SwarmAgent>(swarm));
		scene.getCollisionManager().registerHandler(Seeder.class, new AgentCollisionHandler<Seeder>(swarm));


		return swarm;
	}
	
//	};
	public static IBehavior <Swarm> createDefaultBehavior(PolygonTerrainMap terrain)
	{
//		swarm.setArea(new Point(0, 0));
		final IBehaviorState<Swarm> rotating = new RotatingBehavior();
		final IBehaviorState<Swarm> pathing = new PathingBehavior(3);
		final IBehaviorState<Swarm> spawning = new SpawningBehavior(terrain, Swarm.SPAWNING_INTERVAL);
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
	
	static class AgentCollisionHandler <E extends SwarmAgent> implements ICollisionHandler <E>
	{
		private final Swarm swarm;
		
		private double lastX, lastY;
		public AgentCollisionHandler(Swarm swarm)
		{
			this.swarm = swarm;
			
		}
		@Override
		public boolean setImpactWith(E source, IPhysicalObject target)
		{
				if(target instanceof Damageable && target instanceof ITemple)
				{
					((Damageable) target).hit( new Damage(source.getArea().getMaxRadius(), 0, 0, 0) );
					source.hit( new Damage(1000, 0, 0, 0) );
//					source.markDead();
//					EffectUtils.makeExplosion(source.getArea().getRefPoint(), scene.getWorldLayer(), new Color(1,0,0,1), 64);
					return true;
				}
				else
				if(target instanceof IDamager)
				{

//					source.markDead();
//					EffectUtils.makeExplosion(source.getArea().getRefPoint(), scene.getWorldLayer(), new Color(1,0,0,1), 64);
					return true;
				}
				else
				if( target instanceof ITerrain)
				{
					
					PolygonTerrainMap terrain = swarm.getTerrain();
					
					double atx = source.x();
					double aty = source.y();
					double size = 5*source.getArea().getMaxRadius();
					
					PolyDefault poly = new PolyDefault();
					for(double a = 0 ; a < Angles.PI_2; a += Angles.PI_div_3)
						poly.add( new Point2D.Double(atx + size * Math.cos( a ), aty + size * Math.sin( a )) );

					terrain.apply( atx, aty, size, size, true, poly );
//					swarm.setUnpassable(target.getArea().getRefPoint().x(), target.getArea().getRefPoint().y());
					
//					if( !(source instanceof Seeder) ) {
						double rawDamage = source.getIntegrity().hit(MATTER_DAMAGE);
						
						swarm.setDanger(source, 10*rawDamage);
						if(source.getIntegrity().getHitPoints() <= 0)
						{
							source.markDead();
	//						EffectUtils.makeExplosion(source.getArea().getRefPoint(), scene.getWorldLayer(), new Color(0,1,0,1), 32);
							return true;
						}
//					}
				}
			
				return false;
			}

		};	

}
