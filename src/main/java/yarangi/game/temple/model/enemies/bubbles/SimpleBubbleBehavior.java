package yarangi.game.temple.model.enemies.bubbles;

import yarangi.graphics.curves.BezierBubble;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.math.AABB;
import yarangi.math.DistanceUtils;
import yarangi.math.Vector2D;

public class SimpleBubbleBehavior implements Behavior<SimpleBubble> 
{
	double shrinkSpeed = 0.05;
	double expandSpeed = 0.05;
	
	double k = 0.01;
	
	public boolean behave(double time, SimpleBubble entity, boolean isVisible) 
	{
		AABB aabb = entity.getAABB();
		
		aabb.x += entity.getVelocity().x;
		aabb.y += entity.getVelocity().y;
		
		if(entity.getIntegrity().getHitPoints() <= 0)
		{
			entity.setIsAlive(false);
			return false;
		}
		
		BezierBubble bubble = entity.getCurve();
		Vector2D prev = bubble.getAnchor(bubble.getNodesCount()-1);
		Vector2D curr = bubble.getAnchor(0);
		Vector2D next;
		BubbleNode node;
		Vector2D fPrev, fNext, fCenter;
		double dPrev = -1, dNext = -1;
		for(int idx = 0; idx < bubble.getNodesCount(); idx ++)
		{
			 node = entity.getNodes().get(idx);
 			 next = bubble.getAnchor(idx+1 < bubble.getNodesCount() ? idx+1 : 0);
 			 
 			 if(dPrev < 0)
 				dPrev = Math.sqrt(DistanceUtils.calcDistanceSquare(prev, curr));
 			 else
 				dPrev = dNext;
  			 dNext = Math.sqrt(DistanceUtils.calcDistanceSquare(next, curr));
 			 double dCenter = curr.abs();
 			 fPrev = prev.minus(curr).normalize().mul(k*(dPrev-node.neighbour)); 
 			 fNext = next.minus(curr).normalize().mul(k*(dNext-node.neighbour));
 			 fCenter = curr.normalize().minus().mul(k*(dCenter-entity.getRadius()));
				 
		     entity.getNodes().get(idx).force = fCenter.plus(fNext).plus(fPrev);
//				System.out.println(curr + " : " + entity.getNodes().get(idx).force);
		     
		     prev = curr;
			 curr = next;
		}
	
		for(int idx = 0; idx < bubble.getNodesCount(); idx ++)
		{
			node = entity.getNodes().get(idx);
			curr = bubble.getAnchor(idx);
			node.speed.x += node.force.x*time;
			node.speed.y += node.force.y*time;
			curr.x += node.speed.x*time;
			curr.y += node.speed.y*time;
		}
		


		return true;
	}

}
