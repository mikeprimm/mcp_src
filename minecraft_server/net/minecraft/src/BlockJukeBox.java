package net.minecraft.src;

import java.util.Random;

public class BlockJukeBox extends BlockContainer
{
    protected BlockJukeBox(int par1, int par2)
    {
        super(par1, par2, Material.wood);
        func_56326_a(CreativeTabs.field_56388_c);
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int par1)
    {
        return blockIndexInTexture + (par1 != 1 ? 0 : 1);
    }

    public boolean func_56323_a(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.getBlockMetadata(par2, par3, par4) == 0)
        {
            return false;
        }
        else
        {
            ejectRecord(par1World, par2, par3, par4);
            return true;
        }
    }

    /**
     * Inserts the given record into the JukeBox.
     */
    public void insertRecord(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par1World.isRemote)
        {
            return;
        }

        TileEntityRecordPlayer tileentityrecordplayer = (TileEntityRecordPlayer)par1World.getBlockTileEntity(par2, par3, par4);

        if (tileentityrecordplayer == null)
        {
            return;
        }
        else
        {
            tileentityrecordplayer.record = par5;
            tileentityrecordplayer.onInventoryChanged();
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 1);
            return;
        }
    }

    /**
     * Ejects the current record inside of the jukebox.
     */
    public void ejectRecord(World par1World, int par2, int par3, int par4)
    {
        if (par1World.isRemote)
        {
            return;
        }

        TileEntityRecordPlayer tileentityrecordplayer = (TileEntityRecordPlayer)par1World.getBlockTileEntity(par2, par3, par4);

        if (tileentityrecordplayer == null)
        {
            return;
        }

        int i = tileentityrecordplayer.record;

        if (i == 0)
        {
            return;
        }
        else
        {
            par1World.playAuxSFX(1005, par2, par3, par4, 0);
            par1World.playRecord(null, par2, par3, par4);
            tileentityrecordplayer.record = 0;
            tileentityrecordplayer.onInventoryChanged();
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 0);
            float f = 0.7F;
            double d = (double)(par1World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d1 = (double)(par1World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.20000000000000001D + 0.59999999999999998D;
            double d2 = (double)(par1World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(par1World, (double)par2 + d, (double)par3 + d1, (double)par4 + d2, new ItemStack(i, 1, 0));
            entityitem.delayBeforeCanPickup = 10;
            par1World.spawnEntityInWorld(entityitem);
            return;
        }
    }

    public void func_56322_a(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        ejectRecord(par1World, par2, par3, par4);
        super.func_56322_a(par1World, par2, par3, par4, par5, par6);
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        if (par1World.isRemote)
        {
            return;
        }
        else
        {
            super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, 0);
            return;
        }
    }

    public TileEntity func_56351_a(World par1World)
    {
        return new TileEntityRecordPlayer();
    }
}
