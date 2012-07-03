package net.minecraft.src;

import java.util.Random;

public class ItemFlintAndSteel extends Item
{
    public ItemFlintAndSteel(int par1)
    {
        super(par1);
        maxStackSize = 1;
        setMaxDamage(64);
        func_56455_a(CreativeTabs.field_56397_i);
    }

    public boolean func_56454_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par7 == 0)
        {
            par5--;
        }

        if (par7 == 1)
        {
            par5++;
        }

        if (par7 == 2)
        {
            par6--;
        }

        if (par7 == 3)
        {
            par6++;
        }

        if (par7 == 4)
        {
            par4--;
        }

        if (par7 == 5)
        {
            par4++;
        }

        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6))
        {
            return false;
        }

        int i = par3World.getBlockId(par4, par5, par6);

        if (i == 0)
        {
            par3World.playSoundEffect((double)par4 + 0.5D, (double)par5 + 0.5D, (double)par6 + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
            par3World.setBlockWithNotify(par4, par5, par6, Block.fire.blockID);
        }

        par1ItemStack.damageItem(1, par2EntityPlayer);
        return true;
    }
}
