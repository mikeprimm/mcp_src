package net.minecraft.src;

import java.util.*;

public class TileEntityMobSpawner extends TileEntity
{
    /** The stored delay before a new spawn. */
    public int delay;

    /**
     * The string ID of the mobs being spawned from this spawner. Defaults to pig, apparently.
     */
    private String mobID;
    private NBTTagCompound field_58010_e;
    public double yaw;
    public double yaw2;
    private int field_58008_f;
    private int field_58009_g;
    private int field_58011_h;

    public TileEntityMobSpawner()
    {
        delay = -1;
        field_58010_e = null;
        yaw2 = 0.0D;
        field_58008_f = 200;
        field_58009_g = 800;
        field_58011_h = 4;
        mobID = "Pig";
        delay = 20;
    }

    public void setMobID(String par1Str)
    {
        mobID = par1Str;
    }

    /**
     * Returns true if there is a player in range (using World.getClosestPlayer)
     */
    public boolean anyPlayerInRange()
    {
        return worldObj.getClosestPlayer((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D, 16D) != null;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        if (!anyPlayerInRange())
        {
            return;
        }

        if (worldObj.isRemote)
        {
            double d = (float)xCoord + worldObj.rand.nextFloat();
            double d1 = (float)yCoord + worldObj.rand.nextFloat();
            double d3 = (float)zCoord + worldObj.rand.nextFloat();
            worldObj.spawnParticle("smoke", d, d1, d3, 0.0D, 0.0D, 0.0D);
            worldObj.spawnParticle("flame", d, d1, d3, 0.0D, 0.0D, 0.0D);
            yaw2 = yaw;

            for (yaw += 1000F / ((float)delay + 200F); yaw > 360D;)
            {
                yaw -= 360D;
                yaw2 -= 360D;
            }
        }
        else
        {
            if (delay == -1)
            {
                updateDelay();
            }

            if (delay > 0)
            {
                delay--;
                return;
            }

            for (int i = 0; i < field_58011_h; i++)
            {
                EntityLiving entityliving = (EntityLiving)EntityList.createEntityByName(mobID, worldObj);

                if (entityliving == null)
                {
                    return;
                }

                int j = worldObj.getEntitiesWithinAABB(entityliving.getClass(), AxisAlignedBB.func_58089_a().func_58067_a(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).expand(8D, 4D, 8D)).size();

                if (j >= 6)
                {
                    updateDelay();
                    return;
                }

                if (entityliving == null)
                {
                    continue;
                }

                double d2 = (double)xCoord + (worldObj.rand.nextDouble() - worldObj.rand.nextDouble()) * 4D;
                double d4 = (yCoord + worldObj.rand.nextInt(3)) - 1;
                double d5 = (double)zCoord + (worldObj.rand.nextDouble() - worldObj.rand.nextDouble()) * 4D;
                entityliving.setLocationAndAngles(d2, d4, d5, worldObj.rand.nextFloat() * 360F, 0.0F);

                if (!entityliving.getCanSpawnHere())
                {
                    continue;
                }

                worldObj.spawnEntityInWorld(entityliving);
                worldObj.playAuxSFX(2004, xCoord, yCoord, zCoord, 0);

                if (field_58010_e != null)
                {
                    NBTTagCompound nbttagcompound = new NBTTagCompound();
                    entityliving.addEntityID(nbttagcompound);
                    NBTBase nbtbase;

                    for (Iterator iterator = field_58010_e.getTags().iterator(); iterator.hasNext(); nbttagcompound.setTag(nbtbase.getName(), nbtbase.copy()))
                    {
                        nbtbase = (NBTBase)iterator.next();
                    }

                    entityliving.readFromNBT(nbttagcompound);
                }

                entityliving.spawnExplosionParticle();
                updateDelay();
            }
        }

        super.updateEntity();
    }

    /**
     * Sets the delay before a new spawn (base delay of 200 + random number up to 600).
     */
    private void updateDelay()
    {
        delay = field_58008_f + worldObj.rand.nextInt(field_58009_g - field_58008_f);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        mobID = par1NBTTagCompound.getString("EntityId");
        delay = par1NBTTagCompound.getShort("Delay");

        if (par1NBTTagCompound.hasKey("SpawnData"))
        {
            field_58010_e = par1NBTTagCompound.getCompoundTag("SpawnData");
        }
        else
        {
            field_58010_e = null;
        }

        if (par1NBTTagCompound.hasKey("MinSpawnDelay"))
        {
            field_58008_f = par1NBTTagCompound.getShort("MinSpawnDelay");
            field_58009_g = par1NBTTagCompound.getShort("MaxSpawnDelay");
            field_58011_h = par1NBTTagCompound.getShort("SpawnCount");
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setString("EntityId", mobID);
        par1NBTTagCompound.setShort("Delay", (short)delay);
        par1NBTTagCompound.setShort("MinSpawnDelay", (short)field_58008_f);
        par1NBTTagCompound.setShort("MaxSpawnDelay", (short)field_58009_g);
        par1NBTTagCompound.setShort("SpawnCount", (short)field_58011_h);

        if (field_58010_e != null)
        {
            par1NBTTagCompound.setCompoundTag("SpawnData", field_58010_e);
        }
    }

    /**
     * Overriden in a sign to provide the text
     */
    public Packet getDescriptionPacket()
    {
        int i = EntityList.getIDFromString(mobID);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, i);
    }
}
