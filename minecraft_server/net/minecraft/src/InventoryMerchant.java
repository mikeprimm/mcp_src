package net.minecraft.src;

public class InventoryMerchant implements IInventory
{
    private final IMerchant field_56107_a;
    private ItemStack field_56105_b[];
    private final EntityPlayer field_56106_c;
    private MerchantRecipe field_56103_d;
    private int field_56104_e;

    public InventoryMerchant(EntityPlayer par1EntityPlayer, IMerchant par2IMerchant)
    {
        field_56105_b = new ItemStack[3];
        field_56106_c = par1EntityPlayer;
        field_56107_a = par2IMerchant;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return field_56105_b.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return field_56105_b[par1];
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (field_56105_b[par1] != null)
        {
            if (par1 == 2)
            {
                ItemStack itemstack = field_56105_b[par1];
                field_56105_b[par1] = null;
                return itemstack;
            }

            if (field_56105_b[par1].stackSize <= par2)
            {
                ItemStack itemstack1 = field_56105_b[par1];
                field_56105_b[par1] = null;

                if (func_56099_d(par1))
                {
                    func_56100_g();
                }

                return itemstack1;
            }

            ItemStack itemstack2 = field_56105_b[par1].splitStack(par2);

            if (field_56105_b[par1].stackSize == 0)
            {
                field_56105_b[par1] = null;
            }

            if (func_56099_d(par1))
            {
                func_56100_g();
            }

            return itemstack2;
        }
        else
        {
            return null;
        }
    }

    private boolean func_56099_d(int par1)
    {
        return par1 == 0 || par1 == 1;
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (field_56105_b[par1] != null)
        {
            ItemStack itemstack = field_56105_b[par1];
            field_56105_b[par1] = null;
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
        field_56105_b[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit())
        {
            par2ItemStack.stackSize = getInventoryStackLimit();
        }

        if (func_56099_d(par1))
        {
            func_56100_g();
        }
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return "mob.villager";
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
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return field_56107_a.func_56132_z() == par1EntityPlayer;
    }

    public void openChest()
    {
    }

    public void closeChest()
    {
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void onInventoryChanged()
    {
        func_56100_g();
    }

    public void func_56100_g()
    {
        field_56103_d = null;
        ItemStack itemstack = field_56105_b[0];
        ItemStack itemstack1 = field_56105_b[1];

        if (itemstack == null)
        {
            itemstack = itemstack1;
            itemstack1 = null;
        }

        if (itemstack == null)
        {
            setInventorySlotContents(2, null);
        }
        else
        {
            MerchantRecipeList merchantrecipelist = field_56107_a.func_56131_d(field_56106_c);

            if (merchantrecipelist != null)
            {
                MerchantRecipe merchantrecipe = merchantrecipelist.func_56414_a(itemstack, itemstack1, field_56104_e);

                if (merchantrecipe != null)
                {
                    field_56103_d = merchantrecipe;
                    setInventorySlotContents(2, merchantrecipe.func_56732_d().copy());
                }
                else if (itemstack1 != null)
                {
                    MerchantRecipe merchantrecipe1 = merchantrecipelist.func_56414_a(itemstack1, itemstack, field_56104_e);

                    if (merchantrecipe1 != null)
                    {
                        field_56103_d = merchantrecipe1;
                        setInventorySlotContents(2, merchantrecipe1.func_56732_d().copy());
                    }
                    else
                    {
                        setInventorySlotContents(2, null);
                    }
                }
                else
                {
                    setInventorySlotContents(2, null);
                }
            }
        }
    }

    public MerchantRecipe func_56102_h()
    {
        return field_56103_d;
    }

    public void func_56101_c(int par1)
    {
        field_56104_e = par1;
        func_56100_g();
    }
}
