package yarangi.game.temple.model.temple;

import java.util.Map;
import java.util.Set;

import yarangi.graphics.lights.CircleLightLook;
import yarangi.graphics.lights.ICircleLightEntity;
import yarangi.graphics.quadraturin.events.CursorEvent;
import yarangi.graphics.quadraturin.events.CursorListener;
import yarangi.graphics.quadraturin.objects.SceneEntity;
import yarangi.graphics.utils.colors.Color;
import yarangi.spatial.AABB;
import yarangi.spatial.ISpatialObject;

public class ObserverEntity extends SceneEntity implements ICircleLightEntity, CursorListener 
{
	private Map<ISpatialObject, Double> entities;
	
	private double radius;
	
	private Color color;
	
	public ObserverEntity(AABB aabb, double radius, Color color) {
		super(aabb);
		
		this.color = color;
		
		this.radius = radius;
		
		setLook(new ObserverLook());
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	public void onCursorMotion(CursorEvent event) {
		if(event.getWorldLocation() == null)
			return;
		this.getAABB().x = event.getWorldLocation().x;
		this.getAABB().y = event.getWorldLocation().y;
	}

	public void setEntities(Map<ISpatialObject, Double> entities) {
		this.entities = entities;
	}

	public Map<ISpatialObject, Double> getEntities() {
		return entities;
	}

	public double getSensorRadius() {
		return radius;
	}


	public Color getColor() {
		return color;
	}

}
