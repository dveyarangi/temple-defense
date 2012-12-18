package yarangi.game.harmonium.temple.harvester;

import yarangi.graphics.colors.Color;
import yarangi.graphics.colors.MaskUtil;
import yarangi.graphics.quadraturin.objects.IBehavior;
import yarangi.graphics.quadraturin.objects.Sensor;
import yarangi.graphics.quadraturin.terrain.ITerrain;
import yarangi.graphics.quadraturin.terrain.MultilayerTilePoly;
import yarangi.graphics.quadraturin.terrain.PolygonTerrainMap;
import yarangi.math.Angles;
import yarangi.numbers.RandomUtil;

import com.seisw.util.geom.Poly;
import com.seisw.util.geom.PolyDefault;


public class ErrodingBehavior extends Sensor <ITerrain> implements IBehavior <Harvester>
{
	
	private final PolygonTerrainMap terrain;
	MultilayerTilePoly harvestedTile = null;
	MultilayerTilePoly reserveTile = null;
	boolean harvestedFound = false;
//	final GridyTerrainMap terrain = (GridyTerrainMap)scene.getWorldLayer().<Bitmap>getTerrain();
	private static final int MASK_WIDTH =16; 
	
	private static final byte [] HARV_MASK = MaskUtil.createCircleMask( MASK_WIDTH/2, new Color(0f, 0f, 0f, 1f), true);
	
	private static final double ERRODE_INVERVAL = 1;
	
//	private Tile <Bitmap> prevTile;
	
	private int lastSaturation = 1;
	private int saturation = 1;

	public ErrodingBehavior(double radius, PolygonTerrainMap terrain)
	{
		super(radius, ERRODE_INVERVAL);
		this.terrain = terrain;
	}
	
	@Override
	public boolean objectFound(ITerrain object) 
	{
		
		MultilayerTilePoly tile = (MultilayerTilePoly) object;
		if(!tile.isEmpty())
		{
			saturation ++;
			
			if(RandomUtil.oneOf( lastSaturation/2+1 ))
			{
				harvestedTile = tile;
			}
		}
		
		return false;

	}
	
	private static final double maskWidth = 5 ;


	@Override
	public boolean behave(double time, Harvester harvester, boolean isVisible)
	{
		if(harvestedTile == null) {
			return true;
		}
		
		
		Poly poly = new PolyDefault();

//		do {
		double atx = RandomUtil.getRandomDouble( harvestedTile.getMaxX()-harvestedTile.getMinX() ) + harvestedTile.getMinX();
		double aty = RandomUtil.getRandomDouble( harvestedTile.getMaxY()-harvestedTile.getMinY() ) + harvestedTile.getMinY();
//		System.out.println("harvesting at : " + atx + " : " + atx);
		for(double ang = 0 ; ang < Angles.TAU; ang += Angles.PI_div_6)
			poly.add( atx + maskWidth * Math.cos( ang ), aty + maskWidth * Math.sin( ang) );
		
		double dx = atx - harvester.getArea().getAnchor().x();
		double dy = aty - harvester.getArea().getAnchor().y();
//		} while()
		if(dx*dx+dy*dy < harvester.getTerrainSensor().getRadius()*harvester.getTerrainSensor().getRadius())
			terrain.apply( atx, aty, maskWidth, maskWidth, true, poly );

		if(harvestedTile.isEmpty())
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

}

