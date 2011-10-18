package yarangi.game.temple.controllers;

import yarangi.game.temple.controllers.bots.BotInterface;
import yarangi.game.temple.model.temple.BattleInterface;
import yarangi.game.temple.model.weapons.Projectile;
import yarangi.game.temple.model.weapons.Weapon;
import yarangi.graphics.quadraturin.objects.Behavior;

public class ControlBehavior implements Behavior <TempleController> 
{


	public boolean behave(double time, TempleController ctrl, boolean isVisible) 
	{
		BattleInterface bi = ctrl.getBattleInterface();
		{

				for(Weapon fireable : bi.getFireables())
				{
					// TODO: not good
					Projectile fire = fireable.fire();
					
					if(fire == null)
						continue;
					fire.setFeedback(bi.createFeedbackBeacon(fireable));
					ctrl.getScene().addEntity(fire);
					
					
				}
		}
		bi.clearObservedObjects();
		
		BotInterface bots = ctrl.getBotInterface();
		double transferRate = bots.changeTransferRate( 0.01 * time );
		
		// temple heart-beat:
		ctrl.getTemple().setHealth((transferRate-BotInterface.MIN_TRANSFER_RATE)/(BotInterface.MAX_TRANSFER_RATE-BotInterface.MIN_TRANSFER_RATE));
	
		return false;
	}

}