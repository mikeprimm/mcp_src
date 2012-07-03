package net.minecraft.src;

import java.util.*;

public class EntityArrow extends Entity
{
    private int xTile;
    private int yTile;
    private int zTile;
    private int inTile;
    private int inData;
    private boolean inGround;
    public int field_58012_a;

    /** Seems to be some sort of timer for animating an arrow. */
    public int arrowShake;

    /** The owner of this arrow. */
    public Entity shootingEntity;
    private int ticksInGround;
    private int ticksInAir;
    private double damage;
    private int field_46010_n;

    public EntityArrow(World par1World)
    {
        super(par1World);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        inData = 0;
        inGround = false;
        field_58012_a = 0;
        arrowShake = 0;
        ticksInAir = 0;
        damage = 2D;
        setSize(0.5F, 0.5F);
    }

    public EntityArrow(World par1World, double par2, double par4, double par6)
    {
        super(par1World);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        inData = 0;
        inGround = false;
        field_58012_a = 0;
        arrowShake = 0;
        ticksInAir = 0;
        damage = 2D;
        setSize(0.5F, 0.5F);
        setPosition(par2, par4, par6);
        yOffset = 0.0F;
    }

    public EntityArrow(World par1World, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving, float par4, float par5)
    {
        super(par1World);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        inData = 0;
        inGround = false;
        field_58012_a = 0;
        arrowShake = 0;
        ticksInAir = 0;
        damage = 2D;
        shootingEntity = par2EntityLiving;

        if (par2EntityLiving instanceof EntityPlayer)
        {
            field_58012_a = 1;
        }

        posY = (par2EntityLiving.posY + (double)par2EntityLiving.getEyeHeight()) - 0.10000000149011612D;
        double d = par3EntityLiving.posX - par2EntityLiving.posX;
        double d1 = (par3EntityLiving.posY + (double)par3EntityLiving.getEyeHeight()) - 0.69999998807907104D - posY;
        double d2 = par3EntityLiving.posZ - par2EntityLiving.posZ;
        double d3 = MathHelper.sqrt_double(d * d + d2 * d2);

        if (d3 < 9.9999999999999995E-008D)
        {
            return;
        }
        else
        {
            float f = (float)((Math.atan2(d2, d) * 180D) / Math.PI) - 90F;
            float f1 = (float)(-((Math.atan2(d1, d3) * 180D) / Math.PI));
            double d4 = d / d3;
            double d5 = d2 / d3;
            setLocationAndAngles(par2EntityLiving.posX + d4, posY, par2EntityLiving.posZ + d5, f, f1);
            yOffset = 0.0F;
            float f2 = (float)d3 * 0.2F;
            setArrowHeading(d, d1 + (double)f2, d2, par4, par5);
            return;
        }
    }

    public EntityArrow(World par1World, EntityLiving par2EntityLiving, float par3)
    {
        super(par1World);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        inData = 0;
        inGround = false;
        field_58012_a = 0;
        arrowShake = 0;
        ticksInAir = 0;
        damage = 2D;
        shootingEntity = par2EntityLiving;

        if (par2EntityLiving instanceof EntityPlayer)
        {
            field_58012_a = 1;
        }

        setSize(0.5F, 0.5F);
        setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + (double)par2EntityLiving.getEyeHeight(), par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
        posX -= MathHelper.cos((rotationYaw / 180F) * (float)Math.PI) * 0.16F;
        posY -= 0.10000000149011612D;
        posZ -= MathHelper.sin((rotationYaw / 180F) * (float)Math.PI) * 0.16F;
        setPosition(posX, posY, posZ);
        yOffset = 0.0F;
        motionX = -MathHelper.sin((rotationYaw / 180F) * (float)Math.PI) * MathHelper.cos((rotationPitch / 180F) * (float)Math.PI);
        motionZ = MathHelper.cos((rotationYaw / 180F) * (float)Math.PI) * MathHelper.cos((rotationPitch / 180F) * (float)Math.PI);
        motionY = -MathHelper.sin((rotationPitch / 180F) * (float)Math.PI);
        setArrowHeading(motionX, motionY, motionZ, par3 * 1.5F, 1.0F);
    }

    protected void entityInit()
    {
        dataWatcher.addObject(16, Byte.valueOf((byte)0));
    }

