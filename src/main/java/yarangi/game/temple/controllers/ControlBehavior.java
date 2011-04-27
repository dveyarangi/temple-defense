package yarangi.game.temple.controllers;

import yarangi.game.temple.actions.Fireable;
import yarangi.game.temple.model.weapons.Projectile;
import yarangi.graphics.quadraturin.objects.Behavior;

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
					Projectile fire = fireable.fire();
					
					if(fire == null)
						continue;
					
					fire.setFeedback(ctrl.createFeedbackBeacon(fireable));
					ctrl.getPlayground().addEntity(fire);
					
					
				}
				ctrl.setFireStopped(false);
			}
		}
		ctrl.clearObservedObjects();
	
		
//		Vector2D cp = ctrl.getCursorLocation();
//		if ( cp != null)
//		ctrl.getPlayground().getBackground().setLightPosition(new float [] {(float)cp.x, (float)cp.y, 1.0f, 1.0f});
		
		return false;
	}

}
