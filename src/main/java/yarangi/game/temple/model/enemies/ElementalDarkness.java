package yarangi.game.temple.model.enemies;

import yarangi.game.temple.model.Integrity;
import yarangi.graphics.curves.BezierBubble;
import yarangi.graphics.quadraturin.objects.SceneEntity;
import yarangi.graphics.quadraturin.simulations.IPhysicalObject;
import yarangi.math.AABB;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;

public class ElementalDarkness extends GenericEnemy  
{

	public static final int SIZE = 40;
	private Vector2D velocity;
	
	private Vector2D force;
	
	private double mass;
	
	
	private BezierBubble bubble;
	
	public ElementalDarkness(double x, double y, double a, SceneEntity target) 
	{
		super(new AABB(x, y, SIZE, a), target, new Integrity(1000, new double [] { 0,0,0,0}, 0));
		setLook(/*new SpriteLook <ElementalDarkness> (*/new ElementalDarknessLook()/*, 64, 64, false)*/);
		setBehavior(new ElementalDarknessBehavior());
		
		double speed = RandomUtil.getRandomDouble(0.1)+0.2;
		double ang = Angles.toRadians(a);
		this.velocity = new Vector2D(speed*Math.cos(ang), speed*Math.sin(ang));
		
		bubble = new BezierBubble(new Vector2D(0,0), SIZE, 8);
		
		
	}
	public BezierBubble getCurve() {
		return bubble;
	}

	public void setImpactWith(IPhysicalObject arg0) {
		// TODO Auto-generated method stub
		
	}


}
