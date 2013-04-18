package yarangi.game.harmonium.battle;


/**
 * Seeds can be dropped into {@link MazeInterface} to create or erase obstacles.
 * @author dveyarangi
 *
 * @param <T>
 */
public interface ISeed <T> 
{ 
	/**
	 * Grow the seed here.
	 * 
	 * @param dt length of this iteration of growing
	 * @param terrain
	 * @return
	 */
	public boolean grow(double dt, T terrain);
}