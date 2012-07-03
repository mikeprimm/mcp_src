package net.minecraft.src;

import java.util.Random;

public class WorldGeneratorBonusChest extends WorldGenerator
{
    private final WeightedRandomChestContent field_55131_a[];
    private final int field_55130_b;

    public WorldGeneratorBonusChest(WeightedRandomChestContent par1ArrayOfWeightedRandomChestContent[], int par2)
    {
        field_55131_a = par1ArrayOfWeightedRandomChestContent;
        field_55130_b = par2;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        for (int i = 0; ((i = par1World.getBlockId(par3, par4, par5)) == 0 || i == Block.leaves.blockID) && par4 > 1; par4--) { }

        if (par4 < 1)
        {
            return false;
        }

        par4++;

        for (int j = 0; j < 4; j++)
        {
            int k = (par3 + par2Random.nextInt(4)) - par2Random.nextInt(4);
            int l = (par4 + par2Random.nextInt(3)) - par2Random.nextInt(3);
            int i1 = (par5 + par2Random.nextInt(4)) - par2Random.nextInt(4);

            if (par1World.isAirBlock(k, l, i1) && par1World.func_58038_s(k, l - 1, i1))
            {
                par1World.setBlockWithNotify(k, l, i1, Block.chest.blockID);
                TileEntityChest tileentitychest = (TileEntityChest)par1World.getBlockTileEntity(k, l, i1);

                if (tileentitychest != null && tileentitychest != null)
                {
                    WeightedRandomChestContent.func_55216_a(par2Random, field_55131_a, tileentitychest, field_55130_b);
                }

                if (par1World.isAirBlock(k - 1, l, i1) && par1World.func_58038_s(k - 1, l - 1, i1))
                {
                    par1World.setBlockWithNotify(k - 1, l, i1, Block.torchWood.blockID);
                }

                if (par1World.isAirBlock(k + 1, l, i1) && par1World.func_58038_s(k - 1, l - 1, i1))
                {
                    par1World.setBlockWithNotify(k + 1, l, i1, Block.torchWood.blockID);
                }

                if (par1World.isAirBlock(k, l, i1 - 1) && par1World.func_58038_s(k - 1, l - 1, i1))
                {
                    par1World.setBlockWithNotify(k, l, i1 - 1, Block.torchWood.blockID);
                }

                if (par1World.isAirBlock(k, l, i1 + 1) && par1World.func_58038_s(k - 1, l - 1, i1))
                {
                    par1World.setBlockWithNotify(k, l, i1 + 1, Block.torchWood.blockID);
                }

                return true;
            }
        }

        return false;
    }
}
