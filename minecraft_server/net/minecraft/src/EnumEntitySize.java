package net.minecraft.src;

public enum EnumEntitySize
{
    SIZE_1,
    SIZE_2,
    SIZE_3,
    SIZE_4,
    SIZE_5,
    SIZE_6;

    public int func_55187_a(double par1)
    {
        double d = par1 - ((double)MathHelper.floor_double(par1) + 0.5D);

        switch (EnumEntitySizeHelper.field_55230_a[ordinal()])
        {
            case 1:
                if (d >= 0.0D ? d < 0.3125D : d < -0.3125D)
                {
                    return MathHelper.func_55185_d(par1 * 32D);
                }
                else
                {
                    return MathHelper.floor_double(par1 * 32D);
                }

            case 2:
                if (d >= 0.0D ? d < 0.3125D : d < -0.3125D)
                {
                    return MathHelper.floor_double(par1 * 32D);
                }
                else
                {
                    return MathHelper.func_55185_d(par1 * 32D);
                }

            case 3:
                if (d > 0.0D)
                {
                    return MathHelper.floor_double(par1 * 32D);
                }
                else
                {
                    return MathHelper.func_55185_d(par1 * 32D);
                }

            case 4:
                if (d >= 0.0D ? d < 0.1875D : d < -0.1875D)
                {
                    return MathHelper.func_55185_d(par1 * 32D);
                }
                else
                {
                    return MathHelper.floor_double(par1 * 32D);
                }

            case 5:
                if (d >= 0.0D ? d < 0.1875D : d < -0.1875D)
                {
                    return MathHelper.floor_double(par1 * 32D);
                }
                else
                {
                    return MathHelper.func_55185_d(par1 * 32D);
                }
        }

        if (d > 0.0D)
        {
            return MathHelper.func_55185_d(par1 * 32D);
        }
        else
        {
            return MathHelper.floor_double(par1 * 32D);
        }
    }
}
