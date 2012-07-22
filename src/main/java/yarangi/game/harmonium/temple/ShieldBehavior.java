package yarangi.game.harmonium.temple;

import yarangi.game.harmonium.environment.resources.Port;
import yarangi.game.harmonium.environment.resources.Resource;
import yarangi.graphics.quadraturin.objects.IBehavior;

public class ShieldBehavior implements IBehavior<Shield>
{
	private float normalRadius = 100;
	private float speed;
	
	
		@Override
	public boolean behave(double time, Shield shield, boolean isVisible) {
		
			double radius = shield.getArea().getMaxRadius();
			Port port = shield.getPort();
			double resourcePercent = port.get( Resource.Type.ENERGY ).getAmount() / port.getCapacity( Resource.Type.ENERGY );
			float targetRadius = 1*(float)(normalRadius * (resourcePercent + 0.6f));
			float force = (float)((targetRadius-radius))* 0.00001f;
			speed += force * time / 2;
			
			if(speed > 0.01) speed -= 0.0001*time;
			else
			if(speed < 0.01) speed += 0.0001*time;
			
			radius += speed*time;
			shield.getArea().fitTo( radius );
			
			return true;
	}


}
