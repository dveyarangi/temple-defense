package yarangi.game.harmonium.temple.weapons;

import yar.quadraturin.IRenderingContext;
import yar.quadraturin.IVeil;
import yar.quadraturin.graphics.textures.TextureLook;
import yar.quadraturin.objects.IBeing;
import yar.quadraturin.objects.ISensor;
import yar.quadraturin.objects.Sensor;
import yarangi.game.harmonium.battle.IEnemy;
import yarangi.math.Angles;
import yarangi.math.BitUtils;
import yarangi.math.IVector2D;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;
import yarangi.physics.Body;
import yarangi.spatial.AABB;
import yarangi.spatial.Area;
import yarangi.spatial.ISpatialFilter;

public class WeaponFactory
{
	//new MinigunBurstLook(weapon.getPort(), weapon.getProps())
	private static TextureLook look = new TextureLook("/textures/red_gradient.jpg") { 
		
		
		private IVeil veil;
		
		@Override
		public void init(IRenderingContext ctx) {
			super.init( ctx );
			
			veil = null;//context.getPlugin( BlurVeil.NAME );
		}
		
		@Override
		public IVeil getVeil() { return veil; }
	};
//	private static MetaCircleLook look = new MetaCircleLook();
	
	public static Projectile createProjectile(Weapon weapon, double velocity)
	{
		Area area = weapon.getArea();
		IVector2D location = weapon.getArea().getAnchor();
		
		
		double fireAngle = RandomUtil.STD(
				area.getOrientation(), weapon.getProps().getProjectileTrajectoryAccuracy());
	
		SimpleProjectileBehavior shellBehavior = new SimpleProjectileBehavior();
		

		Vector2D speed = Vector2D.POLAR(velocity, area.getOrientation());
		Projectile shell = new Projectile( speed, weapon.getProps());
		shell.setLook(look);
		shell.setBehavior(shellBehavior);
		shell.setArea(AABB.createSquare(location.x(), location.y(), weapon.getProps().getProjectileHitRadius(), fireAngle*Angles.TO_DEG));
		shell.setBody(new Body(0.001, velocity));
		shell.getBody().addVelocity(speed.x(), speed.y());
//		System.out.println("proj velocity " + v + " : " + getVelocity()+ " (" + a + ")");
		return shell;
	}
	

	public static ISpatialFilter <IBeing> ENEMY_SENSOR = new ISpatialFilter <IBeing> () {

		@Override public boolean accept(IBeing entity) 
		{ 
			return (entity instanceof IEnemy);
		}
		
	};

	public static ISensor<?> createSensor(Weapon weapon)
	{
		return new Sensor(BitUtils.po2Ceiling( (int)weapon.getProps().getSensorRange()), 3, ENEMY_SENSOR);
	}

}
