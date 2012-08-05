package yarangi.game.harmonium.temple.harvester;

import yarangi.graphics.quadraturin.objects.IBehavior;
import yarangi.graphics.quadraturin.objects.IBeing;
import yarangi.graphics.quadraturin.objects.Sensor;
import yarangi.graphics.quadraturin.terrain.Bitmap;
import yarangi.graphics.quadraturin.terrain.MultilayerTilePoly;
import yarangi.graphics.quadraturin.terrain.PolygonGrid;
import yarangi.graphics.quadraturin.terrain.PolygonTerrainMap;
import yarangi.graphics.quadraturin.terrain.TilePoly;
import yarangi.math.Angles;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.ISpatialSensor;
import yarangi.spatial.Tile;

import com.seisw.util.geom.Poly;
import com.seisw.util.geom.PolyDefault;


public class EnforcingBehavior extends Sensor implements IBehavior <Waller>
{
	
	private final PolygonTerrainMap terrain;
	MultilayerTilePoly harvestedTile = null;
	MultilayerTilePoly reserveTile = null;
	boolean harvestedFound = false;
	
	private static final double maskWidth = 3 ;
//	final GridyTerrainMap terrain = (GridyTerrainMap)scene.getWorldLayer().<Bitmap>getTerrain();
//	private static final int MASK_WIDTH =16; 
	
//	private static final byte [] HARV_MASK = MaskUtil.createCircleMask( MASK_WIDTH/2, new Color(0f, 0f, 0f, 1f), true);
	
	private static final double ERRODE_INVERVAL = 0;
	
	private Tile <Bitmap> prevTile;
	
	private int lastSaturation = 1;
	private int saturation = 1;
	
	private final PolygonGrid <TilePoly> reinforcementMap;

	public EnforcingBehavior(double radius, PolygonTerrainMap terrain, PolygonGrid <TilePoly> reinforcementMap)
	{
		super(radius, ERRODE_INVERVAL, true);
		this.terrain = terrain;
		
		this.reinforcementMap = reinforcementMap;
	}
	
	@Override
	public boolean objectFound(IBeing object) 
	{
		if(!(object instanceof MultilayerTilePoly)) {
			super.objectFound(object );
			return false;
		}
		
		MultilayerTilePoly tile = (MultilayerTilePoly) object;
//		if(!tile.get().isEmpty())
		{
			saturation ++;
			
			if(RandomUtil.oneOf( lastSaturation/2+1 ))
			{
				harvestedTile = tile;
//				return true;
			}
		}
		
		return false;

	}


	@Override
	public boolean behave(double time, Waller waller, boolean isVisible)
	{
		if(harvestedTile == null) {
			return false;
		}
		
		
		Poly poly = new PolyDefault();
		//		System.out.println("harvesting at : " + atx + " : " + atx);

//		do {
		double atx = RandomUtil.getRandomDouble( harvestedTile.getMaxX()-harvestedTile.getMinX() ) + harvestedTile.getMinX();
		double aty = RandomUtil.getRandomDouble( harvestedTile.getMaxY()-harvestedTile.getMinY() ) + harvestedTile.getMinY();
		// TODO: transpose prepared poly instead.
		for(double ang = 0 ; ang < Angles.PI_2; ang += Angles.PI_div_6)
			poly.add( atx + maskWidth * Math.cos( ang ), aty + maskWidth * Math.sin( ang) );
		
		ReinforcementSensor s = new ReinforcementSensor(poly);
		reinforcementMap.queryAABB( s, atx, aty, 1, 1 );
		
		Poly res = s.getRes();
		
		if(res == null)
			return false;
		
		
		double dx = atx - waller.x();
		double dy = aty - waller.y();
//		} while()
		if(dx*dx+dy*dy < waller.getSensor().getRadius()*waller.getSensor().getRadius())
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

	public MultilayerTilePoly getErrodedTile()
	{
		// TODO Auto-generated method stub
		return harvestedTile;
	}
	
	private static class ReinforcementSensor implements ISpatialSensor <TilePoly> {

		Poly poly;
		
		Poly res;
		
		public ReinforcementSensor(Poly poly) {
			this.poly = poly;
		}
		
		@Override
		public boolean objectFound(TilePoly object)
		{
			if(object.getPoly() == null)
				return true;
			
			res = object.getPoly().intersection( poly );
			
			return true;
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

