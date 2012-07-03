package net.minecraft.src;

import java.util.Random;

public class BlockStep extends BlockHalfSlab
{
    public static final String blockStepTypes[] =
    {
        "stone", "sand", "wood", "cobble", "brick", "smoothStoneBrick"
    };

    public BlockStep(int par1, boolean par2)
    {
        super(par1, par2, Material.rock);
        func_56326_a(CreativeTabs.field_56387_b);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        int i = par2 & 7;

        if (i == 0)
        {
            return par1 > 1 ? 5 : 6;
        }

        if (i == 1)
        {
            if (par1 == 0)
            {
                return 208;
            }

            return par1 != 1 ? 192 : 176;
        }

        if (i == 2)
        {
            return 4;
        }

        if (i == 3)
        {
            return 16;
        }

        if (i == 4)
        {
            return Block.brick.blockIndexInTexture;
        }

        if (i == 5)
        {
            return Block.stoneBrick.blockIndexInTexture;
        }
        else
        {
            return 6;
        }
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int par1)
    {
        return getBlockTextureFromSideAndMetadata(par1, 0);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Block.field_55133_ak.blockID;
    }

    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
    protected ItemStack createStackedBlock(int par1)
    {
        return new ItemStack(Block.field_55133_ak.blockID, 2, par1 & 7);
    }

    public String func_55138_c(int par1)
    {
        if (par1 < 0 || par1 >= blockStepTypes.length)
        {
            par1 = 0;
        }

        return (new StringBuilder()).append(super.getBlockName()).append(".").append(blockStepTypes[par1]).toString();
    }
}
