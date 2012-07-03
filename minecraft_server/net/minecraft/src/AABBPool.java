package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class AABBPool
{
    private final int field_58073_a;
    private final int field_58071_b;
    private final List field_58072_c = new ArrayList();
    private int field_58069_d;
    private int field_58070_e;
    private int field_58068_f;

    public AABBPool(int par1, int par2)
    {
        field_58069_d = 0;
        field_58070_e = 0;
        field_58068_f = 0;
        field_58073_a = par1;
        field_58071_b = par2;
    }

    public AxisAlignedBB func_58067_a(double par1, double par3, double par5, double par7, double par9, double par11)
    {
        AxisAlignedBB axisalignedbb;

        if (field_58069_d >= field_58072_c.size())
        {
            axisalignedbb = new AxisAlignedBB(par1, par3, par5, par7, par9, par11);
            field_58072_c.add(axisalignedbb);
        }
        else
        {
            axisalignedbb = (AxisAlignedBB)field_58072_c.get(field_58069_d);
            axisalignedbb.setBounds(par1, par3, par5, par7, par9, par11);
        }

        field_58069_d++;
        return axisalignedbb;
    }

    public void func_58066_a()
    {
        if (field_58069_d > field_58070_e)
        {
            field_58070_e = field_58069_d;
        }

        if (field_58068_f++ == field_58073_a)
        {
            for (int i = Math.max(field_58070_e, field_58072_c.size() - field_58071_b); field_58072_c.size() > i; field_58072_c.remove(i)) { }

            field_58070_e = 0;
            field_58068_f = 0;
        }

        field_58069_d = 0;
    }
}
