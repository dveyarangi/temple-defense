package yarangi.game.harmonium.enemies.swarm.agents;


import yar.quadraturin.graphics.colors.Color;
import yar.quadraturin.graphics.colors.MaskUtil;
import yar.quadraturin.graphics.curves.Bezier4Curve;
import yar.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.game.harmonium.battle.MazeInterface;
import yarangi.numbers.RandomUtil;

public class SeederBehavior implements IBehaviorState <Seeder>
{
	private static int id = RandomUtil.N(Integer.MAX_VALUE);
	
	private double windingPhase = 0;
	public static final double WINDING_COEF = 2;
	public static final double WINDING_SPEED = 0.5;	
	private final DroneBehavior drone = new DroneBehavior(40);
	private final MazeInterface maze;

	public static double SEED_INTERVAL = 20;
	
	
	private static final int MASK_WIDTH = 32; 
	
	private static final byte [] SEED_MASK = MaskUtil.createCircleMask( MASK_WIDTH/2, new Color(0.0f, 0.0f, 0.0f, 1f), false);
	
	private double elapsedTime = 0;
	private double lastSeedTime = 0;
	
	public SeederBehavior(MazeInterface maze)
	{
		this.maze = maze;

	}
	
	@Override
	public double behave(double time, Seeder seeder) 
	{
		
		double scale = Math.sqrt( seeder.getArea().getAnchor().x()*seeder.getArea().getAnchor().x() + seeder.getArea().getAnchor().y()*seeder.getArea().getAnchor().y() ) / 500.;
		
		seeder.getArea().fitTo( scale*10 );
		drone.setInertion( scale*100 );
		drone.behave(time, seeder);
		
		windingPhase += WINDING_SPEED * time;// / seeder.getBody().getMass();
		
		Bezier4Curve left = seeder.getLeftEdge();
		Bezier4Curve right = seeder.getRightEdge();
		
		double phaseOffset = WINDING_COEF * Math.sin(windingPhase); 
		
		left.p2().sety(seeder.getLeftOffset().y() + phaseOffset);
		left.p3().sety(seeder.getLeftOffset().y() - phaseOffset);
		right.p2().sety(seeder.getRightOffset().y() + phaseOffset);
		right.p3().sety(seeder.getRightOffset().y() - phaseOffset);
		elapsedTime += time;
/*		if(elapsedTime - lastSeedTime > SEED_INTERVAL) {
			if(maze != null)
			maze.seed( time, seeder.getSeed() );
			lastSeedTime = elapsedTime;
		}*/
		
		return 0;
	}
	
	
	@Override public int getId() { return id; }

}
