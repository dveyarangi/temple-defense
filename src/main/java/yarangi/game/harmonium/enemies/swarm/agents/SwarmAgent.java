package yarangi.game.harmonium.enemies.swarm.agents;

import yarangi.game.harmonium.enemies.IEnemy;
import yarangi.game.harmonium.enemies.swarm.Swarm;
import yarangi.game.harmonium.model.Damage;
import yarangi.game.harmonium.model.Damageable;
import yarangi.game.harmonium.model.Integrity;
import yarangi.graphics.quadraturin.objects.Entity;

public class SwarmAgent extends Entity implements Damageable, IEnemy
{
	private Swarm swarm;
	
	private Integrity integrity;
	
	private double leadership;
	private double attractiveness;
	
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
		integrity.hit(damage);
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

}
