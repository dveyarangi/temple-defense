package yarangi.game.temple.ai;

import org.apache.log4j.Logger;

import yarangi.ai.nn.init.InitializerFactory;
import yarangi.ai.nn.init.RandomWeightsInitializer;
import yarangi.ai.nn.numeric.ArrayInput;
import yarangi.ai.nn.numeric.BackpropNetwork;
import yarangi.ai.nn.numeric.CompleteNeuronLayer;
import yarangi.ai.nn.numeric.LinearAF;
import yarangi.ai.nn.numeric.NeuralNetworkRunner;
import yarangi.ai.nn.numeric.Normalizer;
import yarangi.ai.nn.numeric.NumericAF;
import yarangi.ai.nn.numeric.ScalingNormalizer;
import yarangi.ai.nn.numeric.TanHAF;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.math.Vector2D;

/**
 * 
 * Adapter for 
 * 
 * @author dveyarangi
 *
 */
public class NetCore extends NeuralNetworkRunner implements IntellectCore
{
	
//	private BackpropNetwork network = new BackpropNetwork(1);
	private String name;
	
	/** 
	 * Back-propagate neural network algorithm. 
	 */
	private BackpropNetwork network;
	
	
	static {
		InitializerFactory.setWeightsInitializer(new RandomWeightsInitializer(-1, 1));

	}
	
	private Logger log;

	
	public NetCore(String name, int worldWidth, int worldHeight)
	{
		super();
		this.name = name;
		
		this.log = Logger.getLogger(name);
		try {
			network = NeuralNetworkRunner.load(name);
			log.debug("Loaded NN core " + name);
		} catch (Exception e) {
			log.debug("Cannot load NN core " + name + ", creating new one");
			network = new BackpropNetwork(2);
			network.addLayer(new CompleteNeuronLayer(new NumericAF [] { new LinearAF(0.1), new LinearAF(0.1), new LinearAF(0.1), new LinearAF(0.1), new LinearAF(0.1), new LinearAF(0.1), new LinearAF(0.1), new LinearAF(0.1), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF() }, 1));
			network.addLayer(new CompleteNeuronLayer(new NumericAF [] { new LinearAF(0.1), new LinearAF(0.1), new LinearAF(0.1), new LinearAF(0.1), new LinearAF(0.1), new LinearAF(0.1), new LinearAF(0.1), new LinearAF(0.1), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF() }, 1));
			network.addLayer(new CompleteNeuronLayer(new NumericAF [] { new LinearAF(0.1), new LinearAF(0.1), new LinearAF(0.1), new LinearAF(0.1), new LinearAF(0.1), new LinearAF(0.1), new LinearAF(0.1), new LinearAF(0.1), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF() }, 1));
//			network.addLayer(new CompleteNeuronLayer(10, new NumericAF [] { new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF()}, 1));
			network.addInput(new ArrayInput(new double [8]));
		}
		
//		network.addLayer(new CompleteNeuronLayer(10, new TanHAF(), 1));
		
		Normalizer normalizer = new ScalingNormalizer(
						new double [] {-2*worldWidth, -2*worldHeight, -20, -20, -2*worldWidth, -2*worldHeight, -20, -20}, 
						new double [] { 2*worldWidth,  2*worldHeight,  20,  20,  2*worldWidth,  2*worldHeight,  20,  20},
						new double [] {-worldWidth, -worldHeight}, 
						new double [] { worldWidth,  worldHeight});
		
		setNetwork(network);
		setNormalizer(normalizer);
	}

	public boolean processFeedback(IFeedbackBeacon capsule) 
	{
		LinearFeedbackBeacon beacon = (LinearFeedbackBeacon) capsule;

		if(beacon.getDistance() < 400000) // TODO: real beacon location test
		{
			Vector2D relativeTarget = beacon.getLocation().minus(beacon.getSource().getArea().getRefPoint());
			double [] input = createInput(relativeTarget, beacon.getVelocity(),/*beacon.getSource().getArea().getRefPoint(),*/ beacon.getProjectileVelocity());
			
//			System.out.println(beacon.getSource().getArea().getRefPoint());
			double [] res = run(input);
//			System.out.println("training target: " + capsule.getTarget().getArea().getRefPoint() + " delta: " + (toTarget.x-res[0]) +","+ (toTarget.y-res[1]));
//			System.out.println("feedback: d:" + beacon.getDistance() + " a:" + Math.cos(beacon.getAngle())+ ":" + Math.sin(beacon.getAngle()) + " net: " + res[0] + ":" + res[1]);

			Vector2D toTarget = beacon.getTarget().getArea().getRefPoint().minus(beacon.getSource().getArea().getRefPoint());
//			System.out.println(toTarget + " " + relativeTarget);
			train(new double [] {toTarget.x(), toTarget.y()}, 0.005);
			
			return true;
		}
		
		return false;
	}

	public Vector2D pickTrackPoint(Vector2D sourceLocation, Vector2D projectileVelocity, IEntity target) 
	{
//		System.out.println(sourceLocation);
		Vector2D relativeTarget = target.getArea().getRefPoint().minus(sourceLocation);
		double [] res = run(createInput(relativeTarget, target.getBody().getVelocity(), /*sourceLocation,*/ projectileVelocity));
//		System.out.println(res[0] + " : " + res[1]);
		return new Vector2D(res[0], res[1]).plus(sourceLocation);
//		return Math.atan2(res[1], res[0]);
	}
	private double [] createInput(Vector2D targetLocation, Vector2D targetVelocity, Vector2D projectileVelocity)
	{
		return new double [] {
				targetLocation.x(),
				targetLocation.y(), 
//				Math.cos(target.getAABB().a),
//				Math.sin(target.getAABB().a),
				targetVelocity.x(),
				targetVelocity.y(),
				/*sourceLocation.x,
				sourceLocation.y,*/1,1,
				projectileVelocity.x(),
				projectileVelocity.y()
			};
	}

	@Override
	public void shutdown() {
		NeuralNetworkRunner.save(network, name);
		log.debug("Saved NN core [" + name + "].");
//		log.debug("NN core [" + name + "] is NOT saved.");
	}
}
