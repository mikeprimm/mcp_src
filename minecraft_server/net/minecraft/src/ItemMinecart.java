package net.minecraft.src;

public class ItemMinecart extends Item
{
    public int minecartType;

    public ItemMinecart(int par1, int par2)
    {
        super(par1);
        maxStackSize = 1;
        minecartType = par2;
        func_56455_a(CreativeTabs.field_56386_e);
    }

    public boolean func_56454_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        int i = par3World.getBlockId(par4, par5, par6);

        if (BlockRail.isRailBlock(i))
        {
            if (!par3World.isRemote)
            {
                par3World.spawnEntityInWorld(new EntityMinecart(par3World, (float)par4 + 0.5F, (float)par5 + 0.5F, (float)par6 + 0.5F, minecartType));
            }

            par1ItemStack.stackSize--;
            return true;
        }
        else
        {
            return false;
        }
    }
}
