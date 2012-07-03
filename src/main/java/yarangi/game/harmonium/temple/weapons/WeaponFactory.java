package yarangi.game.harmonium.temple.weapons;

import yarangi.game.harmonium.battle.IEnemy;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.ISensor;
import yarangi.graphics.quadraturin.objects.Sensor;
import yarangi.math.BitUtils;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;
import yarangi.physics.Body;
import yarangi.spatial.AABB;
import yarangi.spatial.Area;
import yarangi.spatial.ISpatialFilter;

public class WeaponFactory
{
	public static Projectile createProjectile(Weapon weapon, double velocity)
	{
		Area area = weapon.getArea();
		Vector2D location = weapon.getArea().getAnchor();
		
		
		double fireAngle = RandomUtil.STD(
				area.getOrientation(), weapon.getProps().getProjectileTrajectoryAccuracy());
	
		SimpleProjectileBehavior shellBehavior = new SimpleProjectileBehavior();
		

		Vector2D speed = Vector2D.POLAR(velocity, area.getOrientation());
		Projectile shell = new Projectile( speed, weapon.getProps());
		shell.setLook(new MinigunBurstLook(weapon.getPort(), weapon.getProps()));
		shell.setBehavior(shellBehavior);
		shell.setArea(AABB.createSquare(location.x(), location.y(), weapon.getProps().getProjectileHitRadius(), fireAngle));
		shell.setBody(new Body());
		shell.getBody().setMaxSpeed(velocity);
		shell.getBody().addVelocity(speed.x(), speed.y());
//		System.out.println("proj velocity " + v + " : " + getVelocity()+ " (" + a + ")");
		shell.getBody().setMass(0.001);
		return shell;
	}
	

	public static ISpatialFilter <IEntity> ENEMY_SENSOR = new ISpatialFilter <IEntity> () {

		@Override public boolean accept(IEntity entity) 
		{ 
			return (entity instanceof IEnemy);
		}
		
	};
	

	public static ISensor<?> createSensor(Weapon weapon)
	{
		return new Sensor(BitUtils.po2Ceiling( (int)weapon.getProps().getSensorRange()), 3, ENEMY_SENSOR, false);
	}

}
