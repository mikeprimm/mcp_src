package net.minecraft.src;

public class MerchantRecipe
{
    private ItemStack field_56738_a;
    private ItemStack field_56736_b;
    private ItemStack field_56737_c;
    private int field_58085_d;

    public MerchantRecipe(NBTTagCompound par1NBTTagCompound)
    {
        func_56733_a(par1NBTTagCompound);
    }

    public MerchantRecipe(ItemStack par1ItemStack, ItemStack par2ItemStack, ItemStack par3ItemStack)
    {
        field_56738_a = par1ItemStack;
        field_56736_b = par2ItemStack;
        field_56737_c = par3ItemStack;
    }

    public MerchantRecipe(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        this(par1ItemStack, null, par2ItemStack);
    }

    public MerchantRecipe(ItemStack par1ItemStack, Item par2Item)
    {
        this(par1ItemStack, new ItemStack(par2Item));
    }

    public ItemStack func_56731_a()
    {
        return field_56738_a;
    }

    public ItemStack func_56729_b()
    {
        return field_56736_b;
    }

    public boolean func_56735_c()
    {
        return field_56736_b != null;
    }

    public ItemStack func_56732_d()
    {
        return field_56737_c;
    }

    public boolean func_56730_a(MerchantRecipe par1MerchantRecipe)
    {
        if (field_56738_a.itemID != par1MerchantRecipe.field_56738_a.itemID || field_56737_c.itemID != par1MerchantRecipe.field_56737_c.itemID)
        {
            return false;
        }
        else
        {
            return field_56736_b == null && par1MerchantRecipe.field_56736_b == null || field_56736_b != null && par1MerchantRecipe.field_56736_b != null && field_56736_b.itemID == par1MerchantRecipe.field_56736_b.itemID;
        }
    }

    public boolean func_56734_b(MerchantRecipe par1MerchantRecipe)
    {
        return func_56730_a(par1MerchantRecipe) && (field_56738_a.stackSize < par1MerchantRecipe.field_56738_a.stackSize || field_56736_b != null && field_56736_b.stackSize < par1MerchantRecipe.field_56736_b.stackSize);
    }

    public int func_58083_e()
    {
        return field_58085_d;
    }

    public void func_58084_f()
    {
        field_58085_d++;
    }

    public void func_56733_a(NBTTagCompound par1NBTTagCompound)
    {
        NBTTagCompound nbttagcompound = par1NBTTagCompound.getCompoundTag("buy");
        field_56738_a = ItemStack.loadItemStackFromNBT(nbttagcompound);
        NBTTagCompound nbttagcompound1 = par1NBTTagCompound.getCompoundTag("sell");
        field_56737_c = ItemStack.loadItemStackFromNBT(nbttagcompound1);

        if (par1NBTTagCompound.hasKey("buyB"))
        {
            field_56736_b = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("buyB"));
        }

        if (par1NBTTagCompound.hasKey("uses"))
        {
            field_58085_d = par1NBTTagCompound.getInteger("uses");
        }
    }

    public NBTTagCompound func_56728_e()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setCompoundTag("buy", field_56738_a.writeToNBT(new NBTTagCompound("buy")));
        nbttagcompound.setCompoundTag("sell", field_56737_c.writeToNBT(new NBTTagCompound("sell")));

        if (field_56736_b != null)
        {
            nbttagcompound.setCompoundTag("buyB", field_56736_b.writeToNBT(new NBTTagCompound("buyB")));
        }

        nbttagcompound.setInteger("uses", field_58085_d);
        return nbttagcompound;
    }
}
