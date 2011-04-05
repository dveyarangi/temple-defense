package yarangi.game.temple.model;

public class Integrity 
{
	private double maxHitPoints;
	private double hitPoints;
	
	private double [] resistances;
	private double armor;
	
	
	public Integrity(double maxHitPoints, double [] resistances, double armor)
	{
		this.maxHitPoints = maxHitPoints;
		this.hitPoints = maxHitPoints;
		this.resistances = resistances;
		this.armor = armor;
	}
	
	public void hit(Damage damage)
	{
		
		for(int type : Damage.TYPES)
		{
//			System.out.println(damage);
			
			double dam = damage.getDamage(type) * (1-resistances[type]);
			if (dam > armor)
				hitPoints -= (dam - armor);
		}
		
		if ( this.hitPoints < 0 )
			this.hitPoints = 0;
	}
	
	public void recover(double hitPoints)
	{
		this.hitPoints += hitPoints;
		if (this.hitPoints > maxHitPoints )
			this.hitPoints = maxHitPoints;
	}

	public double getMaxHitPoints()
	{
		return maxHitPoints;
	}
	
	public double getHitPoints() {
		return hitPoints;
	}
}
