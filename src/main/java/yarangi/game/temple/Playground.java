package yarangi.game.temple;

import javax.media.opengl.GL;

import yarangi.game.temple.ai.IntellectCore;
import yarangi.game.temple.ai.NetCore;
import yarangi.game.temple.controllers.ControlBehavior;
import yarangi.game.temple.controllers.ControlLook;
import yarangi.game.temple.controllers.TempleController;
import yarangi.game.temple.controllers.bots.BotInterface;
import yarangi.game.temple.model.EffectUtils;
import yarangi.game.temple.model.enemies.swarm.Swarm;
import yarangi.game.temple.model.enemies.swarm.SwarmDebugOverlay;
import yarangi.game.temple.model.enemies.swarm.SwarmFactory;
import yarangi.game.temple.model.enemies.swarm.agents.SwarmAgent;
import yarangi.game.temple.model.temple.BattleInterface;
import yarangi.game.temple.model.temple.ObserverBehavior;
import yarangi.game.temple.model.temple.Shield;
import yarangi.game.temple.model.temple.ShieldBehavior;
import yarangi.game.temple.model.temple.ShieldLook;
import yarangi.game.temple.model.temple.ShieldSensor;
import yarangi.game.temple.model.temple.StructureInterface;
import yarangi.game.temple.model.temple.TempleEntity;
import yarangi.game.temple.model.temple.TempleLook;
import yarangi.game.temple.model.temple.bots.Bot;
import yarangi.game.temple.model.temple.bots.BotFactory;
import yarangi.game.temple.model.temple.debug.ShieldDebugLook;
import yarangi.game.temple.model.terrain.Matter;
import yarangi.game.temple.model.terrain.Tile;
import yarangi.game.temple.model.weapons.Minigun;
import yarangi.game.temple.model.weapons.MinigunGlowingLook;
import yarangi.game.temple.model.weapons.Projectile;
import yarangi.game.temple.model.weapons.TrackingBehavior;
import yarangi.game.temple.model.weapons.Weapon;
import yarangi.graphics.colors.Color;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.QuadVoices;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.config.EkranConfig;
import yarangi.graphics.quadraturin.config.SceneConfig;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.graphics.quadraturin.objects.Dummy;
import yarangi.graphics.quadraturin.objects.EntityShell;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.Sensor;
import yarangi.graphics.quadraturin.simulations.Body;
import yarangi.graphics.quadraturin.simulations.ICollisionHandler;
import yarangi.graphics.quadraturin.simulations.IPhysicalObject;
import yarangi.graphics.quadraturin.ui.Direction;
import yarangi.graphics.quadraturin.ui.Insets;
import yarangi.graphics.quadraturin.ui.Overlay;
import yarangi.graphics.quadraturin.ui.Panel;
import yarangi.graphics.quadraturin.ui.PanelLook;
import yarangi.math.Angles;
import yarangi.spatial.AABB;
import yarangi.spatial.Circle;
import yarangi.spatial.ISpatialFilter;
import yarangi.spatial.Point;

public class Playground extends Scene
{
	
	private TempleEntity temple;
	
//	private BackgroundEntity background;
	
//	private int worldWidth = 1000;
//	private int worldHeight = 1000;
	
	private EntityShell <Swarm> swarmShell;
	private EntityShell <Swarm> debugSwarmShell;
	private boolean debugSwarm = false;
	
	private IntellectCore core;
	
