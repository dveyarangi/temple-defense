package yarangi.game.temple.model.weapons;

import yarangi.math.Vector2D;

public class LightningBeam extends Projectile 
{

	private static final long serialVersionUID = -5431860093856478823L;

	protected Vector2D curr;
	protected LightningBeam prev;
	
	protected double age;
	
	private double absSpeed;
	
	public LightningBeam(double x, double y, double a, Vector2D s, WeaponProperties props, LightningBeam prev) {
		super(x, y, a, s, props);
		absSpeed = props.getProjectileSpeed();

		this.prev = prev;
		this.curr = new Vector2D(x,y);
	}
	
	public double getAbsSpeed() { return absSpeed; }
}
