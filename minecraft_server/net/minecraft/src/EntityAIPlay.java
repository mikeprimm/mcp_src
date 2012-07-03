package net.minecraft.src;

import java.util.*;

public class EntityAIPlay extends EntityAIBase
{
    private EntityVillager villagerObj;
    private EntityLiving targetVillager;
    private float field_48166_c;
    private int field_48164_d;

    public EntityAIPlay(EntityVillager par1EntityVillager, float par2)
    {
        villagerObj = par1EntityVillager;
        field_48166_c = par2;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (villagerObj.getGrowingAge() >= 0)
        {
            return false;
        }

        if (villagerObj.getRNG().nextInt(400) != 0)
        {
            return false;
        }

        List list = villagerObj.worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityVillager.class, villagerObj.boundingBox.expand(6D, 3D, 6D));
        double d = Double.MAX_VALUE;
        Iterator iterator = list.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityVillager entityvillager = (EntityVillager)iterator.next();

            if (entityvillager != villagerObj && !entityvillager.getIsPlayingFlag() && entityvillager.getGrowingAge() < 0)
            {
                double d1 = entityvillager.getDistanceSqToEntity(villagerObj);

                if (d1 <= d)
                {
                    d = d1;
                    targetVillager = entityvillager;
                }
            }
        }
        while (true);

        if (targetVillager == null)
        {
            Vec3 vec3 = RandomPositionGenerator.func_48396_a(villagerObj, 16, 3);

            if (vec3 == null)
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return field_48164_d > 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        if (targetVillager != null)
        {
            villagerObj.setIsPlayingFlag(true);
        }

        field_48164_d = 1000;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        villagerObj.setIsPlayingFlag(false);
        targetVillager = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        field_48164_d--;

        if (targetVillager != null)
        {
            if (villagerObj.getDistanceSqToEntity(targetVillager) > 4D)
            {
                villagerObj.getNavigator().func_48652_a(targetVillager, field_48166_c);
            }
        }
        else if (villagerObj.getNavigator().noPath())
        {
            Vec3 vec3 = RandomPositionGenerator.func_48396_a(villagerObj, 16, 3);

            if (vec3 == null)
            {
                return;
            }

            villagerObj.getNavigator().tryMoveToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord, field_48166_c);
        }
    }
}
