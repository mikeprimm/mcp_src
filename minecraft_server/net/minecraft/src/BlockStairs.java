package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockStairs extends Block
{
    private static final int field_56346_a[][] =
    {
        {
            2, 6
        }, {
            3, 7
        }, {
            2, 3
        }, {
            6, 7
        }, {
            0, 4
        }, {
            1, 5
        }, {
            0, 1
        }, {
            4, 5
        }
    };

    /** The block that is used as model for the stair. */
    private final Block modelBlock;
    private final int field_58037_c;
    private boolean field_56345_c;
    private int field_56344_co;

    protected BlockStairs(int par1, Block par2Block, int par3)
    {
        super(par1, par2Block.blockIndexInTexture, par2Block.blockMaterial);
        field_56345_c = false;
        field_56344_co = 0;
        modelBlock = par2Block;
        field_58037_c = par3;
        setHardness(par2Block.blockHardness);
        setResistance(par2Block.blockResistance / 3F);
        setStepSound(par2Block.stepSound);
        setLightOpacity(255);
        func_56326_a(CreativeTabs.field_56387_b);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        if (field_56345_c)
        {
            setBlockBounds(0.5F * (float)(field_56344_co % 2), 0.5F * (float)((field_56344_co / 2) % 2), 0.5F * (float)((field_56344_co / 4) % 2), 0.5F + 0.5F * (float)(field_56344_co % 2), 0.5F + 0.5F * (float)((field_56344_co / 2) % 2), 0.5F + 0.5F * (float)((field_56344_co / 4) % 2));
        }
        else
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
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
        return 10;
    }

    public void func_56330_a(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);
        int j = i & 3;
        float f = 0.0F;
        float f1 = 0.5F;
        float f2 = 0.5F;
        float f3 = 1.0F;

        if ((i & 4) != 0)
        {
            f = 0.5F;
            f1 = 1.0F;
            f2 = 0.0F;
            f3 = 0.5F;
        }

        setBlockBounds(0.0F, f, 0.0F, 1.0F, f1, 1.0F);
        super.func_56330_a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);

        if (j == 0)
        {
            setBlockBounds(0.5F, f2, 0.0F, 1.0F, f3, 1.0F);
            super.func_56330_a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
        else if (j == 1)
        {
            setBlockBounds(0.0F, f2, 0.0F, 0.5F, f3, 1.0F);
            super.func_56330_a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
        else if (j == 2)
        {
            setBlockBounds(0.0F, f2, 0.5F, 1.0F, f3, 1.0F);
            super.func_56330_a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
        else if (j == 3)
        {
            setBlockBounds(0.0F, f2, 0.0F, 1.0F, f3, 0.5F);
            super.func_56330_a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }

        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        modelBlock.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
    }

    /**
     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
     */
    public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5)
    {
        modelBlock.onBlockDestroyedByPlayer(par1World, par2, par3, par4, par5);
    }

    /**
     * Returns how much this block can resist explosions from the passed in entity.
     */
    public float getExplosionResistance(Entity par1Entity)
    {
        return modelBlock.getExplosionResistance(par1Entity);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return modelBlock.getBlockTextureFromSideAndMetadata(par1, field_58037_c);
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int par1)
    {
        return modelBlock.getBlockTextureFromSideAndMetadata(par1, field_58037_c);
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return modelBlock.tickRate();
    }

    /**
     * Can add to the passed in vector for a movement vector to be applied to the entity. Args: x, y, z, entity, vec3d
     */
    public void velocityToAddToEntity(World par1World, int par2, int par3, int par4, Entity par5Entity, Vec3 par6Vec3)
    {
        modelBlock.velocityToAddToEntity(par1World, par2, par3, par4, par5Entity, par6Vec3);
    }

    /**
     * Returns if this block is collidable (only used by Fire). Args: x, y, z
     */
    public boolean isCollidable()
    {
        return modelBlock.isCollidable();
    }

    /**
     * Returns whether this block is collideable based on the arguments passed in Args: blockMetaData, unknownFlag
     */
    public boolean canCollideCheck(int par1, boolean par2)
    {
        return modelBlock.canCollideCheck(par1, par2);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return modelBlock.canPlaceBlockAt(par1World, par2, par3, par4);
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        onNeighborBlockChange(par1World, par2, par3, par4, 0);
        modelBlock.onBlockAdded(par1World, par2, par3, par4);
    }

    public void func_56322_a(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        modelBlock.func_56322_a(par1World, par2, par3, par4, par5, par6);
    }

    /**
     * Called whenever an entity is walking on top of this block. Args: world, x, y, z, entity
     */
    public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        modelBlock.onEntityWalking(par1World, par2, par3, par4, par5Entity);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        modelBlock.updateTick(par1World, par2, par3, par4, par5Random);
    }

    public boolean func_56323_a(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        return modelBlock.func_56323_a(par1World, par2, par3, par4, par5EntityPlayer, 0, 0.0F, 0.0F, 0.0F);
    }

    /**
     * Called upon the block being destroyed by an explosion
     */
    public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4)
    {
        modelBlock.onBlockDestroyedByExplosion(par1World, par2, par3, par4);
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int i = MathHelper.floor_double((double)((par5EntityLiving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
        int j = par1World.getBlockMetadata(par2, par3, par4) & 4;

        if (i == 0)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2 | j);
        }

        if (i == 1)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 1 | j);
        }

        if (i == 2)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3 | j);
        }

        if (i == 3)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 0 | j);
        }
    }

    public void func_56327_a(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8)
    {
        if (par5 == 0)
        {
            int i = par1World.getBlockMetadata(par2, par3, par4);
            par1World.setBlockMetadataWithNotify(par2, par3, par4, i | 4);
        }
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3)
    {
        MovingObjectPosition amovingobjectposition[] = new MovingObjectPosition[8];
        int i = par1World.getBlockMetadata(par2, par3, par4);
        int j = i & 3;
        boolean flag = (i & 4) == 4;
        int ai[] = field_56346_a[j + (flag ? 4 : 0)];
        field_56345_c = true;

        for (int k = 0; k < 8; k++)
        {
            field_56344_co = k;
            int ai2[] = ai;
            int l = ai2.length;

            for (int j1 = 0; j1 < l; j1++)
            {
                int l1 = ai2[j1];

                if (l1 != k);
            }

            amovingobjectposition[k] = super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
        }

        int ai1[] = ai;
        double d = ai1.length;

        for (int i1 = 0; i1 < d; i1++)
        {
            int k1 = ai1[i1];
            amovingobjectposition[k1] = null;
        }

        MovingObjectPosition movingobjectposition = null;
        d = 0.0D;
        MovingObjectPosition amovingobjectposition1[] = amovingobjectposition;
        int i2 = amovingobjectposition1.length;

        for (int j2 = 0; j2 < i2; j2++)
        {
            MovingObjectPosition movingobjectposition1 = amovingobjectposition1[j2];

            if (movingobjectposition1 == null)
            {
                continue;
            }

            double d1 = movingobjectposition1.hitVec.squareDistanceTo(par6Vec3);

            if (d1 > d)
            {
                movingobjectposition = movingobjectposition1;
                d = d1;
            }
        }

        return movingobjectposition;
    }
}
