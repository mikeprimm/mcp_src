package net.minecraft.src;

public class EntityAILookAtTradePlayer extends EntityAIWatchClosest
{
    private final EntityVillager field_56403_b;

    public EntityAILookAtTradePlayer(EntityVillager par1EntityVillager)
    {
        super(par1EntityVillager, net.minecraft.src.EntityPlayer.class, 8F);
        field_56403_b = par1EntityVillager;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (field_56403_b.func_56133_E_())
        {
            closestEntity = field_56403_b.func_56132_z();
            return true;
        }
        else
        {
            return false;
        }
    }
}
