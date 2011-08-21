package yarangi.game.temple.model.enemies.swarm;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import yarangi.game.temple.model.enemies.swarm.Swarm.AStarNode;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.math.FastMath;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;

public class SwarmPathingBehavior implements IBehaviorState<Swarm> 
{
	
	private int flowRadius;
	   ExecutorService executor = Executors.newFixedThreadPool(3);

	FutureTask<String> future = null;
	public SwarmPathingBehavior(int flowRadius)
	{
		this.flowRadius = flowRadius;
	}

	@Override
	public boolean behave(double time, final Swarm swarm, boolean isVisible) 
	{
		if(future != null && !future.isDone())
			return false;
		Vector2D origin = swarm.getSource();
		Vector2D target = swarm.getTarget();

		final int ox = swarm.toBeaconIdx(origin.x()+RandomUtil.getRandomDouble(100)-50);
		final int oy = swarm.toBeaconIdx(origin.y()+RandomUtil.getRandomDouble(100)-50);
		final int tx = swarm.toBeaconIdx(target.x());
		final int ty = swarm.toBeaconIdx(target.y());
//		System.out.println("pathing");
		
	    future = new FutureTask<String>(
	               new Callable<String>()
	               {
	                   public String call()
	                   {
	               			markPath(ox, oy, tx, ty, swarm.getWorldSize(), swarm.getBeacons());
                	   
	                       return null;
	                   }
	               });
	    
	    executor.execute(future);
		
		return false;
	}	
	
	public void markPath(int ox, int oy, int tx, int ty, int WSIZE, AStarNode [][] beacons)
	{
	
		PriorityQueue <AStarNode> openNodes = 
					new PriorityQueue <AStarNode> (1000, new Comparator <AStarNode> () {
			@Override
			public int compare(AStarNode o1, AStarNode o2) {
				return (int)(o1.f - o2.f);
			}});
		
		// TODO: replace with run id:
		List <AStarNode> closedNodes = new LinkedList <AStarNode> ();
		
		AStarNode curr = new AStarNode(ox, oy);
		AStarNode temp;
		
		long time = System.currentTimeMillis();
		boolean tentativeIsBetter;
		int cx = ox, cy = oy, x, y;
		boolean pathFound = false;
		do
		{
//			System.out.println(curr.getX() + " " + curr.getY());
			curr.unmarkOpen();
//			openNodes.remove(curr);
			
			curr.markClosed();
			closedNodes.add(curr);
			
			cx = curr.getX();
			cy = curr.getY();
			
			if(cx == tx && cy == ty) // target reached
			{
				pathFound = true;
				break;
			}
				
			
			
			
			tentativeIsBetter = false;
			for(int dx = -1; dx <= 1; dx ++)
			for(int dy = -1; dy <= 1; dy ++)
				if(dx != 0 || dy != 0)
				{
					x = cx + dx;
					if(x < 0 || x >= WSIZE) continue;
					y = cy + dy;
					if(y < 0 || y >= WSIZE) continue;
					
					temp = beacons[x][y];
					
					if(temp.isUnpassable())
						continue;
					double tentative = curr.g + ((dx == 0 || dy == 0) ? 1 : FastMath.ROOT_2);
//					if(temp == null)
//						temp = beacons[x][y] = new AStarNode(x, y);
					
					
					if(temp.isClosed())
						continue;
					
					if(!temp.isOpen()) // not yet visited
					{
						temp.markOpen();
						temp.update( -(time - temp.getTime())*Swarm.DANGER_FACTOR_DECAY);
					}
					else
					if(tentative < temp.g) // is better than before
						openNodes.remove(temp); // preparing to update
					else
					{
//						temp.unmarkOpen();
//						temp.markClosed();
//						closedNodes.add(temp);
						continue; // no update needed
					}
					
					
					
					temp.origin = curr;
					temp.g = tentative;
					temp.h = temp.getDangerFactor(); // TODO: should be smarter
					temp.f = temp.g + temp.h;
					openNodes.add(temp); // reinstalling the node to OPEN queue with new priority
				}
			;
			
		}
		while((curr = openNodes.poll()) != null);
		
//		System.out.println("search end, pathing");
		// clean up A* flags:
		if(pathFound)
		{
		
		// TODO: set target beacon if null (not supposed to happen)
		
			AStarNode node = beacons[tx][ty];
			while(node.origin != null)
			{
				
//				System.out.println(node.getX() + " : " + node.getY());
				cx = node.origin.getX();
				cy = node.origin.getY();
				Vector2D flow = new Vector2D(node.getX()-node.origin.getX(), node.getY()-node.origin.getY()).normalize();//.mul(0.1);
//				node.origin.setFlow(flow);
				for(int dx = -flowRadius; dx <= flowRadius; dx ++)
					for(int dy = -flowRadius; dy <= flowRadius; dy ++)
						{
						if(dx == 0 && dy == 0)
							continue;
							x = cx + dx;
							if(x < 0 || x >= WSIZE) continue;
							y = cy + dy;
							if(y < 0 || y >= WSIZE) continue;
							
							double d = Math.hypot(dx, dy)+1;
							beacons[x][y].addFlow(flow.x()/(10*d), flow.y()/(10*d));
//							System.out.println(x + " " + y + " ::: " + beacons[x][y].getFlow());
						}
				node = node.origin;
				node.addFlow(flow.x(), flow.y());
			}	
		}
//		System.out.println("pathing end");
		
		// TODO: replace with pass id check:
		for(AStarNode node : openNodes)
		{
			node.unmarkOpen();
			node.origin = null;
		}
		// TODO: replace with pass id check:
		for(AStarNode node : closedNodes)
		{
			node.unmarkClosed();
			node.origin = null;
		}
	}
}
