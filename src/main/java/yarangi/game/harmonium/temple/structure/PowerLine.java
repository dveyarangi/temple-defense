package yarangi.game.harmonium.temple.structure;

import java.util.ArrayList;

import yarangi.math.Vector2D;

public class PowerLine extends ArrayList <Vector2D>
{

	private static final long serialVersionUID = -467781141589824256L;
	private double powerThroughput;
	private double bandwidth;
	
	private PowerConnector target;
	
	public PowerLine(PowerConnector target, Vector2D ... points)
	{
		this.target = target;
		
		for(Vector2D p : points)
			add(p);
	}
	
	public void setPowerThroghput(double powerThroughput) {	this.powerThroughput = powerThroughput;	}
	public double getPowerThroughput() { return powerThroughput; }

	public void setBandwidth(double bandwidth) { this.bandwidth = bandwidth; }
	public double getBandwidth() { return bandwidth; }
	
	public PowerConnector getTarget() { return target; }
}
