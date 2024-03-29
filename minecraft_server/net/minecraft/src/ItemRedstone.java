package net.minecraft.src;

public class ItemRedstone extends Item
{
    public ItemRedstone(int par1)
    {
        super(par1);
        func_56455_a(CreativeTabs.field_56385_d);
    }

    public boolean func_56454_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par3World.getBlockId(par4, par5, par6) != Block.snow.blockID)
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

            if (!par3World.isAirBlock(par4, par5, par6))
            {
                return false;
            }
        }

        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6))
        {
            return false;
        }

        if (Block.redstoneWire.canPlaceBlockAt(par3World, par4, par5, par6))
        {
            par1ItemStack.stackSize--;
            par3World.setBlockWithNotify(par4, par5, par6, Block.redstoneWire.blockID);
        }

        return true;
    }
}
