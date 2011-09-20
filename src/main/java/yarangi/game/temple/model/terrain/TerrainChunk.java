package yarangi.game.temple.model.terrain;

import yarangi.graphics.quadraturin.simulations.IPhysicalObject;

public class TerrainChunk extends Matter implements IPhysicalObject
{
	
	private byte [] pixels;
	
	private int pixelCount;
	
	private double x, y;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param pixels array of pixel color components (r,g,b,a); size = width*height*4
	 */
	public TerrainChunk(double x, double y, byte [] pixels)
	{
		super(null);
		
		this.x = x;
		this.y = y;
		
		this.pixels = pixels;
		pixelCount = 0;
		for(int i = 0; i < pixels.length/4; i += 4)
			if(pixels[i] != 0 || pixels[i+1] != 0 || pixels[i+2] != 0 || pixels[i+3] != 0)
				pixelCount ++;
			
	}
	
	public byte [] getPixels()
	{
		return pixels;
	}
	
	public boolean isEmpty()
	{
		return pixelCount == 0;
	}
	
	public int getPixelCount()
	{
		return pixelCount;
	}

}
