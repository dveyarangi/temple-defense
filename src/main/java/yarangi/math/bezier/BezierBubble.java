package yarangi.math.bezier;

import java.util.ArrayList;
import java.util.List;

import yarangi.math.Angles;
import yarangi.math.Vector2D;

public class BezierBubble implements ParametricCurve
{

	private List <BezierNode> nodes = new ArrayList <BezierNode> ();
	
	public BezierBubble(Vector2D center, double radius, double num)
	{
		if(num < 2)
			throw new IllegalArgumentException("Number of nodes must be 2 or larger.");
		
		double step = Angles.PI_2 / num;
		for(double angleOffset = 0; angleOffset < Angles.PI_2; angleOffset += step)
		{
			nodes.add(new BezierNode(new Vector2D(center.x, center.y, radius, angleOffset),
					                 new Vector2D(0, 0, 16*radius*(Math.sqrt(2)-1)/3/num, angleOffset + Angles.PI_div_2 )));
		}
	}
	
	
	public void addNode(int idx, Vector2D anchor, Vector2D pivot)
	{
		nodes.add(idx, new BezierNode(anchor, pivot));
	}

	public void removeNode(int idx)
	{
		nodes.remove(idx);
	}
	
	public Vector2D pointAt(double t) 
	{
		
		int index = (int)Math.floor(t*nodes.size());
		index = index == nodes.size() ? 0 : index;
		double offset = t*nodes.size() - index;
		if(offset == 0)
			return new Vector2D(nodes.get(index).anchor);
		
		BezierNode node1 = nodes.get(index);
		BezierNode node2 = nodes.get(index == nodes.size()-1 ? 0 : index+1);
		
		double invT = 1-offset;
//		System.out.println("t: " + t + " offset: " + offset + " index: " + index);
		return node1.anchor.mul(invT*invT*invT).
			plus(
			   node1.anchor.plus(node1.pivot).mul(3*offset*invT*invT)).
			plus(
			   node2.anchor.minus(node2.pivot).mul(3*offset*offset*invT)).
			plus(
			   node2.anchor.mul(offset*offset*offset));
	}
	
	public Vector2D getAnchor(int idx) { return nodes.get(idx).anchor; }
	public Vector2D getPivot(int idx) { return nodes.get(idx).pivot; }
	public int getNodesCount() { return nodes.size();}

	public double getMin() {
		return 0;
	}

	public double getMax() {
		return 1;
	}	
	
	public  class BezierNode
	{
		public Vector2D anchor;
		public Vector2D pivot;
		
		public BezierNode(Vector2D anchor, Vector2D pivot)
		{
			this.anchor = anchor;
			this.pivot = pivot;
		}
	}

	public List <BezierNode> getNodes() { return nodes; }
}
