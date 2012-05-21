package yarangi.game.harmonium.temple;

import java.util.List;
import java.util.SortedSet;

import yarangi.graphics.quadraturin.objects.IEntity;
import yarangi.graphics.quadraturin.objects.ISensor;
import yarangi.intervals.AngleInterval;
import yarangi.math.Angles;
import yarangi.math.Geometry;
import yarangi.spatial.Area;
import yarangi.spatial.IAreaChunk;

public class ShieldSensor implements ISensor <IEntity>
{
	private Shield shield;
	
	public ShieldSensor(Shield shield)
	{
		this.shield = shield;
	}

	@Override
	public boolean objectFound(IAreaChunk chunk, IEntity object)
	{
		Area coreArea = shield.getArea();
		Area neighbourArea;
		if(object instanceof Shield)
		{
			Shield otherShield = (Shield) object;
			neighbourArea = otherShield.getArea();
			double distance = Geometry.calcHypot(coreArea.getAnchor(), neighbourArea.getAnchor());
			
			
			double neighbourAngle = coreArea.getAnchor().minus(neighbourArea.getAnchor()).getAngle();
			
			double a = distance;
			double b = coreArea.getMaxRadius();
			double c = neighbourArea.getMaxRadius();
			
			if(a >= b  + c) // not touching
				return false;
			
			
			double cosa =(a*a + b*b - c*c) /
								(2 * a * b);
			double halfSegment = Math.acos( cosa );
			
			double a1 = Angles.normalize(neighbourAngle - halfSegment);
			double a2 = Angles.normalize(neighbourAngle + halfSegment);
			
			AngleInterval interval = new AngleInterval(a1, a2);
//				System.out.println(a1 + " : " + a2);
			
			// storing to exclude from perimeter collision calculations:
			shield.getExcludedSegments().add( interval );
		}
		return false;
	}

	@Override
	public void clear()
	{
		shield.getExcludedSegments().clear();
	}

	@Override
	public List<IEntity> getEntities()
	{
		return null;
	}

	@Override
	public double getRadius()
	{
		return shield.getArea().getMaxRadius();
	}

	@Override
	public boolean isSensingNeeded(double time)
	{
		return true;
	}

	@Override
	public boolean isSenseTerrain()
	{
		return false;
	}

}
