package yarangi.game.temple.model.temple.structure;

import java.util.HashSet;
import java.util.Set;

import yarangi.graphics.quadraturin.SceneLayer;
import yarangi.math.Geometry;

public class PowerGrid
{
	private Set <Connectable> connectors = new HashSet <Connectable> ();
	private SceneLayer veil;
	
	public PowerGrid(SceneLayer veil)
	{
		this.veil = veil;
	}
	
	public void connect(Connectable source, Connectable target)
	{
		PowerConnector sourceConn = null, targetConn = null;
		double min = Double.MAX_VALUE, temp;
		// finding closest connectors:
		// TODO: optimize
		for(PowerConnector con1 : source.getConnectors())
			for(PowerConnector con2 : target.getConnectors())
			{
				temp = Geometry.calcHypotSquare(con1.getLocation(), con2.getLocation());
				if(temp < min)
				{
					min = temp;
					sourceConn = con1;
					targetConn = con2;
				}
			}
		
		if(sourceConn == null)
			throw new IllegalArgumentException("No connectors found.");
			
		sourceConn.generatePath(targetConn);
		
		connectors.add(source);
		veil.addEntity(sourceConn);
//		connectors.add(target);
	}
	
	public Set <Connectable> getConnectables() { return connectors; }

}
