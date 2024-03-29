package net.minecraft.src;

import java.util.Random;

public class BlockMelon extends Block
{
    protected BlockMelon(int par1)
    {
        super(par1, Material.pumpkin);
        blockIndexInTexture = 136;
        func_56326_a(CreativeTabs.field_56387_b);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return par1 != 1 && par1 != 0 ? 136 : 137;
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int par1)
    {
        return par1 != 1 && par1 != 0 ? 136 : 137;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Item.melon.shiftedIndex;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 3 + par1Random.nextInt(5);
    }

    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    public int quantityDroppedWithBonus(int par1, Random par2Random)
    {
        int i = quantityDropped(par2Random) + par2Random.nextInt(1 + par1);

        if (i > 9)
        {
            i = 9;
        }

        return i;
    }
}
