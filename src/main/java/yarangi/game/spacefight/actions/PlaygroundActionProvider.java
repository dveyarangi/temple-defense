package yarangi.game.spacefight.actions;

import yarangi.game.spacefight.model.temple.TempleEntity;
import yarangi.graphics.quadraturin.ViewPoint2D;
import yarangi.graphics.quadraturin.actions.Navigation2DActionProvider;
import yarangi.graphics.quadraturin.events.UserActionEvent;

public class PlaygroundActionProvider extends Navigation2DActionProvider
{
	public PlaygroundActionProvider(TempleEntity temple, ViewPoint2D vp) {
		super(vp);
		
		
		addAction(UserActionEvent.MOUSE_LEFT_BUTTON, temple.getController().getFireAction());
	}
}
