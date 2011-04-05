package yarangi.game.temple.model;

public class Damage 
{
	public static final int KINETIC = 0;
	public static final int THERMAL = 1;
	public static final int DIVINE = 2;
	public static final int VOID = 3;
	
	public static final int [] TYPES = {KINETIC, THERMAL, DIVINE, VOID };
	
	private double [] damage = new double [4];
	
	public Damage(double kinetic, double thermal, double divine, double voidd)
	{
		damage[KINETIC] = kinetic;
		damage[THERMAL] = thermal;
		damage[DIVINE] = divine;
		damage[VOID] = voidd;
	}

	public double getDamage(int type) 
	{
		return damage[type];
	}

	
}
