package yarangi.game.temple.model.enemies.bubbles;

import java.util.List;

import yarangi.graphics.curves.BezierBubble.BezierNode;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;

public class SpawnerBubbleBehavior implements Behavior<SpawnerBubble>
{
	double shrinkSpeed = 0.5;
	double expandSpeed = 0.5;
	
	double k = 0.01;
	
	private SimpleBubbleBehavior simpleBehavior = new SimpleBubbleBehavior();
	
	public boolean behave(double time, SpawnerBubble entity, boolean isVisible) 
	{
		boolean isBehaved = simpleBehavior.behave(time, entity, isVisible);
		
		if(RandomUtil.getRandomInt(40) == 0 && entity.getTimeToSpawn() <= 0)
			entity.spawn();
		if(entity.getTimeToSpawn() > 0)
		{
			List <BezierNode> nodes = entity.getCurve().getNodes();
			int node = entity.getSpawningNodeIdx();
			BezierNode movingNode = nodes.get(node);
			BezierNode leftNode = nodes.get(node-1);
			BezierNode riteNode = nodes.get(node+1);
			
			Vector2D shrinkDir = leftNode.anchor.minus(riteNode.anchor);
			
			
			shrinkDir = shrinkDir.normalize();

			Vector2D expandDir = movingNode.anchor.normalize(); 
			leftNode.anchor.x -= shrinkDir.x * shrinkSpeed * time + expandDir.x * expandSpeed * time/1.5;
			leftNode.anchor.y -= shrinkDir.y * shrinkSpeed * time - expandDir.y * expandSpeed * time/1.5;
			riteNode.anchor.x += shrinkDir.x * shrinkSpeed * time + expandDir.x * expandSpeed * time/1.5;
			riteNode.anchor.y += shrinkDir.y * shrinkSpeed * time + expandDir.y * expandSpeed * time/1.5;
			leftNode.pivot = leftNode.pivot.rotate(-0.05).mul(1.1);
			riteNode.pivot = riteNode.pivot.rotate(0.05).mul(1.1);
			
			movingNode.anchor.x += expandDir.x * expandSpeed * time;
			movingNode.anchor.y += expandDir.y * expandSpeed * time;
//			movingNode.pivot = movingNode.pivot.mul(0.9);
			
			entity.decreaseTimeToSpawn(time);
		}
		else
		{

		}
		
		

		return  true & isBehaved;
	}

}
