package yarangi.game.temple.model.temple.bots;

import yarangi.game.temple.model.temple.MeshNode;
import yarangi.game.temple.model.temple.platforms.CommandPlatform;
import yarangi.graphics.quadraturin.objects.SceneEntity;
import yarangi.graphics.quadraturin.simulations.IPhysicalObject;
import yarangi.math.Vector2D;
import yarangi.spatial.AABB;

public class Bot extends SceneEntity implements IPhysicalObject
{

	private static final long serialVersionUID = -19945327419649387L;

	private Vector2D [] tail;
	
	private CommandPlatform platform;
	
	private MeshNode currTarget, prevTarget;
	
	public Vector2D velocity;
	
	private double maxSpeed = 0.2;
	private double enginePower = 0.1;
	
	public Bot(CommandPlatform platform, Vector2D head, int tailSize)
	{
		super(new AABB(head.x, head.y, 0, 0));
		
		
		tail = new Vector2D[tailSize];
		for(int idx = 0; idx < tailSize; idx ++)
			tail[idx] = head;
		
		this.platform = platform;
		
		setLook(new BotLook());
		setBehavior(new BotBehavior());
	}
	
	public CommandPlatform getCommandPlatform() { return platform; }

	protected Vector2D getHead() { return tail[0]; }
	protected Vector2D[] getTail () { return tail; }

	
	public MeshNode getCurrTarget() { return currTarget; }
	public MeshNode getPrevTarget() { return prevTarget; }
	
	public void setTarget(MeshNode target) 
	{ 
		this.prevTarget = currTarget;
		this.currTarget = target; 
	}
	public double getMaxSpeed() { return maxSpeed; }
	public double getEnginePower() { return enginePower; }


	public boolean isCollidable() { return true; }

	public boolean isPickable() { return true; }

	public double getBoundingRadius() {
		return 1;
	}

	public Vector2D getVelocity() { return velocity; }

	public void setImpactWith(IPhysicalObject arg0) {
		// TODO Auto-generated method stub
		
	}

	public Vector2D getForce() {
		// TODO Auto-generated method stub
		return null;
	}

	public double getMass() {
		// TODO Auto-generated method stub
		return 0;
	}
}
