package net.minecraft.src;

public class ItemSlab extends ItemBlock
{
    private final boolean field_55181_a;
    private final BlockHalfSlab field_55179_b;
    private final BlockHalfSlab field_55180_c;

    public ItemSlab(int par1, BlockHalfSlab par2BlockHalfSlab, BlockHalfSlab par3BlockHalfSlab, boolean par4)
    {
        super(par1);
        field_55179_b = par2BlockHalfSlab;
        field_55180_c = par3BlockHalfSlab;
        field_55181_a = par4;
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
        return field_55179_b.func_55138_c(par1ItemStack.getItemDamage());
    }

    public boolean func_56454_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (field_55181_a)
        {
            return super.func_56454_a(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
        }

        if (par1ItemStack.stackSize == 0)
        {
            return false;
        }

        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6))
        {
            return false;
        }

        int i = par3World.getBlockId(par4, par5, par6);
        int j = par3World.getBlockMetadata(par4, par5, par6);
        int k = j & 7;
        boolean flag = (j & 8) != 0;

        if ((par7 == 1 && !flag || par7 == 0 && flag) && i == field_55179_b.blockID && k == par1ItemStack.getItemDamage())
        {
            if (par3World.checkIfAABBIsClear(field_55180_c.getCollisionBoundingBoxFromPool(par3World, par4, par5, par6)) && par3World.setBlockAndMetadataWithNotify(par4, par5, par6, field_55180_c.blockID, k))
            {
                par3World.playSoundEffect((float)par4 + 0.5F, (float)par5 + 0.5F, (float)par6 + 0.5F, field_55180_c.stepSound.getStepSound(), (field_55180_c.stepSound.getVolume() + 1.0F) / 2.0F, field_55180_c.stepSound.getPitch() * 0.8F);
                par1ItemStack.stackSize--;
            }

            return true;
        }

        if (func_50020_b(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7))
        {
            return true;
        }
        else
        {
            return super.func_56454_a(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
        }
    }

    private boolean func_50020_b(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7)
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

        int i = par3World.getBlockId(par4, par5, par6);
        int j = par3World.getBlockMetadata(par4, par5, par6);
        int k = j & 7;

        if (i == field_55179_b.blockID && k == par1ItemStack.getItemDamage())
        {
            if (par3World.checkIfAABBIsClear(field_55180_c.getCollisionBoundingBoxFromPool(par3World, par4, par5, par6)) && par3World.setBlockAndMetadataWithNotify(par4, par5, par6, field_55180_c.blockID, k))
            {
                par3World.playSoundEffect((float)par4 + 0.5F, (float)par5 + 0.5F, (float)par6 + 0.5F, field_55180_c.stepSound.getStepSound(), (field_55180_c.stepSound.getVolume() + 1.0F) / 2.0F, field_55180_c.stepSound.getPitch() * 0.8F);
                par1ItemStack.stackSize--;
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
