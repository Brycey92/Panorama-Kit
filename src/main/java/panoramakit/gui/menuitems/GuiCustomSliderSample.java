/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.gui.menuitems;

import panoramakit.gui.screens.settingsscreens.GuiScreenSettings;

/**
 * @author dayanto
 */
public class GuiCustomSliderSample extends GuiCustomSlider
{
	
	public GuiCustomSliderSample(int id, int x, int y, GuiScreenSettings settingsScreen, String baseString, String tipMessage, float min, float max, float step, float value)
	{
		super(id, x, y, settingsScreen, baseString, tipMessage, min, max, step, value);
	}
	
	@Override
	public void updateDisplayString() 
	{
		// modify the display string by adding an x after the value.
		displayString = String.format(baseString + ": %.1fx", getValue()); 
	}
}