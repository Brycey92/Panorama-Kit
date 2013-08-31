/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.gui.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

/** 
 * @author dayanto
 */
public class GuiRenderNotice extends GuiScreen
{
	boolean hasDrawn = false; 
	
	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int x, int y, float z)
	{	
		// Waits until what's drawn has been displayed to the screen before letting the rendering start.
		if(hasDrawn) {
			Minecraft.getMinecraft().displayGuiScreen(null);
		}
		
		drawDefaultBackground();
		drawCenteredString(fontRenderer, "Rendering...", width / 2, height / 2, 0xe0e0e0);
		
		hasDrawn = true;
	}
}
