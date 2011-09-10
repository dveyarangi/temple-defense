package yarangi.game.temple.model.temple.bots;

import javax.media.opengl.GL;

import yarangi.graphics.quadraturin.RenderingContext;
import yarangi.graphics.quadraturin.objects.Look;
import yarangi.math.Vector2D;
import static yarangi.math.Angles.*;


public class BotLook implements Look<Bot> {

	
	private Vector2D [] tail;
	
	public BotLook(int tailSize)
	{
		tail = new Vector2D[tailSize];
	}
	
	public void init(GL gl, Bot bot) {
		
		for(int idx = 0; idx < tail.length; idx ++)
			tail[idx] = new Vector2D(bot.getArea().getRefPoint());
		
	}

	public void render(GL gl, double time, Bot bot, RenderingContext context) 
	{
		
/*		if(entity.isHighlighted())
			gl.glColor3f(1.0f, 1.0f, 1.0f);
		else
			gl.glColor3f(0.0f, 1.0f, 0.2f);*/
		
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glColor3f(0.0f,0.5f,1.0f);
		for(int idx = 0; idx < tail.length; idx ++)
			gl.glVertex3f((float)(tail[idx].x()-bot.getArea().getRefPoint().x()), 
					      (float)(tail[idx].y()-bot.getArea().getRefPoint().y()), 0f);
		gl.glEnd();

		if(bot.getCurrTarget() == null)
			return;
		Vector2D seg = bot.getCurrTarget().getServicePoint();
		gl.glBegin(GL.GL_POLYGON);
		for(double angle = 0; angle < PI_2; angle += PI_div_10)
		{
			gl.glVertex3f((float)(seg.x() + 0.1 * Math.cos(angle)), 
						  (float)(seg.y() + 0.1 * Math.sin(angle)), 0);
		}
		gl.glEnd();
		
		for(int idx = tail.length-1; idx > 0; idx --)
			tail[idx] = tail[idx-1];
		
		tail[0] = new Vector2D(bot.getArea().getRefPoint());


	}


	public void destroy(GL gl, Bot entity) {
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
