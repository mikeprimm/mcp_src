package net.minecraft.src;

public class BlockWood extends Block
{
    public static final String field_56347_a[] =
    {
        "oak", "spruce", "birch", "jungle"
    };

    public BlockWood(int par1)
    {
        super(par1, 4, Material.wood);
        func_56326_a(CreativeTabs.field_56387_b);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        switch (par2)
        {
            default:
                return 4;

            case 1:
                return 198;

            case 2:
                return 214;

            case 3:
                return 199;
        }
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    protected int damageDropped(int par1)
    {
        return par1;
    }
}
