package yarangi.game.harmonium.enemies.swarm.agents;

import yar.quadraturin.objects.IBehavior;
import yar.quadraturin.objects.IEntity;
import yar.quadraturin.objects.ISensor;
import yar.quadraturin.objects.Sensor;
import yar.quadraturin.objects.behaviors.IBehaviorState;
import yar.quadraturin.terrain.ITerrain;
import yarangi.game.harmonium.enemies.swarm.Swarm;
import yarangi.math.Angles;
import yarangi.math.Geometry;
import yarangi.math.IVector2D;
import yarangi.numbers.RandomUtil;

public class DroneBehavior /*extends Sensor <IEntity>*/ implements IBehaviorState <SwarmAgent>, IBehavior <SwarmAgent>
{
	double da = RandomUtil.STD(0, 0.1);
	
	double inertion;
	
	public DroneBehavior(double inertion) {
		this.inertion = inertion;
	}
	
	public void setInertion(double inertion)
	{
		this.inertion = inertion;
	}

	@Override
	public double behave(double time, SwarmAgent agent) {
		
/*		if(agent.getTarget() != null)
		{
			double dist = Geometry.calcHypotSquare( agent.getTarget().getArea().getAnchor(), agent.getArea().getAnchor());
			if(dist < 900)
			{
				
			}
		}*/
		
		Swarm swarm = agent.getSwarm();
		IVector2D flow = swarm.getFlow(agent.getArea().getAnchor());
		double a = Angles.atan2Deg(  agent.getBody().getVelocity().y(), agent.getBody().getVelocity().x());
		agent.getArea().setOrientation( a );

//		System.out.println(flow);
		if(flow != null) {
			if(inertion == 0) {
				agent.getBody().addVelocity( flow.x(), flow.y() );
			}
			else
//			if(agent.getBody().getForce().x() == 0 && agent.getBody().getForce().y() == 0)
				agent.getBody().setForce( flow.x()/inertion, flow.y()/inertion );
//			else
//				agent.getBody().addForce(flow.x(), flow.y());
		}
		return 0;
	}
	
	@Override
	public int getId() { return this.getClass().hashCode(); }

	@Override
	public boolean behave(double time, SwarmAgent agent, boolean isVisible)
	{
		behave(time, agent);
		return true;
	}

}
