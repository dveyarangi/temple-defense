package yarangi.game.harmonium.enemies.swarm.agents;

import yarangi.game.harmonium.battle.Damage;
import yarangi.game.harmonium.battle.Damageable;
import yarangi.game.harmonium.battle.IEnemy;
import yarangi.game.harmonium.battle.Integrity;
import yarangi.game.harmonium.enemies.swarm.Swarm;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.graphics.quadraturin.objects.IEntity;

public class SwarmAgent extends Entity implements Damageable, IEnemy
{
	
	private final Swarm swarm;
	
	private final Integrity integrity;
	
	private final double leadership;
	private final double attractiveness;
	
	private IEntity target;
	
	public SwarmAgent(Swarm swarm, Integrity integrity, double leadership, double attractiveness)
	{
		this.swarm = swarm;
		
		this.integrity = integrity;
		
		this.leadership = leadership;
		
		this.attractiveness = attractiveness;
	}
		
	public Swarm getSwarm() { return swarm; }

	@Override
	public Integrity getIntegrity() {
		return integrity;
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

}
