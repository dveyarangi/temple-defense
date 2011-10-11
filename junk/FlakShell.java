package yarangi.game.temple.model.weapons;

import java.util.Map;

import yarangi.graphics.colors.Color;
import yarangi.graphics.lights.ICircleLightEntity;
import yarangi.math.Vector2D;
import yarangi.spatial.IAreaChunk;
import yarangi.spatial.ISpatialObject;

public class FlakShell extends Projectile implements ICircleLightEntity
{

	private static final long serialVersionUID = 5706030916443249716L;
	public static final int EXPLOSION_SPEED = 1;

	public double explosionRangeSquare;
	
	public Vector2D [] frags;
	public Vector2D [] speeds;
	
	private Map<ISpatialObject, Double> entities;
	
	private Color color;

	public FlakShell(double x, double y, double a, Vector2D s, double explosionRangeSquare, WeaponProperties props) {
		super(x, y, 0, s, props);
		
		this.explosionRangeSquare = explosionRangeSquare;
		this.maxRange = explosionRangeSquare + 150;
		
		this.color = new Color(1.0f, 1.0f, 1.0f, 0.0f);
	}
	
	public boolean isAtExplosionRange() 
	{ 
		return getRangeSquare() > explosionRangeSquare;
	}
	
	public void setFrags(Vector2D [] frags) { this.frags = frags; } 
	
	public Vector2D [] getFragLocations() { return frags; }
	public Vector2D [] getFragSpeeds() { return speeds; }

	public void setSpeeds(Vector2D[] fragSpeeds) {
		this.speeds = fragSpeeds;
	}

	public double getExplosionRange() { return explosionRangeSquare; }


	public Color getColor() { return color; }

	@Override
	public void setEntities(Map<IAreaChunk, ISpatialObject> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getSensorRadiusSquare() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<IAreaChunk, ISpatialObject> getEntities() {
		// TODO Auto-generated method stub
		return null;
	}


}
