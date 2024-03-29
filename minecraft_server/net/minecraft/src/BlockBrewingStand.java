package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockBrewingStand extends BlockContainer
{
    private Random rand;

    public BlockBrewingStand(int par1)
    {
        super(par1, Material.iron);
        rand = new Random();
        blockIndexInTexture = 157;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 25;
    }

    public TileEntity func_56351_a(World par1World)
    {
        return new TileEntityBrewingStand();
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public void func_56330_a(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 0.875F, 0.5625F);
        super.func_56330_a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        setBlockBoundsForItemRender();
        super.func_56330_a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
    }

    public boolean func_56323_a(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.isRemote)
        {
            return true;
        }

        TileEntityBrewingStand tileentitybrewingstand = (TileEntityBrewingStand)par1World.getBlockTileEntity(par2, par3, par4);

        if (tileentitybrewingstand != null)
        {
            par5EntityPlayer.displayGUIBrewingStand(tileentitybrewingstand);
        }

        return true;
    }

    public void func_56322_a(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);

        if (tileentity instanceof TileEntityBrewingStand)
        {
            TileEntityBrewingStand tileentitybrewingstand = (TileEntityBrewingStand)tileentity;
            label0:

            for (int i = 0; i < tileentitybrewingstand.getSizeInventory(); i++)
            {
                ItemStack itemstack = tileentitybrewingstand.getStackInSlot(i);

                if (itemstack == null)
                {
                    continue;
                }

                float f = rand.nextFloat() * 0.8F + 0.1F;
                float f1 = rand.nextFloat() * 0.8F + 0.1F;
                float f2 = rand.nextFloat() * 0.8F + 0.1F;

                do
                {
                    if (itemstack.stackSize <= 0)
                    {
                        continue label0;
                    }

                    int j = rand.nextInt(21) + 10;

                    if (j > itemstack.stackSize)
                    {
                        j = itemstack.stackSize;
                    }

                    itemstack.stackSize -= j;
                    EntityItem entityitem = new EntityItem(par1World, (float)par2 + f, (float)par3 + f1, (float)par4 + f2, new ItemStack(itemstack.itemID, j, itemstack.getItemDamage()));
                    float f3 = 0.05F;
                    entityitem.motionX = (float)rand.nextGaussian() * f3;
                    entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
                    entityitem.motionZ = (float)rand.nextGaussian() * f3;
                    par1World.spawnEntityInWorld(entityitem);
                }
                while (true);
            }
        }

        super.func_56322_a(par1World, par2, par3, par4, par5, par6);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Item.brewingStand.shiftedIndex;
    }
}
