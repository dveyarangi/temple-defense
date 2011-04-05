package yarangi.game.temple.model.enemies;

import yarangi.game.temple.model.Integrity;
import yarangi.graphics.quadraturin.objects.SceneEntity;
import yarangi.math.AABB;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;

public class ElementalVoid extends GenericEnemy 
{

	public Vector2D velocity;
	
	public ElementalVoid(double x, double y, double a, SceneEntity target) {
		super(new AABB(x, y, 5, a), target, 
				new Integrity(100, new double [] { 0,0,0,0}, 0)
		);

		setLook(new ElementalVoidLook());
		setBehavior(new LinearBehavior());
		
		double speed = RandomUtil.getRandomDouble(0.1)+0.2;
		double ang = Angles.toRadians(a);
		this.velocity = new Vector2D(speed*Math.cos(ang), speed*Math.sin(ang));
	}

	public Vector2D getVelocity() {
		return velocity;
	}

	public void setImpactWith(SceneEntity arg0) {
		// TODO Auto-generated method stub
		
	}

}
