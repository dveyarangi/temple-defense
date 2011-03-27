package yarangi.game.spacefight.controllers;

import yarangi.game.spacefight.model.temple.Platform;
import yarangi.game.spacefight.model.weapons.Weapon;
import yarangi.math.Vector2D;

public interface BattleInterface 
{
	public Vector2D getTrackingPoint(Weapon weapon);
	
	public double getAbsoluteAngle();
	
	public Vector2D toWorldCoordinates(Platform platform, double x, double y);
//	public Vector2D getAbsoluteLocation(double x, double y);
	
	public double getTempleRadius();
}
