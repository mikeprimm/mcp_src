package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class Vec3Pool
{
    private final int field_58082_a;
    private final int field_58080_b;
    private final List field_58081_c = new ArrayList();
    private int field_58078_d;
    private int field_58079_e;
    private int field_58077_f;

    public Vec3Pool(int par1, int par2)
    {
        field_58078_d = 0;
        field_58079_e = 0;
        field_58077_f = 0;
        field_58082_a = par1;
        field_58080_b = par2;
    }

    public Vec3 func_58076_a(double par1, double par3, double par5)
    {
        Vec3 vec3;

        if (field_58078_d >= field_58081_c.size())
        {
            vec3 = new Vec3(par1, par3, par5);
            field_58081_c.add(vec3);
        }
        else
        {
            vec3 = (Vec3)field_58081_c.get(field_58078_d);
            vec3.setComponents(par1, par3, par5);
        }

        field_58078_d++;
        return vec3;
    }

    public void func_58075_a()
    {
        if (field_58078_d > field_58079_e)
        {
            field_58079_e = field_58078_d;
        }

        if (field_58077_f++ == field_58082_a)
        {
            for (int i = Math.max(field_58079_e, field_58081_c.size() - field_58080_b); field_58081_c.size() > i; field_58081_c.remove(i)) { }

            field_58079_e = 0;
            field_58077_f = 0;
        }

        field_58078_d = 0;
    }
}
