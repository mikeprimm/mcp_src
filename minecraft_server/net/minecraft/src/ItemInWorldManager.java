package net.minecraft.src;

public class ItemInWorldManager
{
    /** The world object that this object is connected to. */
    public World thisWorld;
    public EntityPlayerMP field_56699_b;
    private EnumGameType field_56700_c;
    private boolean field_56698_d;
    private int initialDamage;
    private int curBlockX;
    private int curBlockY;
    private int curBlockZ;
    private int curblockDamage;
    private boolean field_22050_k;
    private int field_22049_l;
    private int field_22048_m;
    private int field_22047_n;
    private int field_22046_o;
    private int field_56701_o;

    public ItemInWorldManager(World par1World)
    {
        field_56700_c = EnumGameType.NOT_SET;
        field_56701_o = -1;
        thisWorld = par1World;
    }

    public void func_56697_a(EnumGameType par1EnumGameType)
    {
        field_56700_c = par1EnumGameType;
        par1EnumGameType.func_56605_a(field_56699_b.capabilities);
        field_56699_b.func_50022_L();
    }

    public EnumGameType func_56695_b()
    {
        return field_56700_c;
    }

    /**
     * Get if we are in creative game mode.
     */
    public boolean isCreative()
    {
        return field_56700_c.func_56606_d();
    }

    public void func_56694_b(EnumGameType par1EnumGameType)
    {
        if (field_56700_c == EnumGameType.NOT_SET)
        {
            field_56700_c = par1EnumGameType;
        }

        func_56697_a(field_56700_c);
    }

    public void updateBlockRemoving()
    {
        curblockDamage++;

        if (field_22050_k)
        {
            int i = curblockDamage - field_22046_o;
            int k = thisWorld.getBlockId(field_22049_l, field_22048_m, field_22047_n);

            if (k == 0)
            {
                field_22050_k = false;
            }
            else
            {
                Block block1 = Block.blocksList[k];
                float f = block1.func_58032_a(field_56699_b, field_56699_b.worldObj, field_22049_l, field_22048_m, field_22047_n) * (float)(i + 1);
                int i1 = (int)(f * 10F);

                if (i1 != field_56701_o)
                {
                    thisWorld.func_56361_g(field_56699_b.entityId, field_22049_l, field_22048_m, field_22047_n, i1);
                    field_56701_o = i1;
                }

                if (f >= 1.0F)
                {
                    field_22050_k = false;
                    blockHarvessted(field_22049_l, field_22048_m, field_22047_n);
                }
            }
        }
        else if (field_56698_d)
        {
            int j = thisWorld.getBlockId(curBlockX, curBlockY, curBlockZ);
            Block block = Block.blocksList[j];

            if (block == null)
            {
                thisWorld.func_56361_g(field_56699_b.entityId, curBlockX, curBlockY, curBlockZ, -1);
                field_56701_o = -1;
                field_56698_d = false;
            }
            else
            {
                int l = curblockDamage - initialDamage;
                float f1 = block.func_58032_a(field_56699_b, field_56699_b.worldObj, curBlockX, curBlockY, curBlockZ) * (float)(l + 1);
                int j1 = (int)(f1 * 10F);

                if (j1 != field_56701_o)
                {
                    thisWorld.func_56361_g(field_56699_b.entityId, curBlockX, curBlockY, curBlockZ, j1);
                    field_56701_o = j1;
                }
            }
        }
    }

    public void blockClicked(int par1, int par2, int par3, int par4)
    {
        if (field_56700_c.func_56609_c())
        {
            return;
        }

        if (isCreative())
        {
            if (!thisWorld.func_48093_a(null, par1, par2, par3, par4))
            {
                blockHarvessted(par1, par2, par3);
            }

            return;
        }

        thisWorld.func_48093_a(field_56699_b, par1, par2, par3, par4);
        initialDamage = curblockDamage;
        float f = 1.0F;
        int i = thisWorld.getBlockId(par1, par2, par3);

        if (i > 0)
        {
            Block.blocksList[i].onBlockClicked(thisWorld, par1, par2, par3, field_56699_b);
            f = Block.blocksList[i].func_58032_a(field_56699_b, field_56699_b.worldObj, par1, par2, par3);
        }

        if (i > 0 && f >= 1.0F)
        {
            blockHarvessted(par1, par2, par3);
        }
        else
        {
            field_56698_d = true;
            curBlockX = par1;
            curBlockY = par2;
            curBlockZ = par3;
            int j = (int)(f * 10F);
            thisWorld.func_56361_g(field_56699_b.entityId, par1, par2, par3, j);
            field_56701_o = j;
        }
    }

