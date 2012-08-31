package yarangi.game.harmonium.controllers;

import yarangi.game.harmonium.ai.economy.IOrderScheduler;
import yarangi.game.harmonium.ai.economy.StupidScheduler;
import yarangi.game.harmonium.temple.BattleInterface;
import yarangi.game.harmonium.temple.weapons.Projectile;
import yarangi.game.harmonium.temple.weapons.Weapon;
import yarangi.graphics.quadraturin.objects.IBehavior;

public class ControlBehavior implements IBehavior <TempleController> 
{


	@Override
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
					fire.setFeedback(bi.createFeedbackBeacon(fireable, fire));
					ctrl.getScene().addEntity(fire);
					
					
				}
		}
		bi.clearObservedObjects();
		
		IOrderScheduler bots = ctrl.getOrderScheduler();
		double transferRate = bots.changeTransferRate( 0.01 * time );
		
		// temple heart-beat:
//		ctrl.getTemple().setHealth((transferRate-StupidScheduler.MIN_TRANSFER_RATE)/(StupidScheduler.MAX_TRANSFER_RATE-StupidScheduler.MIN_TRANSFER_RATE));
	
		return false;
	}

}