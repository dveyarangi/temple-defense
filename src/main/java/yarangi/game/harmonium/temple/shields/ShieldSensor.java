package yarangi.game.harmonium.temple.shields;

import java.util.List;

import yar.quadraturin.objects.IEntity;
import yar.quadraturin.objects.ISensor;
import yarangi.intervals.AngleInterval;
import yarangi.math.Angles;
import yarangi.math.Geometry;
import yarangi.math.Vector2D;
import yarangi.spatial.Area;

public class ShieldSensor implements ISensor <IEntity>
{
	private final Shield shield;
	
	public ShieldSensor(Shield shield)
	{
		this.shield = shield;
	}

	@Override
	public boolean objectFound(IEntity object)
	{
		Area coreArea = shield.getArea();
		Area neighbourArea;
		if(object instanceof Shield)
		{
			Shield otherShield = (Shield) object;
			neighbourArea = otherShield.getArea();
			double distance = Geometry.calcHypot(coreArea.getAnchor(), neighbourArea.getAnchor());
			
			Vector2D anchor = Vector2D.COPY( coreArea.getAnchor() );
			double neighbourAngle = anchor.substract(neighbourArea.getAnchor()).getAngle();

			
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

}
