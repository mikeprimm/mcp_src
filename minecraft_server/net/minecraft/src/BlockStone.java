package net.minecraft.src;

import java.util.Random;

public class BlockStone extends Block
{
    public BlockStone(int par1, int par2)
    {
        super(par1, par2, Material.rock);
        func_56326_a(CreativeTabs.field_56387_b);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Block.cobblestone.blockID;
    }
}
