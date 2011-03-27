package yarangi.game.spacefight.model.weapons;

import static yarangi.math.Angles.toDegrees;
import yarangi.game.spacefight.controllers.BattleInterface;
import yarangi.game.spacefight.model.temple.platforms.WeaponPlatform;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.math.Angles;
import yarangi.math.Vector2D;

public class WeaponBehavior implements Behavior <Weapon> 
{


	public boolean behave(double time, Weapon weapon, boolean isVisible) 
	{
		WeaponPlatform platform = weapon.getPlatform();
		BattleInterface bif = platform.getBattleInterface();
		
		Vector2D trackingPoint = bif.getTrackingPoint(weapon);
		
		double targetAngle;
		if ( trackingPoint == null)
			targetAngle = 0;
		else
//		double absoluteAngle = /*weapon.getA() + */;
			targetAngle = toDegrees(Math.atan2(trackingPoint.y-weapon.getAABB().getY(), 
											   trackingPoint.x-weapon.getAABB().getX())) 
									- platform.getAABB().getA() - bif.getAbsoluteAngle();
		
		double newA = Angles.stepTo(weapon.getAABB().getA(), targetAngle, weapon.getWeaponProperties().getCannonTrackingSpeed());
		
		double window = weapon.getWeaponProperties().getCannonTrackingHalfWidth();
		if(newA > window)
			newA = window;
		if(newA < -window)
			newA = -window;
		weapon.setA( newA );
		weapon.getAABB().a = newA;
//		weapon.setA(absoluteAngle);
		
		// just in case we are reloading:
		weapon.advanceReloading(time);
		
		return false;
	}

}
