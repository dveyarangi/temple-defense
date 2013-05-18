package yarangi.game.harmonium.enemies.swarm;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import yar.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.game.harmonium.enemies.swarm.Swarm.AStarNode;
import yarangi.math.FastMath;
import yarangi.math.IVector2D;
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
	
	private final int flowRadius;
	   ExecutorService executor = Executors.newFixedThreadPool(1);

	private final long lastOmniscienceTime = System.currentTimeMillis();
	
	private int passId = 0;
	
	
	private volatile boolean isRunning = false;

	FutureTask<String> future = null;
	public PathingBehavior(int flowRadius)
	{
		this.flowRadius = flowRadius;
	}

	@Override
	public double behave(double time, final Swarm swarm) 
	{
		if(isRunning)
			return 0;
		
		isRunning = true;
		passId ++;
		
		IVector2D origin = swarm.getSource();
		IVector2D target = swarm.getTarget();

		final int ox = swarm.toBeaconIdx(origin.x()+RandomUtil.getRandomDouble(100)-50);
		final int oy = swarm.toBeaconIdx(origin.y()+RandomUtil.getRandomDouble(100)-50);
		final int tx = swarm.toBeaconIdx(target.x());
		final int ty = swarm.toBeaconIdx(target.y());
//		System.out.println("pathing");
		
	    future = new FutureTask<String>(
	               new Callable<String>()
	               {
	                   @Override
					public String call()
	                   {
	                	   try { 
	                		   
	               				markPath(ox, oy, tx, ty, swarm);
	                	   }
	                	   catch(Exception e) 
	                	   {
	                		   e.printStackTrace();
	                	   }
	               			isRunning = false;
	                       return null;
	                   }
	               });
	    
	    executor.execute(future);
		
		return 0;
	}	
	
	
	private class NodesForceQueue extends PriorityQueue
	{
		
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
			
			curr.markClosed(passId);
			
			cx = curr.getX();
			cy = curr.getY();
			
			if(swarm.hasTarget( cx, cy )) // target reached
			{
				tx = cx;
				ty = cy;
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
					if(temp.isClosed(passId))
						continue;
				
//					if(swarm.isOmniUnpassable(temp.getX(), temp.getY()))
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
								((dx == 0 || dy == 0) ? 1 : FastMath.SQRT2) + 
								temp.getDangerFactor();
//					if(temp == null)
//						temp = beacons[x][y] = new AStarNode(x, y);
					
					
					if(!temp.isOpen(passId)) // not yet visited
					{
						temp.markOpen(passId);
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
			Vector2D flow = Vector2D.R( 0, 0 );
			AStarNode node = swarm.getBeaconByIndex( tx, ty );
			while(node.origin != null)
			{
				
				cx = node.origin.getX();
				cy = node.origin.getY();
				flow.setxy(node.getX()-node.origin.getX(), node.getY()-node.origin.getY()).normalize();//.mul(0.1);
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
				
				swarm.setModifiedByIndex( node.getX(), node.getY() );
			}	
		}
//		System.out.println("pathing end");
		
		// TODO: replace with pass id check:
		for(AStarNode node : openNodes)
		{
			node.unmarkOpen();
			node.reset();
		}
		
//		swarm.fireGridModified();
//		System.out.println("pathing finished");
		isRunning = false;
	}
	@Override
	public int getId() { return this.getClass().hashCode(); }
}
