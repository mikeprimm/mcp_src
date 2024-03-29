package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class ItemBoat extends Item
{
    public ItemBoat(int par1)
    {
        super(par1);
        maxStackSize = 1;
        func_56455_a(CreativeTabs.field_56386_e);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        float f = 1.0F;
        float f1 = par3EntityPlayer.prevRotationPitch + (par3EntityPlayer.rotationPitch - par3EntityPlayer.prevRotationPitch) * f;
        float f2 = par3EntityPlayer.prevRotationYaw + (par3EntityPlayer.rotationYaw - par3EntityPlayer.prevRotationYaw) * f;
        double d = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * (double)f;
        double d1 = (par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * (double)f + 1.6200000000000001D) - (double)par3EntityPlayer.yOffset;
        double d2 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * (double)f;
        Vec3 vec3 = Vec3.func_58052_a().func_58076_a(d, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.01745329F - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.01745329F - (float)Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.01745329F);
        float f6 = MathHelper.sin(-f1 * 0.01745329F);
        float f7 = f4 * f5;
        float f8 = f6;
        float f9 = f3 * f5;
        double d3 = 5D;
        Vec3 vec3_1 = vec3.addVector((double)f7 * d3, (double)f8 * d3, (double)f9 * d3);
        MovingObjectPosition movingobjectposition = par2World.rayTraceBlocks_do(vec3, vec3_1, true);

        if (movingobjectposition == null)
        {
            return par1ItemStack;
        }

        Vec3 vec3_2 = par3EntityPlayer.getLook(f);
        boolean flag = false;
        float f10 = 1.0F;
        List list = par2World.getEntitiesWithinAABBExcludingEntity(par3EntityPlayer, par3EntityPlayer.boundingBox.addCoord(vec3_2.xCoord * d3, vec3_2.yCoord * d3, vec3_2.zCoord * d3).expand(f10, f10, f10));
        Iterator iterator = list.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            Entity entity = (Entity)iterator.next();

            if (entity.canBeCollidedWith())
            {
                float f11 = entity.getCollisionBorderSize();
                AxisAlignedBB axisalignedbb = entity.boundingBox.expand(f11, f11, f11);

                if (axisalignedbb.isVecInside(vec3))
                {
                    flag = true;
                }
            }
        }
        while (true);

        if (flag)
        {
            return par1ItemStack;
        }

        if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE)
        {
            int i = movingobjectposition.blockX;
            int j = movingobjectposition.blockY;
            int k = movingobjectposition.blockZ;

            if (!par2World.isRemote)
            {
                if (par2World.getBlockId(i, j, k) == Block.snow.blockID)
                {
                    j--;
                }

                par2World.spawnEntityInWorld(new EntityBoat(par2World, (float)i + 0.5F, (float)j + 1.0F, (float)k + 0.5F));
            }

            if (!par3EntityPlayer.capabilities.isCreativeMode)
            {
                par1ItemStack.stackSize--;
            }
        }

        return par1ItemStack;
    }
}
