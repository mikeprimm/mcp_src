package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.server.MinecraftServer;

public class CallablePlayers implements Callable
{
    final MinecraftServer field_58064_a;

    public CallablePlayers(MinecraftServer par1MinecraftServer)
    {
        field_58064_a = par1MinecraftServer;
    }

    public String func_58063_a()
    {
        return (new StringBuilder()).append(MinecraftServer.func_56156_a(field_58064_a).playersOnline()).append(" / ").append(MinecraftServer.func_56156_a(field_58064_a).getMaxPlayers()).append("; ").append(MinecraftServer.func_56156_a(field_58064_a).playerEntities).toString();
    }

    public Object call()
    {
        return func_58063_a();
    }
}
