package yarangi.game.temple.model.temple;

import java.util.List;
import java.util.Map;

import javax.media.opengl.GL;

import yarangi.game.temple.actions.Fireable;
import yarangi.game.temple.ai.IFeedbackBeacon;
import yarangi.game.temple.model.weapons.Weapon;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.math.Vector2D;

public interface BattleInterface 
{
	public Vector2D acquireTrackPoint(Weapon weapon);

	public void destroy(GL gl);

	public void addFireable(Weapon weapon);

	public Map <Fireable, IEntity> getTargets();

	public List <Weapon> getFireables();

	public IFeedbackBeacon createFeedbackBeacon(Weapon fireable);

	public void clearObservedObjects();

//	public Vector2D getGuarded(Weapon entity);

	public void objectObserved(IEntity object);

	public void dispatchFeedback(IFeedbackBeacon capsule);

	public double getBattleScale();
	
//	public double getAbsoluteAngle();
	
//	public Vector2D toWorldCoordinates(Platform platform, double x, double y);
//	public Vector2D getAbsoluteLocation(double x, double y);
	
//	public double getTempleRadius();

//	public Vector2D getAbsuloteLocation(Platform platform);
}
