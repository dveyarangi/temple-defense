package yarangi.game.harmonium.battle;

import yar.quadraturin.Scene;
import yar.quadraturin.graphics.colors.Color;
import yar.quadraturin.simulations.ICollisionHandler;
import yar.quadraturin.terrain.ITerrain;
import yarangi.game.harmonium.enemies.swarm.agents.Seeder;
import yarangi.game.harmonium.enemies.swarm.agents.SwarmAgent;
import yarangi.game.harmonium.environment.terrain.ErrosionSeed;
import yarangi.game.harmonium.temple.shields.Shield;
import yarangi.game.harmonium.temple.weapons.Projectile;
import yarangi.physics.IPhysicalObject;

/**
 * Collision handlers registrar
 * @author dveyarangi
 *
 */
public class ImpactFactory
{
	private final static Damage MATTER_DAMAGE = new Damage( 0, 0, 0, 0.5 );
	
	private final MazeInterface maze;
	
	private final Scene scene;

	@SuppressWarnings("unchecked")
	public ImpactFactory(Scene scene, MazeInterface maze)
	{
		
		this.scene = scene;
		this.maze = maze;
		
		scene.getCollisionManager().registerHandler( IEnemy.class, new EnemyCollisionHandler() );
		scene.getCollisionManager().registerHandler( SwarmAgent.class, new EnemyCollisionHandler() );
		scene.getCollisionManager().registerHandler( Seeder.class, new EnemyCollisionHandler() );
		scene.getCollisionManager().registerHandler( Projectile.class, new ProjectileCollider() );
		scene.getCollisionManager().registerHandler( Shield.class, new ShieldCollider() );
	}

	/**
	 * Impact feedback for enemy entity.
	 * @author dveyarangi
	 *
	 */
	public class EnemyCollisionHandler implements ICollisionHandler<IEnemy>
	{

		ErrosionSeed corrosiveSeed = new ErrosionSeed(2);
		@Override
		public boolean setImpactWith(IEnemy source, IPhysicalObject target)
		{
			if ( target instanceof IDamageable && !(target instanceof IEnemy))
			{
				((IDamageable) target).hit( new Damage( source.getArea().getMaxRadius(), 0, 0, 0 ) );
				source.hit( new Damage( 2, 0, 0, 0 ) );
				// source.markDead();
				// EffectUtils.makeExplosion(source.getArea().getRefPoint(),
				// scene.getWorldLayer(), new Color(1,0,0,1), 64);
				return true;
			} 
			else if ( target instanceof ITerrain )
			{
				collide( source, target );

				return true;
			}
			return false;

		}
		
		private boolean collide(IEnemy source, IPhysicalObject target)
		{
			corrosiveSeed.setLocation( source.getArea().getAnchor().x(), source.getArea().getAnchor().y() );
			maze.seed( 0, corrosiveSeed );
			boolean hit = corrosiveSeed.consumeHit();
			if ( hit )
			{
				source.hit( MATTER_DAMAGE );
				// swarm.setUnpassable(target.getArea().getRefPoint().x(),
			}
			
			return hit;
		}
	}
	
	/**
	 * Impact feedback for weapon projectile
	 * @author dveyarangi
	 *
	 */
	class ProjectileCollider implements ICollisionHandler <Projectile>
	{

		@Override
		public boolean setImpactWith(Projectile source, IPhysicalObject target)
		{
			if( target instanceof ITerrain)
			{
//				System.out.println("projectile collided with wall");
				source.markDead();
				EffectUtils.makeExplosion( source.getArea().getAnchor(), scene.getWorldLayer(), new Color(1,0,0,0), 4 );
				return true;
			}
			if(target instanceof IEnemy)
			{
				((SwarmAgent) target).hit( source.getDamage() );
				EffectUtils.makeExplosion( source.getArea().getAnchor(), scene.getWorldLayer(), new Color(1,0,0,1), 4 );
				source.markDead();
				return true;
			}
			
			return false;
		}
		
	};

	
	class ShieldCollider implements ICollisionHandler <Shield>
	{

		@Override
		public boolean setImpactWith(Shield source, IPhysicalObject target)
		{
			if( target instanceof SwarmAgent)
			{
				if(!source.getExcludedSegments().covers(Math.atan2( target.getArea().getAnchor().y()-source.getArea().getAnchor().y(), 
																  target.getArea().getAnchor().x()-source.getArea().getAnchor().x())))
				{
					EffectUtils.makeExplosion( source.getArea().getAnchor(), scene.getWorldLayer(), new Color(1,0,0,0), 4 );
					((SwarmAgent) target).markDead();
					return true;
				}
			}
			
			return false;
		}
		
	}
	
}