	public Playground(SceneConfig sceneConfig, EkranConfig ekranConfig, QuadVoices voices)
	{
		super(sceneConfig, ekranConfig, voices);
		
		createUI();
		
//		BackgroundEntity background = new BackgroundEntity(100, 100, 50);		
//		addEntity(background);

//		PowerGrid grid = new PowerGrid(this.getWorldVeil());
		
		core = new NetCore("netcore", this.getWorldLayer().getWidth(), this.getWorldLayer().getHeight());
//		addEntity(new BubbleSwarm(getWorldVeil(), temple));
		TempleController controller = new TempleController(this, core);
		
//		TempleControlProps c = new TempleControlProps();
		controller.setArea(new Point(0,0));
		controller.setLook(new ControlLook());
		controller.setBehavior(new ControlBehavior());
		addEntity(controller);
		StructureInterface structure = controller.getStructureInterface();
		
		temple = new TempleEntity(this, controller);
		controller.setTemple( temple );
		temple.setLook(new TempleLook( ));
		temple.setBehavior(new ObserverBehavior(0,512,null,0));
		temple.setSensor(new Sensor(512, 3, null, true));
		temple.setArea(AABB.createSquare(0,0,10, 0));
		temple.setBody(new Body());
		addEntity(temple);
		structure.addServiceable( temple );

		BattleInterface bi = controller.getBattleInterface();
		
		float maxCannons = 9;
		for(int a = 0; a < maxCannons; a ++)
		{
			Weapon weapon = new Minigun(bi, a%2 == 0 ? Minigun.PROPS1 : Minigun.PROPS2);
			weapon.setArea(AABB.createSquare((100+ a%3*100)*Math.cos(Angles.PI_2/maxCannons *a), (100+ a%3*100)*Math.sin(Angles.PI_2/maxCannons * a ),5,0));
			weapon.setLook(new MinigunGlowingLook());
//			weapon.setLook(new MinigunLook());
			weapon.setBehavior(new TrackingBehavior());
			weapon.setSensor( new Sensor(128, 3, 
					new ISpatialFilter <IEntity> () {

						@Override public boolean accept(IEntity entity) { return entity.getLook() != null && entity.getLook().isCastsShadow(); }}, 
					false)  );
			addEntity(weapon);
			bi.addFireable(weapon);
			structure.addServiceable( weapon );
			
/*			Shield shield = new Shield(bi, weapon.getPort());
			shield.setArea(new Circle((100+ a%3*100)*Math.cos(Angles.PI_2/maxCannons *a), (100+ a%3*100)*Math.sin(Angles.PI_2/maxCannons * a ),100));
			shield.setLook(new ShieldLook());
//			shield.setLook(new ShieldDebugLook());
//			shield.setSensor(new ShieldSensor(shield));
			shield.setBehavior(new ShieldBehavior());
			shield.setBody( new Body() );
			addEntity(shield);*/
		}
		int maxShields = 3;
		for(int a = 0; a < maxShields; a ++)
		{

//			structure.addServiceable( weapon );
		}

		
/*		for(int i = 0; i < 6; i ++)
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
			
		}*/
		
		
		
//		addEntity(CrystalFactory.generateStar(100, 100, 100));
		
//		KolbasaFactory.generateKolbasaMaze( this );
		
		Swarm swarm = SwarmFactory.createSwarm(sceneConfig.getWidth(), this, 5);
		Behavior <Swarm> swarmBehavior = SwarmFactory.createDefaultBehavior();
		swarmShell = new EntityShell<Swarm>( swarm, swarmBehavior, Dummy.<Swarm>LOOK() );
		addEntity(swarmShell);
		
		SwarmDebugOverlay swarmDebugLook = new SwarmDebugOverlay();
		debugSwarmShell = new EntityShell<Swarm>( swarm, swarmBehavior, swarmDebugLook );
		
		ICollisionHandler<Projectile> projectileCollider = new ICollisionHandler <Projectile> ()
		{

			@Override
			public boolean setImpactWith(Projectile source, IPhysicalObject target)
			{
				if( target instanceof Tile || target instanceof Matter || target instanceof TempleEntity)
				{
					source.markDead();
					EffectUtils.makeExplosion( source.getArea().getRefPoint(), Playground.this.getWorldLayer(), new Color(1,0,0,0), 4 );
					return true;
				}
				if(target instanceof SwarmAgent)
				{
					source.markDead();
					EffectUtils.makeExplosion( source.getArea().getRefPoint(), Playground.this.getWorldLayer(), new Color(0,1,0,0), 4 );
					return true;
				}
				
				return false;
			}
			
		};
		getCollisionManager().registerHandler( Projectile.class, projectileCollider );
		
				
		
		final BotInterface botInterface = controller.getBotInterface();
		
		for(int i = 0; i < 9; i ++)
		{
			Bot bot = BotFactory.createBot( controller );
			addEntity( bot );
			botInterface.add(bot);
		}


		ICollisionHandler<TempleEntity> templeCollider = new ICollisionHandler <TempleEntity> ()
		{

			@Override
			public boolean setImpactWith(TempleEntity source, IPhysicalObject target)
			{
				if( target instanceof SwarmAgent)
				{
//					botInterface.changeTransferRate(-0.5);
					EffectUtils.makeExplosion( source.getArea().getRefPoint(), Playground.this.getWorldLayer(), new Color(1,0,0,0), 4 );
					return true;
				}
				
				return false;
			}
			
		};
		getCollisionManager().registerHandler( TempleEntity.class, templeCollider );
		
		
		ICollisionHandler<Shield> shieldCollider = new ICollisionHandler <Shield> ()
		{

			@Override
			public boolean setImpactWith(Shield source, IPhysicalObject target)
			{
				if( target instanceof SwarmAgent)
				{
					if(!source.getExcludedSegments().covers(Math.atan2( target.getArea().getRefPoint().y()-source.getArea().getRefPoint().y(), 
																	  target.getArea().getRefPoint().x()-source.getArea().getRefPoint().x())))
					{
						EffectUtils.makeExplosion( source.getArea().getRefPoint(), Playground.this.getWorldLayer(), new Color(1,0,0,0), 4 );
						((SwarmAgent) target).markDead();
						return true;
					}
				}
				
				return false;
			}
			
		};
		getCollisionManager().registerHandler( Shield.class, shieldCollider );
		
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
	
	public void toggleSwarmOverlay()
	{
		if(debugSwarm)
		{
			this.removeEntity( debugSwarmShell );
			this.addEntity(swarmShell);
			debugSwarm = false;
		}
		else
		{
			this.removeEntity(swarmShell);
			this.addEntity( debugSwarmShell );
			debugSwarm = true;
		}
//		System.out.println("debug swarm: " + debugSwarm);
	}
	
	
	public void createUI() {
		Panel [] mainPanels = this.getUILayer().getBasePanel().split( 
				new int [] {20, 60, 20 }, Direction.HORIZONTAL );
		
		Panel [] leftPanels = mainPanels[0].split( new int [] {10, 90 }, Direction.VERTICAL );
		
		Overlay panel1 = new Overlay(leftPanels[0], true);
		leftPanels[0].setInsets( new Insets(5,5,5,5));
		panel1.setLook( new PanelLook( new Color(0.1f, 0.3f, 0.3f, 0.7f) ) );
//		Overlay panel2 = new Overlay(leftPanels[1], true);
//		leftPanels[1].setInsets( new Insets(5,5,5,5));
//		panel2.setLook( new PanelLook( new Color(0.1f, 0.3f, 0.3f, 0.7f) ) );
		
//		Overlay panel3 = new Overlay(mainPanels[2], true);
//		mainPanels[2].setInsets( new Insets(5,5,5,5));
//		panel3.setLook( new PanelLook( new Color(0.1f, 0.3f, 0.3f, 0.7f) ) );
		
		this.getUILayer().addEntity( panel1 );
//		this.getUILayer().addEntity( panel2 );
//		this.getUILayer().addEntity( panel3 );
	}
}
