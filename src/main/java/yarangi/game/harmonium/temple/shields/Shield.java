package yarangi.game.harmonium.temple.shields;

import yar.quadraturin.objects.Entity;
import yar.quadraturin.objects.IEntity;
import yarangi.fragments.CircleSegmentList;
import yarangi.game.harmonium.environment.resources.Port;
import yarangi.game.harmonium.temple.BattleInterface;
import yarangi.game.harmonium.temple.ForcePoint;
import yarangi.game.harmonium.temple.weapons.Projectile;
import yarangi.physics.IPhysicalObject;
import yarangi.spatial.ISpatialFilter;

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
	
	private CircleSegmentList segments = new CircleSegmentList(-Math.PI/2, Math.PI/2);

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
	
	public CircleSegmentList getExcludedSegments() 
	{
		return segments;
	}
}
