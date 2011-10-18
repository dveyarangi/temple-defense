package yarangi.game.temple.model.temple.platforms;

import java.util.List;
import java.util.Map;

import javax.media.opengl.GL;

import yarangi.game.temple.actions.Fireable;
import yarangi.game.temple.ai.IFeedbackBeacon;
import yarangi.game.temple.model.resource.Resource;
import yarangi.game.temple.model.resource.Resource.Type;
import yarangi.game.temple.model.temple.BattleInterface;
import yarangi.game.temple.model.temple.Platform;
import yarangi.game.temple.model.temple.TempleEntity;
import yarangi.game.temple.model.temple.TempleStructure;
import yarangi.game.temple.model.temple.structure.Hexagon;
import yarangi.game.temple.model.weapons.Weapon;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.spatial.Area;

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


	public Vector2D acquireTrackPoint(Weapon weapon) 
	{
		return weapon.getBattleInterface().acquireTrackPoint(weapon);
	}
	
	public double getAbsoluteAngle() {
		return getTempleStructure().getArea().getOrientation();
	}
	
	public Vector2D getAbsoluteLocation(double x, double y)
	{
		throw new IllegalStateException("This method is not yet implemented.");
	}


	/**
	 * TODO: get transformation matrices from GL(?) or just optimize
	 */
	public Vector2D toWorldCoordinates(Platform platform, double x, double y) 
	{
		Area area = platform.getArea();
		Vector2D platformLoc = platform.getArea().getRefPoint();
		double absPlatfromAngle = Angles.toRadians(temple.getStructure().getArea().getOrientation()) + 
			Math.PI/2+Math.atan2(platformLoc.y(), platformLoc.x());
		double sin1 = Math.sin(absPlatfromAngle);
		double cos1 = Math.cos(absPlatfromAngle);
		Vector2D absPlatformLoc = new Vector2D(platformLoc.x()*cos1-platformLoc.y()*sin1,
				platformLoc.x()*sin1+platformLoc.y()*cos1);
		
		double absObjectAngle = absPlatfromAngle+Math.atan2(y, x);
		double sin2 = Math.sin(absObjectAngle);
		double cos2 = Math.cos(absObjectAngle);
		Vector2D absObjectLoc = new Vector2D(x*cos2-y*sin2, x*sin2+y*cos2);
		
		
		return absPlatformLoc.plus(absObjectLoc);
	}

	public double getTempleRadius() { return Math.sqrt(temple.getStructure().getBoundingRadiusSquare()); }

	@Override
	public void destroy(GL gl)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFireable(Weapon weapon)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<Fireable, IEntity> getTargets()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Weapon> getFireables()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFeedbackBeacon createFeedbackBeacon(Weapon fireable)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearObservedObjects()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void objectObserved(IEntity object)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispatchFeedback(IFeedbackBeacon capsule)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getBattleScale()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int requestResource(Weapon weapon, Type resource, double amount)
	{
		// TODO Auto-generated method stub
		return 0;
	}


}
