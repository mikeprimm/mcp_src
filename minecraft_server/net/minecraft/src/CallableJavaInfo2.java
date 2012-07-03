package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableJavaInfo2 implements Callable
{
    final CrashReport field_55297_a;

    CallableJavaInfo2(CrashReport par1CrashReport)
    {
        field_55297_a = par1CrashReport;
    }

    public String func_55296_a()
    {
        return (new StringBuilder()).append(System.getProperty("java.vm.name")).append(" (").append(System.getProperty("java.vm.info")).append("), ").append(System.getProperty("java.vm.vendor")).toString();
    }

    public Object call()
    {
        return func_55296_a();
    }
}
