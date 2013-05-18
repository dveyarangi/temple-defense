package yarangi.game.harmonium.enemies;

import yar.quadraturin.objects.ILayerObject;
import yarangi.game.harmonium.battle.Integrity;
import yarangi.game.harmonium.enemies.swarm.agents.DroneBehavior;
import yarangi.math.Vector2D;
import yarangi.spatial.AABB;

public class ElementalVoid extends GenericEnemy 
{
	
	public Vector2D force = Vector2D.ZERO();
	
//	private double mass = 1; 
	
	public ElementalVoid(double x, double y, double a, double size, ILayerObject target) {
		super(AABB.createSquare(x, y, size, a), target, 
				new Integrity(1000,  0, new double [] { 0,0,0,0})
		);

		setLook(new ElementalVoidLook());
		setBehavior(new DroneBehavior(1));
//		setMass(10);
		
//		setVelocity(RandomUtil.getRandomDouble(5)-2.5, RandomUtil.getRandomDouble(5)-2.5);
	}


}
