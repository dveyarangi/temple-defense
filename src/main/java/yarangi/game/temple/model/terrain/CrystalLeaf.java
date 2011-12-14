package yarangi.game.temple.model.terrain;

import yarangi.math.Angles;
import yarangi.math.Vector2D;
import yarangi.spatial.AABB;
import yarangi.spatial.Area;

public class CrystalLeaf extends MatterBranch 
{
	
	/**
	 * Leaf's side edges angles to axis.
	 */
	private static final double BREADTH = Angles.PI_div_40;
	
	private static final double WIDTH_COEF = 0.2; 
	/**
	 * The sharpness of the roof-like ending of the leaf. 
	 */
	private static final double LEAF_END_OFFSET = 0.8;
	
	/** 
	 * directions of the side edges 
	 */
	private Vector2D leftPerimeter, rightPerimeter;
	/** 
	 * directions of the side edges 
	 */
	private Vector2D leftRoof, rightRoof;
	
	/** 
	 * intersections of the side edges with leaf's parent 
	 */
	private Vector2D leftBase, rightBase;
	
	/**
	 * crystal edge
	 */
	private Vector2D roof, roofLead;
	
	private Vector2D base, baseLead;
	
	/**
	 * 
	 * @param location leaf root point
	 * @param direction leaf direction (normalized)
	 * @param length leaf length
	 */
	public CrystalLeaf(Vector2D location, Vector2D direction, double length)
	{
		super(location, direction, length);
		
		// root shape
		baseLead = location ;//.plus(direction.mul((1-LEAF_END_OFFSET)*length));
		Vector2D baseSpan = direction.mul(2*(1-LEAF_END_OFFSET)*length*WIDTH_COEF);
		
		leftBase  = Vector2D.COPY( baseLead ).add(baseSpan.left());
		rightBase = Vector2D.COPY( baseLead ).add(baseSpan.right());
		
		// roof shape
		roof = location.plus(direction.mul(length));
		roofLead = location.plus(direction.mul(LEAF_END_OFFSET*length));
		Vector2D roofSpan = direction.mul(length*WIDTH_COEF);
		
		leftRoof  = Vector2D.COPY( baseLead ).add(roofSpan.left());
		rightRoof = Vector2D.COPY( baseLead ).add(roofSpan.right());
		
		this.leftPerimeter = leftRoof.minus(leftBase);
		this.rightPerimeter = rightRoof.minus(rightBase);
 	}
	

	public Vector2D getRoof() { return roof; }
	public Vector2D getRoofLead() { return roofLead; }
	public Vector2D getBase() { return getLocation(); }
	public Vector2D getLeftRoof() { return leftRoof; }
	public Vector2D getRightRoof() { return rightRoof; }
	public Vector2D getLeftBase() { return leftBase; }
	public Vector2D getRightBase() { return rightBase; }
	public Vector2D getBaseLead() { return baseLead; }
	
	/**
	 * Adds a leaf that branches from this one by given angle, length and offset from root.
	 * @param angle - angle to the direction of parent leaf
	 * @param length - length
	 * @param offset - location on parent leaf (0 - root, 1 - roof)
	 */
	public CrystalLeaf addLeaf(double angle, double length, double offset)
	{
		if(offset < 0 || offset > 1)
			throw new IllegalArgumentException("Offset must be between in [0,1]");
		
		//
		Vector2D location = getLocation().plus(getDirection().mul(getLength()*offset));
		
		CrystalLeaf leaf = new CrystalLeaf(location, Vector2D.UNIT(getDirection().getAngle()+angle), length);
		
		add(leaf, offset);
		
		return leaf;
	}

	public Area getArea() { return AABB.createSquare(0,0,0,0);}

}
