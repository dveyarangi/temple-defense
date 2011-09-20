package yarangi.game.temple.model.enemies.swarm;

import yarangi.math.Vector2D;

public interface IBeacon
{

	public abstract int getX();

	public abstract int getY();

	public abstract int getDangerFactor();

	public abstract void addFlow(double x, double y);

	public abstract Vector2D getFlow();

	public abstract boolean isVisited();

	public abstract boolean isUnpassable();

}