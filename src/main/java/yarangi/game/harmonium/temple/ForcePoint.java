package yarangi.game.harmonium.temple;

import yarangi.numbers.RandomUtil;


public class ForcePoint 
{

	private static final long serialVersionUID = 676739744145128934L;
	
	public double angle;
	public double strength;
	
	public boolean growing;
	
	public double step = 0.05-RandomUtil.getRandomDouble(0.02);
	
	public ForcePoint(double angle, double strength)
	{
		this.angle = angle;
		this.strength = strength;
		this.growing = true;
	}
	
	public void step(double time)
	{
		if(growing)
			strength += step*time;
		else
			strength -= step*time;
		
		if (strength >= 1)
		{
			growing = false;
			strength = 1;
		}
		
	}
	
}
