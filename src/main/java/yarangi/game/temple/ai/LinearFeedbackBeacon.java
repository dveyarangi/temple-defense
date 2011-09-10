package yarangi.game.temple.ai;

import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.math.Geometry;
import yarangi.math.Vector2D;

public class LinearFeedbackBeacon implements IFeedbackBeacon 
{

	private IEntity source;
	
	private Vector2D location;
	private Vector2D velocity;
	private Vector2D projectileVelocity;
	private IEntity target;
	
	private double distance;
	private double angle;
	
	public LinearFeedbackBeacon(IEntity source, IEntity target, Vector2D projectileVelocity)
	{
		this.source = source;
		this.target = target;
		this.projectileVelocity = projectileVelocity;
		
		Vector2D targetLoc = target.getArea().getRefPoint();
		Vector2D sourceLoc = source.getArea().getRefPoint();

		this.angle = Math.atan2(targetLoc.y() - sourceLoc.y(), targetLoc.x() - sourceLoc.x());
		

		this.distance = Geometry.calcHypotSquare(sourceLoc, targetLoc);
		
		this.location = new Vector2D(targetLoc);
		this.velocity = new Vector2D(target.getBody().getVelocity());
	}

	@Override
	public void update() 
	{
		
		Vector2D targetLoc = target.getArea().getRefPoint();
		Vector2D sourceLoc = source.getArea().getRefPoint();
		double d = Geometry.calcHypotSquare(sourceLoc, targetLoc);
		if(d > distance)
			return;

		// updating feedback parameters:
		distance = d;
		angle = Math.atan2(targetLoc.y() - sourceLoc.y(), targetLoc.x() - sourceLoc.x());
//		velocity = target.getVelocity();

	}
	public IEntity getSource() {return source; }	
	public IEntity getTarget() { return target; }
	
	public double getDistance() { return distance; }
	public double getAngle() { return angle; }
	public Vector2D getVelocity() { return velocity; }

	public Vector2D getLocation() { return location; }

	public Vector2D getProjectileVelocity() { return projectileVelocity; }
}
