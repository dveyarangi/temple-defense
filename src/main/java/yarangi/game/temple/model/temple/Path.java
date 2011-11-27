package yarangi.game.temple.model.temple;

import java.util.LinkedList;

import yarangi.graphics.quadraturin.objects.ILayerObject;

public class Path
{
	private ILayerObject origin;
	private ILayerObject target;
	private LinkedList <PathNode> nodes;
	public Path(ILayerObject origin, ILayerObject target, LinkedList<PathNode> nodes)
	{
		this.origin = origin;
		this.target = target;
		this.nodes = nodes;
	}
	protected ILayerObject getOrigin()
	{
		return origin;
	}
	protected ILayerObject getTarget()
	{
		return target;
	}
	protected LinkedList<PathNode> getNodes()
	{
		return nodes;
	}
	
	
}
