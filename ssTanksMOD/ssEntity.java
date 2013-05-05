package net.minecraft.ssTanksMOD;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ssEntity extends Entity
{
	protected int newPosRotationIncrements;
	protected double newPosX;
	protected double newPosY;
	protected double newPosZ;
	protected double newRotationYaw;
	protected double newRotationPitch;

	protected boolean canTriggerWalking()
	{
		return false;
	}

	public AxisAlignedBB getBoundingBox()
	{
		return this.boundingBox;
	}

	public ssEntity(World par1World)
	{
		super(par1World);
		this.preventEntitySpawning = true;
		this.ignoreFrustumCheck = false;
		this.stepHeight = 2.0F;
	}

	protected void entityInit()
	{
		this.dataWatcher.addObject(19, new Integer(0));
	}

	public boolean canBeCollidedWith()
	{
		return !this.isDead;
	}

	public boolean canBePushed()
	{
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
	{
		this.yOffset = 0.0F;
		this.newPosX = par1;
		this.newPosY = par3;
		this.newPosZ = par5;
		this.newRotationYaw = (double)par7;
		this.newRotationPitch = (double)par8;
		this.newPosRotationIncrements = par9;
	}

	public void onUpdate()
	{
		super.onUpdate();
		if(this.worldObj.isRemote){

			if (this.newPosRotationIncrements > 0)
			{
				double var1 = this.posX + (this.newPosX - this.posX) / (double)this.newPosRotationIncrements;
				double var3 = this.posY + (this.newPosY - this.posY) / (double)this.newPosRotationIncrements;
				double var5 = this.posZ + (this.newPosZ - this.posZ) / (double)this.newPosRotationIncrements;
				double var7 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - (double)this.rotationYaw);
				this.rotationYaw = (float)((double)this.rotationYaw + var7 / (double)this.newPosRotationIncrements);
				this.rotationPitch = (float)((double)this.rotationPitch + (this.newRotationPitch - (double)this.rotationPitch) / (double)this.newPosRotationIncrements);
				--this.newPosRotationIncrements;
				this.setPosition(var1, var3, var5);
				this.setRotation(this.rotationYaw, this.rotationPitch);
			}
			else
			{
				this.setPosition(this.posX, this.posY, this.posZ);
				this.setRotation((float)this.rotationYaw, (float)this.rotationPitch);
			}
		}
	}

	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)//無敵
	{
		return false;
	}

	public boolean interact(EntityPlayer par1EntityPlayer)
	{
		if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != par1EntityPlayer)
		{
			return true;
		}
		else
		{
			if (!this.worldObj.isRemote)
			{
				par1EntityPlayer.mountEntity(this);
			}

			return true;
		}
	}

	protected void fall(float par1){}
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound){}
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound){}

	public int getDamageTaken()
	{
		return this.dataWatcher.getWatchableObjectInt(19);
	}

	public void setDamageTaken(int par1)
	{
		this.dataWatcher.updateObject(19, Integer.valueOf(par1));
	}

}
