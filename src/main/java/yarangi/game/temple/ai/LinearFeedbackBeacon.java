package yarangi.game.temple.ai;

import yarangi.graphics.quadraturin.simulations.IPhysicalObject;
import yarangi.math.DistanceUtils;
import yarangi.math.Vector2D;
import yarangi.spatial.ISpatialObject;

public class LinearFeedbackBeacon implements IFeedbackBeacon 
{

	private ISpatialObject source;
	
	private Vector2D location;
	private Vector2D velocity;
	private Vector2D projectileVelocity;
	private IPhysicalObject target;
	
	private double distance;
	private double angle;
	
	public LinearFeedbackBeacon(ISpatialObject source, IPhysicalObject target, Vector2D projectileVelocity)
	{
		this.source = source;
		this.target = target;
		this.projectileVelocity = projectileVelocity;
		
		Vector2D targetLoc = target.getArea().getRefPoint();
		Vector2D sourceLoc = source.getArea().getRefPoint();

		this.angle = Math.atan2(targetLoc.y - sourceLoc.y, targetLoc.x - sourceLoc.x);
		
		this.distance = DistanceUtils.calcDistanceSquare(sourceLoc, targetLoc);
		
		this.location = new Vector2D(targetLoc);
		this.velocity = new Vector2D(target.getVelocity());
	}
	
	@Override
	public IPhysicalObject getTarget() { return target; }

	@Override
	public void update() 
	{
		
		Vector2D targetLoc = target.getArea().getRefPoint();
		Vector2D sourceLoc = source.getArea().getRefPoint();
		double d = DistanceUtils.calcDistanceSquare(sourceLoc, targetLoc);
		if(d > distance)
			return;

		// updating feedback parameters:
		distance = d;
		angle = Math.atan2(targetLoc.y - sourceLoc.y, targetLoc.x - sourceLoc.x);
//		velocity = target.getVelocity();

	}
	public ISpatialObject getSource() {return source; }	
	public double getDistance() { return distance; }
	public double getAngle() { return angle; }
	public Vector2D getVelocity() { return velocity; }

	public Vector2D getLocation() { return location; }

	public Vector2D getProjectileVelocity() { return projectileVelocity; }
}
