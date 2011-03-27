package yarangi.game.spacefight.model.weapons;

import yarangi.math.Vector2D;

public class FlakShell extends Projectile 
{

	private static final long serialVersionUID = 5706030916443249716L;
	public static final int EXPLOSION_SPEED = 1;

	public double explosionRangeSquare;
	
	public Vector2D [] frags;
	public Vector2D [] speeds;

	public FlakShell(double x, double y, double a, Vector2D s, double explosionRangeSquare, WeaponProperties props) {
		super(x, y, a, s, props);
		
		this.explosionRangeSquare = explosionRangeSquare;
		this.maxRange = explosionRangeSquare + 150;
	}
	
	public boolean isAtExplosionRange() 
	{ 
		return getRangeSquare() > explosionRangeSquare;
	}
	
	public void setFrags(Vector2D [] frags) { this.frags = frags; } 
	
	public Vector2D [] getFragLocations() { return frags; }
	public Vector2D [] getFragSpeeds() { return speeds; }

	public void setSpeeds(Vector2D[] fragSpeeds) {
		this.speeds = fragSpeeds;
	}

	public double getExplosionRange() { return explosionRangeSquare; }

}
