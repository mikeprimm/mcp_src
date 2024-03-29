package net.minecraft.src;

import java.util.Random;

public class EntitySquid extends EntityWaterMob
{
    public float field_21063_a;
    public float field_21062_b;
    public float field_21061_c;
    public float field_21059_f;
    public float field_21060_ak;
    public float field_21058_al;

    /** angle of the tentacles in radians */
    public float tentacleAngle;

    /** the last calculated angle of the tentacles in radians */
    public float lastTentacleAngle;
    private float randomMotionSpeed;
    private float field_21054_ap;
    private float field_21053_aq;
    private float randomMotionVecX;
    private float randomMotionVecY;
    private float randomMotionVecZ;

    public EntitySquid(World par1World)
    {
        super(par1World);
        field_21063_a = 0.0F;
        field_21062_b = 0.0F;
        field_21061_c = 0.0F;
        field_21059_f = 0.0F;
        field_21060_ak = 0.0F;
        field_21058_al = 0.0F;
        tentacleAngle = 0.0F;
        lastTentacleAngle = 0.0F;
        randomMotionSpeed = 0.0F;
        field_21054_ap = 0.0F;
        field_21053_aq = 0.0F;
        randomMotionVecX = 0.0F;
        randomMotionVecY = 0.0F;
        randomMotionVecZ = 0.0F;
        texture = "/mob/squid.png";
        setSize(0.95F, 0.95F);
        field_21054_ap = (1.0F / (rand.nextFloat() + 1.0F)) * 0.2F;
    }

    public int getMaxHealth()
    {
        return 10;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return null;
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return null;
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return null;
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 0.4F;
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return 0;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean par1, int par2)
    {
        int i = rand.nextInt(3 + par2) + 1;

        for (int j = 0; j < i; j++)
        {
            entityDropItem(new ItemStack(Item.dyePowder, 1, 0), 0.0F);
        }
    }

    /**
     * Checks if this entity is inside water (if inWater field is true as a result of handleWaterMovement() returning
     * true)
     */
    public boolean isInWater()
    {
        return worldObj.handleMaterialAcceleration(boundingBox.expand(0.0D, -0.60000002384185791D, 0.0D), Material.water, this);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        field_21062_b = field_21063_a;
        field_21059_f = field_21061_c;
        field_21058_al = field_21060_ak;
        lastTentacleAngle = tentacleAngle;
        field_21060_ak += field_21054_ap;

        if (field_21060_ak > ((float)Math.PI * 2F))
        {
            field_21060_ak -= ((float)Math.PI * 2F);

            if (rand.nextInt(10) == 0)
            {
                field_21054_ap = (1.0F / (rand.nextFloat() + 1.0F)) * 0.2F;
            }
        }

        if (isInWater())
        {
            if (field_21060_ak < (float)Math.PI)
            {
                float f = field_21060_ak / (float)Math.PI;
                tentacleAngle = MathHelper.sin(f * f * (float)Math.PI) * (float)Math.PI * 0.25F;

                if ((double)f > 0.75D)
                {
                    randomMotionSpeed = 1.0F;
                    field_21053_aq = 1.0F;
                }
                else
                {
                    field_21053_aq *= 0.8F;
                }
            }
            else
            {
                tentacleAngle = 0.0F;
                randomMotionSpeed *= 0.9F;
                field_21053_aq *= 0.99F;
            }

            if (!worldObj.isRemote)
            {
                motionX = randomMotionVecX * randomMotionSpeed;
                motionY = randomMotionVecY * randomMotionSpeed;
                motionZ = randomMotionVecZ * randomMotionSpeed;
            }

            float f1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            renderYawOffset += ((-(float)Math.atan2(motionX, motionZ) * 180F) / (float)Math.PI - renderYawOffset) * 0.1F;
            rotationYaw = renderYawOffset;
            field_21061_c += (float)Math.PI * field_21053_aq * 1.5F;
            field_21063_a += ((-(float)Math.atan2(f1, motionY) * 180F) / (float)Math.PI - field_21063_a) * 0.1F;
        }
        else
        {
            tentacleAngle = MathHelper.abs(MathHelper.sin(field_21060_ak)) * (float)Math.PI * 0.25F;

            if (!worldObj.isRemote)
            {
                motionX = 0.0D;
                motionY -= 0.080000000000000002D;
                motionY *= 0.98000001907348633D;
                motionZ = 0.0D;
            }

            field_21063_a += (double)(-90F - field_21063_a) * 0.02D;
        }
    }

    /**
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
    public void moveEntityWithHeading(float par1, float par2)
    {
        moveEntity(motionX, motionY, motionZ);
    }

    protected void updateEntityActionState()
    {
        entityAge++;

        if (entityAge > 100)
        {
            randomMotionVecX = randomMotionVecY = randomMotionVecZ = 0.0F;
        }
        else if (rand.nextInt(50) == 0 || !inWater || randomMotionVecX == 0.0F && randomMotionVecY == 0.0F && randomMotionVecZ == 0.0F)
        {
            float f = rand.nextFloat() * (float)Math.PI * 2.0F;
            randomMotionVecX = MathHelper.cos(f) * 0.2F;
            randomMotionVecY = -0.1F + rand.nextFloat() * 0.2F;
            randomMotionVecZ = MathHelper.sin(f) * 0.2F;
        }

        despawnEntity();
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        return posY > 45D && posY < 63D && super.getCanSpawnHere();
    }
}
