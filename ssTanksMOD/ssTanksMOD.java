package net.minecraft.ssTanksMOD;

import java.util.HashMap;
import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "ssTanksMOD", name="フックショットMOD", version="0_0")
@NetworkMod(clientSideRequired = true ,serverSideRequired = true,channels = {"位置合わせ" ,"ssKeyCh"},packetHandler = packethandler.class)
public class ssTanksMOD {

	float クライアント側モーションX = 0;
	float クライアント側モーションY = 0;
	float クライアント側モーションZ = 0;
	boolean クライアント側落ちない = false;
	
	Item moveleg;
	int movelegID;
	
	int EntityHookID;
	
	HashMap<String,byte[]> 入力状態 = new HashMap<String,byte[]>();

	@SidedProxy(clientSide = "net.minecraft.ssTanksMOD.clientproxy", serverSide = "net.minecraft.ssTanksMOD.serverproxy")
	public static serverproxy プロキシ;

	@Mod.Instance("ssTanksMOD")
	public static ssTanksMOD インスタンス;

	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try
		{
			cfg.load();
			movelegID = cfg.getItem("itemID", "movelegID", 5020).getInt();
			EntityHookID = cfg.get("EntityID", "EntityHookID", 4704).getInt();
		}
		catch (Exception e)
		{
			FMLLog.log(Level.SEVERE, e, "コンフィグでエラー");
		}
		finally
		{
			cfg.save();
		}
	}

	@Mod.Init
	public void Init(FMLInitializationEvent event) {

		EntityRegistry.registerGlobalEntityID(EntityHook.class, "EntityHook", this.EntityHookID);
		EntityRegistry.registerModEntity(EntityHook.class, "EntityHook", 0, this, 250, 1,true);

		this.moveleg = new MoveLeggings(this.movelegID,EnumArmorMaterial.DIAMOND,2,2).setUnlocalizedName("leggingsDiamond");
		LanguageRegistry.addName(moveleg, "hookshot");
		LanguageRegistry.instance().addNameForObject(moveleg, "ja_JP", "フックショットもどき");
		
		GameRegistry.addRecipe(
				new ItemStack(moveleg, 1), 
                new Object[]{ " X ","XXX","X X",
                Character.valueOf('X'),Block.wood});
		
		プロキシ.登録();
	}
}
