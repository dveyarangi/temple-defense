package yarangi.game.harmonium;

import java.util.Map;

import yar.quadraturin.actions.IAction;
import yar.quadraturin.events.UserActionEvent;

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
