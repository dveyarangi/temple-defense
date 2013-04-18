package yarangi.game.harmonium.battle;

import yarangi.graphics.quadraturin.terrain.PolygonGrid;
import yarangi.spatial.ITileMap;

public class PolyMazeInterface extends MazeInterface <PolygonGrid>
{
	public PolyMazeInterface(ITileMap <?> terrain)
	{
		super((PolygonGrid) terrain);
	}

	@Override
	protected boolean grow(double time, ISeed<PolygonGrid> seed)
	{
		return seed.grow( time, terrain );
	}

}
