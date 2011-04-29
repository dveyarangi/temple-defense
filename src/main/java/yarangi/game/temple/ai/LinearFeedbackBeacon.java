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

		this.angle = Math.atan2(target.getAABB().y - source.getAABB().y, target.getAABB().x - source.getAABB().x);
		
		this.distance = DistanceUtils.calcDistanceSquare(source.getAABB(), target.getAABB());
		
		this.location = new Vector2D(target.getAABB());
		this.velocity = new Vector2D(target.getVelocity());
	}
	
	@Override
	public IPhysicalObject getTarget() { return target; }

	@Override
	public void update() 
	{
		double d = DistanceUtils.calcDistanceSquare(source.getAABB(), target.getAABB());
		if(d > distance)
			return;

		// updating feedback parameters:
		distance = d;
		angle = Math.atan2(target.getAABB().y - source.getAABB().y, target.getAABB().x - source.getAABB().x);
//		velocity = target.getVelocity();

	}
	public ISpatialObject getSource() {return source; }	
	public double getDistance() { return distance; }
	public double getAngle() { return angle; }
	public Vector2D getVelocity() { return velocity; }

	public Vector2D getLocation() { return location; }

	public Vector2D getProjectileVelocity() { return projectileVelocity; }
}