    public void blockRemoving(int par1, int par2, int par3)
    {
        if (par1 == curBlockX && par2 == curBlockY && par3 == curBlockZ)
        {
            int i = curblockDamage - initialDamage;
            int j = thisWorld.getBlockId(par1, par2, par3);

            if (j != 0)
            {
                Block block = Block.blocksList[j];
                float f = block.func_58032_a(field_56699_b, field_56699_b.worldObj, par1, par2, par3) * (float)(i + 1);

                if (f >= 0.7F)
                {
                    field_56698_d = false;
                    thisWorld.func_56361_g(field_56699_b.entityId, par1, par2, par3, -1);
                    blockHarvessted(par1, par2, par3);
                }
                else if (!field_22050_k)
                {
                    field_56698_d = false;
                    field_22050_k = true;
                    field_22049_l = par1;
                    field_22048_m = par2;
                    field_22047_n = par3;
                    field_22046_o = initialDamage;
                }
            }
        }
    }

    public void func_56693_c(int par1, int par2, int par3)
    {
        field_56698_d = false;
        thisWorld.func_56361_g(field_56699_b.entityId, curBlockX, curBlockY, curBlockZ, -1);
    }

    /**
     * Removes a block and triggers the appropriate events
     */
    private boolean removeBlock(int par1, int par2, int par3)
    {
        Block block = Block.blocksList[thisWorld.getBlockId(par1, par2, par3)];
        int i = thisWorld.getBlockMetadata(par1, par2, par3);

        if (block != null)
        {
            block.func_56324_a(thisWorld, par1, par2, par3, i, field_56699_b);
        }

        boolean flag = thisWorld.setBlockWithNotify(par1, par2, par3, 0);

        if (block != null && flag)
        {
            block.onBlockDestroyedByPlayer(thisWorld, par1, par2, par3, i);
        }

        return flag;
    }

    public boolean blockHarvessted(int par1, int par2, int par3)
    {
        if (field_56700_c.func_56609_c())
        {
            return false;
        }

        int i = thisWorld.getBlockId(par1, par2, par3);
        int j = thisWorld.getBlockMetadata(par1, par2, par3);
        thisWorld.playAuxSFXAtEntity(field_56699_b, 2001, par1, par2, par3, i + (thisWorld.getBlockMetadata(par1, par2, par3) << 12));
        boolean flag = removeBlock(par1, par2, par3);

        if (isCreative())
        {
            field_56699_b.playerNetServerHandler.sendPacket(new Packet53BlockChange(par1, par2, par3, thisWorld));
        }
        else
        {
            ItemStack itemstack = field_56699_b.getCurrentEquippedItem();
            boolean flag1 = field_56699_b.canHarvestBlock(Block.blocksList[i]);

            if (itemstack != null)
            {
                itemstack.func_58088_a(thisWorld, i, par1, par2, par3, field_56699_b);

                if (itemstack.stackSize == 0)
                {
                    field_56699_b.destroyCurrentEquippedItem();
                }
            }

            if (flag && flag1)
            {
                Block.blocksList[i].harvestBlock(thisWorld, field_56699_b, par1, par2, par3, j);
            }
        }

        return flag;
    }

    public boolean itemUsed(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack)
    {
        int i = par3ItemStack.stackSize;
        int j = par3ItemStack.getItemDamage();
        ItemStack itemstack = par3ItemStack.useItemRightClick(par2World, par1EntityPlayer);

        if (itemstack != par3ItemStack || itemstack != null && itemstack.stackSize != i || itemstack != null && itemstack.getMaxItemUseDuration() > 0)
        {
            par1EntityPlayer.inventory.mainInventory[par1EntityPlayer.inventory.currentItem] = itemstack;

            if (isCreative())
            {
                itemstack.stackSize = i;
                itemstack.setItemDamage(j);
            }

            if (itemstack.stackSize == 0)
            {
                par1EntityPlayer.inventory.mainInventory[par1EntityPlayer.inventory.currentItem] = null;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean func_56696_a(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        int i = par2World.getBlockId(par4, par5, par6);

        if (i > 0 && Block.blocksList[i].func_56323_a(par2World, par4, par5, par6, par1EntityPlayer, par7, par8, par9, par10))
        {
            return true;
        }

        if (par3ItemStack == null)
        {
            return false;
        }

        if (isCreative())
        {
            int j = par3ItemStack.getItemDamage();
            int k = par3ItemStack.stackSize;
            boolean flag = par3ItemStack.func_56775_a(par1EntityPlayer, par2World, par4, par5, par6, par7, par8, par9, par10);
            par3ItemStack.setItemDamage(j);
            par3ItemStack.stackSize = k;
            return flag;
        }
        else
        {
            return par3ItemStack.func_56775_a(par1EntityPlayer, par2World, par4, par5, par6, par7, par8, par9, par10);
        }
    }

    /**
     * Sets the world instance.
     */
    public void setWorld(WorldServer par1WorldServer)
    {
        thisWorld = par1WorldServer;
    }
}
