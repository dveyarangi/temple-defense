package yarangi.game.harmonium.controllers;

import yarangi.math.Vector2D;

public class Waypoint
{
	private final Vector2D location;
	
	// TODO: add orders at location
	
	public Waypoint(double x, double y) {
		this.location = Vector2D.R( x, y );
	}
	
	public Vector2D getLocation() {
		return location;
	}
}
