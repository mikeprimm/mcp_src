package net.minecraft.src;

public class ItemShears extends Item
{
    public ItemShears(int par1)
    {
        super(par1);
        setMaxStackSize(1);
        setMaxDamage(238);
        func_56455_a(CreativeTabs.field_56397_i);
    }

    public boolean func_58049_a(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLiving par7EntityLiving)
    {
        if (par3 == Block.leaves.blockID || par3 == Block.web.blockID || par3 == Block.tallGrass.blockID || par3 == Block.vine.blockID || par3 == Block.field_56333_bU.blockID)
        {
            par1ItemStack.damageItem(1, par7EntityLiving);
            return true;
        }
        else
        {
            return super.func_58049_a(par1ItemStack, par2World, par3, par4, par5, par6, par7EntityLiving);
        }
    }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    public boolean canHarvestBlock(Block par1Block)
    {
        return par1Block.blockID == Block.web.blockID;
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        if (par2Block.blockID == Block.web.blockID || par2Block.blockID == Block.leaves.blockID)
        {
            return 15F;
        }

        if (par2Block.blockID == Block.cloth.blockID)
        {
            return 5F;
        }
        else
        {
            return super.getStrVsBlock(par1ItemStack, par2Block);
        }
    }
}
