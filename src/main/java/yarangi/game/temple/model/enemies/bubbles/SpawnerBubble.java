package yarangi.game.temple.model.enemies.bubbles;

import yarangi.game.temple.model.Integrity;
import yarangi.graphics.curves.BezierBubble;
import yarangi.graphics.curves.BezierBubble.BezierNode;
import yarangi.graphics.quadraturin.SceneVeil;
import yarangi.graphics.quadraturin.objects.SceneEntity;
import yarangi.graphics.quadraturin.util.colors.Color;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;

public class SpawnerBubble extends SimpleBubble  
{

	public static final int MAX_SPAWN_TIME = 20; 
	
	private SceneVeil sceneVeil;
	
	private double timeToSpawn = 0;
	
	private int spawningNodeIdx = -1;
	
	protected BezierNode origLeft;
	protected BezierNode origRite;
	protected BezierNode origMoving;
	
	protected int nodesNum = 16;

	public SpawnerBubble(SceneVeil veil, double x, double y, double a, Vector2D velocity, SceneEntity target, double size) 
	{
		super(x, y , a, velocity, target, size, new Integrity(1000, new double [] { 0,0,0,0}, 0),
				new Color( (float)(0.5),
						   (float)(RandomUtil.getRandomDouble(0.5)+0.5), 
						   (float)(RandomUtil.getRandomDouble(0.5)+0.5), 1));
		
		sceneVeil = veil;
		setLook(/*new SpriteLook <ElementalDarkness> (*/new BubbleLook()/*, 64, 64, false)*/);
		setBehavior(new SpawnerBubbleBehavior());
		
//		props.add(0, null);
//		for(int i = 0; i < nodesNum; i ++)
//			bubble.getAnchor(i).x += i % 2 == 0 ? 3 : -3;
//		bubble.getAnchor(0).x += 5;

	}
	
	public Vector2D getVelocity() { return velocity; }

	public BezierBubble getCurve() {
		return bubble;
	}
	
	public SceneVeil getSceneVeil() { return sceneVeil; }
	
	public void spawn()
	{
		timeToSpawn = MAX_SPAWN_TIME;
		spawningNodeIdx = RandomUtil.getRandomInt(bubble.getNodesCount());
		
		Vector2D spawningAnchor = bubble.getAnchor(spawningNodeIdx);
		
		double a = Math.atan2(spawningAnchor.y, spawningAnchor.x);
		
		int helperIdx1 = spawningNodeIdx;
		spawningNodeIdx ++;
		int helperIdx2 = spawningNodeIdx+1;
		
//		System.out.println(helperIdx1 + " : " + spawningNodeIdx + " : " + helperIdx2);
		
		double helperA1 = a - Angles.PI_2/nodesNum/2;
		double helperA2 = a + Angles.PI_2/nodesNum/2;
		
		bubble.addNode(helperIdx1, new Vector2D(0,0,radius,helperA1), new Vector2D(0,0, 0.5, helperA1+Angles.PI_div_2));
		props.add(helperIdx1, new BubbleNode(0,0));
		bubble.addNode(helperIdx2, new Vector2D(0,0,radius,helperA2), new Vector2D(0,0, 0.5, helperA2+Angles.PI_div_2));
		props.add(helperIdx2, new BubbleNode(0,0));
		
	}
	
	public double getTimeToSpawn() { return timeToSpawn; }
	public void decreaseTimeToSpawn(double time)
	{
		this.timeToSpawn -= time;
		if(timeToSpawn <= 0 && spawningNodeIdx != -1)
		{
			Vector2D spawningNode = bubble.getAnchor(spawningNodeIdx);
			Vector2D spawnDir = spawningNode.rotate(Angles.toRadians(getAABB().a));
			Vector2D spawnLocation = getAABB().plus(spawnDir.mul(0.9));
			
			SimpleBubble born = new SimpleBubble(spawnLocation.x, spawnLocation.y, 0, 
					spawnDir.normalize().mul(1), 
					this, 2, new Integrity(100, new double [] { 0,0,0,0}, 0),
					getColor());
			getSceneVeil().addEntity(born);
//			System.out.println(getAABB() + " : " + spawningNode + " : " + born.getAABB());
			
			spawningNode.x = bubble.getAnchor(spawningNodeIdx+1).x;
			spawningNode.y = bubble.getAnchor(spawningNodeIdx+1).y;
			bubble.removeNode(spawningNodeIdx+1);
			bubble.removeNode(spawningNodeIdx-1);
			props.remove(spawningNodeIdx+1);
			props.remove(spawningNodeIdx-1);
			
			spawningNodeIdx = -1;
			

		}
	}
	public int getSpawningNodeIdx() { return spawningNodeIdx; }
}
