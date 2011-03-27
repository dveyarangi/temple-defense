package yarangi.game.spacefight.controllers;

import yarangi.game.spacefight.actions.Fireable;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.graphics.quadraturin.objects.SceneEntity;

public class ControlBehavior implements Behavior <ControlEntity> 
{


	public boolean behave(double time, ControlEntity ctrl, boolean isVisible) 
	{
//		FireAction action = ctrl.getFireAction();
		
//		if (action.isActive())
		{
		
			if(!ctrl.isButtonHeld())
			{
				if(!ctrl.isFireStopped())
				{
					for(Fireable fireable : ctrl.getFireables())
						fireable.stop();
					ctrl.setFireStopped(true);
				}
			}
			else
			{
				for(Fireable fireable : ctrl.getFireables())
				{
					SceneEntity fire = fireable.fire();
					
					if(fire == null)
						continue;
					ctrl.getPlayground().addEntity(fire);
				}
				ctrl.setFireStopped(false);
			}
		}
		
//		Vector2D cp = ctrl.getCursorLocation();
//		if ( cp != null)
//		ctrl.getPlayground().getBackground().setLightPosition(new float [] {(float)cp.x, (float)cp.y, 1.0f, 1.0f});
		
		return false;
	}

}
