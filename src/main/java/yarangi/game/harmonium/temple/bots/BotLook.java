package yarangi.game.harmonium.temple.bots;

import javax.media.opengl.GL;

import yarangi.game.harmonium.environment.resources.Resource;
import yarangi.graphics.colors.Color;
import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.Q;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.graphics.veils.BlurVeil;
import yarangi.math.Vector2D;
import yarangi.numbers.RandomUtil;


public class BotLook implements Look<Bot> {

	
	private Vector2D [] tail;
	private int step = 0;
	private int headIdx = 0;
	IVeil veil;
	
	private Color color = new Color(RandomUtil.getRandomGaussian( 0.8f, 0.2f ), 
			RandomUtil.getRandomGaussian(  0.8f, 0.2f  ), 
			RandomUtil.getRandomGaussian( 0.8f, 0.2f ), 
			0.5f);
	
	public BotLook(int tailSize)
	{
		tail = new Vector2D[tailSize];
	}
	
	public void init(GL gl, Bot bot, IRenderingContext context) {
		
		veil = context.<BlurVeil>getPlugin( BlurVeil.NAME );
		if(veil == null)
		{
			Q.rendering.warn( "Plugin [" + BlurVeil.NAME + "] requested by look [" + this.getClass() + "] is not available."  );
			veil = IVeil.ORIENTING;
		}
		for(int idx = 0; idx < tail.length; idx ++)
			tail[idx] = Vector2D.COPY(bot.getArea().getRefPoint());
		
	}

	public void render(GL gl, double time, Bot bot, IRenderingContext context) 
	{
		
/*		if(entity.isHighlighted())
			gl.glColor3f(1.0f, 1.0f, 1.0f);
		else
			gl.glColor3f(0.0f, 1.0f, 0.2f);*/
		
//		gl.glDisable(GL.GL_DEPTH_TEST);
		double resourcePercent = bot.getPort().get( Resource.Type.ENERGY ).getAmount() / bot.getPort().getCapacity( Resource.Type.ENERGY );
		gl.glColor4f((float)(1-resourcePercent), (float)(1.0),(float)(1-resourcePercent),0.3f);
//		gl.glColor4f(0,1,0,(float)(0.2+resourcePercent*0.5));
//		color.apply( gl );
		double bx = bot.getArea().getRefPoint().x();
		double by = bot.getArea().getRefPoint().y();
		gl.glBegin(GL.GL_QUADS);
			gl.glVertex2f((float)(tail[0].x()-bx-0.2), (float)(tail[0].y()-by-0.2)); 
			gl.glVertex2f((float)(tail[0].x()-bx-0.2), (float)(tail[0].y()-by+0.2)); 
			gl.glVertex2f((float)(tail[0].x()-bx+0.2), (float)(tail[0].y()-by+0.2)); 
			gl.glVertex2f((float)(tail[0].x()-bx+0.2), (float)(tail[0].y()-by-0.2)); 

		gl.glEnd();

		gl.glBegin(GL.GL_LINE_STRIP);

		headIdx --;
		if(headIdx < 0)
			headIdx =  tail.length-1;
		
		tail[headIdx] = Vector2D.COPY(bot.getArea().getRefPoint());
		
		for(int idx = headIdx; idx < tail.length; idx ++)
			gl.glVertex2f((float)(tail[idx].x()-bx), (float)(tail[idx].y()-by)); 
		for(int idx = 0; idx < headIdx; idx ++)
			gl.glVertex2f((float)(tail[idx].x()-bx), (float)(tail[idx].y()-by)); 
		gl.glEnd();
		gl.glEnable(GL.GL_DEPTH_TEST);


		


	}


	public void destroy(GL gl, Bot entity, IRenderingContext context) {
	}

	@Override
	public boolean isCastsShadow()
	{
		return false;
	}
	@Override
	public float getPriority() { return 0; }
	@Override
	public IVeil getVeil() { return veil; }

}
