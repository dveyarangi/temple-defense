package yarangi.game.harmonium.enemies.swarm;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import yarangi.game.harmonium.enemies.swarm.Swarm.AStarNode;
import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.math.FastMath;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;

/**
 * TODO: requires clean-up and optimization 
 * TODO: danger factor wear-out is not working
 * 
 * @author fimar
 *
 */
public class PathingBehavior implements IBehaviorState<Swarm> 
{
	
	private int flowRadius;
	   ExecutorService executor = Executors.newFixedThreadPool(3);

	private long lastOmniscienceTime = System.currentTimeMillis();

	FutureTask<String> future = null;
	public PathingBehavior(int flowRadius)
	{
		this.flowRadius = flowRadius;
	}

	@Override
	public double behave(double time, final Swarm swarm) 
	{
		if(future != null && !future.isDone())
			return 0;
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
	               			markPath(ox, oy, tx, ty, swarm);
                	   
	                       return null;
	                   }
	               });
	    
	    executor.execute(future);
		
		return 0;
	}	
	
	public void markPath(int ox, int oy, int tx, int ty, Swarm swarm)
	{
		int WSIZE = swarm.getWorldSize();
//		AStarNode [][] beacons = swarm.getBeacons();
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
		int omniscienceCount = 0;//(int)((time - lastOmniscienceTime) / Swarm.OMNISCIENCE_PERIOD);
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
					
					temp = swarm.getBeaconByIndex( x, y );
					if(temp.isClosed())
						continue;
				
					if(swarm.isOmniUnpassable(temp.getX(), temp.getY()))
					{
//						if(omniscienceCount == 0)
//							continue;
//						if(swarm.isOmniUnpassable(temp.getX(), temp.getY()))
//							continue;
/*						else
						{
							temp.setUnpassable(false);
							swarm.cellModified( temp.getX(), temp.getY() );
						}
						lastOmniscienceTime = time;
						omniscienceCount --;*/
					}
					
//					if(temp.isDeadly(SpawningBehavior.AGENT_HEALTH))
//						continue;
					
					double tentative = curr.g + 
								((dx == 0 || dy == 0) ? 1 : FastMath.ROOT_2) + 
								temp.getDangerFactor();
//					if(temp == null)
//						temp = beacons[x][y] = new AStarNode(x, y);
					
					
					if(!temp.isOpen()) // not yet visited
					{
						temp.markOpen();
						temp.decayDanger(time);
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
					temp.h = Math.sqrt((x-tx)*(x-tx) + (y-ty)*(y-ty));
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
		
			AStarNode node = swarm.getBeaconByIndex( tx, ty );
			while(node.origin != null)
			{
				
//				System.out.println(node.getX() + " : " + node.getY());
				cx = node.origin.getX();
				cy = node.origin.getY();
				Vector2D flow = Vector2D.R(node.getX()-node.origin.getX(), node.getY()-node.origin.getY()).normalize();//.mul(0.1);
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
							swarm.getBeaconByIndex( x, y ).addFlow(flow.x()/(10*d), flow.y()/(10*d));
//							System.out.println(x + " " + y + " ::: " + beacons[x][y].getFlow());
						}
				node = node.origin;
				node.addFlow(flow.x(), flow.y());
				
				swarm.setModified( node.getX(), node.getY() );
			}	
		}
//		System.out.println("pathing end");
		
		// TODO: replace with pass id check:
		for(AStarNode node : openNodes)
		{
			node.unmarkOpen();
			node.reset();
		}
		// TODO: replace with pass id check:
		for(AStarNode node : closedNodes)
		{
			node.unmarkClosed();
			node.reset();
		}
		
		swarm.fireGridModified();
	}
	public int getId() { return this.getClass().hashCode(); }
}
