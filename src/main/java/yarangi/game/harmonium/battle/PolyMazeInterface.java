package yarangi.game.harmonium.battle;

import com.spinn3r.log5j.Logger;

import yar.quadraturin.terrain.PolygonGrid;
import yarangi.game.harmonium.environment.terrain.EnforcingSeed;
import yarangi.game.harmonium.environment.terrain.MazeNode;
import yarangi.math.Angles;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.ITileMap;
import yarangi.spatial.SpatialHashMap;
import yarangi.spatial.SpatialIndexer;

public class PolyMazeInterface extends MazeInterface <PolygonGrid>
{
	private SpatialIndexer <MazeNode> nodes;
	
	private static final String NAME = MazeInterface.class.toString();
	
	private Logger log = Logger.getLogger();
	
	public PolyMazeInterface(ITileMap <?> terrain)
	{
		super((PolygonGrid) terrain);
		
		float width = terrain.getWidth();
		float height = terrain.getHeight();
		
		float cellSize = 10;
		
		int size = (int)Math.ceil( width*height/(cellSize*cellSize) );
		
		this.nodes = new SpatialHashMap<MazeNode>( NAME, size, cellSize, width, height );
		
		///////////////////////////////////////////////
		// filling the anchor nodes for maze growth algorithm
		// nodes are distributed in circles around the zero
		
		// distance between node circle layers
		float dr = 30.0f;
		
		// distance between nodes in layers
		float dl = 30.0f;
		
		// noise to dislocate node
		float rnoise = 2f; //  
		float tnoise = 0.01f;  
		
		
		EnforcingSeed seed = new EnforcingSeed( 5, null );
		
		// building nodes:
		for(float r = dr; r < width; r += dr)
		{
			float dt = (float) ( 2 * Math.asin( dl / (2 * r) ));
			int steps = (int) Math.ceil( Angles.TAU / dt );
			float fi = RandomUtil.R( (float)Angles.TAU );
			for(int idx = 0; idx < steps; idx ++)
			{
			
				float nr = RandomUtil.STD( r, rnoise );
				float nt = RandomUtil.STD( dt * idx, tnoise );
				
				float x = (float)( nr * Math.cos( fi + nt ) );
				float y = (float)( nr * Math.sin( fi + nt ) );
				
				MazeNode node = new MazeNode( x, y );
				
				nodes.add( node.getArea(), node );
				
				//
				seed.setLocation( x, y );
				
				this.seed( 1, seed );
			}
		}
	}

	@Override
	protected boolean grow(double time, ISeed<PolygonGrid> seed)
	{
		return seed.grow( time, terrain );
	}

}
