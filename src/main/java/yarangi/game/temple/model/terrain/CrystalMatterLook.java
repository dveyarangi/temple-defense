package yarangi.game.temple.model.terrain;

import javax.media.opengl.GL;

import yarangi.graphics.quadraturin.RenderingContext;
import yarangi.graphics.quadraturin.objects.Look;

public class CrystalMatterLook implements Look <CrystalMatter> 
{

	@Override
	public void init(GL gl, CrystalMatter entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GL gl, double time, CrystalMatter crystal, RenderingContext context) 
	{
		render(gl, time, crystal.getRoot());
	}
	
	public void render(GL gl, double time, CrystalLeaf leaf)
	{
		gl.glEnable(GL.GL_DEPTH_TEST);

		gl.glColor4f(0.5f, 0.5f, 1.0f, 1f);
		gl.glBegin(GL.GL_POLYGON);
		gl.glVertex3f((float)leaf.getRoof().x(), (float)leaf.getRoof().y(), 0);
		gl.glVertex3f((float)leaf.getRoofLead().x(), (float)leaf.getRoofLead().y(), 1);
		gl.glVertex3f((float)leaf.getLeftRoof().x(), (float)leaf.getLeftRoof().y(), 0);
		gl.glEnd();
		
		gl.glColor4f(0.5f, 0.5f, 0.8f, 0.8f);
		gl.glBegin(GL.GL_POLYGON);
		gl.glVertex3f((float)leaf.getRoof().x(), (float)leaf.getRoof().y(), 0);
		gl.glVertex3f((float)leaf.getRoofLead().x(), (float)leaf.getRoofLead().y(), 1);
		gl.glVertex3f((float)leaf.getRightRoof().x(), (float)leaf.getRightRoof().y(), 0);
		gl.glEnd();
		
		
		gl.glBegin(GL.GL_POLYGON);
		gl.glVertex3f((float)leaf.getRoofLead().x(), (float)leaf.getRoofLead().y(), 1);
		gl.glVertex3f((float)leaf.getRightRoof().x(), (float)leaf.getRightRoof().y(), 0);
		gl.glVertex3f((float)leaf.getRightBase().x(), (float)leaf.getRightBase().y(), 0);
		gl.glVertex3f((float)leaf.getBaseLead().x(), (float)leaf.getBaseLead().y(), 1);
		gl.glEnd();

		gl.glColor4f(0.5f, 0.5f, 1.0f, 1f);
		gl.glBegin(GL.GL_POLYGON);
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
			render(gl, time, (CrystalLeaf) branch);
		
	}

	@Override
	public void destroy(GL gl, CrystalMatter entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCastsShadow()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
