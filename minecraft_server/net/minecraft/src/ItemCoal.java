package net.minecraft.src;

public class ItemCoal extends Item
{
    public ItemCoal(int par1)
    {
        super(par1);
        setHasSubtypes(true);
        setMaxDamage(0);
        func_56455_a(CreativeTabs.field_56392_l);
    }

    public String getItemNameIS(ItemStack par1ItemStack)
    {
        if (par1ItemStack.getItemDamage() == 1)
        {
            return "item.charcoal";
        }
        else
        {
            return "item.coal";
        }
    }
}
