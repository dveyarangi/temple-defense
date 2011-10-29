package yarangi.game.temple.model.temple;

import java.util.LinkedList;

import yarangi.graphics.quadraturin.objects.IVeilEntity;

public class Path
{
	private IVeilEntity origin;
	private IVeilEntity target;
	private LinkedList <PathNode> nodes;
	public Path(IVeilEntity origin, IVeilEntity target, LinkedList<PathNode> nodes)
	{
		this.origin = origin;
		this.target = target;
		this.nodes = nodes;
	}
	protected IVeilEntity getOrigin()
	{
		return origin;
	}
	protected IVeilEntity getTarget()
	{
		return target;
	}
	protected LinkedList<PathNode> getNodes()
	{
		return nodes;
	}
	
	
}
