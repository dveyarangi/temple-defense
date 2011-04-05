package yarangi.game.temple.model.temple;

import yarangi.game.temple.controllers.BattleInterface;
import yarangi.graphics.quadraturin.interaction.PhysicalEntity;
import yarangi.graphics.quadraturin.objects.SceneEntity;
import yarangi.math.AABB;
import yarangi.math.Vector2D;

public class ShieldEntity extends SceneEntity implements PhysicalEntity 
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

	public void setImpactWith(SceneEntity e) {
		// TODO Auto-generated method stub
		
	}

}
