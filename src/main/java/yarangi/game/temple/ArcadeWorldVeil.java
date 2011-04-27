package yarangi.game.temple;

import javax.media.opengl.GL;

import yarangi.game.temple.model.enemies.ElementalVoid;
import yarangi.game.temple.model.enemies.bubbles.BubbleSwarm;
import yarangi.graphics.quadraturin.IViewPoint;
import yarangi.graphics.quadraturin.ViewPoint2D;
import yarangi.graphics.quadraturin.WorldVeil;
import yarangi.graphics.quadraturin.actions.DefaultCollisionManager;
import yarangi.graphics.quadraturin.simulations.ICollisionManager;
import yarangi.math.Angles;
import yarangi.math.RangedDouble;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;


public class ArcadeWorldVeil extends WorldVeil 
{
	

	private BubbleSwarm swarm;
	
	public ArcadeWorldVeil(int width, int height) {
		super(width, height);
		
//		setOverlayEffect(new BlurVeilEffect());
		
	}

	@Override
	public void init(GL gl) 
	{
		super.init(gl);

		gl.glClearColor(0.0f,0.0f, 0.0f, 0.0f);
	}

	@Override
	protected void initViewPoint(IViewPoint viewPoint) 
	{
		ViewPoint2D vp = (ViewPoint2D) viewPoint;
		
		vp.setCenter(new Vector2D(0,0));
		vp.setHeight(new RangedDouble(1, 400, 500));
	}
	
	@Override
	public void preDisplay(GL gl) 
	{	
       gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	}

	@Override
	public void postDisplay(GL gl) {}

	@Override
	public ICollisionManager createCollisionManager() 
	{
		return new DefaultCollisionManager(this);			
	}


}
