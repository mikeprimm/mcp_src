package net.minecraft.src;

public class SlotMerchantResult extends Slot
{
    private final InventoryMerchant field_56522_a;
    private EntityPlayer field_56520_f;
    private int field_56521_g;
    private final IMerchant field_56523_h;

    public SlotMerchantResult(EntityPlayer par1EntityPlayer, IMerchant par2IMerchant, InventoryMerchant par3InventoryMerchant, int par4, int par5, int par6)
    {
        super(par3InventoryMerchant, par4, par5, par6);
        field_56520_f = par1EntityPlayer;
        field_56523_h = par2IMerchant;
        field_56522_a = par3InventoryMerchant;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return false;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack decrStackSize(int par1)
    {
        if (getHasStack())
        {
            field_56521_g += Math.min(par1, getStack().stackSize);
        }

        return super.decrStackSize(par1);
    }

    protected void func_48415_a(ItemStack par1ItemStack, int par2)
    {
        field_56521_g += par2;
        func_48416_b(par1ItemStack);
    }

    protected void func_48416_b(ItemStack par1ItemStack)
    {
        par1ItemStack.onCrafting(field_56520_f.worldObj, field_56520_f, field_56521_g);
        field_56521_g = 0;
    }

    /**
     * Called when the player picks up an item from an inventory slot
     */
    public void onPickupFromSlot(ItemStack par1ItemStack)
    {
        func_48416_b(par1ItemStack);
        MerchantRecipe merchantrecipe = field_56522_a.func_56102_h();

        if (merchantrecipe != null)
        {
            ItemStack itemstack = field_56522_a.getStackInSlot(0);
            ItemStack itemstack1 = field_56522_a.getStackInSlot(1);

            if (func_56519_a(merchantrecipe, itemstack, itemstack1) || func_56519_a(merchantrecipe, itemstack1, itemstack))
            {
                if (itemstack != null && itemstack.stackSize <= 0)
                {
                    itemstack = null;
                }

                if (itemstack1 != null && itemstack1.stackSize <= 0)
                {
                    itemstack1 = null;
                }

                field_56522_a.setInventorySlotContents(0, itemstack);
                field_56522_a.setInventorySlotContents(1, itemstack1);
                field_56523_h.func_56130_a(merchantrecipe);
            }
        }
    }

    private boolean func_56519_a(MerchantRecipe par1MerchantRecipe, ItemStack par2ItemStack, ItemStack par3ItemStack)
    {
        ItemStack itemstack = par1MerchantRecipe.func_56731_a();
        ItemStack itemstack1 = par1MerchantRecipe.func_56729_b();

        if (par2ItemStack != null && par2ItemStack.itemID == itemstack.itemID)
        {
            if (itemstack1 != null && par3ItemStack != null && itemstack1.itemID == par3ItemStack.itemID)
            {
                par2ItemStack.stackSize -= itemstack.stackSize;
                par3ItemStack.stackSize -= itemstack1.stackSize;
                return true;
            }

            if (itemstack1 == null && par3ItemStack == null)
            {
                par2ItemStack.stackSize -= itemstack.stackSize;
                return true;
            }
        }

        return false;
    }
}
