package yarangi.game.harmonium.temple.harvester;

import yarangi.graphics.colors.Color;
import yarangi.graphics.colors.MaskUtil;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.Sensor;
import yarangi.graphics.quadraturin.terrain.Bitmap;
import yarangi.graphics.quadraturin.terrain.PolygonTerrainMap;
import yarangi.graphics.quadraturin.terrain.TilePoly;
import yarangi.math.Angles;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.IAreaChunk;
import yarangi.spatial.Tile;

import com.seisw.util.geom.Poly;
import com.seisw.util.geom.PolyDefault;


public class ErrodingBehavior extends Sensor implements Behavior <Harvester>
{
	
	private PolygonTerrainMap terrain;
	Tile <TilePoly> harvestedTile = null;
	Tile <TilePoly> reserveTile = null;
	boolean harvestedFound = false;
//	final GridyTerrainMap terrain = (GridyTerrainMap)scene.getWorldLayer().<Bitmap>getTerrain();
	private static final int MASK_WIDTH =16; 
	
	private static final byte [] HARV_MASK = MaskUtil.createCircleMask( MASK_WIDTH/2, new Color(0f, 0f, 0f, 1f), true);
	
	private static final double ERRODE_INVERVAL = 1;
	
	private Tile <Bitmap> prevTile;
	
	private int lastSaturation = 1;
	private int saturation = 1;

	public ErrodingBehavior(double radius, PolygonTerrainMap terrain)
	{
		super(radius, ERRODE_INVERVAL, true);
		this.terrain = terrain;
	}
	
	@Override
	public boolean objectFound(IAreaChunk chunk, IEntity object) 
	{
		if(!(chunk instanceof Tile)) {
			super.objectFound( chunk, object );
			return false;
		}
		
		Tile <TilePoly> tile = (Tile <TilePoly>) chunk;
		if(!tile.get().isEmpty())
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
			return false;
		}
		
		
		Poly poly = new PolyDefault();

//		do {
		double atx = RandomUtil.getRandomDouble( harvestedTile.getMaxX()-harvestedTile.getMinX() ) + harvestedTile.getMinX();
		double aty = RandomUtil.getRandomDouble( harvestedTile.getMaxY()-harvestedTile.getMinY() ) + harvestedTile.getMinY();
//		System.out.println("harvesting at : " + atx + " : " + atx);
		for(double ang = 0 ; ang < Angles.PI_2; ang += Angles.PI_div_6)
			poly.add( atx + maskWidth * Math.cos( ang ), aty + maskWidth * Math.sin( ang) );
		
		double dx = atx - harvester.getArea().getAnchor().x();
		double dy = aty - harvester.getArea().getAnchor().y();
//		} while()
		if(dx*dx+dy*dy < harvester.getSensor().getRadius()*harvester.getSensor().getRadius())
			terrain.apply( atx-maskWidth, aty-maskWidth, atx+maskWidth, aty+maskWidth, true, poly );

		if(harvestedTile.get().isEmpty())
			harvestedTile = null;

		return true;

	}
	
	public void clear() { 
		
		super.clear();
		lastSaturation = saturation;
		saturation = 1;
		harvestedFound = false;
	}

	public Tile <TilePoly> getErrodedTile()
	{
		// TODO Auto-generated method stub
		return harvestedTile;
	}

}

