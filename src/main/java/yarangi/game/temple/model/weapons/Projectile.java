package yarangi.game.temple.model.weapons;

import yarangi.game.temple.ai.IFeedbackBeacon;
import yarangi.game.temple.ai.IFeedbackCarrier;
import yarangi.game.temple.model.Damage;
import yarangi.game.temple.model.Damageable;
import yarangi.graphics.quadraturin.objects.NewtonialSceneEntity;
import yarangi.graphics.quadraturin.simulations.IPhysicalObject;
import yarangi.math.Vector2D;
import yarangi.spatial.AABB;

public class Projectile extends NewtonialSceneEntity implements IFeedbackCarrier
{

	private static final long serialVersionUID = -2909463886504124942L;

	private Damage damage;
	
	private double range;
	
	protected double maxRange;
	
//	public Vector2D velocity;
	
	protected Vector2D impactPoint;
	
	protected IFeedbackBeacon beacon;
	
	public Projectile(double x, double y, double a, Vector2D v, WeaponProperties props) 
	{
		super(new AABB(x, y, props.getProjectileHitRadius(), a));
		this.range = 0;
		this.maxRange = props.getProjectileRange();
		this.damage = props.getDamage();
		setVelocity(v.x, v.y);
		setMass(0.001);
	}
	public Damage getDamage() { return damage; }
	public double getMaxRange() { return maxRange; }
	public double getRangeSquare() { return range; }
	
	public boolean addRangeSquare(double dr)
	{
		range += dr;
		if(range >= maxRange)
			return true;
		
		return false;
	}
	
	public boolean isCollidable() { return true; }
	
	public boolean isPickable() { return false; }

	public void setImpactWith(IPhysicalObject e) 
	{
		
		if(e instanceof Damageable)
		{
			// TODO: impact something.
			impactPoint = new Vector2D(e.getArea().getRefPoint());
			((Damageable)e).hit(this.getDamage());
		}
		
	}
	
	public Vector2D getImpactPoint() { return impactPoint; }
	
	public IFeedbackBeacon getFeedback() { return beacon; }
	public void setFeedback(IFeedbackBeacon beacon) { this.beacon = beacon; }
}
