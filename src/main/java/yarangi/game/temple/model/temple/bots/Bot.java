package yarangi.game.temple.model.temple.bots;

import yarangi.game.temple.model.temple.Serviceable;
import yarangi.game.temple.model.temple.StructureInterface;
import yarangi.graphics.quadraturin.objects.Entity;

public class Bot extends Entity
{

	private static final long serialVersionUID = -19945327419649387L;

	private StructureInterface platform;
	
	private Serviceable currTarget;
	private double enginePower = 0.05;
	
	public Bot(StructureInterface platform)
	{
		super();
		
			
		this.platform = platform;

	}
	
	public StructureInterface getStructureInterface() { return platform; }
	
	public Serviceable getCurrTarget() { return currTarget; }
	
	public void setTarget(Serviceable target) 
	{ 
		this.currTarget = target; 
	}
	public double getEnginePower() { return enginePower; }

}
