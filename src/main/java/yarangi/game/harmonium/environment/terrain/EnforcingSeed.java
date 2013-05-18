package yarangi.game.harmonium.environment.terrain;

import yar.quadraturin.terrain.ITilePoly;
import yar.quadraturin.terrain.PolygonGrid;
import yarangi.game.harmonium.battle.ISeed;
import yarangi.math.Angles;
import yarangi.spatial.ISpatialSensor;

import com.seisw.util.geom.Poly;
import com.seisw.util.geom.PolyDefault;

public class EnforcingSeed implements ISeed <PolygonGrid>
{
	
	private final float maskWidth;
	private float atx = Float.NaN, aty = Float.NaN;
	private float patx = 0, paty = 0;
	
	private final Poly poly = new PolyDefault();

	
	private final PolygonGrid reinforcementMap;

	public EnforcingSeed(float maskWidth, PolygonGrid reinforcementMap)
	{
		this.maskWidth = maskWidth;
		
		this.reinforcementMap = reinforcementMap;
		
	}

	@Override
	public boolean grow(double time, PolygonGrid terrain)
	{
		if(Double.isNaN( atx ) || Double.isNaN(aty)) // XXX
			return false;
		
		// TODO: transpose is faster?
		poly.clear();
		for(double ang = 0 ; ang < Angles.TAU; ang += Angles.PI_div_3)
			poly.add( atx + maskWidth *Angles.COS( ang ), aty + maskWidth * Angles.SIN( ang) );
		
		ReinforcementSensor s = new ReinforcementSensor(poly);
//		reinforcementMap.queryAABB( s, cx, cy, rx, ry );
		if(reinforcementMap != null) {
			reinforcementMap.queryAABB( s, atx, aty, maskWidth, maskWidth );
		
			Poly res = s.getRes();
		
			if(res == null)
				return true;
			terrain.apply( atx, aty, maskWidth, maskWidth, false, res );
		}
		else
			terrain.apply( atx, aty, maskWidth, maskWidth, false, poly );
			
		return false;
	}

	public void setLocation(float atx, float aty)
	{
		patx = this.atx;
		paty = this.aty;
		this.atx = atx;
		this.aty = aty;
	}
	
	private static class ReinforcementSensor implements ISpatialSensor <ITilePoly> {

		Poly mask;
		
		Poly res;
		
		public ReinforcementSensor(Poly mask) {
			this.mask = mask;
			this.res = new PolyDefault();
		}
		
		@Override
		public boolean objectFound(ITilePoly object)
		{
			if(object.isEmpty())
				return false;
			
			res = res.union(object.getPoly().intersection( mask ));
			
			return false;
		}
		
		public Poly getRes() {
			return res;
		}

		@Override
		public void clear()
		{
			res = null;
		}
		 
	}
}
