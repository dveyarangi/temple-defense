package yarangi.game.temple.model.temple;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import yarangi.game.temple.ai.IFeedbackBeacon;
import yarangi.game.temple.controllers.ControlEntity;
import yarangi.game.temple.model.temple.structure.Connectable;
import yarangi.game.temple.model.temple.structure.PowerConnector;
import yarangi.game.temple.model.weapons.Projectile;
import yarangi.graphics.colors.Color;
import yarangi.graphics.lights.ICircleLightEntity;
import yarangi.graphics.quadraturin.objects.SceneEntity;
import yarangi.spatial.AABB;
import yarangi.spatial.IAreaChunk;
import yarangi.spatial.ISpatialFilter;
import yarangi.spatial.ISpatialObject;

public class ObserverEntity extends SceneEntity implements ICircleLightEntity, Connectable 
{
	private Map <IAreaChunk, ISpatialObject> entities;
	
	private double radius;
	
	private Color color;
	
	private PowerConnector [] connectors;
	
	private ControlEntity ctrl;
	private Set <IFeedbackBeacon> trackedObjects = new HashSet <IFeedbackBeacon> ();

	
	public ObserverEntity(AABB aabb, ControlEntity ctrl, ISpatialFilter filter, double radius, Color color)
	{
		super(aabb);
		
		this.color = color;
		
		this.radius = radius;
		
		this.ctrl = ctrl;
		
		setLook(new ObserverLook(filter));
		
/*		connectors = new PowerConnector[6];
		int idx = 0;
		for(double a = 0.01; a < Angles.PI_2; a += Angles.PI_div_3)
			connectors[idx++] = new PowerConnector(this.getArea().getRefPoint(), a);*/

	}
	public ObserverEntity(AABB aabb, ControlEntity ctrl, double radius, Color color) 
	{
		this(aabb, ctrl, new ISpatialFilter() {

			@Override
			public boolean accept(ISpatialObject entity) {
				if(entity instanceof Projectile || entity instanceof ShieldEntity)
					return false;
				return true;
			}}
		, radius, color);
	}

	@Override
	public boolean isPickable() { return true; }
	
	public Set <IFeedbackBeacon> getTrackedObjects() {
		return trackedObjects;
	}
	
	public void setTrackedObjects(Set <IFeedbackBeacon> objects) { this.trackedObjects = objects; }

	public void setEntities(Map <IAreaChunk, ISpatialObject> entities) {
		this.entities = entities;
	}

	public Map <IAreaChunk, ISpatialObject> getEntities() {
		return entities;
	}

	public double getSensorRadius() {
		return radius;
	}


	public Color getColor() {
		return color;
	}

	@Override
	public PowerConnector[] getConnectors() { return connectors; }

	public ControlEntity getController() { return ctrl; }

}
