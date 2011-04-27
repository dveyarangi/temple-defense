package yarangi.game.temple.model.enemies;

import yarangi.game.temple.model.Integrity;
import yarangi.graphics.quadraturin.objects.SceneEntity;
import yarangi.graphics.quadraturin.simulations.IPhysicalObject;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.AABB;

public class ElementalVoid extends GenericEnemy 
{
	
	public Vector2D force = new Vector2D(0,0);
	
//	private double mass = 1; 
	
	public ElementalVoid(double x, double y, double a, double size, SceneEntity target) {
		super(new AABB(x, y, size, a), target, 
				new Integrity(300, new double [] { 0,0,0,0}, 0)
		);

		setLook(new ElementalVoidLook());
		setBehavior(new DroneBehavior());
		setMass(10);
		
		setVelocity(RandomUtil.getRandomDouble(4)-2, RandomUtil.getRandomDouble(4)-2);
	}

	public void setImpactWith(IPhysicalObject arg0) {
		// TODO Auto-generated method stub
		
	}


}
