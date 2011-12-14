package yarangi.game.harmonium.temple.weapons;

import yarangi.graphics.quadraturin.simulations.Body;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.AABB;
import yarangi.spatial.Area;

public class WeaponFactory
{
	public static Projectile createProjectile(Weapon weapon)
	{
		Area area = weapon.getArea();
		Vector2D location = weapon.getArea().getRefPoint();
		
		
		double fireAngle = RandomUtil.getRandomGaussian(
				area.getOrientation(), weapon.getProps().getProjectileTrajectoryAccuracy());

		double speedScalar = weapon.getProps().getProjectileSpeed();
	
		SimpleProjectileBehavior shellBehavior = new SimpleProjectileBehavior(weapon.getBattleInterface());
		

		Vector2D speed = Vector2D.POLAR(speedScalar, area.getOrientation());
		Projectile shell = new Projectile( speed, weapon.getProps());
		shell.setLook(new MinigunBurstLook(weapon.getPort(), weapon.getProps()));
		shell.setBehavior(shellBehavior);
		shell.setArea(AABB.createSquare(location.x(), location.y(), weapon.getProps().getProjectileHitRadius(), fireAngle));
		shell.setBody(new Body());
		shell.getBody().setMaxSpeed(speedScalar);
		shell.getBody().addVelocity(speed.x(), speed.y());
//		System.out.println("proj velocity " + v + " : " + getVelocity()+ " (" + a + ")");
		shell.getBody().setMass(0.001);
		return shell;
	}

}
