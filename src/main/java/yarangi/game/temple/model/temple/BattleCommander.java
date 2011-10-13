package yarangi.game.temple.model.temple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.media.opengl.GL;

import yarangi.game.temple.actions.Fireable;
import yarangi.game.temple.ai.IFeedbackBeacon;
import yarangi.game.temple.ai.IntellectCore;
import yarangi.game.temple.ai.LinearFeedbackBeacon;
import yarangi.game.temple.controllers.TempleController;
import yarangi.game.temple.model.Resource;
import yarangi.game.temple.model.enemies.swarm.agents.SwarmAgent;
import yarangi.game.temple.model.weapons.Weapon;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.math.Geometry;
import yarangi.math.Vector2D;

public class BattleCommander implements BattleInterface
{
	private static final long serialVersionUID = -6746350958616093717L;

	private TempleController controller;
	/**
	 * List of currently selected weapons.
	 */
	private List <Weapon> fireables = new ArrayList <Weapon> ();
	private Set <IEntity> observedEntities = new HashSet <IEntity> ();
	
	private Map <Fireable, IEntity> targets = new HashMap <Fireable, IEntity> ();
	
//	private Vector2D guarded = new Vector2D(0,0);
	
	private IntellectCore core;
	public BattleCommander(TempleController controller, IntellectCore core)
	{
		this.controller = controller;
		this.core = core;
	}
	
/*	@Override
	public Vector2D getGuarded(Weapon entity) {
		// TODO Auto-generated method stub
		return guarded;
	}*/

	public IEntity getTarget(Fireable fireable) 
	{
		return targets.get(fireable);
	}
	
	@Override
	public Map <Fireable, IEntity> getTargets()
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
		IEntity target = getTarget(fireable);
		if(target == null)
			return null;
		
		double speed = fireable.getWeaponProperties().getProjectileSpeed();
		double angle = fireable.getArea().getOrientation();
		return core.pickTrackPoint(fireable.getArea().getRefPoint(), new Vector2D(speed, angle, true), target);
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
		Vector2D objectLoc;
		for(IEntity object : observedEntities)
		{

			objectLoc = object.getArea().getRefPoint();
			for(int idx = 0; idx < fireables.size(); idx ++)
				if(object == targets.get(fireables.get(idx)))
				{
					Vector2D weaponLoc = fireables.get(idx).getArea().getRefPoint();
					double d = Geometry.calcHypotSquare(weaponLoc.x(), weaponLoc.y(), objectLoc.x(), objectLoc.y());
					if(d < fireables.get(idx).getWeaponProperties().getEffectiveRange())
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
		
		Iterator <IEntity> it;
		Weapon fireable;
		for(int idx = 0; idx < fireables.size(); idx ++)
		{
			if(found[idx])
				continue;
			it = observedEntities.iterator();
			
			fireable = fireables.get(idx);
			Vector2D weaponLoc = fireable.getArea().getRefPoint();
			double range = fireable.getWeaponProperties().getEffectiveRange();
			double minDistance = Double.MAX_VALUE;
			
			targets.remove(fireable);
			while(it.hasNext())
			{
				IEntity o = it.next();
				
				if(!(o instanceof SwarmAgent))
					continue;
				
				if(!targets.values().contains(o))
				{
					distance = Geometry.calcHypotSquare(o.getArea().getRefPoint().x(), o.getArea().getRefPoint().y(), weaponLoc.x(), weaponLoc.y());
					// distance = Geometry.calcHypotSquare(o.getArea().getRefPoint().x(), o.getArea().getRefPoint().y(), weaponLoc.x(), weaponLoc.y());
					if(distance < minDistance && range > distance)
					{
//						System.out.println("observed: " +fireables.size());
						if(!controller.testLOS(weaponLoc.x(), weaponLoc.y(), o.getArea().getRefPoint().x(), o.getArea().getRefPoint().y()))
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
	public void objectObserved(IEntity object)
	{
		
		this.observedEntities.add(object);
	}
	
	public IFeedbackBeacon createFeedbackBeacon(Weapon weapon) 
	{
		if(!observedEntities.isEmpty())
		{
			double speed = weapon.getWeaponProperties().getProjectileSpeed();
			double angle = weapon.getArea().getOrientation();
			
//			return core.pickTrackPoint(fireable.getAABB(), new Vector2D(speed, angle, true), target);
			return new LinearFeedbackBeacon(weapon, getTarget(weapon), new Vector2D(speed, angle, true));
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
	public int requestResource(Weapon weapon, Resource resource)
	{
		return controller.getBotInterface().requestResource( weapon, controller.getTemple(), resource, 1 );
	}


}