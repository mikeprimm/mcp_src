package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class Block
{
    private CreativeTabs field_56332_a;
    public static final StepSound soundPowderFootstep;
    public static final StepSound soundWoodFootstep;
    public static final StepSound soundGravelFootstep;
    public static final StepSound soundGrassFootstep;
    public static final StepSound soundStoneFootstep;
    public static final StepSound soundMetalFootstep;
    public static final StepSound soundGlassFootstep;
    public static final StepSound soundClothFootstep;
    public static final StepSound soundSandFootstep;
    public static final Block blocksList[];
    public static final boolean opaqueCubeLookup[];
    public static final int lightOpacity[];
    public static final boolean canBlockGrass[];
    public static final int lightValue[];
    public static final boolean requiresSelfNotify[];
    public static boolean useNeighborBrightness[];
    public static final Block stone;
    public static final BlockGrass grass;
    public static final Block dirt;
    public static final Block cobblestone;
    public static final Block planks;
    public static final Block sapling;
    public static final Block bedrock;
    public static final Block waterMoving;
    public static final Block waterStill;
    public static final Block lavaMoving;

    /** Stationary lava source block */
    public static final Block lavaStill;
    public static final Block sand;
    public static final Block gravel;
    public static final Block oreGold;
    public static final Block oreIron;
    public static final Block oreCoal;
    public static final Block wood;
    public static final BlockLeaves leaves;
    public static final Block sponge;
    public static final Block glass;
    public static final Block oreLapis;
    public static final Block blockLapis;
    public static final Block dispenser;
    public static final Block sandStone;
    public static final Block music;
    public static final Block bed;
    public static final Block railPowered;
    public static final Block railDetector;
    public static final Block pistonStickyBase;
    public static final Block web;
    public static final BlockTallGrass tallGrass;
    public static final BlockDeadBush deadBush;
    public static final Block pistonBase;
    public static final BlockPistonExtension pistonExtension;
    public static final Block cloth;
    public static final BlockPistonMoving pistonMoving;
    public static final BlockFlower plantYellow;
    public static final BlockFlower plantRed;
    public static final BlockFlower mushroomBrown;
    public static final BlockFlower mushroomRed;
    public static final Block blockGold;
    public static final Block blockSteel;
    public static final BlockHalfSlab field_55134_aj;
    public static final BlockHalfSlab field_55133_ak;
    public static final Block brick;
    public static final Block tnt;
    public static final Block bookShelf;
    public static final Block cobblestoneMossy;
    public static final Block obsidian;
    public static final Block torchWood;
    public static final BlockFire fire;
    public static final Block mobSpawner;
    public static final Block stairCompactPlanks;
    public static final Block chest;
    public static final Block redstoneWire;
    public static final Block field_56337_aw;
    public static final Block field_56336_ax;
    public static final Block workbench;
    public static final Block crops;
    public static final Block tilledField;
    public static final Block stoneOvenIdle;
    public static final Block stoneOvenActive;
    public static final Block signPost;
    public static final Block doorWood;
    public static final Block ladder;
    public static final Block rail;
    public static final Block stairCompactCobblestone;
    public static final Block signWall;
    public static final Block lever;
    public static final Block pressurePlateStone;
    public static final Block doorSteel;
    public static final Block pressurePlatePlanks;
    public static final Block oreRedstone;
    public static final Block oreRedstoneGlowing;
    public static final Block torchRedstoneIdle;
    public static final Block torchRedstoneActive;
    public static final Block button;
    public static final Block snow;
    public static final Block ice;
    public static final Block blockSnow;
    public static final Block cactus;
    public static final Block blockClay;
    public static final Block reed;
    public static final Block jukebox;
    public static final Block fence;
    public static final Block pumpkin;
    public static final Block netherrack;
    public static final Block slowSand;
    public static final Block glowStone;

    /** The purple teleport blocks inside the obsidian circle */
    public static final BlockPortal portal;
    public static final Block pumpkinLantern;
    public static final Block cake;
    public static final Block redstoneRepeaterIdle;
    public static final Block redstoneRepeaterActive;

    /**
     * April fools secret locked chest, only spawns on new chunks on 1st April.
     */
    public static final Block lockedChest;
    public static final Block trapdoor;
    public static final Block silverfish;
    public static final Block stoneBrick;
    public static final Block mushroomCapBrown;
    public static final Block mushroomCapRed;
    public static final Block fenceIron;
    public static final Block thinGlass;
    public static final Block melon;
    public static final Block pumpkinStem;
    public static final Block melonStem;
    public static final Block vine;
    public static final Block fenceGate;
    public static final Block stairsBrick;
    public static final Block stairsStoneBrickSmooth;
    public static final BlockMycelium mycelium;
    public static final Block waterlily;
    public static final Block netherBrick;
    public static final Block netherFence;
    public static final Block stairsNetherBrick;
    public static final Block netherStalk;
    public static final Block enchantmentTable;
    public static final Block brewingStand;
    public static final Block cauldron;
    public static final Block endPortal;
    public static final Block endPortalFrame;
    public static final Block whiteStone;
    public static final Block dragonEgg;
    public static final Block redstoneLampIdle;
    public static final Block redstoneLampActive;
    public static final BlockHalfSlab field_55135_bN;
    public static final BlockHalfSlab field_55136_bO;
    public static final Block field_56338_bP;
    public static final Block field_56339_bQ;
    public static final Block oreDiamond;
    public static final Block field_56335_bS;
    public static final BlockTripWireSource field_56334_bT;
    public static final Block field_56333_bU;
    public static final Block blockDiamond;
    public static final Block field_58036_bW;
    public static final Block field_58035_bX;
    public static final Block field_58034_bY;

    /**
     * The index of the texture to be displayed for this block. May vary based on graphics settings. Mostly seems to
     * come from terrain.png, and the index is 0-based (grass is 0).
     */
    public int blockIndexInTexture;

    /** ID of the block. */
    public final int blockID;

    /** Indicates how many hits it takes to break a block. */
    protected float blockHardness;

    /** Indicates the blocks resistance to explosions. */
    protected float blockResistance;

    /**
     * set to true when Block's constructor is called through the chain of super()'s. Note: Never used
     */
    protected boolean blockConstructorCalled;

    /**
     * If this field is true, the block is counted for statistics (mined or placed)
     */
    protected boolean enableStats;

    /**
     * Flags whether or not this block is of a type that needs random ticking. Ref-counted by ExtendedBlockStorage in
     * order to broadly cull a chunk from the random chunk update list for efficiency's sake.
     */
    protected boolean needsRandomTick;

    /** true if the Block contains a Tile Entity */
    protected boolean isBlockContainer;

    /** minimum X for the block bounds (local coordinates) */
    public double minX;

    /** minimum Y for the block bounds (local coordinates) */
    public double minY;

    /** minimum Z for the block bounds (local coordinates) */
    public double minZ;

    /** maximum X for the block bounds (local coordinates) */
    public double maxX;

    /** maximum Y for the block bounds (local coordinates) */
    public double maxY;

    /** maximum Z for the block bounds (local coordinates) */
    public double maxZ;

    /** Sound of stepping on the block */
    public StepSound stepSound;
    public float blockParticleGravity;

    /** Block material definition. */
    public final Material blockMaterial;

    /**
     * Determines how much velocity is maintained while moving on top of this block
     */
    public float slipperiness;
    private String blockName;

    protected Block(int par1, Material par2Material)
    {
        blockConstructorCalled = true;
        enableStats = true;
        stepSound = soundPowderFootstep;
        blockParticleGravity = 1.0F;
        slipperiness = 0.6F;

        if (blocksList[par1] != null)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Slot ").append(par1).append(" is already occupied by ").append(blocksList[par1]).append(" when adding ").append(this).toString());
        }
        else
        {
            blockMaterial = par2Material;
            blocksList[par1] = this;
            blockID = par1;
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            opaqueCubeLookup[par1] = isOpaqueCube();
            lightOpacity[par1] = isOpaqueCube() ? 255 : 0;
            canBlockGrass[par1] = !par2Material.getCanBlockGrass();
            return;
        }
    }

    /**
     * Blocks with this attribute will not notify all near blocks when it's metadata change. The default behavior is
     * always notify every neightbor block when anything changes.
     */
    protected Block setRequiresSelfNotify()
    {
        requiresSelfNotify[blockID] = true;
        return this;
    }

    /**
     * This method is called on a block after all other blocks gets already created. You can use it to reference and
     * configure something on the block that needs the others ones.
     */
    protected void initializeBlock()
    {
    }

    protected Block(int par1, int par2, Material par3Material)
    {
        this(par1, par3Material);
        blockIndexInTexture = par2;
    }

    /**
     * Sets the footstep sound for the block. Returns the object for convenience in constructing.
     */
    protected Block setStepSound(StepSound par1StepSound)
    {
        stepSound = par1StepSound;
        return this;
    }

    /**
     * Sets how much light is blocked going through this block. Returns the object for convenience in constructing.
     */
    protected Block setLightOpacity(int par1)
    {
        lightOpacity[blockID] = par1;
        return this;
    }

    /**
     * Sets the amount of light emitted by a block from 0.0f to 1.0f (converts internally to 0-15). Returns the object
     * for convenience in constructing.
     */
    protected Block setLightValue(float par1)
    {
        lightValue[blockID] = (int)(15F * par1);
        return this;
    }

    /**
     * Sets the the blocks resistance to explosions. Returns the object for convenience in constructing.
     */
    protected Block setResistance(float par1)
    {
        blockResistance = par1 * 3F;
        return this;
    }

    public static boolean isNormalCube(int par0)
    {
        Block block = blocksList[par0];

        if (block == null)
        {
            return false;
        }
        else
        {
            return block.blockMaterial.isOpaque() && block.renderAsNormalBlock();
        }
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return true;
    }

    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return !blockMaterial.blocksMovement();
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 0;
    }

    /**
     * Sets how many hits it takes to break a block.
     */
    protected Block setHardness(float par1)
    {
        blockHardness = par1;

        if (blockResistance < par1 * 5F)
        {
            blockResistance = par1 * 5F;
        }

        return this;
    }

    /**
     * This method will make the hardness of the block equals to -1, and the block is indestructible.
     */
    protected Block setBlockUnbreakable()
    {
        setHardness(-1F);
        return this;
    }

    public float func_58033_f(World par1World, int par2, int par3, int par4)
    {
        return blockHardness;
    }

    /**
     * Sets whether this block type will receive random update ticks
     */
    protected Block setTickRandomly(boolean par1)
    {
        needsRandomTick = par1;
        return this;
    }

    /**
     * Returns whether or not this block is of a type that needs random ticking. Called for ref-counting purposes by
     * ExtendedBlockStorage in order to broadly cull a chunk from the random chunk update list for efficiency's sake.
     */
    public boolean getTickRandomly()
    {
        return needsRandomTick;
    }

    public boolean hasTileEntity()
    {
        return isBlockContainer;
    }

    /**
     * Sets the bounds of the block.  minX, minY, minZ, maxX, maxY, maxZ
     */
    public void setBlockBounds(float par1, float par2, float par3, float par4, float par5, float par6)
    {
        minX = par1;
        minY = par2;
        minZ = par3;
        maxX = par4;
        maxY = par5;
        maxZ = par6;
    }

    /**
     * Returns Returns true if the given side of this block type should be rendered (if it's solid or not), if the
     * adjacent block is at the given coordinates. Args: blockAccess, x, y, z, side
     */
    public boolean isBlockSolid(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return par1IBlockAccess.getBlockMaterial(par2, par3, par4).isSolid();
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return getBlockTextureFromSide(par1);
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int par1)
    {
        return blockIndexInTexture;
    }

    public void func_56330_a(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        AxisAlignedBB axisalignedbb = getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);

        if (axisalignedbb != null && par5AxisAlignedBB.intersectsWith(axisalignedbb))
        {
            par6List.add(axisalignedbb);
        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return AxisAlignedBB.func_58089_a().func_58067_a((double)par2 + minX, (double)par3 + minY, (double)par4 + minZ, (double)par2 + maxX, (double)par3 + maxY, (double)par4 + maxZ);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return true;
    }

    /**
     * Returns whether this block is collideable based on the arguments passed in Args: blockMetaData, unknownFlag
     */
    public boolean canCollideCheck(int par1, boolean par2)
    {
        return isCollidable();
    }

    /**
     * Returns if this block is collidable (only used by Fire). Args: x, y, z
     */
    public boolean isCollidable()
    {
        return true;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World world, int i, int j, int k, Random random)
    {
    }

    /**
     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
     */
    public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l)
    {
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 10;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, int i, int j, int k)
    {
    }

    public void func_56322_a(World world, int i, int j, int k, int l, int i1)
    {
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return blockID;
    }

    public float func_58032_a(EntityPlayer par1EntityPlayer, World par2World, int par3, int par4, int par5)
    {
        float f = func_58033_f(par2World, par3, par4, par5);

        if (f < 0.0F)
        {
            return 0.0F;
        }

        if (!par1EntityPlayer.canHarvestBlock(this))
        {
            return 1.0F / f / 100F;
        }
        else
        {
            return par1EntityPlayer.getCurrentPlayerStrVsBlock(this) / f / 30F;
        }
    }

    /**
     * Drops the specified block items
     */
    public final void dropBlockAsItem(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, 1.0F, par6);
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

        int i = quantityDroppedWithBonus(par7, par1World.rand);

        for (int j = 0; j < i; j++)
        {
            if (par1World.rand.nextFloat() > par6)
            {
                continue;
            }

            int k = idDropped(par5, par1World.rand, par7);

            if (k > 0)
            {
                dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(k, 1, damageDropped(par5)));
            }
        }
    }

    /**
     * Spawns EntityItem in the world for the given ItemStack if the world is not remote.
     */
    protected void dropBlockAsItem_do(World par1World, int par2, int par3, int par4, ItemStack par5ItemStack)
    {
        if (par1World.isRemote)
        {
            return;
        }
        else
        {
            float f = 0.7F;
            double d = (double)(par1World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d1 = (double)(par1World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d2 = (double)(par1World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(par1World, (double)par2 + d, (double)par3 + d1, (double)par4 + d2, par5ItemStack);
            entityitem.delayBeforeCanPickup = 10;
            par1World.spawnEntityInWorld(entityitem);
            return;
        }
    }

    protected void func_56328_f(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.isRemote)
        {
            while (par5 > 0)
            {
                int i = EntityXPOrb.getXPSplit(par5);
                par5 -= i;
                par1World.spawnEntityInWorld(new EntityXPOrb(par1World, (double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, i));
            }
        }
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    protected int damageDropped(int par1)
    {
        return 0;
    }

    /**
     * Returns how much this block can resist explosions from the passed in entity.
     */
    public float getExplosionResistance(Entity par1Entity)
    {
        return blockResistance / 5F;
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3)
    {
        setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        par5Vec3 = par5Vec3.addVector(-par2, -par3, -par4);
        par6Vec3 = par6Vec3.addVector(-par2, -par3, -par4);
        Vec3 vec3 = par5Vec3.getIntermediateWithXValue(par6Vec3, minX);
        Vec3 vec3_1 = par5Vec3.getIntermediateWithXValue(par6Vec3, maxX);
        Vec3 vec3_2 = par5Vec3.getIntermediateWithYValue(par6Vec3, minY);
        Vec3 vec3_3 = par5Vec3.getIntermediateWithYValue(par6Vec3, maxY);
        Vec3 vec3_4 = par5Vec3.getIntermediateWithZValue(par6Vec3, minZ);
        Vec3 vec3_5 = par5Vec3.getIntermediateWithZValue(par6Vec3, maxZ);

        if (!isVecInsideYZBounds(vec3))
        {
            vec3 = null;
        }

        if (!isVecInsideYZBounds(vec3_1))
        {
            vec3_1 = null;
        }

        if (!isVecInsideXZBounds(vec3_2))
        {
            vec3_2 = null;
        }

        if (!isVecInsideXZBounds(vec3_3))
        {
            vec3_3 = null;
        }

        if (!isVecInsideXYBounds(vec3_4))
        {
            vec3_4 = null;
        }

        if (!isVecInsideXYBounds(vec3_5))
        {
            vec3_5 = null;
        }

        Vec3 vec3_6 = null;

        if (vec3 != null && (vec3_6 == null || par5Vec3.distanceTo(vec3) < par5Vec3.distanceTo(vec3_6)))
        {
            vec3_6 = vec3;
        }

        if (vec3_1 != null && (vec3_6 == null || par5Vec3.distanceTo(vec3_1) < par5Vec3.distanceTo(vec3_6)))
        {
            vec3_6 = vec3_1;
        }

        if (vec3_2 != null && (vec3_6 == null || par5Vec3.distanceTo(vec3_2) < par5Vec3.distanceTo(vec3_6)))
        {
            vec3_6 = vec3_2;
        }

        if (vec3_3 != null && (vec3_6 == null || par5Vec3.distanceTo(vec3_3) < par5Vec3.distanceTo(vec3_6)))
        {
            vec3_6 = vec3_3;
        }

        if (vec3_4 != null && (vec3_6 == null || par5Vec3.distanceTo(vec3_4) < par5Vec3.distanceTo(vec3_6)))
        {
            vec3_6 = vec3_4;
        }

        if (vec3_5 != null && (vec3_6 == null || par5Vec3.distanceTo(vec3_5) < par5Vec3.distanceTo(vec3_6)))
        {
            vec3_6 = vec3_5;
        }

        if (vec3_6 == null)
        {
            return null;
        }

        byte byte0 = -1;

        if (vec3_6 == vec3)
        {
            byte0 = 4;
        }

        if (vec3_6 == vec3_1)
        {
            byte0 = 5;
        }

        if (vec3_6 == vec3_2)
        {
            byte0 = 0;
        }

        if (vec3_6 == vec3_3)
        {
            byte0 = 1;
        }

        if (vec3_6 == vec3_4)
        {
            byte0 = 2;
        }

        if (vec3_6 == vec3_5)
        {
            byte0 = 3;
        }

        return new MovingObjectPosition(par2, par3, par4, byte0, vec3_6.addVector(par2, par3, par4));
    }

    /**
     * Checks if a vector is within the Y and Z bounds of the block.
     */
    private boolean isVecInsideYZBounds(Vec3 par1Vec3)
    {
        if (par1Vec3 == null)
        {
            return false;
        }
        else
        {
            return par1Vec3.yCoord >= minY && par1Vec3.yCoord <= maxY && par1Vec3.zCoord >= minZ && par1Vec3.zCoord <= maxZ;
        }
    }

    /**
     * Checks if a vector is within the X and Z bounds of the block.
     */
    private boolean isVecInsideXZBounds(Vec3 par1Vec3)
    {
        if (par1Vec3 == null)
        {
            return false;
        }
        else
        {
            return par1Vec3.xCoord >= minX && par1Vec3.xCoord <= maxX && par1Vec3.zCoord >= minZ && par1Vec3.zCoord <= maxZ;
        }
    }

    /**
     * Checks if a vector is within the X and Y bounds of the block.
     */
    private boolean isVecInsideXYBounds(Vec3 par1Vec3)
    {
        if (par1Vec3 == null)
        {
            return false;
        }
        else
        {
            return par1Vec3.xCoord >= minX && par1Vec3.xCoord <= maxX && par1Vec3.yCoord >= minY && par1Vec3.yCoord <= maxY;
        }
    }

    /**
     * Called upon the block being destroyed by an explosion
     */
    public void onBlockDestroyedByExplosion(World world, int i, int j, int k)
    {
    }

    /**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5)
    {
        return canPlaceBlockAt(par1World, par2, par3, par4);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        int i = par1World.getBlockId(par2, par3, par4);
        return i == 0 || blocksList[i].blockMaterial.isGroundCover();
    }

    public boolean func_56323_a(World par1World, int par2, int par3, int i, EntityPlayer entityplayer, int j, float f, float f1, float f2)
    {
        return false;
    }

    /**
     * Called whenever an entity is walking on top of this block. Args: world, x, y, z, entity
     */
    public void onEntityWalking(World world, int i, int j, int k, Entity entity)
    {
    }

    public void func_56327_a(World world, int i, int j, int k, int l, float f, float f1, float f2)
    {
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
    }

    /**
     * Can add to the passed in vector for a movement vector to be applied to the entity. Args: x, y, z, entity, vec3d
     */
    public void velocityToAddToEntity(World world, int i, int j, int k, Entity entity, Vec3 vec3)
    {
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k)
    {
    }

    /**
     * Is this block powering the block on the specified side
     */
    public boolean isPoweringTo(IBlockAccess par1IBlockAccess, int par2, int par3, int i, int j)
    {
        return false;
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean canProvidePower()
    {
        return false;
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
    {
    }

    /**
     * Is this block indirectly powering the block on the specified side
     */
    public boolean isIndirectlyPoweringTo(World par1World, int par2, int par3, int i, int j)
    {
        return false;
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
    }

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        par2EntityPlayer.addStat(StatList.mineBlockStatArray[blockID], 1);
        par2EntityPlayer.addExhaustion(0.025F);

        if (canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier(par2EntityPlayer.inventory))
        {
            ItemStack itemstack = createStackedBlock(par6);

            if (itemstack != null)
            {
                dropBlockAsItem_do(par1World, par3, par4, par5, itemstack);
            }
        }
        else
        {
            int i = EnchantmentHelper.getFortuneModifier(par2EntityPlayer.inventory);
            dropBlockAsItem(par1World, par3, par4, par5, par6, i);
        }
    }

    /**
     * Return true if a player with SlikTouch can harvest this block directly, and not it's normal drops.
     */
    protected boolean canSilkHarvest()
    {
        return renderAsNormalBlock() && !isBlockContainer;
    }

    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
    protected ItemStack createStackedBlock(int par1)
    {
        int i = 0;

        if (blockID >= 0 && blockID < Item.itemsList.length && Item.itemsList[blockID].getHasSubtypes())
        {
            i = par1;
        }

        return new ItemStack(blockID, 1, i);
    }

    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    public int quantityDroppedWithBonus(int par1, Random par2Random)
    {
        return quantityDropped(par2Random);
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean canBlockStay(World par1World, int par2, int par3, int i)
    {
        return true;
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving)
    {
    }

    /**
     * set name of block from language file
     */
    public Block setBlockName(String par1Str)
    {
        blockName = (new StringBuilder()).append("tile.").append(par1Str).toString();
        return this;
    }

    /**
     * gets the localized version of the name of this block using StatCollector.translateToLocal. Used for the statistic
     * page.
     */
    public String translateBlockName()
    {
        return StatCollector.translateToLocal((new StringBuilder()).append(getBlockName()).append(".name").toString());
    }

    public String getBlockName()
    {
        return blockName;
    }

    public void powerBlock(World world, int i, int j, int k, int l, int i1)
    {
    }

    /**
     * Return the state of blocks statistics flags - if the block is counted for mined and placed.
     */
    public boolean getEnableStats()
    {
        return enableStats;
    }

    /**
     * Disable statistics for the block, the block will no count for mined or placed.
     */
    protected Block disableStats()
    {
        enableStats = false;
        return this;
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int getMobilityFlag()
    {
        return blockMaterial.getMaterialMobility();
    }

    /**
     * Block's chance to react to an entity falling on it.
     */
    public void onFallenUpon(World world, int i, int j, int k, Entity entity, float f)
    {
    }

    public Block func_56326_a(CreativeTabs par1CreativeTabs)
    {
        field_56332_a = par1CreativeTabs;
        return this;
    }

    public void func_56324_a(World world, int i, int j, int k, int l, EntityPlayer entityplayer)
    {
    }

    public void func_56329_g(World world, int i, int j, int k, int l)
    {
    }

    public void func_56325_f(World world, int i, int j, int k)
    {
    }

    public void func_56331_a(World world, long l, long l1)
    {
    }

    static
    {
        soundPowderFootstep = new StepSound("stone", 1.0F, 1.0F);
        soundWoodFootstep = new StepSound("wood", 1.0F, 1.0F);
        soundGravelFootstep = new StepSound("gravel", 1.0F, 1.0F);
        soundGrassFootstep = new StepSound("grass", 1.0F, 1.0F);
        soundStoneFootstep = new StepSound("stone", 1.0F, 1.0F);
        soundMetalFootstep = new StepSound("stone", 1.0F, 1.5F);
        soundGlassFootstep = new StepSoundStone("stone", 1.0F, 1.0F);
        soundClothFootstep = new StepSound("cloth", 1.0F, 1.0F);
        soundSandFootstep = new StepSoundSand("sand", 1.0F, 1.0F);
        blocksList = new Block[4096];
        opaqueCubeLookup = new boolean[4096];
        lightOpacity = new int[4096];
        canBlockGrass = new boolean[4096];
        lightValue = new int[4096];
        requiresSelfNotify = new boolean[4096];
        useNeighborBrightness = new boolean[4096];
        stone = (new BlockStone(1, 1)).setHardness(1.5F).setResistance(10F).setStepSound(soundStoneFootstep).setBlockName("stone");
        grass = (BlockGrass)(new BlockGrass(2)).setHardness(0.6F).setStepSound(soundGrassFootstep).setBlockName("grass");
        dirt = (new BlockDirt(3, 2)).setHardness(0.5F).setStepSound(soundGravelFootstep).setBlockName("dirt");
        cobblestone = (new Block(4, 16, Material.rock)).setHardness(2.0F).setResistance(10F).setStepSound(soundStoneFootstep).setBlockName("stonebrick").func_56326_a(CreativeTabs.field_56387_b);
        planks = (new BlockWood(5)).setHardness(2.0F).setResistance(5F).setStepSound(soundWoodFootstep).setBlockName("wood").setRequiresSelfNotify();
        sapling = (new BlockSapling(6, 15)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("sapling").setRequiresSelfNotify();
        bedrock = (new Block(7, 17, Material.rock)).setBlockUnbreakable().setResistance(6000000F).setStepSound(soundStoneFootstep).setBlockName("bedrock").disableStats().func_56326_a(CreativeTabs.field_56387_b);
        waterMoving = (new BlockFlowing(8, Material.water)).setHardness(100F).setLightOpacity(3).setBlockName("water").disableStats().setRequiresSelfNotify();
        waterStill = (new BlockStationary(9, Material.water)).setHardness(100F).setLightOpacity(3).setBlockName("water").disableStats().setRequiresSelfNotify();
        lavaMoving = (new BlockFlowing(10, Material.lava)).setHardness(0.0F).setLightValue(1.0F).setLightOpacity(255).setBlockName("lava").disableStats().setRequiresSelfNotify();
        lavaStill = (new BlockStationary(11, Material.lava)).setHardness(100F).setLightValue(1.0F).setLightOpacity(255).setBlockName("lava").disableStats().setRequiresSelfNotify();
        sand = (new BlockSand(12, 18)).setHardness(0.5F).setStepSound(soundSandFootstep).setBlockName("sand");
        gravel = (new BlockGravel(13, 19)).setHardness(0.6F).setStepSound(soundGravelFootstep).setBlockName("gravel");
        oreGold = (new BlockOre(14, 32)).setHardness(3F).setResistance(5F).setStepSound(soundStoneFootstep).setBlockName("oreGold");
        oreIron = (new BlockOre(15, 33)).setHardness(3F).setResistance(5F).setStepSound(soundStoneFootstep).setBlockName("oreIron");
        oreCoal = (new BlockOre(16, 34)).setHardness(3F).setResistance(5F).setStepSound(soundStoneFootstep).setBlockName("oreCoal");
        wood = (new BlockLog(17)).setHardness(2.0F).setStepSound(soundWoodFootstep).setBlockName("log").setRequiresSelfNotify();
        leaves = (BlockLeaves)(new BlockLeaves(18, 52)).setHardness(0.2F).setLightOpacity(1).setStepSound(soundGrassFootstep).setBlockName("leaves").setRequiresSelfNotify();
        sponge = (new BlockSponge(19)).setHardness(0.6F).setStepSound(soundGrassFootstep).setBlockName("sponge");
        glass = (new BlockGlass(20, 49, Material.glass, false)).setHardness(0.3F).setStepSound(soundGlassFootstep).setBlockName("glass");
        oreLapis = (new BlockOre(21, 160)).setHardness(3F).setResistance(5F).setStepSound(soundStoneFootstep).setBlockName("oreLapis");
        blockLapis = (new Block(22, 144, Material.rock)).setHardness(3F).setResistance(5F).setStepSound(soundStoneFootstep).setBlockName("blockLapis").func_56326_a(CreativeTabs.field_56387_b);
        dispenser = (new BlockDispenser(23)).setHardness(3.5F).setStepSound(soundStoneFootstep).setBlockName("dispenser").setRequiresSelfNotify();
        sandStone = (new BlockSandStone(24)).setStepSound(soundStoneFootstep).setHardness(0.8F).setBlockName("sandStone").setRequiresSelfNotify();
        music = (new BlockNote(25)).setHardness(0.8F).setBlockName("musicBlock").setRequiresSelfNotify();
        bed = (new BlockBed(26)).setHardness(0.2F).setBlockName("bed").disableStats().setRequiresSelfNotify();
        railPowered = (new BlockRail(27, 179, true)).setHardness(0.7F).setStepSound(soundMetalFootstep).setBlockName("goldenRail").setRequiresSelfNotify();
        railDetector = (new BlockDetectorRail(28, 195)).setHardness(0.7F).setStepSound(soundMetalFootstep).setBlockName("detectorRail").setRequiresSelfNotify();
        pistonStickyBase = (new BlockPistonBase(29, 106, true)).setBlockName("pistonStickyBase").setRequiresSelfNotify();
        web = (new BlockWeb(30, 11)).setLightOpacity(1).setHardness(4F).setBlockName("web");
        tallGrass = (BlockTallGrass)(new BlockTallGrass(31, 39)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("tallgrass");
        deadBush = (BlockDeadBush)(new BlockDeadBush(32, 55)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("deadbush");
        pistonBase = (new BlockPistonBase(33, 107, false)).setBlockName("pistonBase").setRequiresSelfNotify();
        pistonExtension = (BlockPistonExtension)(new BlockPistonExtension(34, 107)).setRequiresSelfNotify();
        cloth = (new BlockCloth()).setHardness(0.8F).setStepSound(soundClothFootstep).setBlockName("cloth").setRequiresSelfNotify();
        pistonMoving = new BlockPistonMoving(36);
        plantYellow = (BlockFlower)(new BlockFlower(37, 13)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("flower");
        plantRed = (BlockFlower)(new BlockFlower(38, 12)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("rose");
        mushroomBrown = (BlockFlower)(new BlockMushroom(39, 29)).setHardness(0.0F).setStepSound(soundGrassFootstep).setLightValue(0.125F).setBlockName("mushroom");
        mushroomRed = (BlockFlower)(new BlockMushroom(40, 28)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("mushroom");
        blockGold = (new BlockOreStorage(41, 23)).setHardness(3F).setResistance(10F).setStepSound(soundMetalFootstep).setBlockName("blockGold");
        blockSteel = (new BlockOreStorage(42, 22)).setHardness(5F).setResistance(10F).setStepSound(soundMetalFootstep).setBlockName("blockIron");
        field_55134_aj = (BlockHalfSlab)(new BlockStep(43, true)).setHardness(2.0F).setResistance(10F).setStepSound(soundStoneFootstep).setBlockName("stoneSlab");
        field_55133_ak = (BlockHalfSlab)(new BlockStep(44, false)).setHardness(2.0F).setResistance(10F).setStepSound(soundStoneFootstep).setBlockName("stoneSlab");
        brick = (new Block(45, 7, Material.rock)).setHardness(2.0F).setResistance(10F).setStepSound(soundStoneFootstep).setBlockName("brick").func_56326_a(CreativeTabs.field_56387_b);
        tnt = (new BlockTNT(46, 8)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("tnt");
        bookShelf = (new BlockBookshelf(47, 35)).setHardness(1.5F).setStepSound(soundWoodFootstep).setBlockName("bookshelf");
        cobblestoneMossy = (new Block(48, 36, Material.rock)).setHardness(2.0F).setResistance(10F).setStepSound(soundStoneFootstep).setBlockName("stoneMoss").func_56326_a(CreativeTabs.field_56387_b);
        obsidian = (new BlockObsidian(49, 37)).setHardness(50F).setResistance(2000F).setStepSound(soundStoneFootstep).setBlockName("obsidian");
        torchWood = (new BlockTorch(50, 80)).setHardness(0.0F).setLightValue(0.9375F).setStepSound(soundWoodFootstep).setBlockName("torch").setRequiresSelfNotify();
        fire = (BlockFire)(new BlockFire(51, 31)).setHardness(0.0F).setLightValue(1.0F).setStepSound(soundWoodFootstep).setBlockName("fire").disableStats();
        mobSpawner = (new BlockMobSpawner(52, 65)).setHardness(5F).setStepSound(soundMetalFootstep).setBlockName("mobSpawner").disableStats();
        stairCompactPlanks = (new BlockStairs(53, planks, 0)).setBlockName("stairsWood").setRequiresSelfNotify();
        chest = (new BlockChest(54)).setHardness(2.5F).setStepSound(soundWoodFootstep).setBlockName("chest").setRequiresSelfNotify();
        redstoneWire = (new BlockRedstoneWire(55, 164)).setHardness(0.0F).setStepSound(soundPowderFootstep).setBlockName("redstoneDust").disableStats().setRequiresSelfNotify();
        field_56337_aw = (new BlockOre(56, 50)).setHardness(3F).setResistance(5F).setStepSound(soundStoneFootstep).setBlockName("oreDiamond");
        field_56336_ax = (new BlockOreStorage(57, 24)).setHardness(5F).setResistance(10F).setStepSound(soundMetalFootstep).setBlockName("blockDiamond");
        workbench = (new BlockWorkbench(58)).setHardness(2.5F).setStepSound(soundWoodFootstep).setBlockName("workbench");
        crops = (new BlockCrops(59, 88)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("crops").disableStats().setRequiresSelfNotify();
        tilledField = (new BlockFarmland(60)).setHardness(0.6F).setStepSound(soundGravelFootstep).setBlockName("farmland").setRequiresSelfNotify();
        stoneOvenIdle = (new BlockFurnace(61, false)).setHardness(3.5F).setStepSound(soundStoneFootstep).setBlockName("furnace").setRequiresSelfNotify().func_56326_a(CreativeTabs.field_56388_c);
        stoneOvenActive = (new BlockFurnace(62, true)).setHardness(3.5F).setStepSound(soundStoneFootstep).setLightValue(0.875F).setBlockName("furnace").setRequiresSelfNotify();
        signPost = (new BlockSign(63, net.minecraft.src.TileEntitySign.class, true)).setHardness(1.0F).setStepSound(soundWoodFootstep).setBlockName("sign").disableStats().setRequiresSelfNotify();
        doorWood = (new BlockDoor(64, Material.wood)).setHardness(3F).setStepSound(soundWoodFootstep).setBlockName("doorWood").disableStats().setRequiresSelfNotify();
        ladder = (new BlockLadder(65, 83)).setHardness(0.4F).setStepSound(soundWoodFootstep).setBlockName("ladder").setRequiresSelfNotify();
        rail = (new BlockRail(66, 128, false)).setHardness(0.7F).setStepSound(soundMetalFootstep).setBlockName("rail").setRequiresSelfNotify();
        stairCompactCobblestone = (new BlockStairs(67, cobblestone, 0)).setBlockName("stairsStone").setRequiresSelfNotify();
        signWall = (new BlockSign(68, net.minecraft.src.TileEntitySign.class, false)).setHardness(1.0F).setStepSound(soundWoodFootstep).setBlockName("sign").disableStats().setRequiresSelfNotify();
        lever = (new BlockLever(69, 96)).setHardness(0.5F).setStepSound(soundWoodFootstep).setBlockName("lever").setRequiresSelfNotify();
        pressurePlateStone = (new BlockPressurePlate(70, stone.blockIndexInTexture, EnumMobType.mobs, Material.rock)).setHardness(0.5F).setStepSound(soundStoneFootstep).setBlockName("pressurePlate").setRequiresSelfNotify();
        doorSteel = (new BlockDoor(71, Material.iron)).setHardness(5F).setStepSound(soundMetalFootstep).setBlockName("doorIron").disableStats().setRequiresSelfNotify();
        pressurePlatePlanks = (new BlockPressurePlate(72, planks.blockIndexInTexture, EnumMobType.everything, Material.wood)).setHardness(0.5F).setStepSound(soundWoodFootstep).setBlockName("pressurePlate").setRequiresSelfNotify();
        oreRedstone = (new BlockRedstoneOre(73, 51, false)).setHardness(3F).setResistance(5F).setStepSound(soundStoneFootstep).setBlockName("oreRedstone").setRequiresSelfNotify().func_56326_a(CreativeTabs.field_56387_b);
        oreRedstoneGlowing = (new BlockRedstoneOre(74, 51, true)).setLightValue(0.625F).setHardness(3F).setResistance(5F).setStepSound(soundStoneFootstep).setBlockName("oreRedstone").setRequiresSelfNotify();
        torchRedstoneIdle = (new BlockRedstoneTorch(75, 115, false)).setHardness(0.0F).setStepSound(soundWoodFootstep).setBlockName("notGate").setRequiresSelfNotify();
        torchRedstoneActive = (new BlockRedstoneTorch(76, 99, true)).setHardness(0.0F).setLightValue(0.5F).setStepSound(soundWoodFootstep).setBlockName("notGate").setRequiresSelfNotify().func_56326_a(CreativeTabs.field_56385_d);
        button = (new BlockButton(77, stone.blockIndexInTexture)).setHardness(0.5F).setStepSound(soundStoneFootstep).setBlockName("button").setRequiresSelfNotify();
        snow = (new BlockSnow(78, 66)).setHardness(0.1F).setStepSound(soundClothFootstep).setBlockName("snow").setRequiresSelfNotify().setLightOpacity(0);
        ice = (new BlockIce(79, 67)).setHardness(0.5F).setLightOpacity(3).setStepSound(soundGlassFootstep).setBlockName("ice");
        blockSnow = (new BlockSnowBlock(80, 66)).setHardness(0.2F).setStepSound(soundClothFootstep).setBlockName("snow");
        cactus = (new BlockCactus(81, 70)).setHardness(0.4F).setStepSound(soundClothFootstep).setBlockName("cactus");
        blockClay = (new BlockClay(82, 72)).setHardness(0.6F).setStepSound(soundGravelFootstep).setBlockName("clay");
        reed = (new BlockReed(83, 73)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("reeds").disableStats();
        jukebox = (new BlockJukeBox(84, 74)).setHardness(2.0F).setResistance(10F).setStepSound(soundStoneFootstep).setBlockName("jukebox").setRequiresSelfNotify();
        fence = (new BlockFence(85, 4)).setHardness(2.0F).setResistance(5F).setStepSound(soundWoodFootstep).setBlockName("fence");
        pumpkin = (new BlockPumpkin(86, 102, false)).setHardness(1.0F).setStepSound(soundWoodFootstep).setBlockName("pumpkin").setRequiresSelfNotify();
        netherrack = (new BlockNetherrack(87, 103)).setHardness(0.4F).setStepSound(soundStoneFootstep).setBlockName("hellrock");
        slowSand = (new BlockSoulSand(88, 104)).setHardness(0.5F).setStepSound(soundSandFootstep).setBlockName("hellsand");
        glowStone = (new BlockGlowStone(89, 105, Material.glass)).setHardness(0.3F).setStepSound(soundGlassFootstep).setLightValue(1.0F).setBlockName("lightgem");
        portal = (BlockPortal)(new BlockPortal(90, 14)).setHardness(-1F).setStepSound(soundGlassFootstep).setLightValue(0.75F).setBlockName("portal");
        pumpkinLantern = (new BlockPumpkin(91, 102, true)).setHardness(1.0F).setStepSound(soundWoodFootstep).setLightValue(1.0F).setBlockName("litpumpkin").setRequiresSelfNotify();
        cake = (new BlockCake(92, 121)).setHardness(0.5F).setStepSound(soundClothFootstep).setBlockName("cake").disableStats().setRequiresSelfNotify();
        redstoneRepeaterIdle = (new BlockRedstoneRepeater(93, false)).setHardness(0.0F).setStepSound(soundWoodFootstep).setBlockName("diode").disableStats().setRequiresSelfNotify();
        redstoneRepeaterActive = (new BlockRedstoneRepeater(94, true)).setHardness(0.0F).setLightValue(0.625F).setStepSound(soundWoodFootstep).setBlockName("diode").disableStats().setRequiresSelfNotify();
        lockedChest = (new BlockLockedChest(95)).setHardness(0.0F).setLightValue(1.0F).setStepSound(soundWoodFootstep).setBlockName("lockedchest").setTickRandomly(true).setRequiresSelfNotify();
        trapdoor = (new BlockTrapDoor(96, Material.wood)).setHardness(3F).setStepSound(soundWoodFootstep).setBlockName("trapdoor").disableStats().setRequiresSelfNotify();
        silverfish = (new BlockSilverfish(97)).setHardness(0.75F).setBlockName("monsterStoneEgg");
        stoneBrick = (new BlockStoneBrick(98)).setHardness(1.5F).setResistance(10F).setStepSound(soundStoneFootstep).setBlockName("stonebricksmooth");
        mushroomCapBrown = (new BlockMushroomCap(99, Material.wood, 142, 0)).setHardness(0.2F).setStepSound(soundWoodFootstep).setBlockName("mushroom").setRequiresSelfNotify();
        mushroomCapRed = (new BlockMushroomCap(100, Material.wood, 142, 1)).setHardness(0.2F).setStepSound(soundWoodFootstep).setBlockName("mushroom").setRequiresSelfNotify();
        fenceIron = (new BlockPane(101, 85, 85, Material.iron, true)).setHardness(5F).setResistance(10F).setStepSound(soundMetalFootstep).setBlockName("fenceIron");
        thinGlass = (new BlockPane(102, 49, 148, Material.glass, false)).setHardness(0.3F).setStepSound(soundGlassFootstep).setBlockName("thinGlass");
        melon = (new BlockMelon(103)).setHardness(1.0F).setStepSound(soundWoodFootstep).setBlockName("melon");
        pumpkinStem = (new BlockStem(104, pumpkin)).setHardness(0.0F).setStepSound(soundWoodFootstep).setBlockName("pumpkinStem").setRequiresSelfNotify();
        melonStem = (new BlockStem(105, melon)).setHardness(0.0F).setStepSound(soundWoodFootstep).setBlockName("pumpkinStem").setRequiresSelfNotify();
        vine = (new BlockVine(106)).setHardness(0.2F).setStepSound(soundGrassFootstep).setBlockName("vine").setRequiresSelfNotify();
        fenceGate = (new BlockFenceGate(107, 4)).setHardness(2.0F).setResistance(5F).setStepSound(soundWoodFootstep).setBlockName("fenceGate").setRequiresSelfNotify();
        stairsBrick = (new BlockStairs(108, brick, 0)).setBlockName("stairsBrick").setRequiresSelfNotify();
        stairsStoneBrickSmooth = (new BlockStairs(109, stoneBrick, 0)).setBlockName("stairsStoneBrickSmooth").setRequiresSelfNotify();
        mycelium = (BlockMycelium)(new BlockMycelium(110)).setHardness(0.6F).setStepSound(soundGrassFootstep).setBlockName("mycel");
        waterlily = (new BlockLilyPad(111, 76)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("waterlily");
        netherBrick = (new Block(112, 224, Material.rock)).setHardness(2.0F).setResistance(10F).setStepSound(soundStoneFootstep).setBlockName("netherBrick").func_56326_a(CreativeTabs.field_56387_b);
        netherFence = (new BlockFence(113, 224, Material.rock)).setHardness(2.0F).setResistance(10F).setStepSound(soundStoneFootstep).setBlockName("netherFence");
        stairsNetherBrick = (new BlockStairs(114, netherBrick, 0)).setBlockName("stairsNetherBrick").setRequiresSelfNotify();
        netherStalk = (new BlockNetherStalk(115)).setBlockName("netherStalk").setRequiresSelfNotify();
        enchantmentTable = (new BlockEnchantmentTable(116)).setHardness(5F).setResistance(2000F).setBlockName("enchantmentTable");
        brewingStand = (new BlockBrewingStand(117)).setHardness(0.5F).setLightValue(0.125F).setBlockName("brewingStand").setRequiresSelfNotify();
        cauldron = (new BlockCauldron(118)).setHardness(2.0F).setBlockName("cauldron").setRequiresSelfNotify();
        endPortal = (new BlockEndPortal(119, Material.portal)).setHardness(-1F).setResistance(6000000F);
        endPortalFrame = (new BlockEndPortalFrame(120)).setStepSound(soundGlassFootstep).setLightValue(0.125F).setHardness(-1F).setBlockName("endPortalFrame").setRequiresSelfNotify().setResistance(6000000F).func_56326_a(CreativeTabs.field_56388_c);
        whiteStone = (new Block(121, 175, Material.rock)).setHardness(3F).setResistance(15F).setStepSound(soundStoneFootstep).setBlockName("whiteStone").func_56326_a(CreativeTabs.field_56387_b);
        dragonEgg = (new BlockDragonEgg(122, 167)).setHardness(3F).setResistance(15F).setStepSound(soundStoneFootstep).setLightValue(0.125F).setBlockName("dragonEgg");
        redstoneLampIdle = (new BlockRedstoneLight(123, false)).setHardness(0.3F).setStepSound(soundGlassFootstep).setBlockName("redstoneLight").func_56326_a(CreativeTabs.field_56385_d);
        redstoneLampActive = (new BlockRedstoneLight(124, true)).setHardness(0.3F).setStepSound(soundGlassFootstep).setBlockName("redstoneLight");
        field_55135_bN = (BlockHalfSlab)(new BlockWoodSlab(125, true)).setHardness(2.0F).setResistance(5F).setStepSound(soundWoodFootstep).setBlockName("woodSlab");
        field_55136_bO = (BlockHalfSlab)(new BlockWoodSlab(126, false)).setHardness(2.0F).setResistance(5F).setStepSound(soundWoodFootstep).setBlockName("woodSlab");
        field_56338_bP = (new BlockCocoa(127)).setHardness(0.2F).setResistance(5F).setStepSound(soundWoodFootstep).setBlockName("cocoa").setRequiresSelfNotify();
        field_56339_bQ = (new BlockStairs(128, sandStone, 0)).setBlockName("stairsSandStone").setRequiresSelfNotify();
        oreDiamond = (new BlockOre(129, 171)).setHardness(3F).setResistance(5F).setStepSound(soundStoneFootstep).setBlockName("oreEmerald");
        field_56335_bS = (new BlockEnderChest(130)).setHardness(22.5F).setResistance(1000F).setStepSound(soundStoneFootstep).setBlockName("enderChest").setRequiresSelfNotify().setLightValue(0.5F);
        field_56334_bT = (BlockTripWireSource)(new BlockTripWireSource(131)).setBlockName("tripWireSource").setRequiresSelfNotify();
        field_56333_bU = (new BlockTripWire(132)).setBlockName("tripWire").setRequiresSelfNotify();
        blockDiamond = (new BlockOreStorage(133, 25)).setHardness(5F).setResistance(10F).setStepSound(soundMetalFootstep).setBlockName("blockEmerald");
        field_58036_bW = (new BlockStairs(134, planks, 1)).setBlockName("stairsWoodSpruce").setRequiresSelfNotify();
        field_58035_bX = (new BlockStairs(135, planks, 2)).setBlockName("stairsWoodBirch").setRequiresSelfNotify();
        field_58034_bY = (new BlockStairs(136, planks, 3)).setBlockName("stairsWoodJungle").setRequiresSelfNotify();
        Item.itemsList[cloth.blockID] = (new ItemCloth(cloth.blockID - 256)).setItemName("cloth");
        Item.itemsList[wood.blockID] = (new ItemTree(wood.blockID - 256, wood)).setItemName("log");
        Item.itemsList[planks.blockID] = (new ItemWood(planks.blockID - 256, planks)).setItemName("wood");
        Item.itemsList[silverfish.blockID] = (new ItemBlockSilverfish(silverfish.blockID - 256)).setItemName("monsterStoneEgg");
        Item.itemsList[stoneBrick.blockID] = (new ItemSmoothStone(stoneBrick.blockID - 256, stoneBrick)).setItemName("stonebricksmooth");
        Item.itemsList[sandStone.blockID] = (new ItemSandStone(sandStone.blockID - 256, sandStone)).setItemName("sandStone");
        Item.itemsList[field_55133_ak.blockID] = (new ItemSlab(field_55133_ak.blockID - 256, field_55133_ak, field_55134_aj, false)).setItemName("stoneSlab");
        Item.itemsList[field_55134_aj.blockID] = (new ItemSlab(field_55134_aj.blockID - 256, field_55133_ak, field_55134_aj, true)).setItemName("stoneSlab");
        Item.itemsList[field_55136_bO.blockID] = (new ItemSlab(field_55136_bO.blockID - 256, field_55136_bO, field_55135_bN, false)).setItemName("woodSlab");
        Item.itemsList[field_55135_bN.blockID] = (new ItemSlab(field_55135_bN.blockID - 256, field_55136_bO, field_55135_bN, true)).setItemName("woodSlab");
        Item.itemsList[sapling.blockID] = (new ItemSapling(sapling.blockID - 256)).setItemName("sapling");
        Item.itemsList[leaves.blockID] = (new ItemLeaves(leaves.blockID - 256)).setItemName("leaves");
        Item.itemsList[vine.blockID] = new ItemColored(vine.blockID - 256, false);
        Item.itemsList[tallGrass.blockID] = (new ItemColored(tallGrass.blockID - 256, true)).setBlockNames(new String[]
                {
                    "shrub", "grass", "fern"
                });
        Item.itemsList[waterlily.blockID] = new ItemLilyPad(waterlily.blockID - 256);
        Item.itemsList[pistonBase.blockID] = new ItemPiston(pistonBase.blockID - 256);
        Item.itemsList[pistonStickyBase.blockID] = new ItemPiston(pistonStickyBase.blockID - 256);

        for (int i = 0; i < 256; i++)
        {
            if (blocksList[i] == null)
            {
                continue;
            }

            if (Item.itemsList[i] == null)
            {
                Item.itemsList[i] = new ItemBlock(i - 256);
                blocksList[i].initializeBlock();
            }

            boolean flag = false;

            if (i > 0 && blocksList[i].getRenderType() == 10)
            {
                flag = true;
            }

            if (i > 0 && (blocksList[i] instanceof BlockHalfSlab))
            {
                flag = true;
            }

            if (i == tilledField.blockID)
            {
                flag = true;
            }

            if (canBlockGrass[i])
            {
                flag = true;
            }

            useNeighborBrightness[i] = flag;
        }

        canBlockGrass[0] = true;
        StatList.initBreakableStats();
    }
}
