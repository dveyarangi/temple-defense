package yarangi.game.temple;

import javax.media.opengl.GL;

import yarangi.game.temple.ai.IntellectCore;
import yarangi.game.temple.ai.NetCore;
import yarangi.game.temple.controllers.ControlBehavior;
import yarangi.game.temple.controllers.ControlLook;
import yarangi.game.temple.controllers.TempleController;
import yarangi.game.temple.model.EffectUtils;
import yarangi.game.temple.model.enemies.swarm.Swarm;
import yarangi.game.temple.model.enemies.swarm.SwarmFactory;
import yarangi.game.temple.model.enemies.swarm.agents.SwarmAgent;
import yarangi.game.temple.model.temple.BattleInterface;
import yarangi.game.temple.model.temple.ObserverBehavior;
import yarangi.game.temple.model.temple.ObserverEntity;
import yarangi.game.temple.model.temple.ObserverLook;
import yarangi.game.temple.model.temple.StructureInterface;
import yarangi.game.temple.model.temple.TempleEntity;
import yarangi.game.temple.model.terrain.KolbasaFactory;
import yarangi.game.temple.model.terrain.Matter;
import yarangi.game.temple.model.weapons.Minigun;
import yarangi.game.temple.model.weapons.MinigunLook;
import yarangi.game.temple.model.weapons.Projectile;
import yarangi.game.temple.model.weapons.TrackingBehavior;
import yarangi.game.temple.model.weapons.Weapon;
import yarangi.graphics.colors.Color;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.QuadVoices;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.config.SceneConfig;
import yarangi.graphics.quadraturin.objects.Dummy;
import yarangi.graphics.quadraturin.objects.Sensor;
import yarangi.graphics.quadraturin.simulations.Body;
import yarangi.graphics.quadraturin.simulations.ICollisionHandler;
import yarangi.graphics.quadraturin.simulations.IPhysicalObject;
import yarangi.graphics.quadraturin.terrain.Tile;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.spatial.AABB;
import yarangi.spatial.Point;

public class Playground extends Scene
{
	
	private TempleEntity temple;
	
//	private BackgroundEntity background;
	
//	private int worldWidth = 1000;
//	private int worldHeight = 1000;
	
