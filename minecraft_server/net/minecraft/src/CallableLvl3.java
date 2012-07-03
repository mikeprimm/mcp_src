package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLvl3 implements Callable
{
    final World field_55315_a;

    CallableLvl3(World par1World)
    {
        field_55315_a = par1World;
    }

    public String func_55314_a()
    {
        return field_55315_a.chunkProvider.func_46040_d();
    }

    public Object call()
    {
        return func_55314_a();
    }
}
