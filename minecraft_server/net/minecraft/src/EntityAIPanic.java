package net.minecraft.src;

public class EntityAIPanic extends EntityAIBase
{
    private EntityCreature field_48208_a;
    private float speed;
    private double field_48207_c;
    private double field_48204_d;
    private double field_48205_e;

    public EntityAIPanic(EntityCreature par1EntityCreature, float par2)
    {
        field_48208_a = par1EntityCreature;
        speed = par2;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (field_48208_a.getAITarget() == null)
        {
            return false;
        }

        Vec3 vec3 = RandomPositionGenerator.func_48396_a(field_48208_a, 5, 4);

        if (vec3 == null)
        {
            return false;
        }
        else
        {
            field_48207_c = vec3.xCoord;
            field_48204_d = vec3.yCoord;
            field_48205_e = vec3.zCoord;
            return true;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        field_48208_a.getNavigator().tryMoveToXYZ(field_48207_c, field_48204_d, field_48205_e, speed);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !field_48208_a.getNavigator().noPath();
    }
}
