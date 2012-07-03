package net.minecraft.src;

import java.util.Random;

public class BlockDispenser extends BlockContainer
{
    private Random random;

    protected BlockDispenser(int par1)
    {
        super(par1, Material.rock);
        random = new Random();
        blockIndexInTexture = 45;
        func_56326_a(CreativeTabs.field_56385_d);
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 4;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Block.dispenser.blockID;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);
        setDispenserDefaultDirection(par1World, par2, par3, par4);
    }

    /**
     * sets Dispenser block direction so that the front faces an non-opaque block; chooses west to be direction if all
     * surrounding blocks are opaque.
     */
    private void setDispenserDefaultDirection(World par1World, int par2, int par3, int par4)
    {
        if (par1World.isRemote)
        {
            return;
        }

        int i = par1World.getBlockId(par2, par3, par4 - 1);
        int j = par1World.getBlockId(par2, par3, par4 + 1);
        int k = par1World.getBlockId(par2 - 1, par3, par4);
        int l = par1World.getBlockId(par2 + 1, par3, par4);
        byte byte0 = 3;

        if (Block.opaqueCubeLookup[i] && !Block.opaqueCubeLookup[j])
        {
            byte0 = 3;
        }

        if (Block.opaqueCubeLookup[j] && !Block.opaqueCubeLookup[i])
        {
            byte0 = 2;
        }

        if (Block.opaqueCubeLookup[k] && !Block.opaqueCubeLookup[l])
        {
            byte0 = 5;
        }

        if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[k])
        {
            byte0 = 4;
        }

        par1World.setBlockMetadataWithNotify(par2, par3, par4, byte0);
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int par1)
    {
        if (par1 == 1)
        {
            return blockIndexInTexture + 17;
        }

        if (par1 == 0)
        {
            return blockIndexInTexture + 17;
        }

        if (par1 == 3)
        {
            return blockIndexInTexture + 1;
        }
        else
        {
            return blockIndexInTexture;
        }
    }

    public boolean func_56323_a(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.isRemote)
        {
            return true;
        }

        TileEntityDispenser tileentitydispenser = (TileEntityDispenser)par1World.getBlockTileEntity(par2, par3, par4);

        if (tileentitydispenser != null)
        {
            par5EntityPlayer.displayGUIDispenser(tileentitydispenser);
        }

        return true;
    }

    /**
     * dispenses an item from a randomly selected item stack from the blocks inventory into the game world.
     */
    private void dispenseItem(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);
        int j = 0;
        int k = 0;

        if (i == 3)
        {
            k = 1;
        }
        else if (i == 2)
        {
            k = -1;
        }
        else if (i == 5)
        {
            j = 1;
        }
        else
        {
            j = -1;
        }

        TileEntityDispenser tileentitydispenser = (TileEntityDispenser)par1World.getBlockTileEntity(par2, par3, par4);

        if (tileentitydispenser != null)
        {
            int l = tileentitydispenser.func_56110_m_();

            if (l < 0)
            {
                par1World.playAuxSFX(1001, par2, par3, par4, 0);
            }
            else
            {
                double d = (double)par2 + (double)j * 0.59999999999999998D + 0.5D;
                double d1 = (double)par3 + 0.5D;
                double d2 = (double)par4 + (double)k * 0.59999999999999998D + 0.5D;
                ItemStack itemstack = tileentitydispenser.getStackInSlot(l);
                int i1 = func_56353_a(tileentitydispenser, par1World, itemstack, par5Random, par2, par3, par4, j, k, d, d1, d2);

                if (i1 == 1)
                {
                    tileentitydispenser.decrStackSize(l, 1);
                }
                else if (i1 == 0)
                {
                    ItemStack itemstack1 = tileentitydispenser.decrStackSize(l, 1);
                    func_56352_a(par1World, itemstack1, par5Random, 6, j, k, d, d1, d2);
                    par1World.playAuxSFX(1000, par2, par3, par4, 0);
                }

                par1World.playAuxSFX(2000, par2, par3, par4, j + 1 + (k + 1) * 3);
            }
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par5 > 0 && Block.blocksList[par5].canProvidePower())
        {
            boolean flag = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4);

            if (flag)
            {
                par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate());
            }
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isRemote && (par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4)))
        {
            dispenseItem(par1World, par2, par3, par4, par5Random);
        }
    }

    public TileEntity func_56351_a(World par1World)
    {
        return new TileEntityDispenser();
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int i = MathHelper.floor_double((double)((par5EntityLiving.rotationYaw * 4F) / 360F) + 0.5D) & 3;

        if (i == 0)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2);
        }

        if (i == 1)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 5);
        }

        if (i == 2)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3);
        }

        if (i == 3)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 4);
        }
    }

    public void func_56322_a(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        TileEntityDispenser tileentitydispenser = (TileEntityDispenser)par1World.getBlockTileEntity(par2, par3, par4);

        if (tileentitydispenser != null)
        {
            label0:

            for (int i = 0; i < tileentitydispenser.getSizeInventory(); i++)
            {
                ItemStack itemstack = tileentitydispenser.getStackInSlot(i);

                if (itemstack == null)
                {
                    continue;
                }

                float f = random.nextFloat() * 0.8F + 0.1F;
                float f1 = random.nextFloat() * 0.8F + 0.1F;
                float f2 = random.nextFloat() * 0.8F + 0.1F;

                do
                {
                    if (itemstack.stackSize <= 0)
                    {
                        continue label0;
                    }

                    int j = random.nextInt(21) + 10;

                    if (j > itemstack.stackSize)
                    {
                        j = itemstack.stackSize;
                    }

                    itemstack.stackSize -= j;
                    EntityItem entityitem = new EntityItem(par1World, (float)par2 + f, (float)par3 + f1, (float)par4 + f2, new ItemStack(itemstack.itemID, j, itemstack.getItemDamage()));

                    if (itemstack.hasTagCompound())
                    {
                        entityitem.item.setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                    }

                    float f3 = 0.05F;
                    entityitem.motionX = (float)random.nextGaussian() * f3;
                    entityitem.motionY = (float)random.nextGaussian() * f3 + 0.2F;
                    entityitem.motionZ = (float)random.nextGaussian() * f3;
                    par1World.spawnEntityInWorld(entityitem);
                }
                while (true);
            }
        }

        super.func_56322_a(par1World, par2, par3, par4, par5, par6);
    }

    private static void func_56352_a(World par0World, ItemStack par1ItemStack, Random par2Random, int par3, int par4, int par5, double par6, double par8, double par10)
    {
        EntityItem entityitem = new EntityItem(par0World, par6, par8 - 0.29999999999999999D, par10, par1ItemStack);
        double d = par2Random.nextDouble() * 0.10000000000000001D + 0.20000000000000001D;
        entityitem.motionX = (double)par4 * d;
        entityitem.motionY = 0.20000000298023224D;
        entityitem.motionZ = (double)par5 * d;
        entityitem.motionX += par2Random.nextGaussian() * 0.0074999998323619366D * (double)par3;
        entityitem.motionY += par2Random.nextGaussian() * 0.0074999998323619366D * (double)par3;
        entityitem.motionZ += par2Random.nextGaussian() * 0.0074999998323619366D * (double)par3;
        par0World.spawnEntityInWorld(entityitem);
    }

    private static int func_56353_a(TileEntityDispenser par0TileEntityDispenser, World par1World, ItemStack par2ItemStack, Random par3Random, int par4, int par5, int par6, int par7, int par8, double par9, double par11, double par13)
    {
        float f = 1.1F;
        int i = 6;

        if (par2ItemStack.itemID == Item.arrow.shiftedIndex)
        {
            EntityArrow entityarrow = new EntityArrow(par1World, par9, par11, par13);
            entityarrow.setArrowHeading(par7, 0.10000000149011612D, par8, f, i);
            entityarrow.field_58012_a = 1;
            par1World.spawnEntityInWorld(entityarrow);
            par1World.playAuxSFX(1002, par4, par5, par6, 0);
            return 1;
        }

        if (par2ItemStack.itemID == Item.egg.shiftedIndex)
        {
            EntityEgg entityegg = new EntityEgg(par1World, par9, par11, par13);
            entityegg.setThrowableHeading(par7, 0.10000000149011612D, par8, f, i);
            par1World.spawnEntityInWorld(entityegg);
            par1World.playAuxSFX(1002, par4, par5, par6, 0);
            return 1;
        }

        if (par2ItemStack.itemID == Item.snowball.shiftedIndex)
        {
            EntitySnowball entitysnowball = new EntitySnowball(par1World, par9, par11, par13);
            entitysnowball.setThrowableHeading(par7, 0.10000000149011612D, par8, f, i);
            par1World.spawnEntityInWorld(entitysnowball);
            par1World.playAuxSFX(1002, par4, par5, par6, 0);
            return 1;
        }

        if (par2ItemStack.itemID == Item.potion.shiftedIndex && ItemPotion.isSplash(par2ItemStack.getItemDamage()))
        {
            EntityPotion entitypotion = new EntityPotion(par1World, par9, par11, par13, par2ItemStack.getItemDamage());
            entitypotion.setThrowableHeading(par7, 0.10000000149011612D, par8, f * 1.25F, (float)i * 0.5F);
            par1World.spawnEntityInWorld(entitypotion);
            par1World.playAuxSFX(1002, par4, par5, par6, 0);
            return 1;
        }

        if (par2ItemStack.itemID == Item.expBottle.shiftedIndex)
        {
            EntityExpBottle entityexpbottle = new EntityExpBottle(par1World, par9, par11, par13);
            entityexpbottle.setThrowableHeading(par7, 0.10000000149011612D, par8, f * 1.25F, (float)i * 0.5F);
            par1World.spawnEntityInWorld(entityexpbottle);
            par1World.playAuxSFX(1002, par4, par5, par6, 0);
            return 1;
        }

        if (par2ItemStack.itemID == Item.monsterPlacer.shiftedIndex)
        {
            ItemMonsterPlacer.func_48390_a(par1World, par2ItemStack.getItemDamage(), par9 + (double)par7 * 0.29999999999999999D, par11 - 0.29999999999999999D, par13 + (double)par8 * 0.29999999999999999D);
            par1World.playAuxSFX(1002, par4, par5, par6, 0);
            return 1;
        }

        if (par2ItemStack.itemID == Item.fireballCharge.shiftedIndex)
        {
            EntitySmallFireball entitysmallfireball = new EntitySmallFireball(par1World, par9 + (double)par7 * 0.29999999999999999D, par11, par13 + (double)par8 * 0.29999999999999999D, (double)par7 + par3Random.nextGaussian() * 0.050000000000000003D, par3Random.nextGaussian() * 0.050000000000000003D, (double)par8 + par3Random.nextGaussian() * 0.050000000000000003D);
            par1World.spawnEntityInWorld(entitysmallfireball);
            par1World.playAuxSFX(1009, par4, par5, par6, 0);
            return 1;
        }

        if (par2ItemStack.itemID == Item.bucketLava.shiftedIndex || par2ItemStack.itemID == Item.bucketWater.shiftedIndex)
        {
            ItemBucket itembucket = (ItemBucket)par2ItemStack.getItem();

            if (itembucket.func_56461_a(par1World, par4, par5, par6, par4 + par7, par5, par6 + par8))
            {
                par2ItemStack.itemID = Item.bucketEmpty.shiftedIndex;
                par2ItemStack.stackSize = 1;
                return 2;
            }
            else
            {
                return 0;
            }
        }

        if (par2ItemStack.itemID == Item.bucketEmpty.shiftedIndex)
        {
            int j = par4 + par7;
            int k = par6 + par8;
            Material material = par1World.getBlockMaterial(j, par5, k);
            int l = par1World.getBlockMetadata(j, par5, k);

            if (material == Material.water && l == 0)
            {
                par1World.setBlockWithNotify(j, par5, k, 0);

                if (--par2ItemStack.stackSize == 0)
                {
                    par2ItemStack.itemID = Item.bucketWater.shiftedIndex;
                    par2ItemStack.stackSize = 1;
                }
                else if (par0TileEntityDispenser.func_56111_a(new ItemStack(Item.bucketWater)) < 0)
                {
                    func_56352_a(par1World, new ItemStack(Item.bucketWater), par3Random, 6, par7, par8, par9, par11, par13);
                }

                return 2;
            }

            if (material == Material.lava && l == 0)
            {
                par1World.setBlockWithNotify(j, par5, k, 0);

                if (--par2ItemStack.stackSize == 0)
                {
                    par2ItemStack.itemID = Item.bucketLava.shiftedIndex;
                    par2ItemStack.stackSize = 1;
                }
                else if (par0TileEntityDispenser.func_56111_a(new ItemStack(Item.bucketLava)) < 0)
                {
                    func_56352_a(par1World, new ItemStack(Item.bucketLava), par3Random, 6, par7, par8, par9, par11, par13);
                }

                return 2;
            }
            else
            {
                return 0;
            }
        }

        if (par2ItemStack.getItem() instanceof ItemMinecart)
        {
            par9 = (double)par4 + (par7 >= 0 ? (float)par7 * 1.8F : (double)par7 * 0.80000000000000004D) + (double)((float)Math.abs(par8) * 0.5F);
            par13 = (double)par6 + (par8 >= 0 ? (float)par8 * 1.8F : (double)par8 * 0.80000000000000004D) + (double)((float)Math.abs(par7) * 0.5F);

            if (BlockRail.isRailBlockAt(par1World, par4 + par7, par5, par6 + par8))
            {
                par11 = (float)par5 + 0.5F;
            }
            else if (par1World.isAirBlock(par4 + par7, par5, par6 + par8) && BlockRail.isRailBlockAt(par1World, par4 + par7, par5 - 1, par6 + par8))
            {
                par11 = (float)par5 - 0.5F;
            }
            else
            {
                return 0;
            }

            EntityMinecart entityminecart = new EntityMinecart(par1World, par9, par11, par13, ((ItemMinecart)par2ItemStack.getItem()).minecartType);
            par1World.spawnEntityInWorld(entityminecart);
            par1World.playAuxSFX(1000, par4, par5, par6, 0);
            return 1;
        }

        if (par2ItemStack.itemID == Item.boat.shiftedIndex)
        {
            par9 = (double)par4 + (par7 >= 0 ? (float)par7 * 1.8F : (double)par7 * 0.80000000000000004D) + (double)((float)Math.abs(par8) * 0.5F);
            par13 = (double)par6 + (par8 >= 0 ? (float)par8 * 1.8F : (double)par8 * 0.80000000000000004D) + (double)((float)Math.abs(par7) * 0.5F);

            if (par1World.getBlockMaterial(par4 + par7, par5, par6 + par8) == Material.water)
            {
                par11 = (float)par5 + 1.0F;
            }
            else if (par1World.isAirBlock(par4 + par7, par5, par6 + par8) && par1World.getBlockMaterial(par4 + par7, par5 - 1, par6 + par8) == Material.water)
            {
                par11 = par5;
            }
            else
            {
                return 0;
            }

            EntityBoat entityboat = new EntityBoat(par1World, par9, par11, par13);
            par1World.spawnEntityInWorld(entityboat);
            par1World.playAuxSFX(1000, par4, par5, par6, 0);
            return 1;
        }
        else
        {
            return 0;
        }
    }
}
