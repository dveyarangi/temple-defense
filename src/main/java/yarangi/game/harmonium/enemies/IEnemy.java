package yarangi.game.harmonium.enemies;

import yarangi.graphics.quadraturin.objects.IEntity;

public interface IEnemy extends IEntity
{
	public double getLeadership();
	public double getAttractiveness();
}
