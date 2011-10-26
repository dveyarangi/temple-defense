package yarangi.game.temple.model.temple;

import java.util.LinkedList;

import yarangi.graphics.quadraturin.objects.ILayerEntity;

public class Path
{
	private ILayerEntity origin;
	private ILayerEntity target;
	private LinkedList <PathNode> nodes;
	public Path(ILayerEntity origin, ILayerEntity target, LinkedList<PathNode> nodes)
	{
		this.origin = origin;
		this.target = target;
		this.nodes = nodes;
	}
	protected ILayerEntity getOrigin()
	{
		return origin;
	}
	protected ILayerEntity getTarget()
	{
		return target;
	}
	protected LinkedList<PathNode> getNodes()
	{
		return nodes;
	}
	
	
}
