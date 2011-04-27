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
import yarangi.ai.nn.numeric.ScalingNormalizer;
import yarangi.ai.nn.numeric.TanHAF;
import yarangi.graphics.quadraturin.simulations.IPhysicalObject;
import yarangi.math.Angles;
import yarangi.math.Vector2D;

public class IIntellectCore 
{
	
//	private BackpropNetwork network = new BackpropNetwork(1);
	private NeuralNetworkRunner runner; 
	private BackpropNetwork network;
	private Normalizer normalizer;
	
	private String name;
	static {
		InitializerFactory.setWeightsInitializer(new RandomWeightsInitializer(-1, 1));

	}
	
	private Logger log;
	
	public IIntellectCore(String name)
	{
		this.name = name;
		
		this.log = Logger.getLogger(name);
		try {
			network = NeuralNetworkRunner.load(name);
			log.debug("Loaded NN core " + name);
		} catch (Exception e) {
			log.debug("Cannot load NN core " + name + ", creating new one");
			network = new BackpropNetwork(2);
			network.addLayer(new CompleteNeuronLayer(10, new TanHAF(), 1));
			network.addLayer(new CompleteNeuronLayer(20, new TanHAF(), 1));
			network.addLayer(new CompleteNeuronLayer(10, new TanHAF(), 1));
			network.addInput(new ArrayInput(new double [normalizer.inputSize()]));
		}
		
//		network.addLayer(new CompleteNeuronLayer(10, new TanHAF(), 1));
		
		normalizer = new ScalingNormalizer(new double [] {-1000, -1000, -10, -10}, new double [] {1000, 1000, 10, 10},
												      new double [] {-1000, -1000}, new double [] {1000, 1000});
		
		runner = new NeuralNetworkRunner(normalizer, network);
		
	}

	public void processFeedback(IFeedbackBeacon capsule) 
	{
		LinearFeedbackBeacon beacon = (LinearFeedbackBeacon) capsule;

//		if(beacon.getDistance() < 400000)
		{
			Vector2D toTarget = capsule.getTarget().getAABB().minus(beacon.getSource().getAABB());
			double [] res = runner.run(createInput(beacon.getLocation(), beacon.getVelocity()));
//			System.out.println("training target: " + capsule.getTarget().getAABB() + " delta: " + (toTarget.x-res[0]) +","+ (toTarget.y-res[1]));
//			System.out.println("feedback: d:" + beacon.getDistance() + " a:" + Math.cos(beacon.getAngle())+ ":" + Math.sin(beacon.getAngle()) + " net: " + res[0] + ":" + res[1]);

			runner.train(normalizer.normalizeInput(createInput(beacon.getLocation(), beacon.getVelocity())), 
					normalizer.normalizeOutput(new double [] {toTarget.x, toTarget.y}), 0.15);
		}
	}

	public double pickAngle(IPhysicalObject target) {
		double [] res = runner.run(createInput(target.getAABB(), target.getVelocity()));
		
		return Math.atan2(res[1], res[0]);
	}
	private double [] createInput(Vector2D location, Vector2D velocity)
	{
		return new double [] {
				location.x,
				location.y, 
//				Math.cos(target.getAABB().a),
//				Math.sin(target.getAABB().a),
				velocity.x,
				velocity.y
			};
	}

	public void save() {
//		System.out.println("hehe");
		runner.save(network, name);
		log.debug("Saved NN core " + name);
	}
}
