package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableMemoryInfo implements Callable
{
    final CrashReport field_55272_a;

    CallableMemoryInfo(CrashReport par1CrashReport)
    {
        field_55272_a = par1CrashReport;
    }

    public String func_55271_a()
    {
        Runtime runtime = Runtime.getRuntime();
        long l = runtime.maxMemory();
        long l1 = runtime.totalMemory();
        long l2 = runtime.freeMemory();
        long l3 = l / 1024L / 1024L;
        long l4 = l1 / 1024L / 1024L;
        long l5 = l2 / 1024L / 1024L;
        return (new StringBuilder()).append(l2).append(" bytes (").append(l5).append(" MB) / ").append(l1).append(" bytes (").append(l4).append(" MB) up to ").append(l).append(" bytes (").append(l3).append(" MB)").toString();
    }

    public Object call()
    {
        return func_55271_a();
    }
}
