package yarangi.game.harmonium.environment.terrain;

import yarangi.graphics.quadraturin.objects.Dummy;
import yarangi.math.Vector2D;

public class CrystalMatter extends Matter 
{
	
	private CrystalLeaf root;

	public CrystalMatter(Vector2D loc, double rootAngle, double rootLength) {
		super(null);
		root = new CrystalLeaf(loc, Vector2D.UNIT(rootAngle), rootLength);
		
		setLook(new CrystalMatterLook());
		setBehavior(Dummy.BEHAVIOR);
		setArea(root.getArea());
	}


	public CrystalLeaf getRoot() { return root; }

}
