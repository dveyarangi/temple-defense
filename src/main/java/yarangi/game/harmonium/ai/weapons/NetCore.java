package yarangi.game.harmonium.ai.weapons;


import yarangi.ai.nn.init.InitializerFactory;
import yarangi.ai.nn.init.RandomWeightsInitializer;
import yarangi.ai.nn.numeric.ArrayInput;
import yarangi.ai.nn.numeric.BackpropNetwork;
import yarangi.ai.nn.numeric.CompleteNeuronLayer;
import yarangi.ai.nn.numeric.NeuralNetworkRunner;
import yarangi.ai.nn.numeric.Normalizer;
import yarangi.ai.nn.numeric.NumericAF;
import yarangi.ai.nn.numeric.ScalingNormalizer;
import yarangi.ai.nn.numeric.TanHAF;
import yarangi.math.Vector2D;

import com.spinn3r.log5j.Logger;

/**
 * 
 * Adapter for 
 * 
 * @author dveyarangi
 *
 */
public class NetCore extends NeuralNetworkRunner <IFeedbackBeacon, Vector2D>implements IntellectCore
{
	
//	private BackpropNetwork network = new BackpropNetwork(1);
	private String name;
	
	private static final float LEARNING_RATE = 0.1f;
	
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
		super(LEARNING_RATE);
		this.name = name;
		
		this.log = Logger.getLogger(name);
		try {
			network = NeuralNetworkRunner.load(name);
			log.debug("Loaded NN core " + name);
		} catch (Exception e) {
			log.debug("Cannot load NN core " + name + ", creating new one");
			network = new BackpropNetwork(2);
			network.addLayer(new CompleteNeuronLayer(new NumericAF [] { new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF() }, 1));
			network.addLayer(new CompleteNeuronLayer(new NumericAF [] { new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF() }, 1));
			network.addLayer(new CompleteNeuronLayer(new NumericAF [] { new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF() }, 1));
//			network.addLayer(new CompleteNeuronLayer(10, new NumericAF [] { new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF(), new TanHAF()}, 1));
			network.addInput(new ArrayInput(new double [8]));
		}
		
//		network.addLayer(new CompleteNeuronLayer(10, new TanHAF(), 1));
		
		Normalizer normalizer = new ScalingNormalizer(
						new double [] {-2*worldWidth, -2*worldHeight, -20, -20, -2*worldWidth, -2*worldHeight, 0 }, 
						new double [] { 2*worldWidth,  2*worldHeight,  20,  20, 2*worldWidth, 2*worldHeight,  20},
						new double [] {-worldWidth, -worldHeight}, 
						new double [] { worldWidth,  worldHeight});
		
		setNetwork(network);
		setNormalizer(normalizer);
	}

	public boolean processFeedback(IFeedbackBeacon capsule) 
	{
		LinearFeedbackBeacon beacon = (LinearFeedbackBeacon) capsule;

		if(beacon.getDistance() < 40000) // TODO: real beacon location test
		{
			Vector2D toTarget = beacon.getTargetLocationMemo().minus(beacon.getSource());
			run(beacon);
			train( toTarget );
			
			return true;
		}
		
		return false;
	}

	public Vector2D pickTrackPoint(Vector2D sourceLocation, double projectileVelocity, Vector2D targetLocation, Vector2D targetVelocity) 
	{
//		System.out.println(sourceLocation);
		Vector2D relativeTarget = targetLocation.minus(sourceLocation);
		Vector2D res = toOutput(run(toInputArray(relativeTarget, targetVelocity, sourceLocation, projectileVelocity)));
		
		return res.add( sourceLocation );
//		System.out.println(res[0] + " : " + res[1]);
//		return Math.atan2(res[1], res[0]);
	}

	@Override
	public void shutdown() {
		NeuralNetworkRunner.save(network, name);
		log.debug("Saved NN core [" + name + "].");
//		log.debug("NN core [" + name + "] is NOT saved.");
	}

	@Override
	public double[] toInputArray(IFeedbackBeacon input)
	{
		LinearFeedbackBeacon beacon = (LinearFeedbackBeacon) input;
		Vector2D relativeTarget = beacon.getInitialTargetLocation().minus(beacon.getSource());
		return toInputArray(
				relativeTarget,
					beacon.getVelocity(),
					beacon.getSource(),
					beacon.getProjectileVelocity());
	}
	
	public double [] toInputArray(Vector2D targetLoc, Vector2D targetVel, Vector2D cannonLoc, double projVel)
	{
//		Vector2D relativeTarget = targetLoc.minus(cannonLoc);
		return new double [] {
			targetLoc.x(),
			targetLoc.y(), 
//			Math.cos(target.getAABB().a),
//			Math.sin(target.getAABB().a),
			targetVel.x(),
			targetVel.y(),
			targetVel.x(),
			targetVel.y(),
			projVel
		};
	}
	@Override
	public double[] toOutputArray(Vector2D output)
	{
		return new double [] {output.x(), output.y() };
	}

	@Override
	public Vector2D toOutput(double[] outputs)
	{
		return Vector2D.R( outputs[0], outputs[1] );
	}


}
