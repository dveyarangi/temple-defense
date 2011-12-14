package yarangi.game.temple.controllers;

import yarangi.game.temple.ai.economy.StupidScheduler;
import yarangi.game.temple.ai.economy.IOrderScheduler;
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
		
		IOrderScheduler bots = ctrl.getBotInterface();
		double transferRate = bots.changeTransferRate( 0.01 * time );
		
		// temple heart-beat:
		ctrl.getTemple().setHealth((transferRate-StupidScheduler.MIN_TRANSFER_RATE)/(StupidScheduler.MAX_TRANSFER_RATE-StupidScheduler.MIN_TRANSFER_RATE));
	
		return false;
	}

}