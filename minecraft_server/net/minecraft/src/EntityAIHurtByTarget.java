package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class EntityAIHurtByTarget extends EntityAITarget
{
    boolean field_48300_a;
    EntityLiving field_56402_b;

    public EntityAIHurtByTarget(EntityLiving par1EntityLiving, boolean par2)
    {
        super(par1EntityLiving, 16F, false);
        field_48300_a = par2;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return func_48284_a(taskOwner.getAITarget(), true);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return taskOwner.getAITarget() != null && taskOwner.getAITarget() != field_56402_b;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        taskOwner.setAttackTarget(taskOwner.getAITarget());
        field_56402_b = taskOwner.getAITarget();

        if (field_48300_a)
        {
            List list = taskOwner.worldObj.getEntitiesWithinAABB(taskOwner.getClass(), AxisAlignedBB.func_58089_a().func_58067_a(taskOwner.posX, taskOwner.posY, taskOwner.posZ, taskOwner.posX + 1.0D, taskOwner.posY + 1.0D, taskOwner.posZ + 1.0D).expand(targetDistance, 4D, targetDistance));
            Iterator iterator = list.iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                EntityLiving entityliving = (EntityLiving)iterator.next();

                if (taskOwner != entityliving && entityliving.getAttackTarget() == null)
                {
                    entityliving.setAttackTarget(taskOwner.getAITarget());
                }
            }
            while (true);
        }

        super.startExecuting();
    }
}
