package net.minecraft.src;

import java.util.*;

public class EntityAIFollowGolem extends EntityAIBase
{
    private EntityVillager theVillager;
    private EntityIronGolem theGolem;
    private int field_48215_c;
    private boolean field_48213_d;

    public EntityAIFollowGolem(EntityVillager par1EntityVillager)
    {
        field_48213_d = false;
        theVillager = par1EntityVillager;
        setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (theVillager.getGrowingAge() >= 0)
        {
            return false;
        }

        if (!theVillager.worldObj.isDaytime())
        {
            return false;
        }

        List list = theVillager.worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityIronGolem.class, theVillager.boundingBox.expand(6D, 2D, 6D));

        if (list.isEmpty())
        {
            return false;
        }

        Iterator iterator = list.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityIronGolem entityirongolem = (EntityIronGolem)iterator.next();

            if (entityirongolem.func_48382_m_() <= 0)
            {
                continue;
            }

            theGolem = entityirongolem;
            break;
        }
        while (true);

        return theGolem != null;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return theGolem.func_48382_m_() > 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        field_48215_c = theVillager.getRNG().nextInt(320);
        field_48213_d = false;
        theGolem.getNavigator().clearPathEntity();
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        theGolem = null;
        theVillager.getNavigator().clearPathEntity();
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        theVillager.getLookHelper().setLookPositionWithEntity(theGolem, 30F, 30F);

        if (theGolem.func_48382_m_() == field_48215_c)
        {
            theVillager.getNavigator().func_48652_a(theGolem, 0.15F);
            field_48213_d = true;
        }

        if (field_48213_d && theVillager.getDistanceSqToEntity(theGolem) < 4D)
        {
            theGolem.func_48383_a(false);
            theVillager.getNavigator().clearPathEntity();
        }
    }
}
