package net.minecraft.ssTanksMOD;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class clientproxy extends serverproxy{
	
	static Minecraft mc = FMLClientHandler.instance().getClient();
	
	@Override
	public void 登録()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityHook.class, new RenderHook());
		
		TickRegistry.registerTickHandler(new clienttickhandler(), Side.CLIENT);
	}
}