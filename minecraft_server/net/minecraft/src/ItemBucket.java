package net.minecraft.src;

import java.util.Random;

public class ItemBucket extends Item
{
    /** field for checking if the bucket has been filled. */
    private int isFull;

    public ItemBucket(int par1, int par2)
    {
        super(par1);
        maxStackSize = 1;
        isFull = par2;
        func_56455_a(CreativeTabs.field_56383_f);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        float f = 1.0F;
        double d = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * (double)f;
        double d1 = (par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * (double)f + 1.6200000000000001D) - (double)par3EntityPlayer.yOffset;
        double d2 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * (double)f;
        boolean flag = isFull == 0;
        MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, flag);

        if (movingobjectposition == null)
        {
            return par1ItemStack;
        }

        if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE)
        {
            int i = movingobjectposition.blockX;
            int j = movingobjectposition.blockY;
            int k = movingobjectposition.blockZ;

            if (!par2World.canMineBlock(par3EntityPlayer, i, j, k))
            {
                return par1ItemStack;
            }

            if (isFull == 0)
            {
                if (!par3EntityPlayer.canPlayerEdit(i, j, k))
                {
                    return par1ItemStack;
                }

                if (par2World.getBlockMaterial(i, j, k) == Material.water && par2World.getBlockMetadata(i, j, k) == 0)
                {
                    par2World.setBlockWithNotify(i, j, k, 0);

                    if (par3EntityPlayer.capabilities.isCreativeMode)
                    {
                        return par1ItemStack;
                    }

                    if (--par1ItemStack.stackSize <= 0)
                    {
                        return new ItemStack(Item.bucketWater);
                    }

                    if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.bucketWater)))
                    {
                        par3EntityPlayer.dropPlayerItem(new ItemStack(Item.bucketWater.shiftedIndex, 1, 0));
                    }

                    return par1ItemStack;
                }

                if (par2World.getBlockMaterial(i, j, k) == Material.lava && par2World.getBlockMetadata(i, j, k) == 0)
                {
                    par2World.setBlockWithNotify(i, j, k, 0);

                    if (par3EntityPlayer.capabilities.isCreativeMode)
                    {
                        return par1ItemStack;
                    }

                    if (--par1ItemStack.stackSize <= 0)
                    {
                        return new ItemStack(Item.bucketLava);
                    }

                    if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.bucketLava)))
                    {
                        par3EntityPlayer.dropPlayerItem(new ItemStack(Item.bucketLava.shiftedIndex, 1, 0));
                    }

                    return par1ItemStack;
                }
            }
            else
            {
                if (isFull < 0)
                {
                    return new ItemStack(Item.bucketEmpty);
                }

                if (movingobjectposition.sideHit == 0)
                {
                    j--;
                }

                if (movingobjectposition.sideHit == 1)
                {
                    j++;
                }

                if (movingobjectposition.sideHit == 2)
                {
                    k--;
                }

                if (movingobjectposition.sideHit == 3)
                {
                    k++;
                }

                if (movingobjectposition.sideHit == 4)
                {
                    i--;
                }

                if (movingobjectposition.sideHit == 5)
                {
                    i++;
                }

                if (!par3EntityPlayer.canPlayerEdit(i, j, k))
                {
                    return par1ItemStack;
                }

                if (func_56461_a(par2World, d, d1, d2, i, j, k) && !par3EntityPlayer.capabilities.isCreativeMode)
                {
                    return new ItemStack(Item.bucketEmpty);
                }
            }
        }
        else if (isFull == 0 && (movingobjectposition.entityHit instanceof EntityCow))
        {
            return new ItemStack(Item.bucketMilk);
        }

        return par1ItemStack;
    }

    public boolean func_56461_a(World par1World, double par2, double par4, double par6, int par8, int par9, int par10)
    {
        if (isFull <= 0)
        {
            return false;
        }

        if (par1World.isAirBlock(par8, par9, par10) || !par1World.getBlockMaterial(par8, par9, par10).isSolid())
        {
            if (par1World.worldProvider.isHellWorld && isFull == Block.waterMoving.blockID)
            {
                par1World.playSoundEffect(par2 + 0.5D, par4 + 0.5D, par6 + 0.5D, "random.fizz", 0.5F, 2.6F + (par1World.rand.nextFloat() - par1World.rand.nextFloat()) * 0.8F);

                for (int i = 0; i < 8; i++)
                {
                    par1World.spawnParticle("largesmoke", (double)par8 + Math.random(), (double)par9 + Math.random(), (double)par10 + Math.random(), 0.0D, 0.0D, 0.0D);
                }
            }
            else
            {
                par1World.setBlockAndMetadataWithNotify(par8, par9, par10, isFull, 0);
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
