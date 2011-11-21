package yarangi.game.temple.model.temple;

import yarangi.game.temple.model.resource.Port;
import yarangi.game.temple.model.resource.Resource;
import yarangi.graphics.quadraturin.objects.Behavior;

public class ShieldBehavior implements Behavior<Shield>
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
