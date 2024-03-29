package net.minecraft.src;

public class ItemBucketMilk extends Item
{
    public ItemBucketMilk(int par1)
    {
        super(par1);
        setMaxStackSize(1);
        func_56455_a(CreativeTabs.field_56383_f);
    }

    public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            par1ItemStack.stackSize--;
        }

        if (!par2World.isRemote)
        {
            par3EntityPlayer.clearActivePotions();
        }

        if (par1ItemStack.stackSize <= 0)
        {
            return new ItemStack(Item.bucketEmpty);
        }
        else
        {
            return par1ItemStack;
        }
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 32;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.drink;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
}
