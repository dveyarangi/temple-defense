package yarangi.game.temple.model.terrain;

import java.util.HashMap;
import java.util.Map;

import yarangi.math.Vector2D;

public class MatterBranch 
{
	private Vector2D location;
	private Vector2D direction;
	
	private double length;
	
	private MatterBranch next;
	
	private Map <MatterBranch, Double> branches;
	
	public MatterBranch(Vector2D location, Vector2D direction, double length)
	{
		this.location = location;
		this.direction = direction;
		
		this.length = length;
		
		branches = new HashMap <MatterBranch, Double> ();
	}
	
	protected Vector2D getDirection() { return direction; }

	protected Vector2D getLocation() { return location; }

	protected double getLength() { return length; }

	
	public void setNext(MatterBranch next)
	{
		this.next = next;
	}
	
	protected MatterBranch getNext()
	{
		return next;
	}
	
	public void add(MatterBranch branch, double offset)
	{
		branches.put(branch, offset);
	}
	
	public Map <MatterBranch, Double> getBranches()
	{
		return branches;
	}
}
