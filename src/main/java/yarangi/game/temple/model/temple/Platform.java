package yarangi.game.temple.model.temple;

import yarangi.game.temple.model.Damage;
import yarangi.game.temple.model.Damageable;
import yarangi.game.temple.model.Integrity;
import yarangi.game.temple.model.temple.structure.Hexagon;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.spatial.AABB;

public abstract class Platform extends Entity implements Damageable
{

	private static final long serialVersionUID = 5435486264209745580L;
	
	private int nnCost;
	private int flowCost;

	private Integrity integrity;
	
	private Hexagon hexagon;
	
	protected Platform(Hexagon hexagon) 
	{
		super();
		
		setArea(new AABB(hexagon.getX(), hexagon.getY(), 1, /*Angles.toDegrees(Math.atan2(hexagon.getY(), hexagon.getX()))*/0));
		
		this.hexagon = hexagon;

	}
	
	public int getNNCost() { return nnCost; }
	public int getFlowCost() { return flowCost; }
	
	
	public void hit(Damage damage) 
	{

		integrity.hit(damage);
	}
	
	public Integrity getIntegrity() { return integrity; }

	public Hexagon getHexagon() { return hexagon; }

	public boolean isPickable() { return true; }
}
