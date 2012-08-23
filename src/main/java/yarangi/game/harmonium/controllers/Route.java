package yarangi.game.harmonium.controllers;

import java.util.LinkedList;
import java.util.List;

public class Route
{
	private final List <Waypoint> waypoints = new LinkedList <Waypoint> ();
	
	
	public void addWaypoint(double x, double y) {
		waypoints.add( new Waypoint( x, y ) );
	}
}
