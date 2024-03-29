package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockPane extends Block
{
    /**
     * Holds the texture index of the side of the pane (the thin lateral side)
     */
    private int sideTextureIndex;

    /**
     * If this field is true, the pane block drops itself when destroyed (like the iron fences), otherwise, it's just
     * destroyed (like glass panes)
     */
    private final boolean canDropItself;

    protected BlockPane(int par1, int par2, int par3, Material par4Material, boolean par5)
    {
        super(par1, par2, par4Material);
        sideTextureIndex = par3;
        canDropItself = par5;
        func_56326_a(CreativeTabs.field_56388_c);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        if (!canDropItself)
        {
            return 0;
        }
        else
        {
            return super.idDropped(par1, par2Random, par3);
        }
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
        return 18;
    }

    public void func_56330_a(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        boolean flag = canThisPaneConnectToThisBlockID(par1World.getBlockId(par2, par3, par4 - 1));
        boolean flag1 = canThisPaneConnectToThisBlockID(par1World.getBlockId(par2, par3, par4 + 1));
        boolean flag2 = canThisPaneConnectToThisBlockID(par1World.getBlockId(par2 - 1, par3, par4));
        boolean flag3 = canThisPaneConnectToThisBlockID(par1World.getBlockId(par2 + 1, par3, par4));

        if (flag2 && flag3 || !flag2 && !flag3 && !flag && !flag1)
        {
            setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
            super.func_56330_a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
        else if (flag2 && !flag3)
        {
            setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
            super.func_56330_a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
        else if (!flag2 && flag3)
        {
            setBlockBounds(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
            super.func_56330_a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }

        if (flag && flag1 || !flag2 && !flag3 && !flag && !flag1)
        {
            setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
            super.func_56330_a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
        else if (flag && !flag1)
        {
            setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
            super.func_56330_a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
        else if (!flag && flag1)
        {
            setBlockBounds(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
            super.func_56330_a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        float f = 0.4375F;
        float f1 = 0.5625F;
        float f2 = 0.4375F;
        float f3 = 0.5625F;
        boolean flag = canThisPaneConnectToThisBlockID(par1IBlockAccess.getBlockId(par2, par3, par4 - 1));
        boolean flag1 = canThisPaneConnectToThisBlockID(par1IBlockAccess.getBlockId(par2, par3, par4 + 1));
        boolean flag2 = canThisPaneConnectToThisBlockID(par1IBlockAccess.getBlockId(par2 - 1, par3, par4));
        boolean flag3 = canThisPaneConnectToThisBlockID(par1IBlockAccess.getBlockId(par2 + 1, par3, par4));

        if (flag2 && flag3 || !flag2 && !flag3 && !flag && !flag1)
        {
            f = 0.0F;
            f1 = 1.0F;
        }
        else if (flag2 && !flag3)
        {
            f = 0.0F;
        }
        else if (!flag2 && flag3)
        {
            f1 = 1.0F;
        }

        if (flag && flag1 || !flag2 && !flag3 && !flag && !flag1)
        {
            f2 = 0.0F;
            f3 = 1.0F;
        }
        else if (flag && !flag1)
        {
            f2 = 0.0F;
        }
        else if (!flag && flag1)
        {
            f3 = 1.0F;
        }

        setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
    }

    /**
     * Gets passed in the blockID of the block adjacent and supposed to return true if its allowed to connect to the
     * type of blockID passed in. Args: blockID
     */
    public final boolean canThisPaneConnectToThisBlockID(int par1)
    {
        return Block.opaqueCubeLookup[par1] || par1 == blockID || par1 == Block.glass.blockID;
    }

    /**
     * Return true if a player with SlikTouch can harvest this block directly, and not it's normal drops.
     */
    protected boolean canSilkHarvest()
    {
        return true;
    }

    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
    protected ItemStack createStackedBlock(int par1)
    {
        return new ItemStack(blockID, 1, par1);
    }
}
