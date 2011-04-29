package yarangi.game.temple.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.media.opengl.GL;

import yarangi.game.temple.Playground;
import yarangi.game.temple.actions.Fireable;
import yarangi.game.temple.ai.IFeedbackBeacon;
import yarangi.game.temple.ai.IIntellectCore;
import yarangi.game.temple.ai.LinearFeedbackBeacon;
import yarangi.game.temple.model.temple.ObserverBehavior;
import yarangi.game.temple.model.temple.ObserverEntity;
import yarangi.game.temple.model.temple.TempleEntity;
import yarangi.game.temple.model.weapons.Projectile;
import yarangi.game.temple.model.weapons.Weapon;
import yarangi.graphics.colors.Color;
import yarangi.graphics.quadraturin.actions.IAction;
import yarangi.graphics.quadraturin.events.CursorEvent;
import yarangi.graphics.quadraturin.events.CursorListener;
import yarangi.graphics.quadraturin.events.UserActionEvent;
import yarangi.graphics.quadraturin.objects.SceneEntity;
import yarangi.graphics.quadraturin.simulations.IPhysicalObject;
import yarangi.math.DistanceUtils;
import yarangi.math.Vector2D;
import yarangi.spatial.AABB;
import yarangi.spatial.ISpatialFilter;
import yarangi.spatial.ISpatialObject;

public class ControlEntity extends SceneEntity implements CursorListener
{

	private static final long serialVersionUID = -2094957235603223096L;

	private TempleEntity temple;
	
	private Playground playground ;
	/**
	 * List of currently selected weapons.
	 */
	private List <Weapon> fireables = new ArrayList <Weapon> ();

	
	private Vector2D cursorLocation;
	
	private SceneEntity highlighted;
	
	private boolean fireStopped = false;
	
	private boolean isMouseButtonHeld = false;
	
	private Set <IPhysicalObject> observedEntities = new HashSet <IPhysicalObject> ();
	private IIntellectCore core;
	
	private Map <Fireable, IPhysicalObject> targets = new HashMap <Fireable, IPhysicalObject> ();
	
/*	private Action cursorMovedAction = new Action() {
		@Override public void act(UserActionEvent event) {		// TODO Auto-generated method stub
			
		}
		
	};*/
	
	private IAction fireOnAction = new IAction() {
		public void act(UserActionEvent event) { isMouseButtonHeld = true; }
	};
	
	private IAction fireOffAction = new IAction() {
		public void act(UserActionEvent event) { /*isMouseButtonHeld = false;*/ }
	};

	
	ObserverEntity cursor;

	
	ObserverEntity highlight;
	
	HighLightFilter filter = new HighLightFilter();

	public ControlEntity(Playground playground, TempleEntity temple) 
	{
		super(new AABB(0,0,0,0));
		
		this.temple = temple;
		this.playground = playground;
		
		setLook(new ControlLook());
		setBehavior(new ControlBehavior());
		
		highlight = new ObserverEntity(new AABB(0, 0, 10, 0), this, filter,
		256,  new Color(1.0f,0.3f,0.3f,1f));
		highlight.setBehavior(new ObserverBehavior(playground.getWorldVeil().getEntityIndex()));
		playground.addEntity(highlight);
		
		cursor = new ObserverEntity(new AABB(0, 0, 10, 0), this, 64,  new Color(1.0f,1.0f,0.5f,1f));		
		cursor.setBehavior(new ObserverBehavior(playground.getWorldVeil().getEntityIndex()));
		playground.addEntity(cursor);
		
		core = new IIntellectCore("netcore", playground.getWorldVeil().getWidth(), playground.getWorldVeil().getHeight());
	}

	
	public TempleEntity getTemple() { return temple; }
	public boolean isFireStopped()
	{
		return fireStopped;
	}
	
	public void setFireStopped(boolean stopped)
	{
		this.fireStopped = stopped;
	}
	
	public void addFireable(Weapon fireable)
	{
		fireables.add(fireable);
	}
	
