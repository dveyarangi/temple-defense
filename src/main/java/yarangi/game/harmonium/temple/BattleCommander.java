package yarangi.game.harmonium.temple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.media.opengl.GL;

import yarangi.game.harmonium.ai.weapons.IFeedbackBeacon;
import yarangi.game.harmonium.ai.weapons.IntellectCore;
import yarangi.game.harmonium.ai.weapons.LinearFeedbackBeacon;
import yarangi.game.harmonium.controllers.TempleController;
import yarangi.game.harmonium.enemies.swarm.agents.SwarmAgent;
import yarangi.game.harmonium.environment.resources.Resource;
import yarangi.game.harmonium.temple.weapons.Projectile;
import yarangi.game.harmonium.temple.weapons.Weapon;
import yarangi.graphics.quadraturin.objects.IBeing;
import yarangi.math.Geometry;
import yarangi.math.IVector2D;
import yarangi.math.Vector2D;

public class BattleCommander implements BattleInterface
{

	private final TempleController controller;
	/**
	 * List of currently selected weapons.
	 */
	private final List <Weapon> fireables = new ArrayList <Weapon> ();
	private final Set <IBeing> observedEntities = new HashSet <IBeing> ();
	
	private final Map <Weapon, IBeing> targets = new HashMap <Weapon, IBeing> ();
	
//	private Vector2D guarded = new Vector2D(0,0);
	
	private final IntellectCore core;
	public BattleCommander(TempleController controller, IntellectCore core)
	{
		this.controller = controller;
		this.core = core;
	}
	
/*	@Override
	public Vector2D getGuarded(Weapon entity) {
		return guarded;
	}*/

	public IBeing getTarget(Weapon fireable) 
	{
		return targets.get(fireable);
	}
	
	@Override
	public Map <Weapon, IBeing> getTargets()
	{
		return targets;
	}

	@Override
	public double getBattleScale()
	{
		return controller.getScene().getFrameLength() * 10;
	}
	
	@Override
	public Vector2D acquireTrackPoint(Weapon fireable)
	{
		IBeing target = getTarget(fireable);
		if(target == null)
			return null;
		
//		double speed = fireable.getProps().getProjectileSpeed();
//		double angle = fireable.getArea().getOrientation();
		return core.pickTrackPoint(fireable.getArea().getAnchor(), 
				fireable.getProjectileSpeed(), target.getArea().getAnchor(), target.getBody().getVelocity());
//
	}
	
	@Override
	public void clearObservedObjects() 
	{
		double distance = Double.MAX_VALUE;
		
		boolean [] found = new boolean [fireables.size()];
		for(int i = 0; i < fireables.size(); i ++)
			found[i] = false;
//		Vector2D cursorLoc = cursor.getArea().getRefPoint();
		IVector2D objectLoc;
		for(IBeing object : observedEntities)
		{

			objectLoc = object.getArea().getAnchor();
			for(int idx = 0; idx < fireables.size(); idx ++)
				if(object == targets.get(fireables.get(idx)))
				{
					IVector2D weaponLoc = fireables.get(idx).getArea().getAnchor();
					double d = Geometry.calcHypotSquare(weaponLoc.x(), weaponLoc.y(), objectLoc.x(), objectLoc.y());
					double range = fireables.get(idx).getProps().getEffectiveRange();
					if(d < range*range)
						if(controller.testLOS(weaponLoc.x(), weaponLoc.y(), objectLoc.x(), objectLoc.y()))

						found[idx] = true;
					break;
				}
			
			
	/*		double d = Geometry.calcHypotSquare(cursorLoc.x, cursorLoc.y, objectLoc.x, objectLoc.y);
			if(d > distance)
			{
				distance = d;
				temp = object;

			}*/
		}
		
		Iterator <IBeing> it;
		Weapon fireable;
		for(int idx = 0; idx < fireables.size(); idx ++)
		{
			fireable = fireables.get(idx);
			if(found[idx] && fireable.isPoweredUp())
				continue;
			
			targets.remove(fireable);
			
			if(!fireable.isPoweredUp())
				continue;
			
			IVector2D weaponLoc = fireable.getArea().getAnchor();
			double range = fireable.getProps().getEffectiveRange()*fireable.getProps().getEffectiveRange();
			double minDistance = Double.MAX_VALUE;
			
			it = observedEntities.iterator();
			
			while(it.hasNext())
			{
				IBeing o = it.next();
				
				if(!(o instanceof SwarmAgent))
					continue;
				
				if(!targets.values().contains(o))
				{
					distance = Geometry.calcHypotSquare(o.getArea().getAnchor().x(), o.getArea().getAnchor().y(), weaponLoc.x(), weaponLoc.y());
					// distance = Geometry.calcHypotSquare(o.getArea().getRefPoint().x(), o.getArea().getRefPoint().y(), weaponLoc.x(), weaponLoc.y());
					if(distance < minDistance && range > distance)
					{
//						System.out.println("observed: " +fireables.size());
						if(!controller.testLOS(weaponLoc.x(), weaponLoc.y(), o.getArea().getAnchor().x(), o.getArea().getAnchor().y()))
							continue;
						minDistance = distance;
						targets.put(fireable, o);
					}
				}
			}
		}
		
		
/*		filter.setHighlighted(targetEntity);
		if(targetEntity != null)
		{
			highlight.getAABB().x = targetEntity.getAABB().x; 
			highlight.getAABB().y = targetEntity.getAABB().y; 
			highlight.getAABB().r = targetEntity.getAABB().r;
		}	
		else 
			highlight.getAABB().x = 10000;*/
		
		observedEntities.clear();
	}
	@Override
	public void objectObserved(IBeing object)
	{
		
		this.observedEntities.add(object);
	}
	
	@Override
	public IFeedbackBeacon createFeedbackBeacon(Weapon weapon, Projectile projectile) 
	{
		if(!observedEntities.isEmpty())
		{
			double speed = projectile.getBody().getVelocity().abs();
//			double angle = weapon.getArea().getOrientation();
			
//			return core.pickTrackPoint(fireable.getAABB(), new Vector2D(speed, angle, true), target);
			if(getTarget(weapon) != null)
			return new LinearFeedbackBeacon(weapon, getTarget(weapon), speed);
		}
		return null;
	}

	@Override
	public void dispatchFeedback(IFeedbackBeacon beacon) {
		core.processFeedback(beacon);
	}

	@Override
	public void destroy(GL gl) {
		core.shutdown();
	}
	
	@Override
	public void addFireable(Weapon fireable)
	{
		fireables.add(fireable);
	}
	
	public void removeFireable(Weapon fireable)
	{
		fireables.remove(fireable);
	}
	

	@Override
	public  List <Weapon> getFireables() { return fireables; }

	@Override
	public int requestResource(Weapon weapon, Resource.Type type, double amount)
	{
		return controller.getOrderScheduler().requestResource( weapon, controller.getTemple(), type, amount, 1 );
	}

	@Override
	public IntellectCore getCore()
	{
		return core;
	}


}