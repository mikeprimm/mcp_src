package net.minecraft.src;

import java.util.Random;

public class BlockTripWireSource extends Block
{
    public BlockTripWireSource(int par1)
    {
        super(par1, 172, Material.circuits);
        func_56326_a(CreativeTabs.field_56385_d);
        setTickRandomly(true);
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
        return 29;
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 10;
    }

    /**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par5 == 2 && par1World.isBlockNormalCube(par2, par3, par4 + 1))
        {
            return true;
        }

        if (par5 == 3 && par1World.isBlockNormalCube(par2, par3, par4 - 1))
        {
            return true;
        }

        if (par5 == 4 && par1World.isBlockNormalCube(par2 + 1, par3, par4))
        {
            return true;
        }

        return par5 == 5 && par1World.isBlockNormalCube(par2 - 1, par3, par4);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        if (par1World.isBlockNormalCube(par2 - 1, par3, par4))
        {
            return true;
        }

        if (par1World.isBlockNormalCube(par2 + 1, par3, par4))
        {
            return true;
        }

        if (par1World.isBlockNormalCube(par2, par3, par4 - 1))
        {
            return true;
        }

        return par1World.isBlockNormalCube(par2, par3, par4 + 1);
    }

    public void func_56327_a(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8)
    {
        byte byte0 = 0;

        if (par5 == 2 && par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true))
        {
            byte0 = 2;
        }

        if (par5 == 3 && par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true))
        {
            byte0 = 0;
        }

        if (par5 == 4 && par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true))
        {
            byte0 = 1;
        }

        if (par5 == 5 && par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true))
        {
            byte0 = 3;
        }

        func_56356_a(par1World, par2, par3, par4, blockID, byte0, false, -1, 0);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par5 == blockID)
        {
            return;
        }

        if (func_56357_g(par1World, par2, par3, par4))
        {
            int i = par1World.getBlockMetadata(par2, par3, par4);
            int j = i & 3;
            boolean flag = false;

            if (!par1World.isBlockNormalCube(par2 - 1, par3, par4) && j == 3)
            {
                flag = true;
            }

            if (!par1World.isBlockNormalCube(par2 + 1, par3, par4) && j == 1)
            {
                flag = true;
            }

            if (!par1World.isBlockNormalCube(par2, par3, par4 - 1) && j == 0)
            {
                flag = true;
            }

            if (!par1World.isBlockNormalCube(par2, par3, par4 + 1) && j == 2)
            {
                flag = true;
            }

            if (flag)
            {
                dropBlockAsItem(par1World, par2, par3, par4, i, 0);
                par1World.setBlockWithNotify(par2, par3, par4, 0);
            }
        }
    }

    public void func_56356_a(World par1World, int par2, int par3, int par4, int par5, int par6, boolean par7, int par8, int par9)
    {
        int i = par6 & 3;
        boolean flag = (par6 & 4) == 4;
        boolean flag1 = (par6 & 8) == 8;
        boolean flag2 = par5 == Block.field_56334_bT.blockID;
        boolean flag3 = false;
        boolean flag4 = !par1World.func_58038_s(par2, par3 - 1, par4);
        int j = Direction.offsetX[i];
        int k = Direction.offsetZ[i];
        int l = 0;
        int ai[] = new int[42];

        for (int i1 = 1; i1 < 42; i1++)
        {
            int k1 = par2 + j * i1;
            int j2 = par4 + k * i1;
            int i3 = par1World.getBlockId(k1, par3, j2);

            if (i3 == Block.field_56334_bT.blockID)
            {
                int l3 = par1World.getBlockMetadata(k1, par3, j2);

                if ((l3 & 3) == Direction.footInvisibleFaceRemap[i])
                {
                    l = i1;
                }

                break;
            }

            if (i3 == Block.field_56333_bU.blockID || i1 == par8)
            {
                int i4 = i1 != par8 ? par1World.getBlockMetadata(k1, par3, j2) : par9;
                boolean flag5 = (i4 & 8) != 8;
                boolean flag6 = (i4 & 1) == 1;
                boolean flag7 = (i4 & 2) == 2;
                flag2 &= flag7 == flag4;
                flag3 |= flag5 && flag6;
                ai[i1] = i4;

                if (i1 == par8)
                {
                    par1World.scheduleBlockUpdate(par2, par3, par4, par5, tickRate());
                    flag2 &= flag5;
                }
            }
            else
            {
                ai[i1] = -1;
                flag2 = false;
            }
        }

        flag2 &= l > 1;
        flag3 &= flag2;
        int j1 = (flag2 ? 4 : 0) | (flag3 ? 8 : 0);
        par6 = i | j1;

        if (l > 0)
        {
            int l1 = par2 + j * l;
            int k2 = par4 + k * l;
            int j3 = Direction.footInvisibleFaceRemap[i];
            par1World.setBlockMetadataWithNotify(l1, par3, k2, j3 | j1);
            func_56359_e(par1World, l1, par3, k2, j3);
            func_56358_a(par1World, l1, par3, k2, flag2, flag3, flag, flag1);
        }

        func_56358_a(par1World, par2, par3, par4, flag2, flag3, flag, flag1);

        if (par5 > 0)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, par6);

            if (par7)
            {
                func_56359_e(par1World, par2, par3, par4, i);
            }
        }

        if (flag != flag2)
        {
            for (int i2 = 1; i2 < l; i2++)
            {
                int l2 = par2 + j * i2;
                int k3 = par4 + k * i2;
                int j4 = ai[i2];

                if (j4 < 0)
                {
                    continue;
                }

                if (flag2)
                {
                    j4 |= 4;
                }
                else
                {
                    j4 &= -5;
                }

                par1World.setBlockMetadataWithNotify(l2, par3, k3, j4);
            }
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        func_56356_a(par1World, par2, par3, par4, blockID, par1World.getBlockMetadata(par2, par3, par4), true, -1, 0);
    }

    private void func_56358_a(World par1World, int par2, int par3, int par4, boolean par5, boolean par6, boolean par7, boolean par8)
    {
        if (par6 && !par8)
        {
            par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.10000000000000001D, (double)par4 + 0.5D, "random.click", 0.4F, 0.6F);
        }
        else if (!par6 && par8)
        {
            par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.10000000000000001D, (double)par4 + 0.5D, "random.click", 0.4F, 0.5F);
        }
        else if (par5 && !par7)
        {
            par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.10000000000000001D, (double)par4 + 0.5D, "random.click", 0.4F, 0.7F);
        }
        else if (!par5 && par7)
        {
            par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.10000000000000001D, (double)par4 + 0.5D, "random.bowhit", 0.4F, 1.2F / (par1World.rand.nextFloat() * 0.2F + 0.9F));
        }
    }

    private void func_56359_e(World par1World, int par2, int par3, int par4, int par5)
    {
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4, blockID);

        if (par5 == 3)
        {
            par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, blockID);
        }
        else if (par5 == 1)
        {
            par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, blockID);
        }
        else if (par5 == 0)
        {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, blockID);
        }
        else if (par5 == 2)
        {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, blockID);
        }
    }

    private boolean func_56357_g(World par1World, int par2, int par3, int par4)
    {
        if (!canPlaceBlockAt(par1World, par2, par3, par4))
        {
            dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockWithNotify(par2, par3, par4, 0);
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int i = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 3;
        float f = 0.1875F;

        if (i == 3)
        {
            setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
        }
        else if (i == 1)
        {
            setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
        }
        else if (i == 0)
        {
            setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
        }
        else if (i == 2)
        {
            setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
        }
    }

    public void func_56322_a(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        boolean flag = (par6 & 4) == 4;
        boolean flag1 = (par6 & 8) == 8;

        if (flag || flag1)
        {
            func_56356_a(par1World, par2, par3, par4, 0, par6, false, -1, 0);
        }

        if (flag1)
        {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4, blockID);
            int i = par6 & 3;

            if (i == 3)
            {
                par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, blockID);
            }
            else if (i == 1)
            {
                par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, blockID);
            }
            else if (i == 0)
            {
                par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, blockID);
            }
            else if (i == 2)
            {
                par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, blockID);
            }
        }

        super.func_56322_a(par1World, par2, par3, par4, par5, par6);
    }

    /**
     * Is this block powering the block on the specified side
     */
    public boolean isPoweringTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) == 8;
    }

    /**
     * Is this block indirectly powering the block on the specified side
     */
    public boolean isIndirectlyPoweringTo(World par1World, int par2, int par3, int par4, int par5)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);

        if ((i & 8) != 8)
        {
            return false;
        }

        int j = i & 3;

        if (j == 2 && par5 == 2)
        {
            return true;
        }

        if (j == 0 && par5 == 3)
        {
            return true;
        }

        if (j == 1 && par5 == 4)
        {
            return true;
        }

        return j == 3 && par5 == 5;
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean canProvidePower()
    {
        return true;
    }
}
