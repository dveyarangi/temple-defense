package yarangi.game.temple.controllers;

import java.util.LinkedList;
import java.util.List;

import yarangi.game.temple.Playground;
import yarangi.game.temple.actions.Fireable;
import yarangi.game.temple.model.temple.TempleEntity;
import yarangi.graphics.quadraturin.actions.IAction;
import yarangi.graphics.quadraturin.events.CursorEvent;
import yarangi.graphics.quadraturin.events.CursorListener;
import yarangi.graphics.quadraturin.events.UserActionEvent;
import yarangi.graphics.quadraturin.objects.SceneEntity;
import yarangi.math.Vector2D;
import yarangi.spatial.AABB;

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
	
/*	private Action cursorMovedAction = new Action() {
		@Override public void act(UserActionEvent event) {		// TODO Auto-generated method stub
			
		}
		
	};*/
	
	private IAction fireOnAction = new IAction() {
		public void act(UserActionEvent event) { isMouseButtonHeld = true; }
	};
	
	private IAction fireOffAction = new IAction() {
		public void act(UserActionEvent event) { isMouseButtonHeld = false; }
	};

	
	public ControlEntity(Playground playground, TempleEntity temple) 
	{
		super(new AABB(0,0,0,0));
		
		this.temple = temple;
		this.playground = playground;
		
		setLook(new ControlLook());
		setBehavior(new ControlBehavior());
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

}
