package net.minecraft.src;

import java.util.Random;

public class MathHelper
{
    private static float SIN_TABLE[];

    public MathHelper()
    {
    }

    /**
     * sin looked up in a table
     */
    public static final float sin(float par0)
    {
        return SIN_TABLE[(int)(par0 * 10430.38F) & 0xffff];
    }

    /**
     * cos looked up in the sin table with the appropriate offset
     */
    public static final float cos(float par0)
    {
        return SIN_TABLE[(int)(par0 * 10430.38F + 16384F) & 0xffff];
    }

    public static final float sqrt_float(float par0)
    {
        return (float)Math.sqrt(par0);
    }

    public static final float sqrt_double(double par0)
    {
        return (float)Math.sqrt(par0);
    }

    /**
     * Returns the greatest integer less than or equal to the float argument
     */
    public static int floor_float(float par0)
    {
        int i = (int)par0;
        return par0 >= (float)i ? i : i - 1;
    }

    /**
     * Returns the greatest integer less than or equal to the double argument
     */
    public static int floor_double(double par0)
    {
        int i = (int)par0;
        return par0 >= (double)i ? i : i - 1;
    }

    /**
     * Long version of floor_double
     */
    public static long floor_double_long(double par0)
    {
        long l = (long)par0;
        return par0 >= (double)l ? l : l - 1L;
    }

    public static float abs(float par0)
    {
        return par0 < 0.0F ? -par0 : par0;
    }

    /**
     * Returns the unsigned value of an int.
     */
    public static int abs(int par0)
    {
        return par0 < 0 ? -par0 : par0;
    }

    public static int func_55185_d(double par0)
    {
        int i = (int)par0;
        return par0 <= (double)i ? i : i + 1;
    }

    /**
     * Returns the value of the first parameter, clamped to be within the lower and upper limits given by the second and
     * third parameters.
     */
    public static int clamp_int(int par0, int par1, int par2)
    {
        if (par0 < par1)
        {
            return par1;
        }

        if (par0 > par2)
        {
            return par2;
        }
        else
        {
            return par0;
        }
    }

    /**
     * Maximum of the absolute value of two numbers.
     */
    public static double abs_max(double par0, double par2)
    {
        if (par0 < 0.0D)
        {
            par0 = -par0;
        }

        if (par2 < 0.0D)
        {
            par2 = -par2;
        }

        return par0 <= par2 ? par2 : par0;
    }

    public static int getRandomIntegerInRange(Random par0Random, int par1, int par2)
    {
        if (par1 >= par2)
        {
            return par1;
        }
        else
        {
            return par0Random.nextInt((par2 - par1) + 1) + par1;
        }
    }

    public static double func_56405_a(long par0ArrayOfLong[])
    {
        long l = 0L;
        long al[] = par0ArrayOfLong;
        int i = al.length;

        for (int j = 0; j < i; j++)
        {
            long l1 = al[j];
            l += l1;
        }

        return (double)l / (double)par0ArrayOfLong.length;
    }

    static
    {
        SIN_TABLE = new float[0x10000];

        for (int i = 0; i < 0x10000; i++)
        {
            SIN_TABLE[i] = (float)Math.sin(((double)i * Math.PI * 2D) / 65536D);
        }
    }
}
