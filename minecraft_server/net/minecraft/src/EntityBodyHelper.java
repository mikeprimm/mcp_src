package net.minecraft.src;

public class EntityBodyHelper
{
    private EntityLiving field_48435_a;
    private int field_48433_b;
    private float field_48434_c;

    public EntityBodyHelper(EntityLiving par1EntityLiving)
    {
        field_48433_b = 0;
        field_48434_c = 0.0F;
        field_48435_a = par1EntityLiving;
    }

    public void func_48431_a()
    {
        double d = field_48435_a.posX - field_48435_a.prevPosX;
        double d1 = field_48435_a.posZ - field_48435_a.prevPosZ;

        if (d * d + d1 * d1 > 2.5000002779052011E-007D)
        {
            field_48435_a.renderYawOffset = field_48435_a.rotationYaw;
            field_48435_a.rotationYawHead = func_48432_a(field_48435_a.renderYawOffset, field_48435_a.rotationYawHead, 75F);
            field_48434_c = field_48435_a.rotationYawHead;
            field_48433_b = 0;
            return;
        }

        float f = 75F;

        if (Math.abs(field_48435_a.rotationYawHead - field_48434_c) > 15F)
        {
            field_48433_b = 0;
            field_48434_c = field_48435_a.rotationYawHead;
        }
        else
        {
            field_48433_b++;

            if (field_48433_b > 10)
            {
                f = Math.max(1.0F - (float)(field_48433_b - 10) / 10F, 0.0F) * 75F;
            }
        }

        field_48435_a.renderYawOffset = func_48432_a(field_48435_a.rotationYawHead, field_48435_a.renderYawOffset, f);
    }

    private float func_48432_a(float par1, float par2, float par3)
    {
        float f;

        for (f = par1 - par2; f < -180F; f += 360F) { }

        for (; f >= 180F; f -= 360F) { }

        if (f < -par3)
        {
            f = -par3;
        }

        if (f >= par3)
        {
            f = par3;
        }

        return par1 - f;
    }
}
