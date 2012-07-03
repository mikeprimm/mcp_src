package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class ThreadServerApplication extends Thread
{
    final MinecraftServer field_56543_a;

    public ThreadServerApplication(MinecraftServer par1MinecraftServer, String par2Str)
    {
        super(par2Str);
        field_56543_a = par1MinecraftServer;
    }

    public void run()
    {
        field_56543_a.run();
    }
}