	public static int WORLD_SIZE = 1500;
	private IntellectCore core;
	public Playground(SceneConfig config, QuadVoices voices)
	{
		super(config, voices);
		
//		BackgroundEntity background = new BackgroundEntity(100, 100, 50);		
//		addEntity(background);

//		PowerGrid grid = new PowerGrid(this.getWorldVeil());
		
		core = new NetCore("netcore", this.getWorldVeil().getWidth(), this.getWorldVeil().getHeight());
//		addEntity(new BubbleSwarm(getWorldVeil(), temple));
		TempleController controller = new TempleController(this, core);
		
//		TempleControlProps c = new TempleControlProps();
		controller.setArea(new Point(0,0));
		controller.setLook(new ControlLook());
		controller.setBehavior(new ControlBehavior());
		addEntity(controller);
		StructureInterface structure = controller.getStructureInterface();
		
		temple = new TempleEntity(this, controller);
		temple.setLook(Dummy.LOOK);
		temple.setBehavior(Dummy.BEHAVIOR);
		temple.setArea(new AABB(0,0,10, 0));
		temple.setBody(new Body());
		addEntity(temple);
		structure.addServiceable( temple );
		
		voices.addCursorListener(controller);

		BattleInterface bi = controller.getBattleInterface();
		
		Weapon weapon = new Minigun(bi);
		weapon.setArea(new AABB(-150, 100,5,0));
		weapon.setLook(new MinigunLook());
		weapon.setBehavior(new TrackingBehavior());
		addEntity(weapon);
		bi.addFireable(weapon);
		structure.addServiceable( weapon );

		 weapon = new Minigun(bi);
		weapon.setArea(new AABB(-100, -100,5,0));
		weapon.setLook(new MinigunLook());
		weapon.setBehavior(new TrackingBehavior());
		addEntity(weapon);
		bi.addFireable(weapon);
		structure.addServiceable( weapon );

  	    weapon = new Minigun(bi);
		weapon.setArea(new AABB(50, 200,5,0));
		weapon.setLook(new MinigunLook());
		weapon.setBehavior(new TrackingBehavior());
		addEntity(weapon);
		bi.addFireable(weapon);
		structure.addServiceable( weapon );
/*		for(int i = 0; i < 20; i ++)
		{
			Bot bot = new Bot(structure);
			
			bot.setLook(new BotLook(10));
			bot.setBehavior(new BotBehavior());
			bot.setBody( new Body() );
			bot.setArea( new AABB(0,0,1,0) );
			addEntity( bot );
		}*/


		
		for(int i = 0; i < 6; i ++)
		{
			double angle = i * Angles.PI_div_3 + Angles.PI_div_6;
//			double angle = RandomUtil.getRandomDouble(Angles.PI_2);
			double radius = 350;//RandomUtil.getRandomGaussian(400, 100);
			ObserverEntity sensor2 = new ObserverEntity(controller);
			sensor2.setLook(new ObserverLook(new Color(0f, 0.7f, 1.0f, 1)));
			sensor2.setBehavior(new ObserverBehavior(angle, radius, new Vector2D(0,0), 0.001));
			sensor2.setArea(new AABB(radius*Math.cos(angle), radius*Math.sin(angle), 30, 0));
			sensor2.setSensor(new Sensor(256, 3, null, false));
			addEntity(sensor2);
//			grid.connect(temple.getStructure(), sensor2);
			
		}
		
		
		
//		addEntity(CrystalFactory.generateStar(100, 100, 100));
		
//		KolbasaFactory.generateKolbasaMaze( this );
		
		Swarm swarm = SwarmFactory.createSwarm(WORLD_SIZE, this, 5);
//		swarm.setLook(new SwarmDebugOverlay()); 
		swarm.setLook(Dummy.LOOK);
		addEntity(swarm);
		
		ICollisionHandler<Projectile> projectileCollider = new ICollisionHandler <Projectile> ()
		{

			@Override
			public boolean setImpactWith(Projectile source, IPhysicalObject target)
			{
				if( target instanceof Tile || target instanceof Matter || target instanceof TempleEntity)
				{
					source.markDead();
					EffectUtils.makeExplosion( source.getArea().getRefPoint(), Playground.this.getWorldVeil(), new Color(1,0,0,0), 4 );
					return true;
				}
				if(target instanceof SwarmAgent)
				{
					source.markDead();
					EffectUtils.makeExplosion( source.getArea().getRefPoint(), Playground.this.getWorldVeil(), new Color(0,1,0,0), 4 );
					return true;
				}
				
				return false;
			}
			
		};
		
		getCollisionManager().registerHandler( Projectile.class, projectileCollider );
		
/*		ICollisionHandler<TempleEntity> templeCollider = new ICollisionHandler <TempleEntity> ()
		{

			@Override
			public void setImpactWith(TempleEntity source, SceneEntity target)
			{
				if(target instanceof Matter)
					source.markDead();
	
//				System.out.println(target);
//				if(target instanceof SwarmAgent)
//				EffectUtils.makeExplosion( source.getArea().getRefPoint(), Playground.this.getWorldVeil(), , 32 );
				
			}
			
		};
		
		getCollisionManager().registerHandler( TempleEntity.class, templeCollider );*/
	}

/*	public void animate(double time)
	{
		super.animate(time);
		
		if(RandomUtil.oneOf(2))
		{
			double angle = RandomUtil.getRandomDouble(Angles.PI_2);
			double radius = RandomUtil.getRandomGaussian(800, 0);
			addEntity(new ElementalVoid(radius*Math.cos(angle), radius*Math.sin(angle), 0, RandomUtil.getRandomInt(1)+7, temple));
		}
	}*/
	
	public void init(GL gl, IRenderingContext context)
	{
		super.init(gl, context);
		gl.glClearColor(0.0f,0.0f, 0.0f, 0.0f);
	}
	
	public void destroy(GL gl, IRenderingContext context)
	{
		super.destroy(gl, context);
		core.shutdown();
	}

	public void preDisplay(GL gl, double time, boolean useNames)
	{
		super.preDisplay(gl, time, false);
	      gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	}
	
	public String toString() { return "playground scene"; }
}
