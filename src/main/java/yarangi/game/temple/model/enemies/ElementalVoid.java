package yarangi.game.temple.model.enemies;

import yarangi.game.temple.model.Integrity;
import yarangi.graphics.quadraturin.objects.ILayerObject;
import yarangi.graphics.quadraturin.simulations.IPhysicalObject;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;
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
		setBehavior(new DroneBehavior());
//		setMass(10);
		
//		setVelocity(RandomUtil.getRandomDouble(5)-2.5, RandomUtil.getRandomDouble(5)-2.5);
	}


}
