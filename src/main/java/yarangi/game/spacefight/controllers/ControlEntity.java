package yarangi.game.spacefight.controllers;

import java.util.LinkedList;
import java.util.List;

import yarangi.game.spacefight.Playground;
import yarangi.game.spacefight.actions.Fireable;
import yarangi.game.spacefight.model.temple.TempleEntity;
import yarangi.graphics.quadraturin.actions.Action;
import yarangi.graphics.quadraturin.events.CursorMotionEvent;
import yarangi.graphics.quadraturin.events.CursorMotionListener;
import yarangi.graphics.quadraturin.events.UserActionEvent;
import yarangi.graphics.quadraturin.interaction.spatial.AABB;
import yarangi.graphics.quadraturin.objects.SceneEntity;
import yarangi.math.Vector2D;

public class ControlEntity extends SceneEntity implements CursorMotionListener
{

	private static final long serialVersionUID = -2094957235603223096L;

	public static final int FIRE_ACTION = 11111111;
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
	
	private Action fireAction = new Action()
	{
		public void act(UserActionEvent event) {
				isMouseButtonHeld = event.isPressed() && event.getInputCode() == UserActionEvent.MOUSE_LEFT_BUTTON;
		}
		
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


	public void onCursorMotion(CursorMotionEvent event) 
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

	public Action getFireAction() { return fireAction; }


	public SceneEntity getHighlighted() { return highlighted; }

}
