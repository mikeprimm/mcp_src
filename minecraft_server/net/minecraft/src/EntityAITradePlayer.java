package net.minecraft.src;

public class EntityAITradePlayer extends EntityAIBase
{
    private EntityVillager field_56404_a;

    public EntityAITradePlayer(EntityVillager par1EntityVillager)
    {
        field_56404_a = par1EntityVillager;
        setMutexBits(5);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (field_56404_a.isInWater())
        {
            return false;
        }

        if (!field_56404_a.onGround)
        {
            return false;
        }

        if (field_56404_a.velocityChanged)
        {
            return false;
        }

        EntityPlayer entityplayer = field_56404_a.func_56132_z();

        if (entityplayer == null)
        {
            return false;
        }

        if (field_56404_a.getDistanceSqToEntity(entityplayer) > 16D)
        {
            return false;
        }

        return entityplayer.craftingInventory instanceof Container;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        field_56404_a.getNavigator().clearPathEntity();
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        field_56404_a.func_56129_c(null);
    }
}
