package yarangi.game.spacefight.model.enemies;

import yarangi.game.spacefight.model.Integrity;
import yarangi.graphics.quadraturin.interaction.spatial.AABB;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;

public class ElementalVoid extends GenericEnemy 
{

	public Vector2D velocity;
	
	public ElementalVoid(double x, double y, double a, Vector2D target) {
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

}
