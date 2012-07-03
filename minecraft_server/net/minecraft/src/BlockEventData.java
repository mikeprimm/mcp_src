package net.minecraft.src;

public class BlockEventData
{
    private int field_58062_a;
    private int field_58060_b;
    private int field_58061_c;
    private int field_58058_d;
    private int field_58059_e;

    public BlockEventData(int par1, int par2, int par3, int par4, int par5)
    {
        field_58062_a = par1;
        field_58060_b = par2;
        field_58061_c = par3;
        field_58058_d = par4;
        field_58059_e = par5;
    }

    public int func_58055_a()
    {
        return field_58062_a;
    }

    public int func_58057_b()
    {
        return field_58060_b;
    }

    public int func_58056_c()
    {
        return field_58061_c;
    }

    public int func_58054_d()
    {
        return field_58058_d;
    }

    public int func_58053_e()
    {
        return field_58059_e;
    }
}
