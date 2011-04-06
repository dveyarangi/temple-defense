package yarangi.game.temple.model.enemies.bubbles;

import java.util.ArrayList;
import java.util.List;

import yarangi.game.temple.model.Integrity;
import yarangi.game.temple.model.enemies.GenericEnemy;
import yarangi.graphics.curves.BezierBubble;
import yarangi.graphics.quadraturin.objects.SceneEntity;
import yarangi.graphics.quadraturin.simulations.IPhysicalObject;
import yarangi.graphics.utils.colors.Color;
import yarangi.math.AABB;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;

public class SimpleBubble extends GenericEnemy  
{

	
	protected BezierBubble bubble;
	
	protected ArrayList <BubbleNode> props = new ArrayList <BubbleNode> ();
	protected double radius;
//	protected double neighbourDistance;
	protected double k = 0.1;
	protected double maxSpeed = RandomUtil.getRandomDouble(1)+0.5;
	protected double enginePower = 0.0002;
	protected Color color;
	
	protected int nodesNum = 8;
	
	public SimpleBubble(double x, double y, double a, Vector2D velocity, SceneEntity target, double size, Integrity integrity, Color color) 
	{
		super(new AABB(x, y, size, a), target, integrity);
		
		setLook(/*new SpriteLook <ElementalDarkness> (*/new BubbleLook()/*, 64, 64, false)*/);
		setBehavior(new ChasingBubbleBehavior());
		
		this.velocity = velocity;
		this.color = color;
		
		bubble = new BezierBubble(new Vector2D(0,0), size, nodesNum);
		radius = size;
		double  neighbourDistance = 2*size*Math.sin(Angles.PI/nodesNum);
//		props.add(0, null);
//		for(int i = 0; i < nodesNum; i ++)
//			bubble.getAnchor(i).x += i % 2 == 0 ? 3 : -3;
//		bubble.getAnchor(0).x += 5;
		for(int i = 0; i < nodesNum; i ++)
			props.add(new BubbleNode(size, neighbourDistance));
	}
	
	public double getMaxSpeed() { return maxSpeed; }
	public double getEnginePower() { return enginePower; }
	
	public BezierBubble getCurve() {
		return bubble;
	}
	
	public double getRadius() { return radius; }
//	public double getNeighbourDistance() { return neighbourDistance; }
	public List <BubbleNode> getNodes() { return props; }

	public Color getColor() { return color; }

	public void setImpactWith(IPhysicalObject e) {
		// TODO Auto-generated method stub
		
	}

}
