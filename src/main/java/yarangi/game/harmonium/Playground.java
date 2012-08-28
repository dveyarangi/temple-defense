package yarangi.game.harmonium;

import javax.media.opengl.GL;

import yarangi.game.harmonium.ai.weapons.IntellectCore;
import yarangi.game.harmonium.ai.weapons.NetCore;
import yarangi.game.harmonium.battle.Damageable;
import yarangi.game.harmonium.battle.EffectUtils;
import yarangi.game.harmonium.battle.EntityCenter;
import yarangi.game.harmonium.battle.IEnemy;
import yarangi.game.harmonium.controllers.ControlBehavior;
import yarangi.game.harmonium.controllers.ControlLook;
import yarangi.game.harmonium.controllers.OrdersActionController;
import yarangi.game.harmonium.controllers.TempleController;
import yarangi.game.harmonium.enemies.swarm.Swarm;
import yarangi.game.harmonium.enemies.swarm.SwarmFactory;
import yarangi.game.harmonium.enemies.swarm.agents.SwarmAgent;
import yarangi.game.harmonium.temple.BattleInterface;
import yarangi.game.harmonium.temple.EnergyCore;
import yarangi.game.harmonium.temple.ObserverBehavior;
import yarangi.game.harmonium.temple.Shield;
import yarangi.game.harmonium.temple.StructureInterface;
import yarangi.game.harmonium.temple.TempleLook;
import yarangi.game.harmonium.temple.harvester.HarvesterFactory;
import yarangi.game.harmonium.temple.harvester.Waller;
import yarangi.game.harmonium.temple.weapons.Minigun;
import yarangi.game.harmonium.temple.weapons.MinigunLook;
import yarangi.game.harmonium.temple.weapons.Projectile;
import yarangi.game.harmonium.temple.weapons.TrackingBehavior;
import yarangi.game.harmonium.temple.weapons.Weapon;
import yarangi.game.harmonium.temple.weapons.WeaponFactory;
import yarangi.game.harmonium.temple.weapons.WeaponProperties;
import yarangi.graphics.colors.Color;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.QVoices;
import yarangi.graphics.quadraturin.Scene;
import yarangi.graphics.quadraturin.config.EkranConfig;
import yarangi.graphics.quadraturin.config.SceneConfig;
import yarangi.graphics.quadraturin.objects.Dummy;
import yarangi.graphics.quadraturin.objects.EntityShell;
import yarangi.graphics.quadraturin.objects.IBehavior;
import yarangi.graphics.quadraturin.objects.Sensor;
import yarangi.graphics.quadraturin.simulations.ICollisionHandler;
import yarangi.graphics.quadraturin.terrain.ITerrain;
import yarangi.graphics.quadraturin.terrain.MultilayerTilePoly;
import yarangi.graphics.quadraturin.terrain.PolygonTerrainMap;
import yarangi.graphics.quadraturin.ui.Direction;
import yarangi.graphics.quadraturin.ui.Insets;
import yarangi.graphics.quadraturin.ui.Overlay;
import yarangi.graphics.quadraturin.ui.Panel;
import yarangi.graphics.quadraturin.ui.PanelLook;
import yarangi.math.Angles;
import yarangi.physics.Body;
import yarangi.physics.IPhysicalObject;
import yarangi.spatial.AABB;
import yarangi.spatial.PointArea;

public class Playground extends Scene
{
	
	private EnergyCore temple;
	
//	private BackgroundEntity background;
	
//	private int worldWidth = 1000;
//	private int worldHeight = 1000;
	
	private EntityShell <Swarm> swarmShell;
	private EntityShell <Swarm> debugSwarmShell;
	private boolean debugSwarm = false;
	
	private IntellectCore core;
	