    /**
     * Uses the provided coordinates as a heading and determines the velocity from it with the set force and random
     * variance. Args: x, y, z, force, forceVariation
     */
    public void setArrowHeading(double par1, double par3, double par5, float par7, float par8)
    {
        float f = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= f;
        par3 /= f;
        par5 /= f;
        par1 += rand.nextGaussian() * 0.0074999998323619366D * (double)par8;
        par3 += rand.nextGaussian() * 0.0074999998323619366D * (double)par8;
        par5 += rand.nextGaussian() * 0.0074999998323619366D * (double)par8;
        par1 *= par7;
        par3 *= par7;
        par5 *= par7;
        motionX = par1;
        motionY = par3;
        motionZ = par5;
        float f1 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
        prevRotationYaw = rotationYaw = (float)((Math.atan2(par1, par5) * 180D) / Math.PI);
        prevRotationPitch = rotationPitch = (float)((Math.atan2(par3, f1) * 180D) / Math.PI);
        ticksInGround = 0;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            prevRotationYaw = rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / Math.PI);
            prevRotationPitch = rotationPitch = (float)((Math.atan2(motionY, f) * 180D) / Math.PI);
        }

        int i = worldObj.getBlockId(xTile, yTile, zTile);

        if (i > 0)
        {
            Block.blocksList[i].setBlockBoundsBasedOnState(worldObj, xTile, yTile, zTile);
            AxisAlignedBB axisalignedbb = Block.blocksList[i].getCollisionBoundingBoxFromPool(worldObj, xTile, yTile, zTile);

            if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3.func_58052_a().func_58076_a(posX, posY, posZ)))
            {
                inGround = true;
            }
        }

        if (arrowShake > 0)
        {
            arrowShake--;
        }

        if (inGround)
        {
            int j = worldObj.getBlockId(xTile, yTile, zTile);
            int k = worldObj.getBlockMetadata(xTile, yTile, zTile);

            if (j != inTile || k != inData)
            {
                inGround = false;
                motionX *= rand.nextFloat() * 0.2F;
                motionY *= rand.nextFloat() * 0.2F;
                motionZ *= rand.nextFloat() * 0.2F;
                ticksInGround = 0;
                ticksInAir = 0;
                return;
            }

            ticksInGround++;

            if (ticksInGround == 1200)
            {
                setDead();
            }

            return;
        }

        ticksInAir++;
        Vec3 vec3 = Vec3.func_58052_a().func_58076_a(posX, posY, posZ);
        Vec3 vec3_1 = Vec3.func_58052_a().func_58076_a(posX + motionX, posY + motionY, posZ + motionZ);
        MovingObjectPosition movingobjectposition = worldObj.rayTraceBlocks_do_do(vec3, vec3_1, false, true);
        vec3 = Vec3.func_58052_a().func_58076_a(posX, posY, posZ);
        vec3_1 = Vec3.func_58052_a().func_58076_a(posX + motionX, posY + motionY, posZ + motionZ);

        if (movingobjectposition != null)
        {
            vec3_1 = Vec3.func_58052_a().func_58076_a(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }

        Entity entity = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
        double d = 0.0D;
        Iterator iterator = list.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            Entity entity1 = (Entity)iterator.next();

            if (entity1.canBeCollidedWith() && (entity1 != shootingEntity || ticksInAir >= 5))
            {
                float f5 = 0.3F;
                AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand(f5, f5, f5);
                MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec3, vec3_1);

                if (movingobjectposition1 != null)
                {
                    double d1 = vec3.distanceTo(movingobjectposition1.hitVec);

                    if (d1 < d || d == 0.0D)
                    {
                        entity = entity1;
                        d = d1;
                    }
                }
            }
        }
        while (true);

        if (entity != null)
        {
            movingobjectposition = new MovingObjectPosition(entity);
        }

        if (movingobjectposition != null)
        {
            if (movingobjectposition.entityHit != null)
            {
                float f1 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
                int i1 = (int)Math.ceil((double)f1 * damage);

                if (func_56126_n())
                {
                    i1 += rand.nextInt(i1 / 2 + 2);
                }

                DamageSource damagesource = null;

                if (shootingEntity == null)
                {
                    damagesource = DamageSource.causeArrowDamage(this, this);
                }
                else
                {
                    damagesource = DamageSource.causeArrowDamage(this, shootingEntity);
                }

                if (isBurning())
                {
                    movingobjectposition.entityHit.setFire(5);
                }

                if (movingobjectposition.entityHit.attackEntityFrom(damagesource, i1))
                {
                    if (movingobjectposition.entityHit instanceof EntityLiving)
                    {
                        ((EntityLiving)movingobjectposition.entityHit).arrowHitTempCounter++;

                        if (field_46010_n > 0)
                        {
                            float f7 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);

                            if (f7 > 0.0F)
                            {
                                movingobjectposition.entityHit.addVelocity((motionX * (double)field_46010_n * 0.60000002384185791D) / (double)f7, 0.10000000000000001D, (motionZ * (double)field_46010_n * 0.60000002384185791D) / (double)f7);
                            }
                        }
                    }

                    worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
                    setDead();
                }
                else
                {
                    motionX *= -0.10000000149011612D;
                    motionY *= -0.10000000149011612D;
                    motionZ *= -0.10000000149011612D;
                    rotationYaw += 180F;
                    prevRotationYaw += 180F;
                    ticksInAir = 0;
                }
            }
            else
            {
                xTile = movingobjectposition.blockX;
                yTile = movingobjectposition.blockY;
                zTile = movingobjectposition.blockZ;
                inTile = worldObj.getBlockId(xTile, yTile, zTile);
                inData = worldObj.getBlockMetadata(xTile, yTile, zTile);
                motionX = (float)(movingobjectposition.hitVec.xCoord - posX);
                motionY = (float)(movingobjectposition.hitVec.yCoord - posY);
                motionZ = (float)(movingobjectposition.hitVec.zCoord - posZ);
                float f2 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
                posX -= (motionX / (double)f2) * 0.05000000074505806D;
                posY -= (motionY / (double)f2) * 0.05000000074505806D;
                posZ -= (motionZ / (double)f2) * 0.05000000074505806D;
                worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
                inGround = true;
                arrowShake = 7;
                func_56125_a(false);
            }
        }

        if (func_56126_n())
        {
            for (int l = 0; l < 4; l++)
            {
                worldObj.spawnParticle("crit", posX + (motionX * (double)l) / 4D, posY + (motionY * (double)l) / 4D, posZ + (motionZ * (double)l) / 4D, -motionX, -motionY + 0.20000000000000001D, -motionZ);
            }
        }

        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        float f3 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / Math.PI);

        for (rotationPitch = (float)((Math.atan2(motionY, f3) * 180D) / Math.PI); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }

        for (; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }

        for (; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) { }

        for (; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) { }

        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        float f4 = 0.99F;
        float f6 = 0.05F;

        if (isInWater())
        {
            for (int j1 = 0; j1 < 4; j1++)
            {
                float f8 = 0.25F;
                worldObj.spawnParticle("bubble", posX - motionX * (double)f8, posY - motionY * (double)f8, posZ - motionZ * (double)f8, motionX, motionY, motionZ);
            }

            f4 = 0.8F;
        }

        motionX *= f4;
        motionY *= f4;
        motionZ *= f4;
        motionY -= f6;
        setPosition(posX, posY, posZ);
        func_56098_S();
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setShort("xTile", (short)xTile);
        par1NBTTagCompound.setShort("yTile", (short)yTile);
        par1NBTTagCompound.setShort("zTile", (short)zTile);
        par1NBTTagCompound.setByte("inTile", (byte)inTile);
        par1NBTTagCompound.setByte("inData", (byte)inData);
        par1NBTTagCompound.setByte("shake", (byte)arrowShake);
        par1NBTTagCompound.setByte("inGround", (byte)(inGround ? 1 : 0));
        par1NBTTagCompound.setByte("pickup", (byte)field_58012_a);
        par1NBTTagCompound.setDouble("damage", damage);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        xTile = par1NBTTagCompound.getShort("xTile");
        yTile = par1NBTTagCompound.getShort("yTile");
        zTile = par1NBTTagCompound.getShort("zTile");
        inTile = par1NBTTagCompound.getByte("inTile") & 0xff;
        inData = par1NBTTagCompound.getByte("inData") & 0xff;
        arrowShake = par1NBTTagCompound.getByte("shake") & 0xff;
        inGround = par1NBTTagCompound.getByte("inGround") == 1;

        if (par1NBTTagCompound.hasKey("damage"))
        {
            damage = par1NBTTagCompound.getDouble("damage");
        }

        if (par1NBTTagCompound.hasKey("pickup"))
        {
            field_58012_a = par1NBTTagCompound.getByte("pickup");
        }
        else if (par1NBTTagCompound.hasKey("player"))
        {
            field_58012_a = par1NBTTagCompound.getBoolean("player") ? 1 : 0;
        }
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
    {
        if (worldObj.isRemote || !inGround || arrowShake > 0)
        {
            return;
        }

        boolean flag = field_58012_a == 1 || field_58012_a == 2 && par1EntityPlayer.capabilities.isCreativeMode;

        if (field_58012_a == 1 && !par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.arrow, 1)))
        {
            flag = false;
        }

        if (flag)
        {
            worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            par1EntityPlayer.onItemPickup(this, 1);
            setDead();
        }
    }

    public void setDamage(double par1)
    {
        damage = par1;
    }

    public double getDamage()
    {
        return damage;
    }

    public void func_46007_b(int par1)
    {
        field_46010_n = par1;
    }

    /**
     * If returns false, the item will not inflict any damage against entities.
     */
    public boolean canAttackWithItem()
    {
        return false;
    }

    public void func_56125_a(boolean par1)
    {
        byte byte0 = dataWatcher.getWatchableObjectByte(16);

        if (par1)
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 | 1)));
        }
        else
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 & -2)));
        }
    }

    public boolean func_56126_n()
    {
        byte byte0 = dataWatcher.getWatchableObjectByte(16);
        return (byte0 & 1) != 0;
    }
}
