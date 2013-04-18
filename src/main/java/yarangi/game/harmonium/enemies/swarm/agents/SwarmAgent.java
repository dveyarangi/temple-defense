package yarangi.game.harmonium.enemies.swarm.agents;

import java.awt.geom.Point2D;

import yarangi.game.harmonium.battle.Damage;
import yarangi.game.harmonium.battle.IEnemy;
import yarangi.game.harmonium.battle.Integrity;
import yarangi.game.harmonium.enemies.swarm.Swarm;
import yarangi.graphics.colors.Color;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.math.Angles;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.Area;

import com.seisw.util.geom.Poly;
import com.seisw.util.geom.PolyDefault;

public class SwarmAgent extends Entity implements IEnemy
{
	
	private final Swarm swarm;
	
	private final Integrity integrity;
	
	private final double leadership;
	private final double attractiveness;
	
	private IEntity target;
	
	private final Color color;
	private final Color otherColor;
	
	protected Poly errosionPoly;
	
	public SwarmAgent(Swarm swarm, Integrity integrity, double leadership, double attractiveness)
	{
		this.swarm = swarm;
		
		this.integrity = integrity;
		
		this.leadership = leadership;
		
		this.attractiveness = attractiveness;
		
		this.color = new Color( (float)(RandomUtil.getRandomDouble(0.5)+0.5),
								(float)(RandomUtil.getRandomDouble(0.5)+0.5),
								(float)(RandomUtil.getRandomDouble(0.5)+0.5), 1);
		this.otherColor = new Color( (float)(RandomUtil.getRandomDouble(0.5)+0.5),
									  (float)(RandomUtil.getRandomDouble(0.5)+0.5),
									  (float)(RandomUtil.getRandomDouble(0.5)+0.5), 1);

	}
		
	public Swarm getSwarm() { return swarm; }

	@Override
	public Integrity getIntegrity() {
		return integrity;
	}
	
	@Override
	public void setArea(Area area) { 
		super.setArea( area );
		errosionPoly = createErrosionPoly( area );
	}
	
	protected Poly createErrosionPoly(Area area)
	{
		double size = area.getMaxRadius();
		PolyDefault poly = new PolyDefault();
		for(double a = 0 ; a < Angles.TAU; a += Angles.PI_div_3)
			poly.add( new Point2D.Double(size * Math.cos( a ), size * Math.sin( a )) );
		return poly;
	}

	@Override
	public void hit(Damage damage) {
		double d = integrity.hit(damage);
		swarm.setDanger(this, d); 
		if(integrity.getHitPoints() <= 0)
			markDead();
	}

	@Override
	public double getLeadership()
	{
		return leadership;
	}

	@Override
	public double getAttractiveness()
	{
		return attractiveness;
	}
	
	public void setTarget(IEntity entity) {
		this.target = entity;
	}

	public IEntity getTarget()
	{
		return target;
	}

	public double getLocalDanger()
	{
		return swarm.getDanger(getArea().getAnchor());
	}

	public Color getColor()
	{
		return color;
	}

	public Color getOtherColor()
	{
		return otherColor;
	}

	@Override
	public Poly getErrosionPoly()
	{
		return errosionPoly;
	}

}
