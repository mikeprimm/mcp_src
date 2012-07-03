package net.minecraft.src;

public class EntityAIMoveTowardsTarget extends EntityAIBase
{
    private EntityCreature theEntity;
    private EntityLiving targetEntity;
    private double movePosX;
    private double movePosY;
    private double movePosZ;
    private float field_48217_f;
    private float field_48218_g;

    public EntityAIMoveTowardsTarget(EntityCreature par1EntityCreature, float par2, float par3)
    {
        theEntity = par1EntityCreature;
        field_48217_f = par2;
        field_48218_g = par3;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        targetEntity = theEntity.getAttackTarget();

        if (targetEntity == null)
        {
            return false;
        }

        if (targetEntity.getDistanceSqToEntity(theEntity) > (double)(field_48218_g * field_48218_g))
        {
            return false;
        }

        Vec3 vec3 = RandomPositionGenerator.func_48395_a(theEntity, 16, 7, Vec3.func_58052_a().func_58076_a(targetEntity.posX, targetEntity.posY, targetEntity.posZ));

        if (vec3 == null)
        {
            return false;
        }
        else
        {
            movePosX = vec3.xCoord;
            movePosY = vec3.yCoord;
            movePosZ = vec3.zCoord;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !theEntity.getNavigator().noPath() && targetEntity.isEntityAlive() && targetEntity.getDistanceSqToEntity(theEntity) < (double)(field_48218_g * field_48218_g);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        targetEntity = null;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        theEntity.getNavigator().tryMoveToXYZ(movePosX, movePosY, movePosZ, field_48217_f);
    }
}
