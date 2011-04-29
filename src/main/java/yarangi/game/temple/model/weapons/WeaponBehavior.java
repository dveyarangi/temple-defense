package yarangi.game.temple.model.weapons;

import static yarangi.math.Angles.toDegrees;
import yarangi.game.temple.controllers.BattleInterface;
import yarangi.game.temple.model.temple.platforms.WeaponPlatform;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.math.Angles;
import yarangi.math.Vector2D;

public class WeaponBehavior implements Behavior <Weapon> 
{


	public boolean behave(double time, Weapon weapon, boolean isVisible) 
	{
		WeaponPlatform platform = weapon.getPlatform();
		BattleInterface bif = platform.getBattleInterface();
		
		Vector2D trackPoint = bif.acquireTrackPoint(weapon);
		if(trackPoint == null)
			return false;
		double targetAngle = toDegrees(trackPoint.getAngle()) 
									- platform.getAABB().getA() - bif.getAbsoluteAngle();

//		double newA = Angles.stepTo(weapon.getAABB().getA(), targetAngle, weapon.getWeaponProperties().getCannonTrackingSpeed());
		
//		double window = weapon.getWeaponProperties().getCannonTrackingHalfWidth();
//		if(newA > window)
//			newA = window;
//		if(newA < -window)
//			newA = -window;
		weapon.setA( targetAngle );
		weapon.getAABB().a = Angles.toRadians(targetAngle);
//		weapon.setA(absoluteAngle);
		
		// just in case we are reloading:
		weapon.advanceReloading(time);
		
		return false;
	}

}
