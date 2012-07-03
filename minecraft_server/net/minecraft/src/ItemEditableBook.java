package net.minecraft.src;

public class ItemEditableBook extends Item
{
    public ItemEditableBook(int par1)
    {
        super(par1);
        setMaxStackSize(1);
    }

    public static boolean func_55182_a(NBTTagCompound par0NBTTagCompound)
    {
        if (!ItemWritableBook.func_55178_a(par0NBTTagCompound))
        {
            return false;
        }

        if (!par0NBTTagCompound.hasKey("title"))
        {
            return false;
        }

        String s = par0NBTTagCompound.getString("title");

        if (s == null || s.length() > 16)
        {
            return false;
        }

        return par0NBTTagCompound.hasKey("author");
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par3EntityPlayer.func_55077_b(par1ItemStack);
        return par1ItemStack;
    }

    public boolean func_46003_i()
    {
        return true;
    }
}
