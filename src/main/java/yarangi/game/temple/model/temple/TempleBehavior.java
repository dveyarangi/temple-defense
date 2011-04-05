package yarangi.game.temple.model.temple;

import yarangi.graphics.quadraturin.objects.Behavior;

public class TempleBehavior implements Behavior <TempleStructure> 
{

	public boolean behave(double time, TempleStructure temple, boolean isVisible) {
		temple.getAABB().a += Math.PI/100.;
		
		return false;
	}
}
