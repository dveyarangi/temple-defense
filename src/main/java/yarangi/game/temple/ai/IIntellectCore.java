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
import yarangi.graphics.quadraturin.simulations.IPhysicalObject;
import yarangi.math.Vector2D;

public class IIntellectCore 
{
	
//	private BackpropNetwork network = new BackpropNetwork(1);
	private NeuralNetworkRunner runner; 
	private BackpropNetwork network;
	
	private String name;
	static {
		InitializerFactory.setWeightsInitializer(new RandomWeightsInitializer(-1, 1));

	}
	
	private Logger log;

	
	public IIntellectCore(String name, int worldWidth, int worldHeight)
	{
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
		
		runner = new NeuralNetworkRunner(normalizer, network);
		
	}

	public void processFeedback(IFeedbackBeacon capsule) 
	{
		LinearFeedbackBeacon beacon = (LinearFeedbackBeacon) capsule;

//		if(beacon.getDistance() < 400000)
		{
			double [] input = createInput(beacon.getLocation(), beacon.getVelocity(), beacon.getSource().getArea().getRefPoint(), beacon.getProjectileVelocity());
			Vector2D toTarget = capsule.getTarget().getArea().getRefPoint().minus(beacon.getSource().getArea().getRefPoint());
			double [] res = runner.run(input);
//			System.out.println("training target: " + capsule.getTarget().getArea().getRefPoint() + " delta: " + (toTarget.x-res[0]) +","+ (toTarget.y-res[1]));
//			System.out.println("feedback: d:" + beacon.getDistance() + " a:" + Math.cos(beacon.getAngle())+ ":" + Math.sin(beacon.getAngle()) + " net: " + res[0] + ":" + res[1]);

			runner.train(new double [] {toTarget.x, toTarget.y}, 0.01);
		}
	}

	public Vector2D pickTrackPoint(Vector2D sourceLocation, Vector2D projectileVelocity, IPhysicalObject target) {
		double [] res = runner.run(createInput(target.getArea().getRefPoint(), target.getVelocity(), sourceLocation, projectileVelocity));
		
		return new Vector2D(res[0], res[1]);
//		return Math.atan2(res[1], res[0]);
	}
	private double [] createInput(Vector2D targetLocation, Vector2D targetVelocity, Vector2D sourceLocation, Vector2D projectileVelocity)
	{
		return new double [] {
				targetLocation.x,
				targetLocation.y, 
//				Math.cos(target.getAABB().a),
//				Math.sin(target.getAABB().a),
				targetVelocity.x,
				targetVelocity.y,
				sourceLocation.x,
				sourceLocation.y,
				projectileVelocity.x,
				projectileVelocity.y
			};
	}

	public void save() {
//		System.out.println("hehe");
		NeuralNetworkRunner.save(network, name);
		log.debug("Saved NN core [" + name + "].");
	}
}
