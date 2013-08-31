/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import panoramakit.engine.task.TaskManager;
import panoramakit.mod.PanoramaKit;

/** 
 * Takes care of loading and drawing preview to the screen.
 * @author dayanto
 */
public class PreviewRenderer
{
	private BufferedImage image;
	private DynamicTexture previewTexture;
	private ResourceLocation resourceLocation;
	private TextureManager textureManager;
	
	public PreviewRenderer(TextureManager textureManager)
	{
		this.textureManager = textureManager;
	}
	
	/**
	 * Returns the image file that can be used for preview images.
	 */
	public static File getPreviewFile()
	{
		return new File(PanoramaKit.instance.getTempRenderDir(), "Preview.png");
	}
	
	public boolean previewAvailable()
	{
		if(getPreviewFile().exists()) {
			// while the preview is being modified, it shouldn't be loaded
			if(!TaskManager.instance.hasTasks()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean loadPreview()
	{
		try {
			image = ImageIO.read(getPreviewFile());
			previewTexture = new DynamicTexture(image);
			resourceLocation = textureManager.func_110578_a("preivew", previewTexture);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void clearPreview()
	{
		File preview = getPreviewFile();
		if(preview.exists()) {
			preview.delete();
		}
	}

	/**
	 * Draws an image centered in the rectangle supplied.
	 */
	public void drawCenteredImage(int xPos, int yPos, int width, int height)
	{
		if(previewTexture == null) {
			boolean successful = loadPreview();
			if(!successful) return;
		}
		drawImage(xPos + (width - image.getWidth()) / 2, yPos + (height - image.getHeight()) / 2, image.getWidth(), image.getHeight());
	}
	
	private void drawImage(int xPos, int yPos, int width, int height)
	{
		previewTexture.func_110564_a();
		Tessellator tessellator = Tessellator.instance;
		textureManager.func_110577_a(resourceLocation);
		GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(xPos        , yPos + height, 0, 0.0, 1.0);
        tessellator.addVertexWithUV(xPos + width, yPos + height, 0, 1.0, 1.0);
        tessellator.addVertexWithUV(xPos + width, yPos         , 0, 1.0, 0.0);
        tessellator.addVertexWithUV(xPos        , yPos         , 0, 0.0, 0.0);
        tessellator.draw();
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
	}
}
