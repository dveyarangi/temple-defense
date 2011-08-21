package yarangi.game.temple.model.enemies.swarm;

import yarangi.math.FastMath;
import yarangi.math.Vector2D;

/**
 * A beacon represent swarm knowledge about space segment. 
 * Grid of SwarmBeacons is updated and used by {@link Swarm} to support 
 * swarm behavior.
 * 
 * @author dveyarangi
 */
public class SwarmBeacon 
{
	private int x, y;
	
	/**
	 * Danger level of this segment.
	 */
	private int dangerFactor = 1;
	
	/**
	 * Swarm drones flow direction.
	 */
	private Vector2D flow;
	
	/**
	 * Beacon data time.
	 */
	private long time = 0;

	private boolean unpassable;
	
	public SwarmBeacon(int x, int y)
	{
		this.x = x;
		this.y = y;
		dangerFactor = 0;
		flow = Vector2D.ZERO();
		time = 0;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	
	public int getDangerFactor() { return dangerFactor; }
	public void update(double damage)
	{
		dangerFactor += FastMath.round(damage);
		if(dangerFactor > Swarm.MAX_DANGER_FACTOR)
			dangerFactor = Swarm.MIN_DANGER_FACTOR;
		else
		if(dangerFactor < 0)
			dangerFactor = 0;
		time = System.currentTimeMillis(); // TODO: should be engine's/scene time
		unpassable = false;
	}
	
	public long getUpdateTime() { return time; }
	
	public void setFlow(double x, double y)
	{
		flow.setxy(x, y);
		double abs = flow.abs();
		if(abs > 1) // TODO: optimize, too many square roots
			flow.multiply(1./abs);
	}
	

	public void addFlow(double x, double y) 
	{
		flow.add(x, y);
		double abs = flow.abs();
		if(abs > 1) // TODO: optimize, too many square roots
			flow.multiply(1./abs);
		
	}

	
	public long getTime() { return time; }
	public Vector2D getFlow() { return flow; }

	public void setDangerFactor(int d) 
	{ 
		this.dangerFactor = d;
		if(dangerFactor < Swarm.MIN_DANGER_FACTOR)
			dangerFactor = Swarm.MIN_DANGER_FACTOR;
		if(dangerFactor > Swarm.MAX_DANGER_FACTOR)
			dangerFactor = Swarm.MAX_DANGER_FACTOR;
//		System.out.println("danger set: " + getDangerFactor());
	}

	public boolean isVisited() {
		return time != 0;
	}

	public void setUnpassable() { this.unpassable = true; }
	public boolean isUnpassable() { return unpassable; }
}
