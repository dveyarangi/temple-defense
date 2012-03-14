package yarangi.game.harmonium.temple.harvester;

import yarangi.graphics.colors.Color;
import yarangi.graphics.colors.MaskUtil;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.Sensor;
import yarangi.graphics.quadraturin.terrain.Bitmap;
import yarangi.graphics.quadraturin.terrain.GridyTerrainMap;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.IAreaChunk;
import yarangi.spatial.Tile;


public class ErrodingBehavior extends Sensor implements Behavior <Harvester>
{
	
	private GridyTerrainMap terrain;
	Tile <Bitmap> harvestedTile = null;
	Tile <Bitmap> reserveTile = null;
	boolean harvestedFound = false;
//	final GridyTerrainMap terrain = (GridyTerrainMap)scene.getWorldLayer().<Bitmap>getTerrain();
	private static final int MASK_WIDTH =16; 
	
	private static final byte [] HARV_MASK = MaskUtil.createCircleMask( MASK_WIDTH/2, new Color(0f, 0f, 0f, 1f), true);
	
	private static final double ERRODE_INVERVAL = 1;
	
	private Tile <Bitmap> prevTile;
	
	private int lastSaturation = 1;
	private int saturation = 1;

	public ErrodingBehavior(double radius, GridyTerrainMap terrain)
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
		
		Tile <Bitmap> tile = (Tile <Bitmap>) chunk;
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

	@Override
	public boolean behave(double time, Harvester harvester, boolean isVisible)
	{
		if(harvestedTile == null) {
			return false;
		}

//		do {
		double offset = MASK_WIDTH/2*terrain.getPixelSize();
		double atx = RandomUtil.getRandomDouble( harvestedTile.getMaxX()-harvestedTile.getMinX() ) + harvestedTile.getMinX();
		double aty = RandomUtil.getRandomDouble( harvestedTile.getMaxY()-harvestedTile.getMinY() ) + harvestedTile.getMinY();
		double eatx = atx - offset;
		double eaty = aty - offset;
		
		double dx = atx - harvester.getArea().getRefPoint().x();
		double dy = aty - harvester.getArea().getRefPoint().y();
//		} while()
		if(dx*dx+dy*dy < harvester.getSensor().getRadius()*harvester.getSensor().getRadius()-offset)
			terrain.apply( eatx, eaty, true, MASK_WIDTH, HARV_MASK );

		
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

	public Tile <Bitmap> getErrodedTile()
	{
		// TODO Auto-generated method stub
		return harvestedTile;
	}

}

