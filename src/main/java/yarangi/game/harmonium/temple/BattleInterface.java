package yarangi.game.harmonium.temple;

import java.util.List;
import java.util.Map;

import javax.media.opengl.GL;

import yarangi.game.harmonium.ai.weapons.IFeedbackBeacon;
import yarangi.game.harmonium.ai.weapons.IntellectCore;
import yarangi.game.harmonium.ai.weapons.NetCore;
import yarangi.game.harmonium.environment.resources.Resource;
import yarangi.game.harmonium.temple.weapons.Projectile;
import yarangi.game.harmonium.temple.weapons.Weapon;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.math.Vector2D;

public interface BattleInterface 
{
	public Vector2D acquireTrackPoint(Weapon weapon);

	public void destroy(GL gl);

	public void addFireable(Weapon weapon);

	public Map <Weapon, IEntity> getTargets();

	public List <Weapon> getFireables();

	public IFeedbackBeacon createFeedbackBeacon(Weapon fireable, Projectile projectile);

	public void clearObservedObjects();

//	public Vector2D getGuarded(Weapon entity);

	public void objectObserved(IEntity object);

	public void dispatchFeedback(IFeedbackBeacon capsule);

	public double getBattleScale();
	
	public int requestResource(Weapon weapon, Resource.Type resource, double amount);

	public IntellectCore getCore();
	
//	public double getAbsoluteAngle();
	
//	public Vector2D toWorldCoordinates(Platform platform, double x, double y);
//	public Vector2D getAbsoluteLocation(double x, double y);
	
//	public double getTempleRadius();

//	public Vector2D getAbsuloteLocation(Platform platform);
}
