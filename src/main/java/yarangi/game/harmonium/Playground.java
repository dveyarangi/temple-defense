package yarangi.game.harmonium;

import yar.quadraturin.IRenderingContext;
import yar.quadraturin.QVoices;
import yar.quadraturin.Scene;
import yar.quadraturin.actions.ActionController;
import yar.quadraturin.actions.DefaultActionFactory;
import yar.quadraturin.config.EkranConfig;
import yar.quadraturin.config.SceneConfig;
import yar.quadraturin.objects.Dummy;
import yar.quadraturin.objects.EntityShell;
import yar.quadraturin.objects.IBehavior;
import yar.quadraturin.objects.IBeing;
import yar.quadraturin.objects.Sensor;
import yarangi.game.harmonium.ai.economy.IOrderScheduler;
import yarangi.game.harmonium.ai.weapons.IntellectCore;
import yarangi.game.harmonium.ai.weapons.NetCore;
import yarangi.game.harmonium.battle.ImpactFactory;
import yarangi.game.harmonium.battle.MazeInterface;
import yarangi.game.harmonium.battle.PolyMazeInterface;
import yarangi.game.harmonium.controllers.ControlBehavior;
import yarangi.game.harmonium.controllers.ControlLook;
import yarangi.game.harmonium.controllers.OrdersActionController;
import yarangi.game.harmonium.controllers.OrdersActionLook;
import yarangi.game.harmonium.controllers.TempleController;
import yarangi.game.harmonium.enemies.swarm.Swarm;
import yarangi.game.harmonium.enemies.swarm.SwarmDebugOverlay;
import yarangi.game.harmonium.enemies.swarm.SwarmFactory;
import yarangi.game.harmonium.environment.resources.Port;
import yarangi.game.harmonium.environment.resources.Resource;
import yarangi.game.harmonium.temple.BattleInterface;
import yarangi.game.harmonium.temple.EnergyCore;
import yarangi.game.harmonium.temple.ObserverBehavior;
import yarangi.game.harmonium.temple.ServiceInterface;
import yarangi.game.harmonium.temple.TempleLook;
import yarangi.game.harmonium.temple.bots.Bot;
import yarangi.game.harmonium.temple.bots.BotFactory;
import yarangi.game.harmonium.temple.harvester.Harvester;
import yarangi.game.harmonium.temple.harvester.HarvesterFactory;
import yarangi.game.harmonium.temple.harvester.Waller;
import yarangi.game.harmonium.temple.weapons.Minigun;
import yarangi.game.harmonium.temple.weapons.MinigunGlowingLook;
import yarangi.game.harmonium.temple.weapons.TrackingBehavior;
import yarangi.game.harmonium.temple.weapons.Weapon;
import yarangi.game.harmonium.temple.weapons.WeaponFactory;
import yarangi.game.harmonium.temple.weapons.WeaponProperties;
import yarangi.math.Angles;
import yarangi.physics.Body;
import yarangi.spatial.AABB;

public class Playground extends Scene
{
	
	private EnergyCore temple;
	
//	private BackgroundEntity background;
	
//	private int worldWidth = 1000;
//	private int worldHeight = 1000;
	
	private EntityShell <Swarm> swarmShell;
	private EntityShell <Swarm> debugSwarmShell;
	private boolean debugSwarm = false;
	int swarmSize;
	
	private IntellectCore core;
	
	private OrdersActionController actionController;
	
	private MazeInterface mazeInterface;
	
	public Playground(SceneConfig sceneConfig, EkranConfig ekranConfig, QVoices voices)
	{
		super(sceneConfig, ekranConfig, voices);

		this.swarmSize = sceneConfig.getWidth();
		
	}
	
	@Override
	public void postAnimate(double time) 
	{
		if(mazeInterface != null) // TODO: remove
			mazeInterface.grow( time );
	}
	
	@Override
	public void init()
	{
		super.init();
		createUI();
		
//		EntityCenter.init(this);
		
		
		
//		BackgroundEntity background = new BackgroundEntity(100, 100, 50);		
//		addEntity(background);

//		PowerGrid grid = new PowerGrid(this.getWorldVeil());
//		final ITileMap terrain = (ITileMap)getWorldLayer().getTerrain();
		
		mazeInterface = new PolyMazeInterface(getWorldLayer().getTerrain());
		
		
		actionController = new OrdersActionController(this, mazeInterface);
		DefaultActionFactory.appendNavActions(this, actionController);
		Debug.appendDebugActions( actionController.getActions(), this );
		
		// TODO: control modes
		this.setActionController(actionController);
		
		// renderer for actions:
		EntityShell <ActionController> actionControllerShell = 
				new EntityShell<ActionController>( actionController, null, new OrdersActionLook(actionController) );
		
		addEntity( actionControllerShell );

		
		// loading cannon's neural network:
		core = new NetCore("netcore", this.getWorldLayer().getWidth(), this.getWorldLayer().getHeight());

		// creating temple
		temple = new EnergyCore(this);
		TempleController controller = new TempleController(this, core, temple);
		temple.setLook(new TempleLook( 512 ));
		temple.setBehavior(new ObserverBehavior(controller));
		temple.setEntitySensor(new Sensor<IBeing>(512, 3, null));
		temple.setArea(AABB.createSquare(0,0,3, 0));
		temple.setBody(new Body(1,0));
		temple.setHealth( 1 );
		addEntity( temple );
		//structure.addServiceable( temple );

		
//		TempleControlProps c = new TempleControlProps();
		controller.setArea(AABB.createPoint( 0,0 ));
		controller.setLook(new ControlLook());
		controller.setBehavior(new ControlBehavior());
		addEntity(controller);
		ServiceInterface structure = controller.getStructureInterface();
		
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
			double radius = (70+ a%2*70);
//			AABB area = AABB.createSquare(RandomUtil.R( 400 )-200, RandomUtil.R( 400 )-200,1,0);
			AABB area = AABB.createSquare(radius*Math.cos(Angles.TAU/maxCannons *a), radius*Math.sin(Angles.TAU/maxCannons * a ),1,0);
			Weapon weapon = new Minigun(bi, area, props);
//			weapon.setLook(new MinigunLook());
			weapon.setBehavior(new TrackingBehavior());
			weapon.setEntitySensor( WeaponFactory.createSensor(weapon));
			weapon.setLook(new MinigunGlowingLook((int)props.getSensorRange()));
			addEntity( weapon );
			bi.addFireable(weapon);
			structure.addServiceable( weapon );
			
			// harvester location is linked to cannon:
			Harvester harvester = HarvesterFactory.createHarvester(area, weapon.getPort(), 64, mazeInterface);
			addEntity(harvester);
//			Port port = Port.createEmptyPort();
			
//			AABB area = AABB.createSquare(RandomUtil.R( 400 )-200, RandomUtil.R( 400 )-200,2,0);
//			Harvester harvester = HarvesterFactory.createHarvester(area, port, 64, mazeInterface);
//			addEntity(harvester);			
			
//			structure.addServiceable( harvester );

			
/*			Shield shield = new Shield(bi, weapon.getPort());
			shield.setArea(new Circle((100+ a%3*100)*Math.cos(Angles.PI_2/maxCannons *a), (100+ a%3*100)*Math.sin(Angles.PI_2/maxCannons * a ),100));
			shield.setLook(new ShieldLook());
//			shield.setLook(new ShieldDebugLook());
//			shield.setSensor(new ShieldSensor(shield));
			shield.setBehavior(new ShieldBehavior());
			shield.setBody( new Body() );
			addEntity(shield);*/
		}
		
