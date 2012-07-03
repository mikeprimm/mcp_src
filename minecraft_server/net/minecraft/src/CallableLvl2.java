package net.minecraft.src;

import java.util.List;
import java.util.concurrent.Callable;

class CallableLvl2 implements Callable
{
    final World field_55313_a;

    CallableLvl2(World par1World)
    {
        field_55313_a = par1World;
    }

    public String func_55312_a()
    {
        return (new StringBuilder()).append(field_55313_a.playerEntities.size()).append(" total; ").append(field_55313_a.playerEntities.toString()).toString();
    }

    public Object call()
    {
        return func_55312_a();
    }
}
