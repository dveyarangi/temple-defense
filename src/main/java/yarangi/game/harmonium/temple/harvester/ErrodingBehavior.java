package yarangi.game.harmonium.temple.harvester;

import java.util.Iterator;

import yarangi.graphics.colors.Color;
import yarangi.graphics.colors.MaskUtil;
import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.ISensor;
import yarangi.graphics.quadraturin.terrain.Bitmap;
import yarangi.graphics.quadraturin.terrain.GridyTerrainMap;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.ITile;


public class ErrodingBehavior implements Behavior <Harvester>
{
	
	private GridyTerrainMap terrain;
	ITile <Bitmap> harvestedTile = null;
//	final GridyTerrainMap terrain = (GridyTerrainMap)scene.getWorldLayer().<Bitmap>getTerrain();
	private static final int MASK_WIDTH = 8; 
	
	private static final byte [] HARV_MASK = MaskUtil.createCircleMask( MASK_WIDTH/2, new Color(1f, 1f, 1f, 1f) );

	public ErrodingBehavior(GridyTerrainMap terrain)
	{
		this.terrain = terrain;
	}

	@Override
	public boolean behave(double time, Harvester harvester, boolean isVisible)
	{
		
		if(harvestedTile == null)
		{
			
			ISensor <IEntity> sensor = harvester.getSensor();
			if(sensor.getEntities().isEmpty())
				return false;
			
			int pick = RandomUtil.getRandomInt( sensor.getEntities().size() );
			Iterator <IEntity> it = sensor.getEntities().iterator();
			for(int i = 0; i < pick; i ++)
				harvestedTile = (ITile<Bitmap>)it.next();
			it.remove();
		}

		double eatx = RandomUtil.getRandomDouble( harvestedTile.getMaxX()-harvestedTile.getMinX() ) + harvestedTile.getMinX();
		double eaty = RandomUtil.getRandomDouble( harvestedTile.getMaxY()-harvestedTile.getMinY() ) + harvestedTile.getMinY();
		terrain.apply( eatx, eaty, true, MASK_WIDTH, HARV_MASK );
		
		if(harvestedTile.get().isEmpty())
			harvestedTile = null;
		
		return true;

	}

}

