package yarangi.game.harmonium.environment.terrain;

import yarangi.game.harmonium.battle.ISeed;
import yarangi.graphics.quadraturin.terrain.PolygonGrid;
import yarangi.math.Angles;

import com.seisw.util.geom.Poly;
import com.seisw.util.geom.PolyDefault;

public class ErrosionSeed implements ISeed <PolygonGrid>
{
	
	private final float maskWidth;
	private double atx =Double.NaN, aty = Double.NaN;
	
	Poly poly = new PolyDefault();

	private boolean hit;

	public ErrosionSeed(float maskWidth)
	{
		this.maskWidth = maskWidth;
	}

	@Override
	public boolean grow(double time, PolygonGrid terrain)
	{
		if(Double.isNaN( atx ) || Double.isNaN( aty ))
			return false;
		poly.clear();
		for(double ang = 0 ; ang < Angles.TAU; ang += Angles.PI_div_6)
			poly.add( atx + maskWidth * Math.cos( ang ), aty + maskWidth * Math.sin( ang) );
		hit = terrain.apply( atx, aty, maskWidth, maskWidth, true, poly );

		return false;
	}

	public void setLocation(double atx, double aty)
	{
		this.atx = atx;
		this.aty = aty;
	}

	public boolean consumeHit()
	{
		boolean tmpHit = hit;
		hit = false;
		return tmpHit;
	}

}
