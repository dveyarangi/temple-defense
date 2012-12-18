package yarangi.game.harmonium.environment.terrain;

import yarangi.graphics.quadraturin.Scene;
import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;
import yarangi.spatial.PolygonArea;

public class KolbasaFactory
{
	public static final int LEVELS = 5;
	
	public static final double density = 5;
	
	public static void generateKolbasaMaze(Scene scene)
	{
		double levelRadius = 200;
		double maxSpan = 7;
		for(int level = 0; level < LEVELS; level ++)
		{
			double levelWidth = RandomUtil.N(30 )+ 60;
			double da = density * Angles.TAU / levelRadius;
			
			boolean start = false;
			
//			maxSpan = 1/da;
			
			PolyLine line = null;
			for(double a = 0; (a < Angles.TAU) || (line != null && line.size() < maxSpan); a += da)
			{

				if(line == null && RandomUtil.oneOf( 6 ))
				{
					line = new PolyLine();
				}
				
				if(line == null)
					continue;
					
				line.addPoint( Vector2D.POLAR(levelRadius, a) );
				
				if(line.size() >= maxSpan)
				{
					Matter matter = new Matter(generatePerimeter(line, levelWidth/2, 1));
					matter.setLook(new MatterLook());

					scene.addEntity( matter);
					
					if(RandomUtil.oneOf( level/2 + 1 ))
						line = null;
					else
					{
						line = new PolyLine();
						line.addPoint( Vector2D.POLAR(levelRadius, a) );
					}
				}
			}
			line = null;
			
			levelRadius += levelWidth;
		}
	}
	
	public static PolygonArea generatePerimeter(PolyLine line, double radius, double accuracy)
	{
		Vector2D refPoint = line.getPoint(0);
		
		PolygonArea poly = new PolygonArea( refPoint.x(), refPoint.y() );
		
		Vector2D nextDir = line.getPoint(1).minus( line.getPoint(0) );
		Vector2D currDir = nextDir;
		Vector2D perimeterDir;
		
		Vector2D currPoint = currDir.minus().multiply( radius );
//		poly.add( currPoint );
		
//		System.out.println(line.size());
		currDir = currDir.left();
		for(int idx = 1; idx < line.size()-1; idx ++)
		{
			currDir = nextDir;
			nextDir = line.getPoint(idx+1).minus( line.getPoint(idx) );

			perimeterDir = nextDir.minus( currDir ).normalize();
			currPoint = perimeterDir.multiply( radius ).add(line.getPoint(idx+1)).substract(refPoint);
			poly.add( currPoint );
		}
		
		return poly;
	}

}
