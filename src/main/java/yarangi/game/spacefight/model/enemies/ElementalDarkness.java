package yarangi.game.spacefight.model.enemies;

import yarangi.game.spacefight.model.Integrity;
import yarangi.graphics.quadraturin.effects.SpriteLook;
import yarangi.graphics.quadraturin.interaction.spatial.AABB;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.math.bezier.BezierBubble;
import yarangi.math.bezier.CubicBezierChain;
import yarangi.numbers.RandomUtil;

public class ElementalDarkness extends GenericEnemy  
{

	public static final int SIZE = 40;
	private Vector2D velocity;
	
	private BezierBubble bubble;
	
	public ElementalDarkness(double x, double y, double a, Vector2D target) 
	{
		super(new AABB(x, y, SIZE, a), target, new Integrity(1000, new double [] { 0,0,0,0}, 0));
		setLook(/*new SpriteLook <ElementalDarkness> (*/new ElementalDarknessLook()/*, 64, 64, false)*/);
		setBehavior(new ElementalDarknessBehavior());
		
		double speed = RandomUtil.getRandomDouble(0.1)+0.2;
		double ang = Angles.toRadians(a);
		this.velocity = new Vector2D(speed*Math.cos(ang), speed*Math.sin(ang));
		
		bubble = new BezierBubble(new Vector2D(0,0), SIZE, 8);
		
		
	}
	
	public Vector2D getVelocity() { return velocity; }

	public BezierBubble getCurve() {
		return bubble;
	}

}
