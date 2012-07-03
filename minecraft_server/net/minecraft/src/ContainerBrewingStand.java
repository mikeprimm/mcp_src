package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class ContainerBrewingStand extends Container
{
    private TileEntityBrewingStand tileBrewingStand;
    private final Slot field_55144_b;
    private int brewTime;

    public ContainerBrewingStand(InventoryPlayer par1InventoryPlayer, TileEntityBrewingStand par2TileEntityBrewingStand)
    {
        brewTime = 0;
        tileBrewingStand = par2TileEntityBrewingStand;
        func_55143_a(new SlotBrewingStandPotion(par1InventoryPlayer.player, par2TileEntityBrewingStand, 0, 56, 46));
        func_55143_a(new SlotBrewingStandPotion(par1InventoryPlayer.player, par2TileEntityBrewingStand, 1, 79, 53));
        func_55143_a(new SlotBrewingStandPotion(par1InventoryPlayer.player, par2TileEntityBrewingStand, 2, 102, 46));
        field_55144_b = func_55143_a(new SlotBrewingStandIngredient(this, par2TileEntityBrewingStand, 3, 79, 17));

        for (int i = 0; i < 3; i++)
        {
            for (int k = 0; k < 9; k++)
            {
                func_55143_a(new Slot(par1InventoryPlayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }
        }

        for (int j = 0; j < 9; j++)
        {
            func_55143_a(new Slot(par1InventoryPlayer, j, 8 + j * 18, 142));
        }
    }

    public void onCraftGuiOpened(ICrafting par1ICrafting)
    {
        super.onCraftGuiOpened(par1ICrafting);
        par1ICrafting.updateCraftingInventoryInfo(this, 0, tileBrewingStand.getBrewTime());
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    public void updateCraftingResults()
    {
        super.updateCraftingResults();
        Iterator iterator = crafters.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            ICrafting icrafting = (ICrafting)iterator.next();

            if (brewTime != tileBrewingStand.getBrewTime())
            {
                icrafting.updateCraftingInventoryInfo(this, 0, tileBrewingStand.getBrewTime());
            }
        }
        while (true);

        brewTime = tileBrewingStand.getBrewTime();
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return tileBrewingStand.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    public ItemStack transferStackInSlot(int par1)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)inventorySlots.get(par1);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par1 >= 0 && par1 <= 2 || par1 == 3)
            {
                if (!mergeItemStack(itemstack1, 4, 40, true))
                {
                    return null;
                }

                slot.func_48417_a(itemstack1, itemstack);
            }
            else if (!field_55144_b.getHasStack() && field_55144_b.isItemValid(itemstack1))
            {
                if (!mergeItemStack(itemstack1, 3, 4, false))
                {
                    return null;
                }
            }
            else if (SlotBrewingStandPotion.func_55195_c(itemstack))
            {
                if (!mergeItemStack(itemstack1, 0, 3, false))
                {
                    return null;
                }
            }
            else if (par1 >= 4 && par1 < 31)
            {
                if (!mergeItemStack(itemstack1, 31, 40, false))
                {
                    return null;
                }
            }
            else if (par1 >= 31 && par1 < 40)
            {
                if (!mergeItemStack(itemstack1, 4, 31, false))
                {
                    return null;
                }
            }
            else if (!mergeItemStack(itemstack1, 4, 40, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(itemstack1);
        }

        return itemstack;
    }
}
