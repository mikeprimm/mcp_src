package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableOSInfo implements Callable
{
    final CrashReport field_55301_a;

    CallableOSInfo(CrashReport par1CrashReport)
    {
        field_55301_a = par1CrashReport;
    }

    public String func_55300_a()
    {
        return (new StringBuilder()).append(System.getProperty("os.name")).append(" (").append(System.getProperty("os.arch")).append(") version ").append(System.getProperty("os.version")).toString();
    }

    public Object call()
    {
        return func_55300_a();
    }
}
