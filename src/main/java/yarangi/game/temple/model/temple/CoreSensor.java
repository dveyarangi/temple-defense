package yarangi.game.temple.model.temple;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.ISensor;
import yarangi.spatial.IAreaChunk;

public class CoreSensor implements ISensor <IEntity> 
{
	private Set <Core> connectedCores = new HashSet <Core> ();

	@Override
	public boolean objectFound(IAreaChunk chunk, IEntity object) {
		if(object instanceof Core) {
			Core otherCore = (Core) object;
		}
		
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<IEntity> getEntities() {
		return (Set<IEntity>)connectedCores;
	}

	@Override
	public double getSensorRadiusSquare() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRadius() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isSensingNeeded(double time) { return false; }

	@Override
	public boolean isSenseTerrain() { return false; }

}
