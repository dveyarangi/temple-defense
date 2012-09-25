package yarangi.game.harmonium.environment.terrain.fractal;

import yarangi.game.harmonium.environment.terrain.ModifiableGridBehavior;
import yarangi.graphics.colors.Color;
import yarangi.graphics.quadraturin.objects.Dummy;
import yarangi.graphics.quadraturin.objects.EntityShell;
import yarangi.graphics.quadraturin.objects.ILook;
import yarangi.graphics.quadraturin.terrain.Bitmap;
import yarangi.graphics.quadraturin.terrain.GridyTerrainMap;
import yarangi.graphics.quadraturin.terrain.ITerrainFactory;

public class MandelbrotFactory implements ITerrainFactory
{
	private final static Color BLACK = new Color(0,0,0,0);
	
/*	private static float xstart = -1.79f;
	private static float ystart = -0.05f;
	private static float xend = -1.73f;
	private static float yend = 0.05f;*/
	private static double centerx = -1.0585;
	private static double centery = 0.26348;
	private static double scale = 0.000002;
//	private static double centerx = -1.05929;
//	private static double centery = 0.26348;
//	private static double scale = 0.000002;
//	private static double centerx = 0.0;
//	private static double centery = 0.0;
//	private static double scale = 0.001;
//	private static double centerx = -1.249f;
//	private static double centery = 0f;
//	private static double scale = 0.00001f;
	
	@Override
	public EntityShell <GridyTerrainMap> generateTerrain(float width, float height, int cellsize)
	{
		GridyTerrainMap terrain = new GridyTerrainMap( width, height, cellsize, 1 );
		int startIt = 215;
		int endIt = 250;
		double xstart = centerx - scale * width;
		double xend = centerx + scale * width;
		double ystart = centery - scale * height;
		double yend = centery + scale * height;
		double toXCoord = (xend-xstart)/width;
		double toYCoord = (yend-ystart)/height;
		double xstep = (xend-xstart) / (cellsize * terrain.getGridWidth());
		double ystep = (yend-ystart) / (cellsize * terrain.getGridHeight());
		double z, zi;
		double newz, newzi;
		double x, y;
		int i, j, k, pi, pj;
		boolean inset = false;
		Color color = null;
		double xoffset, yoffset;
		float toColor = 1f / (endIt-startIt);
		for (i = 0; i < terrain.getGridWidth(); i ++)
		{
			xoffset = xstart + toXCoord * i * cellsize;
		    for( j = 0; j <  terrain.getGridHeight(); j ++)
		    {
				yoffset = ystart + toYCoord * j * cellsize;

		    	Bitmap tile = new Bitmap( terrain.toXCoord( i ), terrain.toXCoord( j ), terrain.getPixelSize()*cellsize, cellsize);

		    	for(pi = 0; pi < cellsize; pi ++)
			    	for(pj = 0; pj < cellsize; pj ++)
			    	{
			    		x = xoffset + xstep * pi;
			    		y = yoffset + ystep * pj;
//			    		System.out.println(x + " : " + y + " = " + xstep + " : " + ystep);
				        z = 0;
				        zi = 0;
				        inset = false;
				        color = null;
				        for (k = startIt; k < endIt; k ++)
				        {
				          /* z^2 = (a+bi)(a+bi) = a^2 + 2abi - b^2 */
					      /* z^3 = (a+bi)(a^2 + 2abi - b^2) = a^3  - 3ab^2 + 3a^2bi- b^3i*/
		//		        	System.out.println(x + " : " + y);
		//			        newz = ((z*z*z)-(3*z*zi*zi) + x);
		//			        newzi = (3*z*z*zi - zi*zi*zi + y);
				        	newz = ((z*z)-(zi*zi) + x);
					        newzi = (2*z*zi + y);
					        if(((newz*newz)+(newzi*newzi)) > 4)
					        {
					            inset = false;
					            float r = k*toColor;
					            float g = k*toColor;
					            float b = k*toColor;
					            float val =(float)(Math.abs( z) + Math.abs( zi))/4;
		//			            System.out.println(val);
		//			            color = new Color(1, 0, 0, 1);
		//			            if(k % 2 == 0)
		//			            if(k > endIt -10)
					            color = new Color(0, 	       
					            		k % 2 == 0 ? val : 0, 
			            				  k % 2 == 0 ? 0 : val, 1);
		//			            System.out.println(k + " : " + val);
		//			            color = new Color(0, toColor*(float)Math.log(iterations-k), toColor*(float)Math.log(iterations-k)/2, 1);
		//			            k = iterations;
		//			            System.out.println(color);
					            break;
					        }
				            z = newz;
				            zi = newzi;
		
				        }
					    if(!inset && color != null)
					    { 
					    	tile.put( color, pi, pj );
			    	    }
			    	}
		    	
		    	terrain.put( terrain.toXCoord( i ), terrain.toXCoord( j ), tile);
		    		
		    }
		}

		ILook <GridyTerrainMap> look = Dummy.LOOK(); //new GridyTerrainLook();
		return new EntityShell<GridyTerrainMap>( terrain, null, look );
	}
}
