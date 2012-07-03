package net.minecraft.src;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MerchantRecipeList extends ArrayList
{
    public MerchantRecipeList()
    {
    }

    public MerchantRecipeList(NBTTagCompound par1NBTTagCompound)
    {
        func_56415_a(par1NBTTagCompound);
    }

    public MerchantRecipe func_56414_a(ItemStack par1ItemStack, ItemStack par2ItemStack, int par3)
    {
        if (par3 > 0 && par3 < size())
        {
            MerchantRecipe merchantrecipe = (MerchantRecipe)get(par3);

            if (par1ItemStack.itemID == merchantrecipe.func_56731_a().itemID && (par2ItemStack == null && !merchantrecipe.func_56735_c() || merchantrecipe.func_56735_c() && par2ItemStack != null && merchantrecipe.func_56729_b().itemID == par2ItemStack.itemID))
            {
                if (par1ItemStack.stackSize >= merchantrecipe.func_56731_a().stackSize && (!merchantrecipe.func_56735_c() || par2ItemStack.stackSize >= merchantrecipe.func_56729_b().stackSize))
                {
                    return merchantrecipe;
                }
                else
                {
                    return null;
                }
            }
        }

        for (int i = 0; i < size(); i++)
        {
            MerchantRecipe merchantrecipe1 = (MerchantRecipe)get(i);

            if (par1ItemStack.itemID == merchantrecipe1.func_56731_a().itemID && par1ItemStack.stackSize >= merchantrecipe1.func_56731_a().stackSize && (!merchantrecipe1.func_56735_c() && par2ItemStack == null || merchantrecipe1.func_56735_c() && par2ItemStack != null && merchantrecipe1.func_56729_b().itemID == par2ItemStack.itemID && par2ItemStack.stackSize >= merchantrecipe1.func_56729_b().stackSize))
            {
                return merchantrecipe1;
            }
        }

        return null;
    }

    public void func_56413_a(MerchantRecipe par1MerchantRecipe)
    {
        for (int i = 0; i < size(); i++)
        {
            MerchantRecipe merchantrecipe = (MerchantRecipe)get(i);

            if (par1MerchantRecipe.func_56730_a(merchantrecipe))
            {
                if (par1MerchantRecipe.func_56734_b(merchantrecipe))
                {
                    set(i, par1MerchantRecipe);
                }

                return;
            }
        }

        add(par1MerchantRecipe);
    }

    public void func_56411_a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte((byte)(size() & 0xff));

        for (int i = 0; i < size(); i++)
        {
            MerchantRecipe merchantrecipe = (MerchantRecipe)get(i);
            Packet.writeItemStack(merchantrecipe.func_56731_a(), par1DataOutputStream);
            Packet.writeItemStack(merchantrecipe.func_56732_d(), par1DataOutputStream);
            ItemStack itemstack = merchantrecipe.func_56729_b();
            par1DataOutputStream.writeBoolean(itemstack != null);

            if (itemstack != null)
            {
                Packet.writeItemStack(itemstack, par1DataOutputStream);
            }
        }
    }

    public void func_56415_a(NBTTagCompound par1NBTTagCompound)
    {
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Recipes");

        for (int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
            add(new MerchantRecipe(nbttagcompound));
        }
    }

    public NBTTagCompound func_56412_a()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        NBTTagList nbttaglist = new NBTTagList("Recipes");

        for (int i = 0; i < size(); i++)
        {
            MerchantRecipe merchantrecipe = (MerchantRecipe)get(i);
            nbttaglist.appendTag(merchantrecipe.func_56728_e());
        }

        nbttagcompound.setTag("Recipes", nbttaglist);
        return nbttagcompound;
    }
}
