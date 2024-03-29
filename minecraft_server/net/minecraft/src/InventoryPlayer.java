package net.minecraft.src;

public class InventoryPlayer implements IInventory
{
    public ItemStack mainInventory[];
    public ItemStack armorInventory[];

    /** The index of the currently held item (0-8). */
    public int currentItem;

    /** The player whose inventory this is. */
    public EntityPlayer player;
    private ItemStack itemStack;

    /**
     * Set true whenever the inventory changes. Nothing sets it false so you will have to write your own code to check
     * it and reset the value.
     */
    public boolean inventoryChanged;

    public InventoryPlayer(EntityPlayer par1EntityPlayer)
    {
        mainInventory = new ItemStack[36];
        armorInventory = new ItemStack[4];
        currentItem = 0;
        inventoryChanged = false;
        player = par1EntityPlayer;
    }

    /**
     * Returns the item stack currently held by the player.
     */
    public ItemStack getCurrentItem()
    {
        if (currentItem < 9 && currentItem >= 0)
        {
            return mainInventory[currentItem];
        }
        else
        {
            return null;
        }
    }

    public static int func_25054_e()
    {
        return 9;
    }

    /**
     * Returns a slot index in main inventory containing a specific itemID
     */
    private int getInventorySlotContainItem(int par1)
    {
        for (int i = 0; i < mainInventory.length; i++)
        {
            if (mainInventory[i] != null && mainInventory[i].itemID == par1)
            {
                return i;
            }
        }

        return -1;
    }

    /**
     * stores an itemstack in the users inventory
     */
    private int storeItemStack(ItemStack par1ItemStack)
    {
        for (int i = 0; i < mainInventory.length; i++)
        {
            if (mainInventory[i] != null && mainInventory[i].itemID == par1ItemStack.itemID && mainInventory[i].isStackable() && mainInventory[i].stackSize < mainInventory[i].getMaxStackSize() && mainInventory[i].stackSize < getInventoryStackLimit() && (!mainInventory[i].getHasSubtypes() || mainInventory[i].getItemDamage() == par1ItemStack.getItemDamage()) && ItemStack.func_46124_a(mainInventory[i], par1ItemStack))
            {
                return i;
            }
        }

        return -1;
    }

    /**
     * Returns the first item stack that is empty.
     */
    public int getFirstEmptyStack()
    {
        for (int i = 0; i < mainInventory.length; i++)
        {
            if (mainInventory[i] == null)
            {
                return i;
            }
        }

        return -1;
    }

    /**
     * This function stores as many items of an ItemStack as possible in a matching slot and returns the quantity of
     * left over items.
     */
    private int storePartialItemStack(ItemStack par1ItemStack)
    {
        int i = par1ItemStack.itemID;
        int j = par1ItemStack.stackSize;

        if (par1ItemStack.getMaxStackSize() == 1)
        {
            int k = getFirstEmptyStack();

            if (k < 0)
            {
                return j;
            }

            if (mainInventory[k] == null)
            {
                mainInventory[k] = ItemStack.copyItemStack(par1ItemStack);
            }

            return 0;
        }

        int l = storeItemStack(par1ItemStack);

        if (l < 0)
        {
            l = getFirstEmptyStack();
        }

        if (l < 0)
        {
            return j;
        }

        if (mainInventory[l] == null)
        {
            mainInventory[l] = new ItemStack(i, 0, par1ItemStack.getItemDamage());

            if (par1ItemStack.hasTagCompound())
            {
                mainInventory[l].setTagCompound((NBTTagCompound)par1ItemStack.getTagCompound().copy());
            }
        }

        int i1 = j;

        if (i1 > mainInventory[l].getMaxStackSize() - mainInventory[l].stackSize)
        {
            i1 = mainInventory[l].getMaxStackSize() - mainInventory[l].stackSize;
        }

        if (i1 > getInventoryStackLimit() - mainInventory[l].stackSize)
        {
            i1 = getInventoryStackLimit() - mainInventory[l].stackSize;
        }

        if (i1 == 0)
        {
            return j;
        }
        else
        {
            j -= i1;
            mainInventory[l].stackSize += i1;
            mainInventory[l].animationsToGo = 5;
            return j;
        }
    }

    /**
     * Decrement the number of animations remaining. Only called on client side. This is used to handle the animation of
     * receiving a block.
     */
    public void decrementAnimations()
    {
        for (int i = 0; i < mainInventory.length; i++)
        {
            if (mainInventory[i] != null)
            {
                mainInventory[i].updateAnimation(player.worldObj, player, i, currentItem == i);
            }
        }
    }

