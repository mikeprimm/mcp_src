package net.minecraft.src;

import java.util.Random;

public class BlockBookshelf extends Block
{
    public BlockBookshelf(int par1, int par2)
    {
        super(par1, par2, Material.wood);
        func_56326_a(CreativeTabs.field_56387_b);
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int par1)
    {
        if (par1 <= 1)
        {
            return 4;
        }
        else
        {
            return blockIndexInTexture;
        }
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 3;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Item.book.shiftedIndex;
    }
}
