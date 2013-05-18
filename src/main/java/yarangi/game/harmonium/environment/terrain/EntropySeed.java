package yarangi.game.harmonium.environment.terrain;

import com.seisw.util.geom.Poly;
import com.seisw.util.geom.PolyDefault;

import yar.quadraturin.objects.IEntity;
import yar.quadraturin.terrain.PolygonGrid;
import yarangi.game.harmonium.battle.ISeed;
import yarangi.game.harmonium.enemies.swarm.agents.Seeder;
import yarangi.math.Angles;
import yarangi.math.IVector2D;
import yarangi.spatial.Area;

public class EntropySeed implements ISeed <PolygonGrid>
{
	protected Poly errosionPoly; //
	private final Seeder seeder;
	
	public EntropySeed(Seeder seeder)
	{
		errosionPoly = new PolyDefault();
		this.seeder = seeder;
	}


	@Override
	public boolean grow(double time, PolygonGrid t)
	{
		Area area = seeder.getArea();
		
		double scale = area.getMaxRadius();
		errosionPoly.clear();
		double dx = Angles.COS( area.getOrientation()*Angles.TO_RAD );
		double dy = Angles.SIN( area.getOrientation()*Angles.TO_RAD );
		double minx = Double.MAX_VALUE;
		double maxx = Double.MIN_VALUE;
		double miny = Double.MAX_VALUE;
		double maxy = Double.MIN_VALUE;
		double px, py;
		
		for(double i = 0; i < 1; i += 0.5) {
			IVector2D point = seeder.getRightEdge().at( i );
			
			px = scale*(point.x()*dx-point.y()*dy); // rotating and scaling
			if(px < minx) minx = px;
			if(px > maxx) maxx = px;
			
			py = scale*(point.x()*dy+point.y()*dx);
			if(py < miny) miny = py;
			if(py > maxy) maxy = py;
			
			errosionPoly.add( area.getAnchor().x() + px, area.getAnchor().y() + py );
		}
		for(double i = 1; i > 0; i -= 0.5) {
			IVector2D point = seeder.getLeftEdge().at( i );
			
			px = scale*(point.x()*dx-point.y()*dy);
			if(px < minx) minx = px; 
			if(px > maxx) maxx = px;
			
			py = scale*(point.x()*dy+point.y()*dx);
			if(py < miny) miny = py; 
			if(py > maxy) maxy = py;
			
			errosionPoly.add( area.getAnchor().x() + px, area.getAnchor().y() + py );
		}

		
		// applying polygonal mask to 
		t.apply( area.getAnchor().x(), area.getAnchor().y(), (maxx-minx), (maxy-miny), false, errosionPoly );

		return false; // seed dies
	}


}
