package yarangi.game.temple;

import java.util.Map;

import yarangi.graphics.quadraturin.actions.IAction;
import yarangi.graphics.quadraturin.events.UserActionEvent;

public class Debug
{
	public static Map <String, IAction> appendDebugActions(Map <String, IAction> actionsMap, final Playground playground)
	{
		actionsMap.put("swarm-debug", new IAction() {
			@Override
			public void act(UserActionEvent event)
			{
				playground.toggleSwarmOverlay();
			}});
		
		return actionsMap;
	}
}
