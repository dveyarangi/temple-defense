package yarangi.game.harmonium.temple.harvester;

import yar.quadraturin.objects.IBehavior;
import yar.quadraturin.objects.Sensor;
import yar.quadraturin.terrain.Bitmap;
import yar.quadraturin.terrain.ITerrain;
import yar.quadraturin.terrain.ITilePoly;
import yar.quadraturin.terrain.PolygonGrid;
import yarangi.game.harmonium.battle.MazeInterface;
import yarangi.game.harmonium.environment.terrain.DragonSeed;
import yarangi.game.harmonium.environment.terrain.EnforcingSeed;
import yarangi.math.Angles;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.ISpatialSensor;
import yarangi.spatial.Tile;

import com.seisw.util.geom.Poly;
import com.seisw.util.geom.PolyDefault;

/**
 * fills reachable marked terrain area with circles
 * @author dveyarangi
 *
 */
public class EnforcingBehavior extends Sensor <ITerrain> implements IBehavior <Waller>
{
	
	private final MazeInterface maze;
	ITilePoly harvestedTile = null;
	ITilePoly reserveTile = null;
	boolean harvestedFound = false;
	
	private static final float maskWidth = 5 ;
//	final GridyTerrainMap terrain = (GridyTerrainMap)scene.getWorldLayer().<Bitmap>getTerrain();
//	private static final int MASK_WIDTH =16; 
	
//	private static final byte [] HARV_MASK = MaskUtil.createCircleMask( MASK_WIDTH/2, new Color(0f, 0f, 0f, 1f), true);
	
	private static final double ERRODE_INVERVAL = 1;
	
	private Tile <Bitmap> prevTile;
	
	private int lastSaturation = 1;
	private int saturation = 1;
	
	private final AskingSensor askingSensor = new AskingSensor();
	
//	private final EnforcingSeed enforcingSeed; 
	private final DragonSeed enforcingSeed; 
	private final PolygonGrid reinforcementMap;

	public EnforcingBehavior(double radius, MazeInterface maze, PolygonGrid reinforcementMap)
	{
		super(radius, ERRODE_INVERVAL);
		this.maze = maze;
		this.reinforcementMap = reinforcementMap;
		
//		enforcingSeed = new EnforcingSeed( maskWidth, reinforcementMap );
		enforcingSeed = new DragonSeed(  );
//		if(maze != null) // TODO: remove
//			maze.seed( ERRODE_INVERVAL, enforcingSeed );
	}
	
	@Override
	public boolean objectFound(ITerrain object) 
	{
		if(!(object instanceof ITilePoly)) {
			super.objectFound(object );
			return false;
		}
		
		ITilePoly tile = (ITilePoly) object;
//		if(!tile.get().isEmpty())
		{
			float rx = (tile.getMaxX()-tile.getMinX()) / 2;
			float ry = (tile.getMaxY()-tile.getMinY()) / 2;
			float cx = tile.getMinX() + rx;
			float cy = tile.getMinY() + ry;
//			if(maze != null) // TODO: remove
//				reinforcementMap.queryAABB( askingSensor, cx, cy, rx, ry );
			
//			if(askingSensor.isFound()) {
				saturation ++;
				if(RandomUtil.oneOf( lastSaturation+1 ))
				{
	//				reinforcementMap.queryAABB( s, minx, miny, maxx-minx, maxy-miny );
	
					harvestedTile = tile;
//					return true;

				}
	//		}
		}
		
		return false;

	}


	@Override
	public boolean behave(double time, Waller waller, boolean isVisible)
	{
		if(harvestedTile == null) {
			return true;
		}
		
		//		System.out.println("harvesting at : " + atx + " : " + atx);

//		do {
		float atx = RandomUtil.getRandomFloat( harvestedTile.getMaxX()-harvestedTile.getMinX() ) + harvestedTile.getMinX();
		float aty = RandomUtil.getRandomFloat( harvestedTile.getMaxY()-harvestedTile.getMinY() ) + harvestedTile.getMinY();
		// TODO: transpose prepared poly instead.
		
/*		double a = Math.atan2( atx, aty );
		double R = Geometry.calcHypot( 0, 0, atx, aty );
		double span = RandomUtil.STD( 0.3, 0.001 );

		double maxx = Double.MIN_VALUE;
		double maxy = Double.MIN_VALUE;
		double miny = Double.MAX_VALUE;
		double minx = Double.MAX_VALUE;
		for(double ang = a - span; ang <= a + span; ang += 5*Angles.TRIG_STEP) {
			double x = waller.x() + (R+maskWidth) * Angles.COS( ang );
			double y = waller.y() + (R+maskWidth) * Angles.SIN( ang );
			if(x < minx) minx = x;
			if(y < miny) miny = y;
			if(x > maxx) maxx = x;
			if(y > maxy) maxy = y;
			poly.add( x, y );
		}
			
		for(double ang = a + span; ang >= a - span; ang -= 5*Angles.TRIG_STEP) {
			double x = waller.x() + (R-maskWidth) * Angles.COS( ang );
			double y = waller.y() + (R-maskWidth) * Angles.SIN( ang );
			if(x < minx) minx = x;
			if(y < miny) miny = y;
			if(x > maxx) maxx = x;
			if(y > maxy) maxy = y;
			poly.add( x, y );
		}*/
//		System.out.println(minx + "," + miny + "," + maxx + "," + maxy);

/*		double rx = (maxx-minx) / 2;
		double ry = (maxy-miny) / 2;
		double cx = minx + rx;
		double cy = miny + ry;*/

		enforcingSeed.setLocation( atx, aty );
		maze.seed( ERRODE_INVERVAL, enforcingSeed );

		double dx = atx - waller.x();
		double dy = aty - waller.y();
		

		if(RandomUtil.oneOf( 10 ))
			harvestedTile = null;

		return true;

	}
	
	@Override
	public void clear() { 
		
		super.clear();
		lastSaturation = saturation;
		saturation = 1;
		harvestedFound = false;
	}

	public ITilePoly getErrodedTile()
	{
		// TODO Auto-generated method stub
		return harvestedTile;
	}
	private static class AskingSensor implements ISpatialSensor <ITilePoly> {
		boolean found = false;
		
		@Override
		public boolean objectFound(ITilePoly object)
		{
			if(!object.isEmpty()) {
				found = true;
				return true;
			}
			
			return false;
		}
		
		public boolean isFound() { return found; }

		@Override
		public void clear()
		{
			found = false;
		}
	}	

}

