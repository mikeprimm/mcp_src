package net.minecraft.src;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class PlayerUsageSnooper
{
    private Map field_52016_a;
    private final String field_56630_b = UUID.randomUUID().toString();
    private final URL field_52015_b;
    private final IPlayerUsage field_56628_d;
    private final Timer field_56629_e = new Timer("Snooper Timer", true);
    private final Object field_56626_f = new Object();
    private boolean field_56627_g;
    private int field_56631_h;

    public PlayerUsageSnooper(String par1Str, IPlayerUsage par2IPlayerUsage)
    {
        field_52016_a = new HashMap();
        field_56627_g = false;
        field_56631_h = 0;

        try
        {
            field_52015_b = new URL((new StringBuilder()).append("http://snoop.minecraft.net/").append(par1Str).append("?version=").append(1).toString());
        }
        catch (MalformedURLException malformedurlexception)
        {
            throw new IllegalArgumentException();
        }

        field_56628_d = par2IPlayerUsage;
    }

    public void func_56619_a()
    {
        if (field_56627_g)
        {
            return;
        }
        else
        {
            field_56627_g = true;
            func_56620_e();
            field_56629_e.schedule(new PlayerUsageSnooperThread(this), 0L, 0xdbba0L);
            return;
        }
    }

    private void func_56620_e()
    {
        func_56625_f();
        func_52014_a("snooper_token", field_56630_b);
        func_52014_a("os_name", System.getProperty("os.name"));
        func_52014_a("os_version", System.getProperty("os.version"));
        func_52014_a("os_architecture", System.getProperty("os.arch"));
        func_52014_a("java_version", System.getProperty("java.version"));
        func_52014_a("version", "12w26a");
        field_56628_d.func_56153_b(this);
    }

    private void func_56625_f()
    {
        RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
        List list = runtimemxbean.getInputArguments();
        int i = 0;
        String s;

        for (Iterator iterator = list.iterator(); iterator.hasNext(); func_52014_a((new StringBuilder()).append("jvm_arg[").append(i++).append("]").toString(), s))
        {
            s = (String)iterator.next();
        }

        func_52014_a("jvm_args", Integer.valueOf(i));
    }

    public void func_56622_b()
    {
        func_52014_a("memory_total", Long.valueOf(Runtime.getRuntime().totalMemory()));
        func_52014_a("memory_max", Long.valueOf(Runtime.getRuntime().maxMemory()));
        func_52014_a("memory_free", Long.valueOf(Runtime.getRuntime().freeMemory()));
        func_52014_a("cpu_cores", Integer.valueOf(Runtime.getRuntime().availableProcessors()));
        field_56628_d.func_56154_a(this);
    }

    public void func_52014_a(String par1Str, Object par2Obj)
    {
        synchronized (field_56626_f)
        {
            field_52016_a.put(par1Str, par2Obj);
        }
    }

    public boolean func_56624_c()
    {
        return field_56627_g;
    }

    public void func_56617_d()
    {
        field_56629_e.cancel();
    }

    static Object func_56618_a(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.field_56626_f;
    }

    static Map func_52011_b(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.field_52016_a;
    }

    static int func_56623_c(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.field_56631_h++;
    }

    static URL func_56621_d(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.field_52015_b;
    }
}
