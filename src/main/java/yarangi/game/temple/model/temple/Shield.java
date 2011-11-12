package yarangi.game.temple.model.temple;

import yarangi.game.temple.model.resource.Port;
import yarangi.game.temple.model.weapons.Projectile;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.simulations.IPhysicalObject;
import yarangi.math.Vector2D;
import yarangi.spatial.ISpatialFilter;
import yarangi.spatial.circle.CircleSegmentTree;

public class Shield extends Entity implements IPhysicalObject 
{
	public static ISpatialFilter <IEntity> SHIELD_FILTER = new ISpatialFilter<IEntity>() { 
		@Override public boolean accept(IEntity entity) { return !(entity instanceof Projectile);	}
	};

	private static final long serialVersionUID = 9214872976966945125L;
	private BattleInterface battleInterface;
	
	private double width;
	
	private ForcePoint [] forcePoints = new ForcePoint[150];
	
	private Port port;
	
	private CircleSegmentTree segments = new CircleSegmentTree();

	public Shield(BattleInterface battleInterface, Port port)
	{
		super();
		this.battleInterface = battleInterface;
		
//		setLook(new ShieldLook());
//		setBehavior(new ShieldBehavior());
		this.width = 1.5;
		
		this.port = port;
	}
	
	public Port getPort() { return port; }

	public ForcePoint[] getForcePoints() { return forcePoints; }

	public double getWidth() { return width; }

	public boolean isCollidable() { return true; }

	public boolean isPickable() { return false; }

	public BattleInterface getBattleInterface() { return battleInterface; }
	
	public CircleSegmentTree getExcludedSegments() 
	{
		return segments;
	}
}
