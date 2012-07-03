package net.minecraft.src;

import java.util.Random;

public class BlockIce extends BlockBreakable
{
    public BlockIce(int par1, int par2)
    {
        super(par1, par2, Material.ice, false);
        slipperiness = 0.98F;
        setTickRandomly(true);
        func_56326_a(CreativeTabs.field_56387_b);
    }

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        par2EntityPlayer.addStat(StatList.mineBlockStatArray[blockID], 1);
        par2EntityPlayer.addExhaustion(0.025F);

        if (canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier(par2EntityPlayer.inventory))
        {
            ItemStack itemstack = createStackedBlock(par6);

            if (itemstack != null)
            {
                dropBlockAsItem_do(par1World, par3, par4, par5, itemstack);
            }
        }
        else
        {
            if (par1World.worldProvider.isHellWorld)
            {
                par1World.setBlockWithNotify(par3, par4, par5, 0);
                return;
            }

            int i = EnchantmentHelper.getFortuneModifier(par2EntityPlayer.inventory);
            dropBlockAsItem(par1World, par3, par4, par5, par6, i);
            Material material = par1World.getBlockMaterial(par3, par4 - 1, par5);

            if (material.blocksMovement() || material.isLiquid())
            {
                par1World.setBlockWithNotify(par3, par4, par5, Block.waterMoving.blockID);
            }
        }
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par1World.getSavedLightValue(EnumSkyBlock.Block, par2, par3, par4) > 11 - Block.lightOpacity[blockID])
        {
            if (par1World.worldProvider.isHellWorld)
            {
                par1World.setBlockWithNotify(par2, par3, par4, 0);
                return;
            }

            dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockWithNotify(par2, par3, par4, Block.waterStill.blockID);
        }
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int getMobilityFlag()
    {
        return 0;
    }
}
