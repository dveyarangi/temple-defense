package yarangi.game.temple.controllers;

import yarangi.game.temple.model.temple.BattleInterface;
import yarangi.game.temple.model.weapons.Projectile;
import yarangi.game.temple.model.weapons.Weapon;
import yarangi.graphics.quadraturin.objects.Behavior;

public class ControlBehavior implements Behavior <TempleController> 
{


	public boolean behave(double time, TempleController ctrl, boolean isVisible) 
	{
//		FireAction action = ctrl.getFireAction();
		BattleInterface bi = ctrl.getBattleInterface();
//		if (action.isActive())
		{
		
/*			if(!ctrl.isButtonHeld())
			{
				if(!ctrl.isFireStopped())
				{
					for(Weapon fireable : ctrl.getFireables())
						fireable.stop();
					ctrl.setFireStopped(true);
				}
			}
			else
			{*/
				for(Weapon fireable : bi.getFireables())
				{
					// TODO: not good
					Projectile fire = fireable.fire();
					
					if(fire == null)
						continue;
					fire.setFeedback(bi.createFeedbackBeacon(fireable));
					ctrl.getScene().addEntity(fire);
					
					
				}
//				ctrl.setFireStopped(false);
			//}
		}
		bi.clearObservedObjects();
	
		
//		Vector2D cp = ctrl.getCursorLocation();
//		if ( cp != null)
//		ctrl.getPlayground().getBackground().setLightPosition(new float [] {(float)cp.x, (float)cp.y, 1.0f, 1.0f});
		
		return false;
	}

}