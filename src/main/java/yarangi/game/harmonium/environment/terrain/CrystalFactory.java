package yarangi.game.harmonium.environment.terrain;

import yarangi.math.Angles;
import yarangi.math.Vector2D;

public class CrystalFactory 
{
	public static CrystalMatter generateStar(double x, double y, double radius)
	{
		CrystalMatter matter = new CrystalMatter(Vector2D.R(x, y), 0, radius);
		
		CrystalLeaf leaf1 = matter.getRoot();
		CrystalLeaf leaf2 = matter.getRoot().addLeaf(2*Angles.PI_div_3, radius, 0);
		CrystalLeaf leaf3 = matter.getRoot().addLeaf(4*Angles.PI_div_3, radius, 0);
		leaf1.addLeaf(-Angles.PI_div_3, radius/2, 0.5);
		leaf1.addLeaf( Angles.PI_div_3, radius/2, 0.5);
		leaf2.addLeaf(-Angles.PI_div_3, radius/2, 0.5);
		leaf2.addLeaf( Angles.PI_div_3, radius/2, 0.5);
		leaf3.addLeaf(-Angles.PI_div_3, radius/2, 0.5);
		leaf3.addLeaf( Angles.PI_div_3, radius/2, 0.5);
		
		return matter;

	}
	
	public static CrystalMatter generateRandom(double x, double y, double radius, int branching)
	{
		CrystalMatter matter = new CrystalMatter(Vector2D.R(x, y), 0, radius);
		
		CrystalLeaf leaf1 = matter.getRoot();
		CrystalLeaf leaf2 = matter.getRoot().addLeaf(2*Angles.PI_div_3, radius, 0);
		CrystalLeaf leaf3 = matter.getRoot().addLeaf(4*Angles.PI_div_3, radius, 0);
		leaf1.addLeaf(-Angles.PI_div_3, radius/2, 0.5);
		leaf1.addLeaf( Angles.PI_div_3, radius/2, 0.5);
		leaf2.addLeaf(-Angles.PI_div_3, radius/2, 0.5);
		leaf2.addLeaf( Angles.PI_div_3, radius/2, 0.5);
		leaf3.addLeaf(-Angles.PI_div_3, radius/2, 0.5);
		leaf3.addLeaf( Angles.PI_div_3, radius/2, 0.5);
		
		return matter;

	}

}
