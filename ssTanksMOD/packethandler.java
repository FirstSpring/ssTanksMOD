package net.minecraft.ssTanksMOD;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class packethandler implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager manager,Packet250CustomPayload packet, Player player){
		if(player instanceof EntityPlayer){
			if(packet.channel.equals("ssKeyCh")){
				EntityPlayer playerd = (EntityPlayer)player;
				ssTanksMOD.インスタンス.入力状態.put(String.valueOf(playerd.username), packet.data);
			}
			else if(packet.channel.equals("位置合わせ")){
				EntityPlayer playerd = (EntityPlayer)player;
				InputStream is = new ByteArrayInputStream(packet.data);
				DataInputStream dis = new DataInputStream(is);

				try {
					ssTanksMOD.インスタンス.クライアント側モーションX = dis.readFloat();
					ssTanksMOD.インスタンス.クライアント側モーションY = dis.readFloat();
					ssTanksMOD.インスタンス.クライアント側モーションZ = dis.readFloat();
					ssTanksMOD.インスタンス.クライアント側落ちない = dis.readBoolean();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
