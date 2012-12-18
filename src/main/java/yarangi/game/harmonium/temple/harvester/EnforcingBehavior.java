package yarangi.game.harmonium.temple.harvester;

import yarangi.graphics.quadraturin.objects.IBehavior;
import yarangi.graphics.quadraturin.objects.Sensor;
import yarangi.graphics.quadraturin.terrain.Bitmap;
import yarangi.graphics.quadraturin.terrain.ITerrain;
import yarangi.graphics.quadraturin.terrain.ITilePoly;
import yarangi.graphics.quadraturin.terrain.PolygonGrid;
import yarangi.graphics.quadraturin.terrain.PolygonTerrainMap;
import yarangi.graphics.quadraturin.terrain.TilePoly;
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
	
	private final PolygonTerrainMap terrain;
	ITilePoly harvestedTile = null;
	ITilePoly reserveTile = null;
	boolean harvestedFound = false;
	
	private static final double maskWidth = 5 ;
//	final GridyTerrainMap terrain = (GridyTerrainMap)scene.getWorldLayer().<Bitmap>getTerrain();
//	private static final int MASK_WIDTH =16; 
	
//	private static final byte [] HARV_MASK = MaskUtil.createCircleMask( MASK_WIDTH/2, new Color(0f, 0f, 0f, 1f), true);
	
	private static final double ERRODE_INVERVAL = 0;
	
	private Tile <Bitmap> prevTile;
	
	private int lastSaturation = 1;
	private int saturation = 1;
	
	private final PolygonGrid <TilePoly> reinforcementMap;
	
	private final AskingSensor askingSensor = new AskingSensor();

	public EnforcingBehavior(double radius, PolygonTerrainMap terrain, PolygonGrid <TilePoly> reinforcementMap)
	{
		super(radius, ERRODE_INVERVAL);
		this.terrain = terrain;
		
		this.reinforcementMap = reinforcementMap;
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
			double rx = (tile.getMaxX()-tile.getMinX()) / 2;
			double ry = (tile.getMaxY()-tile.getMinY()) / 2;
			double cx = tile.getMinX() + rx;
			double cy = tile.getMinY() + ry;
			reinforcementMap.queryAABB( askingSensor, cx, cy, rx, ry );
			
			if(askingSensor.isFound()) {
				saturation ++;
				if(RandomUtil.oneOf( lastSaturation+1 ))
				{
	//				reinforcementMap.queryAABB( s, minx, miny, maxx-minx, maxy-miny );
	
					harvestedTile = tile;
//					return true;

				}
			}
		}
		
		return false;

	}


	@Override
	public boolean behave(double time, Waller waller, boolean isVisible)
	{
		if(harvestedTile == null) {
			return true;
		}
		
		
		Poly poly = new PolyDefault();
		//		System.out.println("harvesting at : " + atx + " : " + atx);

//		do {
		double atx = RandomUtil.getRandomDouble( harvestedTile.getMaxX()-harvestedTile.getMinX() ) + harvestedTile.getMinX();
		double aty = RandomUtil.getRandomDouble( harvestedTile.getMaxY()-harvestedTile.getMinY() ) + harvestedTile.getMinY();
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
	
		for(double ang = 0 ; ang < Angles.TAU; ang += Angles.PI_div_6) {
			poly.add( atx + maskWidth * Math.cos( ang ), aty + maskWidth * Math.sin( ang) );
		}
/*		double rx = (maxx-minx) / 2;
		double ry = (maxy-miny) / 2;
		double cx = minx + rx;
		double cy = miny + ry;*/
		
		ReinforcementSensor s = new ReinforcementSensor(poly);
//		reinforcementMap.queryAABB( s, cx, cy, rx, ry );
		reinforcementMap.queryAABB( s, atx, aty, maskWidth, maskWidth );
		
		Poly res = s.getRes();
		
		if(res == null)
			return true;
		
		
		double dx = atx - waller.x();
		double dy = aty - waller.y();
//		} while()
//		if(dx*dx+dy*dy < waller.getSensor().getRadius()*waller.getSensor().getRadius())
			terrain.apply( atx, aty, maskWidth, maskWidth, false, res );

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
	private static class AskingSensor implements ISpatialSensor <TilePoly> {
		boolean found = false;
		
		@Override
		public boolean objectFound(TilePoly object)
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
	private static class ReinforcementSensor implements ISpatialSensor <TilePoly> {

		Poly mask;
		
		Poly res;
		
		public ReinforcementSensor(Poly mask) {
			this.mask = mask;
			this.res = new PolyDefault();
		}
		
		@Override
		public boolean objectFound(TilePoly object)
		{
			if(object.isEmpty())
				return false;
			
			res = res.union(object.getPoly().intersection( mask ));
			
			return false;
		}
		
		public Poly getRes() {
			return res;
		}

		@Override
		public void clear()
		{
			res = null;
		}
		 
	}
}

