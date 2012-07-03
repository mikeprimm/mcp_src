package net.minecraft.src;

public abstract class BlockContainer extends Block
{
    protected BlockContainer(int par1, Material par2Material)
    {
        super(par1, par2Material);
        isBlockContainer = true;
    }

    protected BlockContainer(int par1, int par2, Material par3Material)
    {
        super(par1, par2, par3Material);
        isBlockContainer = true;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);
        par1World.setBlockTileEntity(par2, par3, par4, func_56351_a(par1World));
    }

    public void func_56322_a(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        super.func_56322_a(par1World, par2, par3, par4, par5, par6);
        par1World.removeBlockTileEntity(par2, par3, par4);
    }

    public abstract TileEntity func_56351_a(World world);

    public void powerBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        super.powerBlock(par1World, par2, par3, par4, par5, par6);
        TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);

        if (tileentity != null)
        {
            tileentity.onTileEntityPowered(par5, par6);
        }
    }
}
