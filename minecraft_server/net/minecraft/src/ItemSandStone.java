package net.minecraft.src;

public class ItemSandStone extends ItemBlock
{
    private Block field_56465_a;

    public ItemSandStone(int par1, Block par2Block)
    {
        super(par1);
        field_56465_a = par2Block;
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

        if (i < 0 || i >= BlockSandStone.field_56350_a.length)
        {
            i = 0;
        }

        return (new StringBuilder()).append(super.getItemName()).append(".").append(BlockSandStone.field_56350_a[i]).toString();
    }
}
