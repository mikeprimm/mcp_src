package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockTripWire extends Block
{
    public BlockTripWire(int par1)
    {
        super(par1, 173, Material.circuits);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.15625F, 1.0F);
        setTickRandomly(true);
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 10;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int i)
    {
        return null;
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
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 30;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Item.silk.shiftedIndex;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);
        boolean flag = (i & 2) == 2;
        boolean flag1 = !par1World.func_58038_s(par2, par3 - 1, par4);

        if (flag != flag1)
        {
            dropBlockAsItem(par1World, par2, par3, par4, i, 0);
            par1World.setBlockWithNotify(par2, par3, par4, 0);
        }
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int i = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        boolean flag = (i & 4) == 4;
        boolean flag1 = (i & 2) == 2;

        if (!flag1)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.09375F, 1.0F);
        }
        else if (!flag)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
        else
        {
            setBlockBounds(0.0F, 0.0625F, 0.0F, 1.0F, 0.15625F, 1.0F);
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        byte byte0 = ((byte)(par1World.func_58038_s(par2, par3 - 1, par4) ? 0 : 2));
        par1World.setBlockMetadataWithNotify(par2, par3, par4, byte0);
        func_56349_e(par1World, par2, par3, par4, byte0);
    }

    public void func_56322_a(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        func_56349_e(par1World, par2, par3, par4, par6 | 1);
    }

    public void func_56324_a(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer)
    {
        if (par1World.isRemote)
        {
            return;
        }

        if (par6EntityPlayer.getCurrentEquippedItem() != null && par6EntityPlayer.getCurrentEquippedItem().itemID == Item.shears.shiftedIndex)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, par5 | 8);
        }
    }

    private void func_56349_e(World par1World, int par2, int par3, int par4, int par5)
    {
        for (int i = 0; i < 2; i++)
        {
            for (int j = 1; j < 42; j++)
            {
                int k = par2 + Direction.offsetX[i] * j;
                int l = par4 + Direction.offsetZ[i] * j;
                int i1 = par1World.getBlockId(k, par3, l);

                if (i1 == Block.field_56334_bT.blockID)
                {
                    Block.field_56334_bT.func_56356_a(par1World, k, par3, l, i1, par1World.getBlockMetadata(k, par3, l), true, j, par5);
                    continue;
                }

                if (i1 != Block.field_56333_bU.blockID)
                {
                    break;
                }
            }
        }
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (par1World.isRemote)
        {
            return;
        }

        if ((par1World.getBlockMetadata(par2, par3, par4) & 1) == 1)
        {
            return;
        }
        else
        {
            func_56348_g(par1World, par2, par3, par4);
            return;
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par1World.isRemote)
        {
            return;
        }

        if ((par1World.getBlockMetadata(par2, par3, par4) & 1) != 1)
        {
            return;
        }
        else
        {
            func_56348_g(par1World, par2, par3, par4);
            return;
        }
    }

    private void func_56348_g(World par1World, int par2, int par3, int par4)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);
        boolean flag = (i & 1) == 1;
        boolean flag1 = false;
        List list = par1World.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.func_58089_a().func_58067_a((double)par2 + minX, (double)par3 + minY, (double)par4 + minZ, (double)par2 + maxX, (double)par3 + maxY, (double)par4 + maxZ));

        if (!list.isEmpty())
        {
            flag1 = true;
        }

        if (flag1 && !flag)
        {
            i |= 1;
        }

        if (!flag1 && flag)
        {
            i &= -2;
        }

        if (flag1 != flag)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, i);
            func_56349_e(par1World, par2, par3, par4, i);
        }

        if (flag1)
        {
            par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate());
        }
    }
}
