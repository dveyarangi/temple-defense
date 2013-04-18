package yarangi.game.harmonium.temple.harvester;

import yarangi.game.harmonium.battle.MazeInterface;
import yarangi.game.harmonium.environment.terrain.ErrosionSeed;
import yarangi.graphics.quadraturin.objects.IBehavior;
import yarangi.graphics.quadraturin.objects.Sensor;
import yarangi.graphics.quadraturin.terrain.ITerrain;
import yarangi.graphics.quadraturin.terrain.MultilayerTilePoly;
import yarangi.numbers.RandomUtil;


public class ErrodingBehavior extends Sensor <ITerrain> implements IBehavior <Harvester>
{
	
	private final MazeInterface maze;
	MultilayerTilePoly harvestedTile = null;
	MultilayerTilePoly reserveTile = null;
	boolean harvestedFound = false;
	private static final int MASK_WIDTH =16; 
	
	private final ErrosionSeed errodingSeed = new ErrosionSeed(MASK_WIDTH);
	
	private static final double ERRODE_INVERVAL = 5;
	
	private int lastSaturation = 1;
	private int saturation = 1;

	public ErrodingBehavior(double radius, MazeInterface maze)
	{
		super(radius, ERRODE_INVERVAL);
		this.maze = maze;
		
		if(maze != null) // TODO: remove
			maze.seed( ERRODE_INVERVAL, errodingSeed );
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

	@Override
	public boolean behave(double time, Harvester harvester, boolean isVisible)
	{
		if(harvestedTile == null) {
			return true; // no harvest target found during the last sensor read (
		}
		
		
		double atx = RandomUtil.getRandomDouble( harvestedTile.getMaxX()-harvestedTile.getMinX() ) + harvestedTile.getMinX();
		double aty = RandomUtil.getRandomDouble( harvestedTile.getMaxY()-harvestedTile.getMinY() ) + harvestedTile.getMinY();

//		do {
		
		double dx = atx - harvester.getArea().getAnchor().x();
		double dy = aty - harvester.getArea().getAnchor().y();
//		} while()
		if(dx*dx+dy*dy < harvester.getTerrainSensor().getRadius()*harvester.getTerrainSensor().getRadius()) {
//			System.out.println("harvesting at : " + atx + " : " + atx);
			
			errodingSeed.setLocation(atx, aty);
		}
		if(harvestedTile.isEmpty())
		{
			harvestedTile = null;
			errodingSeed.setLocation( Double.NaN, Double.NaN );
		}

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

