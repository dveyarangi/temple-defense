package yarangi.game.temple.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
import yarangi.graphics.colors.Color;
import yarangi.graphics.lights.CircleLightBehavior;
import yarangi.graphics.quadraturin.actions.IAction;
import yarangi.graphics.quadraturin.events.CursorEvent;
import yarangi.graphics.quadraturin.events.CursorListener;
import yarangi.graphics.quadraturin.events.UserActionEvent;
import yarangi.graphics.quadraturin.objects.SceneEntity;
import yarangi.graphics.quadraturin.simulations.IPhysicalObject;
import yarangi.math.DistanceUtils;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;
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
	private List <Fireable> fireables = new LinkedList <Fireable> ();

	
	private Vector2D cursorLocation;
	
	private SceneEntity highlighted;
	
	private boolean fireStopped = false;
	
	private boolean isMouseButtonHeld = false;
	
	private Set <IPhysicalObject> observedEntities = new HashSet <IPhysicalObject> ();
	private IPhysicalObject targetEntity;
	private IIntellectCore core = new IIntellectCore("netcore");
	
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
	
	public void addFireable(Fireable fireable)
	{
		fireables.add(fireable);
	}
	
	public void removeFireable(Fireable fireable)
	{
		fireables.remove(fireable);
	}
	
	public Playground getPlayground() { return playground; }

	public  List <Fireable> getFireables() { return fireables; }


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
		boolean found = false;
		for(IPhysicalObject object : observedEntities)
		{
			if(targetEntity == object)
			{
				found = true;
//				break;
			}
			double d = DistanceUtils.calcDistanceSquare(cursor.getAABB().x, cursor.getAABB().y, object.getAABB().x, object.getAABB().y);
			if(d < distance && d < 200)
			{
				distance = d;
				temp = object;

			}
		}
		
		if(!found)
			targetEntity = null;
		
		if(temp != null)
			targetEntity = temp;
		
		if(targetEntity == null && !observedEntities.isEmpty())
			targetEntity = observedEntities.iterator().next();
		
		filter.setHighlighted(targetEntity);
		if(targetEntity != null)
		{
			highlight.getAABB().x = targetEntity.getAABB().x; 
			highlight.getAABB().y = targetEntity.getAABB().y; 
			highlight.getAABB().r = targetEntity.getAABB().r;
		}	
		else 
			highlight.getAABB().x = 10000;
		observedEntities.clear();
	}
	
	public IFeedbackBeacon createFeedbackBeacon(ISpatialObject source) 
	{
		if(!observedEntities.isEmpty())
		{
			return new LinearFeedbackBeacon(source, observedEntities.iterator().next());
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
//		System.out.println(observedEntities.size());
//		IPhysicalObject object = targets.get(fireable);
//		if(object != null)
//			return object;
		
//		if(object.)
			
//		if (observedEntities.isEmpty()) 
//			return null;
		
//		object = observedEntities.iterator().next();
		
//		if(targetEntity != null)
//		targets.put(fireable, targetEntity);
		
		return targetEntity;
	}
	
	public Map <Fireable, IPhysicalObject> getTargets()
	{
		return targets;
	}
	
	public double aquireFireAngle(Fireable fireable)
	{
		IPhysicalObject target = getTarget(fireable);
		if(target == null)
			return -1;
		double angle = core.pickAngle(target);
		return angle;
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
