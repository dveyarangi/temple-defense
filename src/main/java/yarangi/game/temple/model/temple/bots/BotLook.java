package yarangi.game.temple.model.temple.bots;

import javax.media.opengl.GL;

import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.math.Vector2D;


public class BotLook implements Look<Bot> {

	
	private Vector2D [] tail;
	
	public BotLook(int tailSize)
	{
		tail = new Vector2D[tailSize];
	}
	
	public void init(GL gl, Bot bot, IRenderingContext context) {
		
		for(int idx = 0; idx < tail.length; idx ++)
			tail[idx] = new Vector2D(bot.getArea().getRefPoint());
		
	}

	public void render(GL gl, double time, Bot bot, IRenderingContext context) 
	{
		
/*		if(entity.isHighlighted())
			gl.glColor3f(1.0f, 1.0f, 1.0f);
		else
			gl.glColor3f(0.0f, 1.0f, 0.2f);*/
		
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glColor3f(1.0f,1.0f,1.0f);
		for(int idx = 0; idx < tail.length; idx ++)
			gl.glVertex3f((float)(tail[idx].x()-bot.getArea().getRefPoint().x()), 
					      (float)(tail[idx].y()-bot.getArea().getRefPoint().y()), 0f);
		gl.glEnd();

		for(int idx = tail.length-1; idx > 0; idx --)
			tail[idx] = tail[idx-1];
		
		tail[0] = new Vector2D(bot.getArea().getRefPoint());


	}


	public void destroy(GL gl, Bot entity, IRenderingContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCastsShadow()
	{
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public float getPriority() { return 0; }

}
