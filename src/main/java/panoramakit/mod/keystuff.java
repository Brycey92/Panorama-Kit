package panoramakit.mod;

import java.io.File;
import java.util.logging.Logger;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;

import net.minecraft.client.settings.KeyBinding;
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
 * Created by Elec332 on 4-1-2015.
 */
public class keystuff {
    private final Minecraft mc = Minecraft.getMinecraft();
    TaskManager taskManager = TaskManager.instance;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority= EventPriority.NORMAL, receiveCanceled=true)
    public void onEvent(InputEvent.KeyInputEvent event){
        if (PanoramaKit.MENU_KEY.isPressed()){
            if (taskManager.hasTasks()) {
                mc.displayGuiScreen(new GuiScreenProgress());
            } else if(mc.currentScreen == null) {
                mc.displayGuiScreen(new GuiMenuMain());
                SharedSettings.setOrientation(mc.thePlayer.rotationYaw);
            }
        }
        if (!taskManager.hasTasks()) {

        }
    }
}
