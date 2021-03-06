package yarangi.game.harmonium.temple.weapons;

import yar.quadraturin.objects.Entity;
import yarangi.game.harmonium.ai.weapons.IFeedbackBeacon;
import yarangi.game.harmonium.ai.weapons.IFeedbackCarrier;
import yarangi.game.harmonium.battle.Damage;
import yarangi.game.harmonium.battle.IDamager;
import yarangi.game.harmonium.battle.ITemple;
import yarangi.math.Vector2D;

public class Projectile extends Entity implements IFeedbackCarrier, IDamager
{

	private static final long serialVersionUID = -2909463886504124942L;

	private Damage damage;
	
	private double range;
	
	protected double maxRange;
	
//	public Vector2D velocity;
	
	protected Vector2D impactPoint;
	
	protected IFeedbackBeacon beacon;
	
	public Projectile(Vector2D v, WeaponProperties props) 
	{
		super();
		this.range = 0;
		
		this.maxRange = props.getProjectileRange();
		this.damage = props.getDamage();
	}
	public Damage getDamage() { return damage; }
	public double getMaxRange() { return maxRange; }
	public double getRangeSquare() { return range; }
	
	public boolean addRange(double dr)
	{
		range += dr;
		if(range >= maxRange)
			return true;
		
		return false;
	}

	public Vector2D getImpactPoint() { return impactPoint; }
	
	public IFeedbackBeacon getFeedback() { return beacon; }
	public void setFeedback(IFeedbackBeacon beacon) { this.beacon = beacon; }


}
