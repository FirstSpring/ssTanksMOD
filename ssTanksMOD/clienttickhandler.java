package net.minecraft.ssTanksMOD;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.network.packet.Packet250CustomPayload;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;

public class clienttickhandler implements ITickHandler
{
	Minecraft mc = FMLClientHandler.instance().getClient();

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		if(FMLClientHandler.instance().getClient().thePlayer != null&&!mc.isGamePaused&&mc.inGameHasFocus)
		{
			byte[] keys = new byte[20];

			if(Keyboard.isKeyDown(Keyboard.getKeyIndex("W")))
				keys[0] = 1;
			else keys[0] = 0;
			if(Keyboard.isKeyDown(Keyboard.getKeyIndex("A")))
				keys[1] = 1;
			else keys[1] = 0;
			if(Keyboard.isKeyDown(Keyboard.getKeyIndex("S")))
				keys[2] = 1;
			else keys[2] = 0;
			if(Keyboard.isKeyDown(Keyboard.getKeyIndex("D")))
				keys[3] = 1;
			else keys[3] = 0;
			if(Keyboard.isKeyDown(Keyboard.getKeyIndex("SPACE")))
				keys[4] = 1;
			else keys[4] = 0;
			if(Keyboard.isKeyDown(Keyboard.getKeyIndex("LSHIFT")))
				keys[5] = 1;
			else keys[5] = 0;
			if(Mouse.isButtonDown(0))
				keys[6] = 1;
			else keys[6] = 0;
			if(Mouse.isButtonDown(1))
				keys[7] = 1;
			else keys[7] = 0;
			if(Keyboard.isKeyDown(Keyboard.getKeyIndex("TAB")))
				keys[8] = 1;
			else keys[8] = 0;
			if(Keyboard.isKeyDown(Keyboard.getKeyIndex("LCONTROL")))
				keys[9] = 1;
			else keys[9] = 0;
			if(Keyboard.isKeyDown(Keyboard.getKeyIndex("R")))
				keys[10] = 1;
			else keys[10] = 0;
			if(Keyboard.isKeyDown(Keyboard.getKeyIndex("F")))
				keys[11] = 1;
			else keys[11] = 0;
			if(Mouse.isButtonDown(2))
				keys[12] = 1;
			else keys[12] = 0;
			
			PacketDispatcher.sendPacketToServer(new Packet250CustomPayload("ssKeyCh",keys));

		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.RENDER);
	}

	@Override
	public String getLabel() { return null; }

}