package net.minecraft.src;

import java.util.Random;

public class RandomPositionGenerator
{
    private static Vec3 field_48397_a = Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);

    public RandomPositionGenerator()
    {
    }

    public static Vec3 func_48396_a(EntityCreature par0EntityCreature, int par1, int par2)
    {
        return func_48393_c(par0EntityCreature, par1, par2, null);
    }

    public static Vec3 func_48395_a(EntityCreature par0EntityCreature, int par1, int par2, Vec3 par3Vec3)
    {
        field_48397_a.xCoord = par3Vec3.xCoord - par0EntityCreature.posX;
        field_48397_a.yCoord = par3Vec3.yCoord - par0EntityCreature.posY;
        field_48397_a.zCoord = par3Vec3.zCoord - par0EntityCreature.posZ;
        return func_48393_c(par0EntityCreature, par1, par2, field_48397_a);
    }

    public static Vec3 func_48394_b(EntityCreature par0EntityCreature, int par1, int par2, Vec3 par3Vec3)
    {
        field_48397_a.xCoord = par0EntityCreature.posX - par3Vec3.xCoord;
        field_48397_a.yCoord = par0EntityCreature.posY - par3Vec3.yCoord;
        field_48397_a.zCoord = par0EntityCreature.posZ - par3Vec3.zCoord;
        return func_48393_c(par0EntityCreature, par1, par2, field_48397_a);
    }

    private static Vec3 func_48393_c(EntityCreature par0EntityCreature, int par1, int par2, Vec3 par3Vec3)
    {
        Random random = par0EntityCreature.getRNG();
        boolean flag = false;
        int i = 0;
        int j = 0;
        int k = 0;
        float f = -99999F;
        boolean flag1;

        if (par0EntityCreature.hasHome())
        {
            double d = par0EntityCreature.getHomePosition().getEuclideanDistanceTo(MathHelper.floor_double(par0EntityCreature.posX), MathHelper.floor_double(par0EntityCreature.posY), MathHelper.floor_double(par0EntityCreature.posZ)) + 4D;
            flag1 = d < (double)(par0EntityCreature.getMaximumHomeDistance() + (float)par1);
        }
        else
        {
            flag1 = false;
        }

        for (int l = 0; l < 10; l++)
        {
            int i1 = random.nextInt(2 * par1) - par1;
            int j1 = random.nextInt(2 * par2) - par2;
            int k1 = random.nextInt(2 * par1) - par1;

            if (par3Vec3 != null && (double)i1 * par3Vec3.xCoord + (double)k1 * par3Vec3.zCoord < 0.0D)
            {
                continue;
            }

            i1 += MathHelper.floor_double(par0EntityCreature.posX);
            j1 += MathHelper.floor_double(par0EntityCreature.posY);
            k1 += MathHelper.floor_double(par0EntityCreature.posZ);

            if (flag1 && !par0EntityCreature.isWithinHomeDistance(i1, j1, k1))
            {
                continue;
            }

            float f1 = par0EntityCreature.getBlockPathWeight(i1, j1, k1);

            if (f1 > f)
            {
                f = f1;
                i = i1;
                j = j1;
                k = k1;
                flag = true;
            }
        }

        if (flag)
        {
            return Vec3.func_58052_a().func_58076_a(i, j, k);
        }
        else
        {
            return null;
        }
    }
}
