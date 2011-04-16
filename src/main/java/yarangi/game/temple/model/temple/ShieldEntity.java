package yarangi.game.temple.model.temple;

import yarangi.game.temple.controllers.BattleInterface;
import yarangi.graphics.quadraturin.objects.SceneEntity;
import yarangi.graphics.quadraturin.simulations.IPhysicalObject;
import yarangi.math.Vector2D;
import yarangi.spatial.AABB;

public class ShieldEntity extends SceneEntity implements IPhysicalObject 
{

	private static final long serialVersionUID = 9214872976966945125L;
	private BattleInterface battleInterface;
	
	private double width;
	
	private ForcePoint [] forcePoints = new ForcePoint[150];

	public ShieldEntity(BattleInterface battleInterface)
	{
		super(new AABB(0,0,0,0));
		this.battleInterface = battleInterface;
		
		setLook(new ShieldLook());
		setBehavior(new ShieldBehavior());
		this.width = 1.5;
	}

	public ForcePoint[] getForcePoints() { return forcePoints; }

	public double getWidth() { return width; }

	public boolean isCollidable() { return true; }

	public boolean isPickable() { return false; }

	public BattleInterface getBattleInterface() { return battleInterface; }


	public Vector2D getVelocity() {
		return null;
	}

	public void setImpactWith(IPhysicalObject e) {
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
