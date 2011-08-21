package yarangi.game.temple.gui;

import java.util.Map;

import yarangi.graphics.colors.Color;
import yarangi.graphics.lights.ICircleLightEntity;
import yarangi.graphics.quadraturin.objects.IVeilEntity;
import yarangi.graphics.quadraturin.objects.WorldEntity;
import yarangi.spatial.Area;
import yarangi.spatial.IAreaChunk;
import yarangi.spatial.ISpatialObject;

public class HighlightedEntity extends WorldEntity implements ICircleLightEntity
{

	private Map<IAreaChunk, ISpatialObject> entities;
	
	private IVeilEntity entity;
	
	private Color color;
	
	private double radius;
	
	protected HighlightedEntity(Color color, double radius) {
		
		setArea(Area.EMPTY);
		this.radius = radius;
	}
	
	public IVeilEntity getEntity() { return entity; }
	public void setEntity(IVeilEntity entity) { this.entity = entity; }

	@Override
	public void setEntities(Map<IAreaChunk, ISpatialObject> entities) {
		this.entities = entities;
	}

	@Override
	public Map<IAreaChunk, ISpatialObject> getEntities() {
		return entities;
	}


	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public boolean isPickable() {
		return false;
	}

	@Override
	public double getSensorRadiusSquare() {
		return entity == null ? 0 : radius*radius;
	}

}
