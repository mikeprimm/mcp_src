package net.minecraft.src;

public class ItemSmoothStone extends ItemBlock
{
    private Block field_56466_a;

    public ItemSmoothStone(int par1, Block par2Block)
    {
        super(par1);
        field_56466_a = par2Block;
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return par1;
    }

    public String getItemNameIS(ItemStack par1ItemStack)
    {
        int i = par1ItemStack.getItemDamage();

        if (i < 0 || i >= BlockStoneBrick.field_56342_a.length)
        {
            i = 0;
        }

        return (new StringBuilder()).append(super.getItemName()).append(".").append(BlockStoneBrick.field_56342_a[i]).toString();
    }
}