	public void removeFireable(Weapon fireable)
	{
		fireables.remove(fireable);
	}
	
	public Playground getPlayground() { return playground; }

	public  List <Weapon> getFireables() { return fireables; }


	public boolean isPickable() { return false; }


	public void onCursorMotion(CursorEvent event) 
	{
		cursorLocation = event.getWorldLocation();
		highlighted = event.getEntity();
		
		if(cursorLocation != null)
		{
		cursor.getAABB().x = cursorLocation.x;
		cursor.getAABB().y = cursorLocation.y;
		}
		else
		{
			cursor.getAABB().x = 100000;
			cursor.getAABB().y = 100000;
			}
	}
	
	public Vector2D getCursorLocation()
	{
		return cursorLocation;
	}



	public boolean isButtonHeld() {
		return isMouseButtonHeld;
	}

	public IAction getFireOnAction() { return fireOnAction; }
	public IAction getFireOffAction() { return fireOffAction; }


	public SceneEntity getHighlighted() { return highlighted; }
	
	public void objectObserved(IPhysicalObject object)
	{
		
		this.observedEntities.add(object);
	}
	
	public void clearObservedObjects() 
	{
		double distance = Double.MAX_VALUE;
		IPhysicalObject temp = null;
		
		boolean [] found = new boolean [fireables.size()];
		for(IPhysicalObject object : observedEntities)
		{
			for(int idx = 0; idx < fireables.size(); idx ++)
				if(object == targets.get(fireables.get(idx)))
				{
					found[idx] = true;
				}
			double d = DistanceUtils.calcDistanceSquare(cursor.getAABB().x, cursor.getAABB().y, object.getAABB().x, object.getAABB().y);
			if(d < distance && d < 200)
			{
				distance = d;
				temp = object;

			}
		}
		
		Iterator <IPhysicalObject> it = observedEntities.iterator();
		for(int idx = 0; idx < fireables.size(); idx ++)
		{
			if(!found[idx])
			{
				while(it.hasNext())
				{
					IPhysicalObject o = it.next();
					if(!targets.values().contains(o))
					{
						targets.put(fireables.get(idx), o);
						break;
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
	
	public IFeedbackBeacon createFeedbackBeacon(Weapon weapon) 
	{
		if(!observedEntities.isEmpty())
		{
			double speed = weapon.getWeaponProperties().getProjectileSpeed();
			double angle = weapon.getAbsoluteAngle();
//			return core.pickTrackPoint(fireable.getAABB(), new Vector2D(speed, angle, true), target);
			return new LinearFeedbackBeacon(weapon, observedEntities.iterator().next(), new Vector2D(speed, angle, true));
		}
		return null;
	}


	public void dispatchFeedback(IFeedbackBeacon beacon) {
		core.processFeedback(beacon);
	}

//	public IPhysicalObject getTarget(Fireable fireable) {
//		System.out.println(observedEntities.size());
//		return observedEntities.isEmpty() ? null : observedEntities.iterator().next();
//	}
	public IPhysicalObject getTarget(Fireable fireable) 
	{
		return targets.get(fireable);
	}
	
	public Map <Fireable, IPhysicalObject> getTargets()
	{
		return targets;
	}
	
	public Vector2D acquireTrackPoint(Weapon fireable)
	{
		IPhysicalObject target = getTarget(fireable);
		if(target == null)
			return null;
		
		double speed = fireable.getWeaponProperties().getProjectileSpeed();
		double angle = fireable.getAbsoluteAngle();
		return core.pickTrackPoint(fireable.getAABB(), new Vector2D(speed, angle, true), target);
//
	}
	
	public class HighLightFilter implements ISpatialFilter
	{
		private ISpatialObject highlighted;
		public void setHighlighted(ISpatialObject object)
		{
			this.highlighted = object;
		}
		@Override
		public boolean accept(ISpatialObject entity) {
			return !(entity instanceof Projectile) && entity != highlighted;
		}
		
	}
	
	public void destroy(GL gl)
	{
		super.destroy(gl);
		core.save();
	}



}