    /**
     * removed one item of specified itemID from inventory (if it is in a stack, the stack size will reduce with 1)
     */
    public boolean consumeInventoryItem(int par1)
    {
        int i = getInventorySlotContainItem(par1);

        if (i < 0)
        {
            return false;
        }

        if (--mainInventory[i].stackSize <= 0)
        {
            mainInventory[i] = null;
        }

        return true;
    }

    /**
     * Get if a specifiied item id is inside the inventory.
     */
    public boolean hasItem(int par1)
    {
        int i = getInventorySlotContainItem(par1);
        return i >= 0;
    }

    /**
     * Adds the item stack to the inventory, returns false if it is impossible.
     */
    public boolean addItemStackToInventory(ItemStack par1ItemStack)
    {
        if (!par1ItemStack.isItemDamaged())
        {
            int i;

            do
            {
                i = par1ItemStack.stackSize;
                par1ItemStack.stackSize = storePartialItemStack(par1ItemStack);
            }
            while (par1ItemStack.stackSize > 0 && par1ItemStack.stackSize < i);

            if (par1ItemStack.stackSize == i && player.capabilities.isCreativeMode)
            {
                par1ItemStack.stackSize = 0;
                return true;
            }
            else
            {
                return par1ItemStack.stackSize < i;
            }
        }

        int j = getFirstEmptyStack();

        if (j >= 0)
        {
            mainInventory[j] = ItemStack.copyItemStack(par1ItemStack);
            mainInventory[j].animationsToGo = 5;
            par1ItemStack.stackSize = 0;
            return true;
        }

        if (player.capabilities.isCreativeMode)
        {
            par1ItemStack.stackSize = 0;
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        ItemStack aitemstack[] = mainInventory;

        if (par1 >= mainInventory.length)
        {
            aitemstack = armorInventory;
            par1 -= mainInventory.length;
        }

        if (aitemstack[par1] != null)
        {
            if (aitemstack[par1].stackSize <= par2)
            {
                ItemStack itemstack = aitemstack[par1];
                aitemstack[par1] = null;
                return itemstack;
            }

            ItemStack itemstack1 = aitemstack[par1].splitStack(par2);

            if (aitemstack[par1].stackSize == 0)
            {
                aitemstack[par1] = null;
            }

            return itemstack1;
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        ItemStack aitemstack[] = mainInventory;

        if (par1 >= mainInventory.length)
        {
            aitemstack = armorInventory;
            par1 -= mainInventory.length;
        }

        if (aitemstack[par1] != null)
        {
            ItemStack itemstack = aitemstack[par1];
            aitemstack[par1] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        ItemStack aitemstack[] = mainInventory;

        if (par1 >= aitemstack.length)
        {
            par1 -= aitemstack.length;
            aitemstack = armorInventory;
        }

        aitemstack[par1] = par2ItemStack;
    }

    /**
     * Gets the strength of the current item (tool) against the specified block, 1.0f if not holding anything.
     */
    public float getStrVsBlock(Block par1Block)
    {
        float f = 1.0F;

        if (mainInventory[currentItem] != null)
        {
            f *= mainInventory[currentItem].getStrVsBlock(par1Block);
        }

        return f;
    }

    /**
     * Writes the inventory out as a list of compound tags. This is where the slot indices are used (+100 for armor, +80
     * for crafting).
     */
    public NBTTagList writeToNBT(NBTTagList par1NBTTagList)
    {
        for (int i = 0; i < mainInventory.length; i++)
        {
            if (mainInventory[i] != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                mainInventory[i].writeToNBT(nbttagcompound);
                par1NBTTagList.appendTag(nbttagcompound);
            }
        }

        for (int j = 0; j < armorInventory.length; j++)
        {
            if (armorInventory[j] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)(j + 100));
                armorInventory[j].writeToNBT(nbttagcompound1);
                par1NBTTagList.appendTag(nbttagcompound1);
            }
        }

        return par1NBTTagList;
    }

    /**
     * Reads from the given tag list and fills the slots in the inventory with the correct items.
     */
    public void readFromNBT(NBTTagList par1NBTTagList)
    {
        mainInventory = new ItemStack[36];
        armorInventory = new ItemStack[4];

        for (int i = 0; i < par1NBTTagList.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound = (NBTTagCompound)par1NBTTagList.tagAt(i);
            int j = nbttagcompound.getByte("Slot") & 0xff;
            ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);

            if (itemstack == null)
            {
                continue;
            }

            if (j >= 0 && j < mainInventory.length)
            {
                mainInventory[j] = itemstack;
            }

            if (j >= 100 && j < armorInventory.length + 100)
            {
                armorInventory[j - 100] = itemstack;
            }
        }
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return mainInventory.length + 4;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        ItemStack aitemstack[] = mainInventory;

        if (par1 >= aitemstack.length)
        {
            par1 -= aitemstack.length;
            aitemstack = armorInventory;
        }

        return aitemstack[par1];
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return "container.inventory";
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Return damage vs an entity done by the current held weapon, or 1 if nothing is held
     */
    public int getDamageVsEntity(Entity par1Entity)
    {
        ItemStack itemstack = getStackInSlot(currentItem);

        if (itemstack != null)
        {
            return itemstack.getDamageVsEntity(par1Entity);
        }
        else
        {
            return 1;
        }
    }

    /**
     * Returns whether the current item (tool) can harvest from the specified block (actually get a result).
     */
    public boolean canHarvestBlock(Block par1Block)
    {
        if (par1Block.blockMaterial.isHarvestable())
        {
            return true;
        }

        ItemStack itemstack = getStackInSlot(currentItem);

        if (itemstack != null)
        {
            return itemstack.canHarvestBlock(par1Block);
        }
        else
        {
            return false;
        }
    }

    /**
     * Based on the damage values and maximum damage values of each armor item, returns the current armor value.
     */
    public int getTotalArmorValue()
    {
        int i = 0;
        ItemStack aitemstack[] = armorInventory;
        int j = aitemstack.length;

        for (int k = 0; k < j; k++)
        {
            ItemStack itemstack = aitemstack[k];

            if (itemstack != null && (itemstack.getItem() instanceof ItemArmor))
            {
                int l = ((ItemArmor)itemstack.getItem()).damageReduceAmount;
                i += l;
            }
        }

        return i;
    }

    /**
     * Damages armor in each slot by the specified amount.
     */
    public void damageArmor(int par1)
    {
        par1 /= 4;

        if (par1 < 1)
        {
            par1 = 1;
        }

        for (int i = 0; i < armorInventory.length; i++)
        {
            if (armorInventory[i] == null || !(armorInventory[i].getItem() instanceof ItemArmor))
            {
                continue;
            }

            armorInventory[i].damageItem(par1, player);

            if (armorInventory[i].stackSize == 0)
            {
                armorInventory[i] = null;
            }
        }
    }

    /**
     * Drop all armor and main inventory items.
     */
    public void dropAllItems()
    {
        for (int i = 0; i < mainInventory.length; i++)
        {
            if (mainInventory[i] != null)
            {
                player.dropPlayerItemWithRandomChoice(mainInventory[i], true);
                mainInventory[i] = null;
            }
        }

        for (int j = 0; j < armorInventory.length; j++)
        {
            if (armorInventory[j] != null)
            {
                player.dropPlayerItemWithRandomChoice(armorInventory[j], true);
                armorInventory[j] = null;
            }
        }
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void onInventoryChanged()
    {
        inventoryChanged = true;
    }

    public void setItemStack(ItemStack par1ItemStack)
    {
        itemStack = par1ItemStack;
    }

    public ItemStack getItemStack()
    {
        return itemStack;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        if (player.isDead)
        {
            return false;
        }

        return par1EntityPlayer.getDistanceSqToEntity(player) <= 64D;
    }

    /**
     * Returns true if the specified ItemStack exists in the inventory.
     */
    public boolean hasItemStack(ItemStack par1ItemStack)
    {
        ItemStack aitemstack[] = armorInventory;
        int i = aitemstack.length;

        for (int j = 0; j < i; j++)
        {
            ItemStack itemstack = aitemstack[j];

            if (itemstack != null && itemstack.isStackEqual(par1ItemStack))
            {
                return true;
            }
        }

        aitemstack = mainInventory;
        i = aitemstack.length;

        for (int k = 0; k < i; k++)
        {
            ItemStack itemstack1 = aitemstack[k];

            if (itemstack1 != null && itemstack1.isStackEqual(par1ItemStack))
            {
                return true;
            }
        }

        return false;
    }

    public void openChest()
    {
    }

    public void closeChest()
    {
    }

    /**
     * Copy the ItemStack contents from another InventoryPlayer instance
     */
    public void copyInventory(InventoryPlayer par1InventoryPlayer)
    {
        for (int i = 0; i < mainInventory.length; i++)
        {
            mainInventory[i] = ItemStack.copyItemStack(par1InventoryPlayer.mainInventory[i]);
        }

        for (int j = 0; j < armorInventory.length; j++)
        {
            armorInventory[j] = ItemStack.copyItemStack(par1InventoryPlayer.armorInventory[j]);
        }
    }
}
