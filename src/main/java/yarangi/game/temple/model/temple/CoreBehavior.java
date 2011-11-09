package yarangi.game.temple.model.temple;

import java.util.Set;

import yarangi.graphics.quadraturin.objects.Behavior;
import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.math.Geometry;
import yarangi.spatial.Area;

public class CoreBehavior implements Behavior<Core>
{

	@Override
	public boolean behave(double time, Core core, boolean isVisible) {
		Set <IEntity> cores = core.getSensor().getEntities();
		Area coreArea = core.getArea();
		Area neighbourArea;
		for(IEntity entity : cores)
		{
			Core neighbourCore = (Core) entity;
			neighbourArea = neighbourCore.getArea();
			double distance =Geometry.calcHypot(coreArea.getRefPoint(), neighbourCore.getArea().getRefPoint());
			
			double cosa =(distance*distance + coreArea.getMaxRadius()*coreArea.getMaxRadius() - neighbourArea.getMaxRadius()*neighbourArea.getMaxRadius()) /
								(2 * distance * coreArea.getMaxRadius());
						
		}
		
		return false;
	}

}
