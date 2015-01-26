/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.mod;

import java.util.EnumSet;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import panoramakit.engine.task.TaskManager;


/**
 * ClientTickHandler
 * 
 * @author dayanto
 */
public class OnFrameTickHandler{
	TaskManager taskManager = TaskManager.instance;

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event){
		World world = event.player.getEntityWorld();
		if(world.isRemote) {
			taskManager.runTick();
		}
	}
}/* extends TickEvent.ClientTickEvent
{
	public OnFrameTickHandler(){
		super(Phase.START);
		taskManager.runTick();
	}
	private final TaskManager taskManager = TaskManager.instance;

	@Override
	public void tickStart(EnumSet<Type> type, Object... tickData)
	{
		;
	}
	
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{}
	
	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.CLIENT);
	}
	
	//@Override
	////public String getLabel()
	//{
	//	return "panoramakit_client_tick";
	//}

}*/
