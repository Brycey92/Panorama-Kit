/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.mod;

import java.io.File;
import java.util.logging.Logger;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentStyle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.config.Configuration;
import org.lwjgl.input.Keyboard;
import panoramakit.engine.task.TaskManager;
import panoramakit.gui.PreviewRenderer;
import panoramakit.gui.screens.GuiScreenProgress;
import panoramakit.gui.screens.menuscreens.GuiMenuMain;
import panoramakit.gui.settings.ModSettings;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import cpw.mods.fml.relauncher.Side;
import panoramakit.gui.settings.SharedSettings;

/**
 * @author dayanto
 */
@Mod(
		modid = "PanoramaKit",
		name = "Panorama Kit",
		version = VersionInfo.VERSION
)
public class PanoramaKit
{
	private final Minecraft mc = Minecraft.getMinecraft();
	public final Logger L = Logger.getLogger("PanoramaKit");
	
	@Instance("PanoramaKit")
	public static PanoramaKit instance;
	
	private Configuration config;
	private ModSettings settings;
	private File renderDir;
	private File tempRenderDir;
	public static final KeyBinding MENU_KEY = new KeyBinding("key.panoramakit.menu", Keyboard.KEY_P, "Options");
	public static final KeyBinding RENDER_KEY = new KeyBinding("key.panoramakit.rendertest", Keyboard.KEY_K, "Options");
	TaskManager taskManager = TaskManager.instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent evt)
	{
		//setParent(FMLLog.getLogger());
		config = ConfigLoader.getConfig(evt.getSuggestedConfigurationFile());
		settings = new ModSettings();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		//TickRegistry.registerTickHandler(new OnFrameTickHandler(), Side.CLIENT);
		ClientRegistry.registerKeyBinding(RENDER_KEY);
		ClientRegistry.registerKeyBinding(MENU_KEY);
		FMLCommonHandler.instance().bus().register(new keystuff());
		FMLCommonHandler.instance().bus().register(new OnFrameTickHandler());
		//KeyBindingRegistry.registerKeyBinding();
		renderDir = new File(mc.mcDataDir, "panoramas");
		tempRenderDir = new File(renderDir, "temp");
		
		File preview = PreviewRenderer.getPreviewFile();
		if(preview.exists()) {
			preview.delete();
		}
	}

	//@EventHandler
	//public void onClientTickEvent(TickEvent.ClientTickEvent event){

	//	taskManager.runTick();
	//}

	//@SideOnly(Side.CLIENT)


	
	public Configuration getConfig()
	{
		return config;
	}
	
	public ModSettings getModSettings()
	{
		return settings;
	}
	
	public File getRenderDir()
	{
		return renderDir;
	}
	
	public File getTempRenderDir()
	{
		return tempRenderDir;
	}
	
	public void printChat(String msg, Object... params) {
		IChatComponent lolz = new ChatComponentTranslation(msg, params);
        mc.ingameGUI.getChatGUI().printChatMessage(lolz);
			//	addTranslatedMessage(msg, params);
    }
}