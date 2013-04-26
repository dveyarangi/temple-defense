package yarangi.game.harmonium.environment.terrain;

import yarangi.graphics.quadraturin.Scene;
import yarangi.spatial.PolygonArea;

public class PolygonalMatterFactory
{
	public static void createTestPolygons(Scene scene)
	{
		PolygonArea poly = new PolygonArea(-180, -180);
		poly.add(0, 60);
		poly.add(60, 0);
		poly.add(120, -20);
		poly.add(130, -40);
		poly.add(110, -60);
		poly.add(60, -40);
		poly.add(-30, -00);
		poly.add(-100, 20);
		poly.add(-110, 40);
		scene.addEntity(new Matter(poly));

		poly = new PolygonArea(-180, -180);

		poly.add(-110, 40);
		poly.add(-120, 80);
		poly.add(-120, 180);
		poly.add(-110, 200);
		poly.add(-90, 160);
		poly.add(-80, 100);
		poly.add(-40,70);
		poly.add(0, 60);
		
		scene.addEntity(new Matter(poly));
		poly = new PolygonArea(-100, 100);
		poly.add(0, 30);
		poly.add(20, 100);
		poly.add(40, 150);
		poly.add(60, 160);
		poly.add(70, 140);
		poly.add(60,  100);
		poly.add(40, -0);
		poly.add(20, -40);
		poly.add(0, -110);
		poly.add(-20, -150);
		poly.add(-30, -130);
		poly.add(-10, -70);
		
		scene.addEntity(
				new Matter(poly));
		poly = new PolygonArea(200, -200);
		poly.add(0, 60);
		poly.add(60, 70);
		poly.add(100, 120);
		poly.add(110, 110);
		poly.add(100, 90);
		poly.add(60,  50);
		poly.add(-30, -00);
		poly.add(-100, 20);
		poly.add(-110, 40);
		poly.add(-90, 40);
		scene.addEntity(new Matter(poly));
		poly = new PolygonArea(250, 50);
		poly.add(0, 60);
		poly.add(50, 80);
		poly.add(80, 90);
		poly.add(100, 100);
		poly.add(120, 80);
		poly.add(110, 70);
		poly.add(70, 30);
		poly.add(10, -30);
		poly.add(-40, -60);
		poly.add(-90, -120);
		poly.add(-100, -100);
		poly.add(-90, -80);
		poly.add(-60, -20);
//		poly.add(-90, -120);
		scene.addEntity(new Matter(poly));
		 poly = new PolygonArea(200, 300);
		poly.add(0, 60);
		poly.add(60, 0);
		poly.add(120, -20);
		poly.add(130, -40);
		poly.add(110, -60);
		poly.add(60, -40);
		poly.add(-30, -00);
		poly.add(-100, 20);
		poly.add(-110, 40);
		poly.add(-90, 80);
		scene.addEntity(new Matter(poly));
		

	}
}
