package net.minecraft.src;

import java.util.Random;

public class TileEntityEnderChest extends TileEntity
{
    public float field_56118_a;
    public float field_56116_b;
    public int field_56117_c;
    private int field_56115_d;

    public TileEntityEnderChest()
    {
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        super.updateEntity();

        if ((++field_56115_d % 20) * 4 == 0)
        {
            worldObj.playNoteAt(xCoord, yCoord, zCoord, 1, field_56117_c);
        }

        field_56116_b = field_56118_a;
        float f = 0.1F;

        if (field_56117_c > 0 && field_56118_a == 0.0F)
        {
            double d = (double)xCoord + 0.5D;
            double d1 = (double)zCoord + 0.5D;
            worldObj.playSoundEffect(d, (double)yCoord + 0.5D, d1, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (field_56117_c == 0 && field_56118_a > 0.0F || field_56117_c > 0 && field_56118_a < 1.0F)
        {
            float f1 = field_56118_a;

            if (field_56117_c > 0)
            {
                field_56118_a += f;
            }
            else
            {
                field_56118_a -= f;
            }

            if (field_56118_a > 1.0F)
            {
                field_56118_a = 1.0F;
            }

            float f2 = 0.5F;

            if (field_56118_a < f2 && f1 >= f2)
            {
                double d2 = (double)xCoord + 0.5D;
                double d3 = (double)zCoord + 0.5D;
                worldObj.playSoundEffect(d2, (double)yCoord + 0.5D, d3, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (field_56118_a < 0.0F)
            {
                field_56118_a = 0.0F;
            }
        }
    }

    public void onTileEntityPowered(int par1, int par2)
    {
        if (par1 == 1)
        {
            field_56117_c = par2;
        }
    }

    /**
     * invalidates a tile entity
     */
    public void invalidate()
    {
        updateContainingBlockInfo();
        super.invalidate();
    }

    public void func_56114_a()
    {
        field_56117_c++;
        worldObj.playNoteAt(xCoord, yCoord, zCoord, 1, field_56117_c);
    }

    public void func_56112_d()
    {
        field_56117_c--;
        worldObj.playNoteAt(xCoord, yCoord, zCoord, 1, field_56117_c);
    }

    public boolean func_56113_a(EntityPlayer par1EntityPlayer)
    {
        if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
        {
            return false;
        }

        return par1EntityPlayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
    }
}
