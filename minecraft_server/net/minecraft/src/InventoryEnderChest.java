package net.minecraft.src;

public class InventoryEnderChest extends InventoryBasic
{
    private TileEntityEnderChest field_56122_a;

    public InventoryEnderChest()
    {
        super("container.enderchest", 27);
    }

    public void func_56121_a(TileEntityEnderChest par1TileEntityEnderChest)
    {
        field_56122_a = par1TileEntityEnderChest;
    }

    public void func_56119_a(NBTTagList par1NBTTagList)
    {
        for (int i = 0; i < getSizeInventory(); i++)
        {
            setInventorySlotContents(i, null);
        }

        for (int j = 0; j < par1NBTTagList.tagCount(); j++)
        {
            NBTTagCompound nbttagcompound = (NBTTagCompound)par1NBTTagList.tagAt(j);
            int k = nbttagcompound.getByte("Slot") & 0xff;

            if (k >= 0 && k < getSizeInventory())
            {
                setInventorySlotContents(k, ItemStack.loadItemStackFromNBT(nbttagcompound));
            }
        }
    }

    public NBTTagList func_56120_g()
    {
        NBTTagList nbttaglist = new NBTTagList("EnderItems");

        for (int i = 0; i < getSizeInventory(); i++)
        {
            ItemStack itemstack = getStackInSlot(i);

            if (itemstack != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                itemstack.writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        return nbttaglist;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        if (field_56122_a != null && !field_56122_a.func_56113_a(par1EntityPlayer))
        {
            return false;
        }
        else
        {
            return super.isUseableByPlayer(par1EntityPlayer);
        }
    }

    public void openChest()
    {
        if (field_56122_a != null)
        {
            field_56122_a.func_56114_a();
        }

        super.openChest();
    }

    public void closeChest()
    {
        if (field_56122_a != null)
        {
            field_56122_a.func_56112_d();
        }

        super.closeChest();
        field_56122_a = null;
    }
}
