package yarangi.game.harmonium.temple.structure;

import java.util.ArrayList;
import java.util.List;

import yarangi.game.harmonium.temple.IServiceable;
import yarangi.graphics.quadraturin.objects.Entity;
import yarangi.math.Vector2D;
import yarangi.spatial.Area;

public class PowerConnector extends Entity
{
	
	private final IServiceable source;
	private final IServiceable target;
	
	
	
	private final List <PowerLine> paths = new ArrayList <PowerLine> ();
	
	public PowerConnector(IServiceable source, IServiceable target) 
	{
		super();
		
		
		this.source = source;
		this.target = target;
	}


/*	public void generatePath(PowerConnector targetConnector)
	{
		Vector2D targetLoc = targetConnector.getLocation();
		
//		double distance = DistanceUtils.calcDistanceToLine(target, location, sourceDir);
		
//		double targetTan = targetLoc.y/targetLoc.x;
//		double targetAngle = Math.atan(targetTan);
		
//		double targetSide;
//		if( targetAngle % Angles.PI_div_3 > Angles.PI_div_6 )
//			targetSide = -1;
//		else
//			targetSide = 1;
		
		Vector2D targetDir = targetConnector.sourceDir;
		
//		double sourceTan = Math.tan(sourceDir.getAngle() + targetSide * Angles.PI_div_3);
		
		double param = ((sourceLoc.x() - targetLoc.x()) * targetDir.y() - (sourceLoc.y() - targetLoc.y()) * targetDir.x()) /
						(targetDir.x()*sourceDir.y() - sourceDir.x()*targetDir.y());
		
		Vector2D intersection = sourceLoc.plus(sourceDir.mul(param));
		
		paths.add(new PowerLine(targetConnector, sourceLoc, intersection, targetLoc));
		
	}*/
	
	public List <PowerLine> getPaths() { return paths; }


	public IServiceable getSource() { return source;	}
	public IServiceable getTarget() { return target;	}


}