	public Playground(SceneConfig sceneConfig, EkranConfig ekranConfig, QVoices voices)
	{
		super(sceneConfig, ekranConfig, voices);
		
		createUI();
		
		EntityCenter.init(this);
		
//		BackgroundEntity background = new BackgroundEntity(100, 100, 50);		
//		addEntity(background);

//		PowerGrid grid = new PowerGrid(this.getWorldVeil());
		final PolygonTerrainMap terrain = (PolygonTerrainMap)getWorldLayer().<MultilayerTilePoly> getTerrain();
		
		core = new NetCore("netcore", this.getWorldLayer().getWidth(), this.getWorldLayer().getHeight());
//		addEntity(new BubbleSwarm(getWorldVeil(), temple));
		
		temple = new EnergyCore(this);
		TempleController controller = new TempleController(this, core, temple);
		temple.setLook(new TempleLook( ));
		temple.setBehavior(new ObserverBehavior(controller));
		temple.setEntitySensor(new Sensor(512, 3, null));
		temple.setArea(AABB.createSquare(0,0,10, 0));
		temple.setBody(new Body());
		addEntity( temple );
//		structure.addServiceable( temple );

		
//		TempleControlProps c = new TempleControlProps();
		controller.setArea(new PointArea(0,0));
		controller.setLook(new ControlLook());
		controller.setBehavior(new ControlBehavior());
		addEntity(controller);
		StructureInterface structure = controller.getStructureInterface();
		
		BattleInterface bi = controller.getBattleInterface();
		
		float maxCannons = 6;
		for(int a = 0; a < maxCannons; a ++)
		{
			WeaponProperties props = Minigun.PROPS0;
/*			switch(a%3) {
			case 0: props = Minigun.PROPS0; break;
			case 1: props = Minigun.PROP_SMALL; break;
			case 2: props = Minigun.PROP_SMALL; break;
			}*/
			double radius = (70+ a%3*70);
//			AABB area = AABB.createSquare(RandomUtil.R( 400 )-200, RandomUtil.R( 400 )-200,1,0);
			AABB area = AABB.createSquare(radius*Math.cos(Angles.PI_2/maxCannons *a), radius*Math.sin(Angles.PI_2/maxCannons * a ),1,0);
			Weapon weapon = new Minigun(bi, area, props);
//			weapon.setLook(new MinigunGlowingLook());
			weapon.setLook(new MinigunLook());
			weapon.setBehavior(new TrackingBehavior());
			weapon.setEntitySensor( WeaponFactory.createSensor(weapon));
			addEntity( weapon );
			bi.addFireable(weapon);
			structure.addServiceable( weapon );
			
			// harvester location is linked to cannon:
//			Harvester harvester = HarvesterFactory.createHarvester(area, weapon.getPort(), 64, terrain);
//			addEntity(harvester);
			Waller waller = HarvesterFactory.createWaller(area, weapon.getPort(), 64, terrain, ((OrdersActionController)controller.getActionController()).getReinforcementMap());
			addEntity(waller);

			
/*			Shield shield = new Shield(bi, weapon.getPort());
			shield.setArea(new Circle((100+ a%3*100)*Math.cos(Angles.PI_2/maxCannons *a), (100+ a%3*100)*Math.sin(Angles.PI_2/maxCannons * a ),100));
			shield.setLook(new ShieldLook());
//			shield.setLook(new ShieldDebugLook());
//			shield.setSensor(new ShieldSensor(shield));
			shield.setBehavior(new ShieldBehavior());
			shield.setBody( new Body() );
			addEntity(shield);*/
		}
/*		int maxShields = 3;
		for(int a = 0; a < maxShields; a ++)
		{
			double radius = 100;
			Port port = Port.createEmptyPort();
			AABB area = AABB.createSquare(radius*Math.cos(Angles.PI_2/maxCannons *a), radius*Math.sin(Angles.PI_2/maxCannons * a ),6,0);
			structure.addServiceable( harvester );
		}*/
		
		int maxHarvs = 9;
/*		for(int a = 0; a < maxHarvs; a ++)
		{
			double radius = (a % 3+1) * 80;
		}
		final IOrderScheduler botInterface = controller.getOrderScheduler();
		
		for(int i = 0; i < 5; i ++)
		{
			Bot bot = BotFactory.createBot( temple, botInterface );
			addEntity( bot );
			botInterface.add(bot);
		}	*/	
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
		
		Swarm swarm = SwarmFactory.createSwarm(sceneConfig.getWidth(), this, 3);
		IBehavior <Swarm> swarmBehavior = SwarmFactory.
		
		createDefaultBehavior((PolygonTerrainMap)getWorldLayer().<MultilayerTilePoly>getTerrain());
		swarmShell = new EntityShell<Swarm>( swarm, swarmBehavior, Dummy.<Swarm>LOOK() );
		addEntity(swarmShell);
		
//		SwarmDebugOverlay swarmDebugLook = new SwarmDebugOverlay(swarm);
//		debugSwarmShell = new EntityShell<Swarm>( swarm, swarmBehavior, swarmDebugLook );
//		addEntity(debugSwarmShell);
		
		ICollisionHandler<Projectile> projectileCollider = new ICollisionHandler <Projectile> ()
		{

			@Override
			public boolean setImpactWith(Projectile source, IPhysicalObject target)
			{
				if( target instanceof ITerrain)
				{
//					System.out.println("projectile collided with wall");
					source.markDead();
					EffectUtils.makeExplosion( source.getArea().getAnchor(), Playground.this.getWorldLayer(), new Color(1,0,0,0), 4 );
					return true;
				}
				if(target instanceof Damageable && target instanceof IEnemy)
				{
					((SwarmAgent) target).hit( source.getDamage() );
					EffectUtils.makeExplosion( source.getArea().getAnchor(), Playground.this.getWorldLayer(), new Color(1,0,0,1), 4 );
					source.markDead();
					return true;
				}
				
				return false;
			}
			
		};

		getCollisionManager().registerHandler( Projectile.class, projectileCollider );
		
				
		

		
		ICollisionHandler<Shield> shieldCollider = new ICollisionHandler <Shield> ()
		{

			@Override
			public boolean setImpactWith(Shield source, IPhysicalObject target)
			{
				if( target instanceof SwarmAgent)
				{
					if(!source.getExcludedSegments().covers(Math.atan2( target.getArea().getAnchor().y()-source.getArea().getAnchor().y(), 
																	  target.getArea().getAnchor().x()-source.getArea().getAnchor().x())))
					{
						EffectUtils.makeExplosion( source.getArea().getAnchor(), Playground.this.getWorldLayer(), new Color(1,0,0,0), 4 );
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
	
	@Override
	public void init(GL gl, IRenderingContext context)
	{
		super.init(gl, context);
		gl.glClearColor(0.0f,0.0f, 0.0f, 1.0f);
	}
	
	@Override
	public void destroy(GL gl, IRenderingContext context)
	{
		super.destroy(gl, context);
		core.shutdown();
	}

	@Override
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

	@Override
	public void init()
	{
	}

	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub
		
	}
}
