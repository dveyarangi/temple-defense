package yarangi.game.spacefight.model.temple.platforms;

import yarangi.game.spacefight.controllers.BattleInterface;
import yarangi.game.spacefight.model.temple.Hexagon;
import yarangi.game.spacefight.model.temple.Platform;
import yarangi.game.spacefight.model.temple.TempleEntity;
import yarangi.game.spacefight.model.temple.TempleStructure;
import yarangi.game.spacefight.model.weapons.Weapon;
import yarangi.graphics.quadraturin.interaction.spatial.AABB;
import yarangi.math.Angles;
import yarangi.math.Vector2D;

public class CommandPlatform extends Platform implements BattleInterface
{
	private static final long serialVersionUID = -6746350958616093717L;
	private TempleEntity temple;

	public CommandPlatform(TempleEntity temple, Hexagon hexagon)
	{
		super(hexagon);
		this.temple = temple;
//		this.structure = temple.getStructure();
		setBehavior(new PlatformBehavior());
		setLook(new PlatformLook());
	}
	
	// move to construction interface:
	public TempleStructure getTempleStructure() { return temple.getStructure(); }


	public Vector2D getTrackingPoint(Weapon weapon) {
		// TODO: create a nice target acquisition logic
		return temple.getController().getCursorLocation();
	}
	
	public double getAbsoluteAngle() {
		return getTempleStructure().getAABB().getA();
	}
	
	public Vector2D getAbsoluteLocation(double x, double y)
	{
		return new Vector2D(0,0);
	}


	/**
	 * TODO: get transformation matrices from GL(?) or just optimize
	 */
	public Vector2D toWorldCoordinates(Platform platform, double x, double y) 
	{
		AABB aabb = platform.getAABB();
		double absPlatfromAngle = Angles.toRadians(temple.getStructure().getAABB().getA()) + 
			Math.PI/2+Math.atan2(aabb.getY(), aabb.getX());
		double sin1 = Math.sin(absPlatfromAngle);
		double cos1 = Math.cos(absPlatfromAngle);
		Vector2D absPlatformLoc = new Vector2D(aabb.getX()*cos1-aabb.getY()*sin1,
				aabb.getX()*sin1+aabb.getY()*cos1);
		
		double absObjectAngle = absPlatfromAngle+Math.atan2(y, x);
		double sin2 = Math.sin(absObjectAngle);
		double cos2 = Math.cos(absObjectAngle);
		Vector2D absObjectLoc = new Vector2D(x*cos2-y*sin2, x*sin2+y*cos2);
		
		
		return absPlatformLoc.plus(absObjectLoc);
	}

	public double getTempleRadius() { return Math.sqrt(temple.getStructure().getBoundingRadiusSquare()); }

}
