package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableMinecraftVersion implements Callable
{
    final CrashReport field_55299_a;

    CallableMinecraftVersion(CrashReport par1CrashReport)
    {
        field_55299_a = par1CrashReport;
    }

    public String func_55298_a()
    {
        return "12w26a";
    }

    public Object call()
    {
        return func_55298_a();
    }
}
