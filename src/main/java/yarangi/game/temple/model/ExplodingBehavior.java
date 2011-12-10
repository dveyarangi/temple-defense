package yarangi.game.temple.model;

import yarangi.graphics.colors.Color;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.graphics.quadraturin.objects.Entity;

public class ExplodingBehavior implements Behavior <Entity>  
{
	private double explosionTime;
	private double timeLeft;
	
	private Color color;
	private Color maxColor;
	public ExplodingBehavior(Color color, double time)
	{
		 timeLeft = time;
		 explosionTime = time/ 2;
		 this.color = new Color(0,0,0,0);
		 
		 maxColor = new Color(color);
	}
	public Color getActiveColor() { return color; }
	
	@Override
	public boolean behave(double time, Entity entity, boolean isVisible)
	{
		
		float ratio;
		if(timeLeft > explosionTime)
		{
			ratio = (float)((2*explosionTime-timeLeft)/explosionTime); 
		}
		else
		{
			ratio = (float)(timeLeft/explosionTime); 
		}
		color.setRed(ratio * maxColor.getRed());
		color.setBlue(ratio * maxColor.getBlue());
		color.setGreen(ratio * maxColor.getGreen());
		
		timeLeft -= time;
		
		if(timeLeft <= 0)
			entity.markDead();

		return true;

	}
	
	public int getId() { return ExplodingBehavior.class.hashCode(); }
}
