package yarangi.game.harmonium.battle;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.spinn3r.log5j.Logger;

import yarangi.game.harmonium.environment.terrain.MazeNode;
import yarangi.spatial.ITileMap;
import yarangi.spatial.SpatialHashMap;
import yarangi.spatial.SpatialIndexer;

/**
 * Controls growth of seeds.
 * 
 * @author dveyarangi
 */
public abstract class MazeInterface <T extends ITileMap>
{
//	private interface Seed extends ISeed <T> {};
	
	private static final String NAME = MazeInterface.class.toString();
	
	protected final T terrain;
	
	private final Map <Dao, ISeed<T>> dao = new HashMap <Dao, ISeed<T>> ();
	
	private Logger log = Logger.getLogger();
	
	private SpatialIndexer <MazeNode> nodes;
	
	private double dt;
	
	public MazeInterface(T terrain)
	{
		this.terrain = terrain;
		
		float width = terrain.getWidth();
		float height = terrain.getHeight();
		
		float cellSize = 10;
		
		int size = (int)Math.ceil( width*height/(cellSize*cellSize) );
		
		this.nodes = new SpatialHashMap<MazeNode>( NAME, size, cellSize, width, height );
	}
	
	/**
	 * Dao
	 */
	static class Dao
	{
		/**
		 * DAO
		 */
		private static LinkedList <Dao> DAO = new LinkedList <Dao> ();
		
		/**
		 *  dao om
		 */
		private double om = 0;
		
		/**
		 *  dao mo
		 */
		private double mo;

		/**
		 * Oooooooooooooooooooommmmmmmmmmmmmmmmmmmm
		 * @param mo - dao mo
		 */
		private Dao(double mo)
		{
			this.mo = mo;
		}
		
		/**
		 * Oooooooooooooooooooommmmmmmmmmmmmmmmmmmm
		 * @param om - dao om
		 * @return om
		 */
		public boolean om(double om)
		{
			return  (this.om += om) > mo && (this.om = 0) == 0;
		}
		
		/**
		 * Oooooooooooooooooooommmmmmmmmmmmmmmmmmmm
		 * @param mo - dao mo
		 * @return dao
		 */
		protected static Dao dao(double mo) {
			if(Dao.DAO.isEmpty())
				return new Dao(mo);
			
			Dao dao = Dao.DAO.poll();
			dao.mo = mo;
			return dao;
		}
		
		/**
		 * Oooooooooooooooooooommmmmmmmmmmmmmmmmmmm
		 * @param dao = dao
		 */
		protected static void dao(Dao dao) 
		{
			Dao.DAO.add( dao );
		}
	}

	/**
	 * Seed is the beginning of life.
	 * 
	 * @param area - where to seed
	 * @param interval - time between heartbeats
	 * 
	 */
	public boolean seed(double interval, ISeed <T> seed)
	{
		if(!seed.grow( dt, terrain ))
			return false;
		return dao.put ( Dao.dao(interval), seed ) != null;
	}
	
	public void grow(double time)
	{
		dt = time;
		// dao dao
		LinkedList <Dao> daos = new LinkedList <Dao> ();
//		log.trace("Total seeds: " + this.dao.size());
		for(Dao dao : this.dao.keySet())
		{
			if(!dao.om(time)) // dao
				continue;
			if(!grow(dao.mo, this.dao.get( dao ))) // growing seed by one mo.
				daos.add( dao );
		}
		
		// dao dao dao
		for(Dao dao : daos) 
		{
			Dao.dao(dao); // returning dao to DAO
			this.dao.remove( dao );
		}
	}	

	protected abstract boolean grow(double time, ISeed<T> seed);


	public float getWidth()
	{
		return terrain.getWidth();
	}
	public float getHeight()
	{
		return terrain.getHeight();
	}
	
}