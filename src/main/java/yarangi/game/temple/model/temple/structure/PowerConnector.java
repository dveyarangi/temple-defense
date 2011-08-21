package yarangi.game.temple.model.temple.structure;

import java.util.ArrayList;
import java.util.List;

import yarangi.graphics.quadraturin.objects.WorldEntity;
import yarangi.math.Vector2D;
import yarangi.spatial.Area;

public class PowerConnector extends WorldEntity
{
	private Vector2D sourceLoc;

	private Vector2D sourceDir; 
	
	private List <PowerLine> paths = new ArrayList <PowerLine> ();
	
	public PowerConnector(Area area, double sourceAngle) 
	{
		super();
		
		setArea(area);
		this.sourceLoc = new Vector2D(area.getRefPoint().x(), area.getRefPoint().y(), 5, sourceAngle);
		
		this.sourceDir = new Vector2D(1, sourceAngle, true);
		
		setLook(new PowerConnectorLook());
	}

	public Vector2D getLocation() { return sourceLoc; }

	public void generatePath(PowerConnector targetConnector)
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
		
		double param = ((sourceLoc.x - targetLoc.x) * targetDir.y - (sourceLoc.y - targetLoc.y) * targetDir.x) /
						(targetDir.x*sourceDir.y - sourceDir.x*targetDir.y);
		
		Vector2D intersection = sourceLoc.plus(sourceDir.mul(param));
		
		paths.add(new PowerLine(targetConnector, sourceLoc, intersection, targetLoc));
		
	}
	
	public List <PowerLine> getPaths() { return paths; }

	@Override
	public boolean isPickable() { return true; }


}
