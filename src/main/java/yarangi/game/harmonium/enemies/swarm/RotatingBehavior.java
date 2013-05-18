package yarangi.game.harmonium.enemies.swarm;

import yar.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.math.Vector2D;

public class RotatingBehavior implements IBehaviorState <Swarm> 
{

	@Override
	public double behave(double time, Swarm swarm) {
//		Vector2D loc = swarm.getArea().getRefPoint();
		
/*		double d = Math.hypot(400, 400);
		double a = Math.atan2(loc.y, loc.x);
		a += 0.01;
//		System.out.println(d + " ::: " + a);
		swarm.getArea().translate(d * Math.cos(a), d * Math.sin(a));*/
		return 0;
	}
	public int getId() { return this.getClass().hashCode(); }

}
