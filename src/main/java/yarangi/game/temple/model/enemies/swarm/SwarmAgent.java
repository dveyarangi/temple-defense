package yarangi.game.temple.model.enemies.swarm;

import yarangi.game.temple.model.Damage;
import yarangi.game.temple.model.Damageable;
import yarangi.game.temple.model.Integrity;
import yarangi.graphics.quadraturin.objects.WorldEntity;

public class SwarmAgent extends WorldEntity implements Damageable
{
	private Swarm swarm;
	
	private Integrity integrity;
	
	public SwarmAgent(Swarm swarm, Integrity integrity, boolean recon)
	{
		this.swarm = swarm;
		
		this.integrity = integrity;
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

}
