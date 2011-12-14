package yarangi.game.harmonium.enemies.swarm;

import yarangi.math.FastMath;
import yarangi.math.Vector2D;
import yarangi.numbers.AverageCounter;

/**
 * A beacon represent swarm knowledge about space segment. 
 * Grid of SwarmBeacons is updated and used by {@link Swarm} to support 
 * swarm behavior.
 * 
 * @author dveyarangi
 */
public class Beacon implements IBeacon 
{
	private int x, y;
	
	/**
	 * Danger level of this segment.
	 */
	private AverageCounter dangerFactor = new AverageCounter();
	
	/**
	 * Swarm drones flow direction.
	 */
	private Vector2D flow;
	
	/**
	 * Beacon data time.
	 */
	private long time = 0;

	private boolean unpassable;
	
	public Beacon(int x, int y)
	{
		this.x = x;
		this.y = y;
//		dangerFactor = 0;
		flow = Vector2D.ZERO();
		time = 0;
	}
	/* (non-Javadoc)
	 * @see yarangi.game.temple.model.enemies.swarm.IBeacon#getX()
	 */
	@Override
	public int getX() { return x; }
	/* (non-Javadoc)
	 * @see yarangi.game.temple.model.enemies.swarm.IBeacon#getY()
	 */
	@Override
	public int getY() { return y; }
	
	/* (non-Javadoc)
	 * @see yarangi.game.temple.model.enemies.swarm.IBeacon#getDangerFactor()
	 */
	@Override
	public int getDangerFactor() { return FastMath.round(dangerFactor.getSum()); }
	public void update(double damage)
	{
		dangerFactor.addValue( damage > 0 ? damage : 0 );
//		System.out.println(dangerFactor.getAverage());
		time = System.currentTimeMillis(); // TODO: should be engine's/scene time
		unpassable = false;
	}
	
	public void decayDanger(long time)
	{
//		System.out.println(dangerFactor.getAverage());
		double dt = time - this.time;
//		if(dangerFactor.getSum() != 0)
//			System.out.println(dangerFactor.getSum() + " : " + dt * Swarm.DANGER_FACTOR_DECAY);
		dangerFactor.setSum(dangerFactor.getSum() - dt * Swarm.DANGER_FACTOR_DECAY);
		if(dangerFactor.getSum() < 0)
		{
			dangerFactor.reset();
		}
		this.time = time; 
	}
	
	public boolean isDeadly(double damageThreshold)
	{
		return getDangerFactor() > damageThreshold;
		
	}
	
	public long getUpdateTime() { return time; }
	
	public void setFlow(double x, double y)
	{
		flow.setxy(x, y);
		double abs = flow.abs();
		if(abs > 1) // TODO: optimize, too many square roots
			flow.multiply(1./abs);
	}
	

	/* (non-Javadoc)
	 * @see yarangi.game.temple.model.enemies.swarm.IBeacon#addFlow(double, double)
	 */
	@Override
	public void addFlow(double x, double y) 
	{
		flow.add(x, y);
		double abs = flow.abs();
		if(abs > 1) // TODO: optimize, too many square roots
			flow.multiply(1./abs);
		
	}

	
	public long getTime() { return time; }
	/* (non-Javadoc)
	 * @see yarangi.game.temple.model.enemies.swarm.IBeacon#getFlow()
	 */
	@Override
	public Vector2D getFlow() { return flow; }


	/* (non-Javadoc)
	 * @see yarangi.game.temple.model.enemies.swarm.IBeacon#isVisited()
	 */
	@Override
	public boolean isVisited() {
		return time != 0;
	}

	public void setUnpassable(boolean b) { this.unpassable = b; }
	/* (non-Javadoc)
	 * @see yarangi.game.temple.model.enemies.swarm.IBeacon#isUnpassable()
	 */
	@Override
	public boolean isUnpassable() { return unpassable; }
}
