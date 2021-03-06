package yarangi.game.harmonium.ai.weapons;

import java.lang.ref.WeakReference;

import yar.quadraturin.objects.IBeing;
import yarangi.math.Geometry;
import yarangi.math.IVector2D;
import yarangi.math.Vector2D;

public class LinearFeedbackBeacon implements IFeedbackBeacon 
{
	/** beacon launch point */
	private final Vector2D sourceLocationMemo;
	
	private final Vector2D velocity;
	private final double projectileVelocity;
	private final WeakReference<IBeing> target;
	
	private final Vector2D targetInitLoc;
	private Vector2D targetLocationMemo;
	
	private double distance;
	
	public LinearFeedbackBeacon(IBeing source, IBeing target, double projectileVelocity)
	{
		this.sourceLocationMemo = Vector2D.COPY(source.getArea().getAnchor());
		
		this.target = new WeakReference<IBeing>( target );
		this.projectileVelocity = projectileVelocity;
		targetInitLoc = targetLocationMemo = Vector2D.COPY(target.getArea().getAnchor());
		IVector2D sourceLoc = source.getArea().getAnchor();

		this.distance = Geometry.calcHypotSquare(sourceLoc, targetLocationMemo);
		
		this.velocity = Vector2D.COPY(target.getBody().getVelocity());
	}

	@Override
	public boolean update(IVector2D beaconLoc) 
	{
		if(target.get() == null) // if target is dead before a projectile at it:
			return true;
		
		
		IVector2D targetLoc = target.get().getArea().getAnchor();
		double toTarget = Geometry.calcHypotSquare(sourceLocationMemo, targetLoc);
		double toBeacon = Geometry.calcHypotSquare(sourceLocationMemo, beaconLoc);

		double offset = Math.abs(toTarget-toBeacon);
		if(offset < distance)
			return false;
		targetLocationMemo = Vector2D.COPY(targetLoc);

		// updating feedback parameters:
		distance = offset;

		
/*		Vector2D targetLoc = target.get().getArea().getRefPoint();
		double d = Geometry.calcHypotSquare(beaconLocation, targetLoc);

		if(d > distance)
			return false;
		targetLocationMemo = new Vector2D(targetLoc);
		beaconLocationMemo = new Vector2D(beaconLocation);

		// updating feedback parameters:
		distance = d;*/
		
		return false;

	}
	public Vector2D getSource() {return sourceLocationMemo; }	
	
	public double getDistance() { return distance; }
	public Vector2D getVelocity() { return velocity; }


	public double getProjectileVelocity() { return projectileVelocity; }

	public Vector2D getInitialTargetLocation()
	{
		return targetInitLoc;
	}
	public Vector2D getTargetLocationMemo()
	{
		return targetLocationMemo;
	}
}
