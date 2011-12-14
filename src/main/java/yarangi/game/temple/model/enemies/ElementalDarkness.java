package yarangi.game.temple.model.enemies;

import yarangi.game.temple.model.Integrity;
import yarangi.math.Angles;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.AABB;

public class ElementalDarkness extends GenericEnemy  
{

	public static final int SIZE = 40;

	
//	private BezierBubble bubble;
	
	public ElementalDarkness(double x, double y, double a) 
	{
		super(AABB.createSquare(x, y, SIZE, a), null, new Integrity(1000, 0, new double [] { 0,0,0,0}));
		setLook(/*new SpriteLook <ElementalDarkness> (*/new ElementalDarknessLook()/*, 64, 64, false)*/);
		setBehavior(new ElementalDarknessBehavior());
		
		double speed = RandomUtil.getRandomDouble(0.1)+0.2;
		double ang = Angles.toRadians(a);
		
//		this.setVelocity(speed*Math.cos(ang), speed*Math.sin(ang));
		
//		bubble = new BezierBubble(new Vector2D(0,0), SIZE, 8);
		
		
	}
/*	public BezierBubble getCurve() {
		return bubble;
	}*/


}