		int maxWallers = 6;
		for(int a = 0; a < maxWallers; a ++) {
			Port port = new Port();
			
			
			port.setCapacity( Resource.Type.MATTER, 100, 100 );
			double radius = (170+ a%2*70);
//			AABB area = AABB.createSquare(RandomUtil.R( 400 )-200, RandomUtil.R( 400 )-200,1,0);
			AABB area = AABB.createSquare(radius*Math.cos(Angles.TAU/maxCannons *a), radius*Math.sin(Angles.TAU/maxCannons * a ),1,0);
//			AABB area = AABB.createSquare(RandomUtil.R( 400 )-200, RandomUtil.R( 400 )-200,2,0);
			Waller waller = HarvesterFactory.createWaller(area, port, 64, mazeInterface, actionController.getReinforcementMap());
			addEntity(waller);

		}
		int maxHarvs = 3;
/*		for(int a = 0; a < maxHarvs; a ++)
		{
			double radius = 100;
			Port port = Port.createEmptyPort();
			
			AABB area = AABB.createSquare(RandomUtil.R( 400 )-200, RandomUtil.R( 400 )-200,2,0);
			Harvester harvester = HarvesterFactory.createHarvester(area, port, 64, terrain);
			addEntity(harvester);			
			
			structure.addServiceable( harvester );
		}*/
		
//		int maxHarvs = 9;
/*		for(int a = 0; a < maxHarvs; a ++)
		{
			double radius = (a % 3+1) * 80;
		}*/
		final IOrderScheduler botInterface = controller.getOrderScheduler();
		
		for(int i = 0; i < 30; i ++)
		{
			Bot bot = BotFactory.createBot( temple, botInterface );
			addEntity( bot );
			botInterface.add(bot);
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
		
		Swarm swarm = SwarmFactory.createSwarm(swarmSize, this, 3);
		IBehavior <Swarm> swarmBehavior = SwarmFactory.createDefaultBehavior(mazeInterface);
		swarmShell = new EntityShell<Swarm>( swarm, swarmBehavior, Dummy.<Swarm>LOOK() );
		addEntity(swarmShell);
		
		SwarmDebugOverlay swarmDebugLook = new SwarmDebugOverlay(false, true, swarm);
		debugSwarmShell = new EntityShell<Swarm>( swarm, swarmBehavior, swarmDebugLook );
//		addEntity(debugSwarmShell);
		
		ImpactFactory impactFactory = new ImpactFactory( this, mazeInterface );
	}
	
	@Override
	public void destroy()
	{
		super.destroy();
		core.shutdown();
	}
	
	@Override
	public void preRender( IRenderingContext context )
	{
//		context.gl().glClearColor(1.0f,1.0f, 1.0f, 1.0f);
//		context.gl().glCle
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
/*		Panel [] mainPanels = this.getUILayer().getBasePanel().split( 
				new int [] {20, 60, 20 }, Direction.HORIZONTAL );
		
		Panel [] leftPanels = mainPanels[0].split( new int [] {10, 90 }, Direction.VERTICAL );
		
		Color color = new Color(0.3f, 0.5f, 0.9f, 0.2f);
		
		Overlay panel1 = new Overlay(leftPanels[0], true);
		leftPanels[0].setInsets( new Insets(5,5,5,5));
		panel1.setLook( new PanelLook( color ) );
		Overlay panel2 = new Overlay(leftPanels[1], true);
		leftPanels[1].setInsets( new Insets(5,5,5,5));
		panel2.setLook( new PanelLook( color ) );
		
		Overlay panel3 = new Overlay(mainPanels[2], true);
		mainPanels[2].setInsets( new Insets(5,5,5,5));
		panel3.setLook( new PanelLook( color ) );
		
		this.getUILayer().addEntity( panel1 );
		this.getUILayer().addEntity( panel2 );
		this.getUILayer().addEntity( panel3 );*/
	}


}
