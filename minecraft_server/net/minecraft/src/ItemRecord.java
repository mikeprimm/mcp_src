package net.minecraft.src;

public class ItemRecord extends Item
{
    /** The name of the record. */
    public final String recordName;

    protected ItemRecord(int par1, String par2Str)
    {
        super(par1);
        recordName = par2Str;
        maxStackSize = 1;
        func_56455_a(CreativeTabs.field_56383_f);
    }

    public boolean func_56454_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par3World.getBlockId(par4, par5, par6) == Block.jukebox.blockID && par3World.getBlockMetadata(par4, par5, par6) == 0)
        {
            if (par3World.isRemote)
            {
                return true;
            }
            else
            {
                ((BlockJukeBox)Block.jukebox).insertRecord(par3World, par4, par5, par6, shiftedIndex);
                par3World.playAuxSFXAtEntity(null, 1005, par4, par5, par6, shiftedIndex);
                par1ItemStack.stackSize--;
                return true;
            }
        }
        else
        {
            return false;
        }
    }
}
