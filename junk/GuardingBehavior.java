package yarangi.game.temple.model.weapons;

import java.util.Set;

import yarangi.graphics.quadraturin.objects.behaviors.IBehaviorState;
import yarangi.graphics.quadraturin.simulations.IPhysicalObject;
import yarangi.math.Vector2D;

public class GuardingBehavior implements IBehaviorState <Weapon>
{

	@Override
	public boolean behave(double time, Weapon entity, boolean isVisible) 
	{
		Set <IPhysicalObject> targets = entity.getBattleInterface().getTargets(entity);
		if(targets.isEmpty())
			return false;
		Vector2D guarded = entity.getBattleInterface().getGuarded(entity);
		if(guarded == null)
			return false;
		
		double range = Math.pow(entity.getWeaponProperties().getEffectiveRange(), 2);
		double minDistance = Double.MAX_VALUE;
		double d;
		IPhysicalObject result = null;
		Vector2D weaponLoc = entity.getArea().getRefPoint();
		Vector2D targetLoc;
		for(IPhysicalObject target : targets)
		{
			targetLoc = target.getArea().getRefPoint();
			d = Math.pow(targetLoc.x()-guarded.x(), 2) + Math.pow(targetLoc.y()-guarded.y(), 2);
			if(d >= minDistance)
				continue;
			if(Math.pow(targetLoc.x()-weaponLoc.x(), 2) + Math.pow(targetLoc.y()-weaponLoc.y(), 2) >	range)
				continue;
			
			minDistance = d;
			result = target;
		}
		
		entity.setTarget(result);
		
		return false;
	}

}
