package yarangi.game.harmonium.battle;

public class Damage 
{
	public static final int KINETIC = 0;
	public static final int THERMAL = 1;
	public static final int ELECTRO_MAGNETIC = 2;
	public static final int VOID = 3;
	
	public static final int [] TYPES = {KINETIC, THERMAL, ELECTRO_MAGNETIC, VOID };
	
	private double [] damage = new double [4];
	
	public Damage(double kinetic, double thermal, double divine, double voidd)
	{
		damage[KINETIC] = kinetic;
		damage[THERMAL] = thermal;
		damage[ELECTRO_MAGNETIC] = divine;
		damage[VOID] = voidd;
	}

	public double getDamage(int type) 
	{
		return damage[type];
	}

	
}
