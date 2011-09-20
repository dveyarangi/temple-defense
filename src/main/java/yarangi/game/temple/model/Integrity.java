package yarangi.game.temple.model;

import java.util.Arrays;

public class Integrity implements Cloneable 
{
	private double maxHitPoints;
	private double hitPoints;
	
	private double [] resistances;
	private double armor;
	
	
	public Integrity(double maxHitPoints, double armor, double [] resistances)
	{
		this.maxHitPoints = maxHitPoints;
		this.hitPoints = maxHitPoints;
		this.resistances = resistances;
		this.armor = armor;
	}
	
	public double hit(Damage damage)
	{
		
		double totalDamage = 0;
		for(int type : Damage.TYPES)
		{
//			System.out.println(damage);
			
			double dam = damage.getDamage(type) * (1-resistances[type]);
			if (dam > armor)
				totalDamage += (dam - armor);
		}
		
		hitPoints -= totalDamage;
		if ( this.hitPoints < 0 )
			this.hitPoints = 0;
		return totalDamage;
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
	
	public Integrity clone()
	{
		return new Integrity(maxHitPoints, armor, Arrays.copyOf( resistances, resistances.length ));
	}
}
