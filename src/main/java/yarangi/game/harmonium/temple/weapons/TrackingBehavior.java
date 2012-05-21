package yarangi.game.harmonium.temple.weapons;

import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.math.Vector2D;

public class TrackingBehavior implements Behavior <Weapon> 
{


	public boolean behave(double time, Weapon weapon, boolean isVisible) 
	{
//		weapon.getTrackPoint();
		weapon.updateState(time);
		Vector2D trackingPoint = weapon.getBattleInterface().acquireTrackPoint(weapon);
		if(trackingPoint == null)
			return true;
//		System.out.println(trackPoint.getAngle());
//		double newA = Angles.stepTo(weapon.getAABB().getA(), targetAngle, weapon.getWeaponProperties().getCannonTrackingSpeed());
		double newA = trackingPoint.minus(weapon.getArea().getAnchor()).getAngle();
/*		double window = weapon.getWeaponProperties().getCannonTrackingHalfWidth();
		if(newA > window)
			newA = window;
		if(newA < -window)
			newA = -window;*/
//		System.out.println(trackPoint.getAngle());
		weapon.getArea().setOrientation( newA );
//		weapon.getArea().setOrientation(Angles.toRadians(targetAngle));
//		weapon.setA(absoluteAngle);
		
		// just in case we are reloading:
		weapon.advanceReloading(time);
		
		return true;
	}

}
