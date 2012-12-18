package yarangi.game.harmonium.battle;

import com.seisw.util.geom.PolyDefault;

import yarangi.game.harmonium.enemies.swarm.agents.Seeder;
import yarangi.game.harmonium.enemies.swarm.agents.SwarmAgent;
import yarangi.game.harmonium.temple.shields.Shield;
import yarangi.game.harmonium.temple.weapons.Projectile;
import yarangi.graphics.colors.Color;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.simulations.ICollisionHandler;
import yarangi.graphics.quadraturin.terrain.ITerrain;
import yarangi.graphics.quadraturin.terrain.PolygonTerrainMap;
import yarangi.physics.IPhysicalObject;
import yarangi.spatial.Area;

/**
 * Collision handlers registrar
 * @author dveyarangi
 *
 */
public class ImpactFactory
{
	private final static Damage MATTER_DAMAGE = new Damage( 0, 0, 0, 0.5 );
	
	private final PolygonTerrainMap terrain;
	
	private final Scene scene;

	public ImpactFactory(Scene scene, PolygonTerrainMap terrain)
	{
		
		this.scene = scene;
		this.terrain = terrain;
		
		
		
		scene.getCollisionManager().registerHandler(IEnemy.class, new EnemyCollisionHandler());
		scene.getCollisionManager().registerHandler(SwarmAgent.class, new EnemyCollisionHandler());
		scene.getCollisionManager().registerHandler(Seeder.class, new EnemyCollisionHandler());
//		scene.getCollisionManager().registerHandler(IEnemy.class, new EnemyCollisionHandler());
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

		private final PolyDefault transposedPoly = new PolyDefault();
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
				Area area = source.getArea();
				transposedPoly.clear();
				for(int pidx = 0; pidx < source.getErrosionPoly().getNumPoints(); pidx ++)
					transposedPoly.add(area.getAnchor().x() + source.getErrosionPoly().getX( pidx ), area.getAnchor().y() + source.getErrosionPoly().getY( pidx ));
				boolean hit = terrain.apply( area.getAnchor().x(), area.getAnchor().y(), 2*area.getMaxRadius(), 2*area.getMaxRadius(), true,
						transposedPoly);
				// swarm.setUnpassable(target.getArea().getRefPoint().x(),
				// target.getArea().getRefPoint().y());
				// System.out.println(hit);wwwwwwwwww
				if ( hit )
				{
					source.hit( MATTER_DAMAGE );
				}

				return true;
			}
			return false;

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
