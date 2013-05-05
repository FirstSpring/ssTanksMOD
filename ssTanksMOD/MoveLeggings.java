package net.minecraft.ssTanksMOD;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.WeakHashMap;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MoveLeggings extends ItemArmor
{

	WeakHashMap<EntityPlayer,EntityHook> LeftHook = new WeakHashMap<EntityPlayer,EntityHook>();
	WeakHashMap<EntityPlayer,EntityHook> RightHook = new WeakHashMap<EntityPlayer,EntityHook>();

	public MoveLeggings(int par1, EnumArmorMaterial par2EnumArmorMaterial, int par3, int par4) {
		super(par1, par2EnumArmorMaterial, par3, par4);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		super.registerIcons(par1IconRegister);
	}

	public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack)
	{
		if(!player.worldObj.isRemote)
		{
			player.capabilities.allowFlying = true;
			player.fallDistance = 0.0F;
		}

		if(!player.worldObj.isRemote){

			float[] xyz = this.getxyz(player);
			player.stepHeight = 4.0F;
			player.moveEntity(xyz[0],xyz[1],xyz[2]);
			player.stepHeight = 0.5F;
		}
		else
		{
			clientproxy.mc.thePlayer.stepHeight = 4.0F;
			if(ssTanksMOD.インスタンス.クライアント側モーションX==0&&ssTanksMOD.インスタンス.クライアント側モーションY==0&&ssTanksMOD.インスタンス.クライアント側モーションZ == 0)
			{}
			else clientproxy.mc.thePlayer.moveEntity(ssTanksMOD.インスタンス.クライアント側モーションX,ssTanksMOD.インスタンス.クライアント側モーションY,ssTanksMOD.インスタンス.クライアント側モーションZ);
			clientproxy.mc.thePlayer.stepHeight = 0.5F;
			if(ssTanksMOD.インスタンス.クライアント側落ちない)
			{
				clientproxy.mc.thePlayer.motionY = 0.0F;
			}
		}

		if(player.worldObj.isRemote)
		{

		}
		else if(ssTanksMOD.インスタンス.入力状態.containsKey(player.username))
		{

			if(ssTanksMOD.インスタンス.入力状態.get(player.username)[12] == 1)
			{
				if(!LeftHook.containsKey(player)){}
				else
				{
					this.LeftHook.get(player).setDead();
					this.LeftHook.remove(player);
				}
				if(!RightHook.containsKey(player)){}
				else
				{
					this.RightHook.get(player).setDead();
					this.RightHook.remove(player);
				}
			}
			if(ssTanksMOD.インスタンス.入力状態.get(player.username)[5] == 1)
			{
				if(ssTanksMOD.インスタンス.入力状態.get(player.username)[6] == 1&&
						ssTanksMOD.インスタンス.入力状態.get(player.username)[7] == 1)
				{
					if(!LeftHook.containsKey(player)){}
					else
					{
						this.LeftHook.get(player).setDead();
						this.LeftHook.remove(player);
					}

					EntityHook LeftHook = new EntityHook(player.worldObj,player,6.0F,-15.0F);
					this.LeftHook.put(player,LeftHook);
					player.worldObj.spawnEntityInWorld(LeftHook);

					if(!RightHook.containsKey(player)){}
					else
					{
						this.RightHook.get(player).setDead();
						this.RightHook.remove(player);
					}

					EntityHook RightHook = new EntityHook(player.worldObj,player,6.0F,15.0F);
					this.RightHook.put(player,RightHook);
					player.worldObj.spawnEntityInWorld(RightHook);

				}
				else if(ssTanksMOD.インスタンス.入力状態.get(player.username)[6] == 1)
				{
					if(!LeftHook.containsKey(player)){}
					else
					{
						this.LeftHook.get(player).setDead();
						this.LeftHook.remove(player);
					}

					EntityHook LeftHook = new EntityHook(player.worldObj,player,6.0F);
					this.LeftHook.put(player,LeftHook);
					player.worldObj.spawnEntityInWorld(LeftHook);
				}
				else if(ssTanksMOD.インスタンス.入力状態.get(player.username)[7] == 1)
				{
					if(!RightHook.containsKey(player)){}
					else
					{
						this.RightHook.get(player).setDead();
						this.RightHook.remove(player);
					}

					EntityHook RightHook = new EntityHook(player.worldObj,player,6.0F);
					this.RightHook.put(player,RightHook);
					player.worldObj.spawnEntityInWorld(RightHook);
				}
			}
		}
	}

	private float[] getxyz(EntityPlayer player) {

		float[] xyz = {0,0,0};
		boolean 落ちない = false;

		if(this.LeftHook.containsKey(player))
		{
			EntityHook LeftHook = this.LeftHook.get(player);
			float lxyz[] = LeftHook.getxyz();
			xyz[0] += lxyz[0];
			xyz[1] += lxyz[1];
			xyz[2] += lxyz[2];
			落ちない = true;
		}

		if(this.RightHook.containsKey(player))
		{
			EntityHook RightHook =  this.RightHook.get(player);
			float rxyz[] = RightHook.getxyz();
			xyz[0] += rxyz[0];
			xyz[1] += rxyz[1];
			xyz[2] += rxyz[2];
			落ちない = true;
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream(25);
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeFloat(xyz[0]);
			dos.writeFloat(xyz[1]);
			dos.writeFloat(xyz[2]);
			dos.writeBoolean(落ちない);
		} catch (IOException e) {
			e.printStackTrace();
		}

		PacketDispatcher.sendPacketToPlayer(new Packet250CustomPayload("位置合わせ",bos.toByteArray()),(Player) player);

		return xyz;
	}
}
