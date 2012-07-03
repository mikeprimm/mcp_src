package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.server.MinecraftServer;

public class CallableMCS5 implements Callable
{
    final MinecraftServer field_55222_a;

    public CallableMCS5(MinecraftServer par1MinecraftServer)
    {
        field_55222_a = par1MinecraftServer;
    }

    public String func_55221_a()
    {
        String s = field_55222_a.func_52003_getServerModName();

        if (!s.equals("vanilla"))
        {
            return (new StringBuilder()).append("Definitely; '").append(s).append("'").toString();
        }
        else
        {
            return "Unknown (can't tell)";
        }
    }

    public Object call()
    {
        return func_55221_a();
    }
}
