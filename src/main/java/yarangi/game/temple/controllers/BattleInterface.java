package yarangi.game.temple.controllers;

import yarangi.game.temple.model.temple.Platform;
import yarangi.game.temple.model.temple.platforms.WeaponPlatform;
import yarangi.game.temple.model.weapons.Weapon;
import yarangi.math.Vector2D;

public interface BattleInterface 
{
	public Vector2D acquireTrackPoint(Weapon weapon); 
	
	public double getAbsoluteAngle();
	
	public Vector2D toWorldCoordinates(Platform platform, double x, double y);
//	public Vector2D getAbsoluteLocation(double x, double y);
	
	public double getTempleRadius();

//	public Vector2D getAbsuloteLocation(Platform platform);
}
