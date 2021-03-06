package yarangi.game.harmonium.environment.terrain;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import yarangi.graphics.quadraturin.IRenderingContext;
import yarangi.graphics.quadraturin.IVeil;
import yarangi.graphics.quadraturin.objects.ILook;

public class CrystalMatterLook implements ILook <CrystalMatter> 
{

	@Override
	public void init(GL gl, IRenderingContext context) { }

	@Override
	public void render(GL gl, CrystalMatter crystal, IRenderingContext context) 
	{
		render(gl, crystal.getRoot());
	}
	
	public void render(GL gl1, CrystalLeaf leaf)
	{
		GL2 gl = gl1.getGL2();
		gl.glEnable(GL.GL_DEPTH_TEST);

		gl.glColor4f(0.5f, 0.5f, 1.0f, 1f);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex3f((float)leaf.getRoof().x(), (float)leaf.getRoof().y(), 0);
		gl.glVertex3f((float)leaf.getRoofLead().x(), (float)leaf.getRoofLead().y(), 1);
		gl.glVertex3f((float)leaf.getLeftRoof().x(), (float)leaf.getLeftRoof().y(), 0);
		gl.glEnd();
		
		gl.glColor4f(0.5f, 0.5f, 0.8f, 0.8f);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex3f((float)leaf.getRoof().x(), (float)leaf.getRoof().y(), 0);
		gl.glVertex3f((float)leaf.getRoofLead().x(), (float)leaf.getRoofLead().y(), 1);
		gl.glVertex3f((float)leaf.getRightRoof().x(), (float)leaf.getRightRoof().y(), 0);
		gl.glEnd();
		
		
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex3f((float)leaf.getRoofLead().x(), (float)leaf.getRoofLead().y(), 1);
		gl.glVertex3f((float)leaf.getRightRoof().x(), (float)leaf.getRightRoof().y(), 0);
		gl.glVertex3f((float)leaf.getRightBase().x(), (float)leaf.getRightBase().y(), 0);
		gl.glVertex3f((float)leaf.getBaseLead().x(), (float)leaf.getBaseLead().y(), 1);
		gl.glEnd();

		gl.glColor4f(0.5f, 0.5f, 1.0f, 1f);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex3f((float)leaf.getRoofLead().x(), (float)leaf.getRoofLead().y(), 1);
		gl.glVertex3f((float)leaf.getLeftRoof().x(), (float)leaf.getLeftRoof().y(), 0);
		gl.glVertex3f((float)leaf.getLeftBase().x(), (float)leaf.getLeftBase().y(), 0);
		gl.glVertex3f((float)leaf.getBaseLead().x(), (float)leaf.getBaseLead().y(), 1);
		gl.glEnd();

		/*	
		gl.glColor4f(0.5f, 0.5f, 0.8f, 0.8f);
		gl.glBegin(GL.GL_POLYGON);
		gl.glVertex3f((float)leaf.getBase().x(), (float)leaf.getBase().y(), 0);
		gl.glVertex3f((float)leaf.getBaseLead().x(), (float)leaf.getBaseLead().y(), 1);
		gl.glVertex3f((float)leaf.getRightBase().x(), (float)leaf.getRightBase().y(), 0);
		gl.glEnd();

		gl.glColor4f(0.5f, 0.5f, 1.0f, 1f);
		gl.glBegin(GL.GL_POLYGON);
		gl.glVertex3f((float)leaf.getBase().x(), (float)leaf.getBase().y(), 0);
		gl.glVertex3f((float)leaf.getBaseLead().x(), (float)leaf.getBaseLead().y(), 1);
		gl.glVertex3f((float)leaf.getLeftBase().x(), (float)leaf.getLeftBase().y(), 0);
		gl.glEnd();
		
		gl.glColor4f(0.5f, 0.5f, 0.8f, 0.8f);
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex3f((float)leaf.getRoof().x(), (float)leaf.getRoof().y(), 0);
		gl.glVertex3f((float)leaf.getBase().x(), (float)leaf.getBase().y(), 0);
		gl.glEnd();
		*/
		
		for(MatterBranch branch : leaf.getBranches().keySet())
			render(gl, (CrystalLeaf) branch);
		
	}

	@Override
	public void destroy(GL gl, IRenderingContext context) { }

	@Override
	public boolean isCastsShadow()
	{
		return false;
	}
	@Override
	public float getPriority() { return 1; }
	@Override
	public IVeil getVeil() { return null; }

	@Override
	public boolean isOriented() { return true; }
}
