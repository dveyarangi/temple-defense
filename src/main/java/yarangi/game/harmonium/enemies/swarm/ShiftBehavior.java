package yarangi.game.harmonium.enemies.swarm;

import yar.quadraturin.objects.behaviors.IBehaviorState;

class ShiftBehavior implements IBehaviorState<Swarm> 
{

	@Override
	public double behave(double time, Swarm swarm) {
		swarm.nextNode();
		
/*		Set <ITemple> structures = EntityCenter.getTempleTargets();
		int targetOrdinal = RandomUtil.N( structures.size() );
		int idx = 0;
		for(ITemple structure : structures) {
			if(targetOrdinal == idx)
			{
				swarm.setTarget(structure);
				break;
			}
			targetOrdinal ++;
		}*/
		return 0;
	}
	
	public int getId() { return this.getClass().hashCode(); }

}
