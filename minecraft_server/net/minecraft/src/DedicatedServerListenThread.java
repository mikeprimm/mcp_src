package net.minecraft.src;

import java.io.IOException;
import java.net.InetAddress;
import net.minecraft.server.MinecraftServer;

public class DedicatedServerListenThread extends NetworkListenThread
{
    private final ServerListenThread field_56508_c;

    public DedicatedServerListenThread(MinecraftServer par1MinecraftServer, InetAddress par2InetAddress, int par3) throws IOException
    {
        super(par1MinecraftServer);
        field_56508_c = new ServerListenThread(this, par2InetAddress, par3);
        field_56508_c.start();
    }

    public void func_56504_a()
    {
        super.func_56504_a();
        field_56508_c.func_56488_b();
        field_56508_c.interrupt();
    }

    /**
     * Handles all incoming connections and packets
     */
    public void handleNetworkListenThread()
    {
        field_56508_c.func_56486_a();
        super.handleNetworkListenThread();
    }

    public DedicatedServer func_56507_d()
    {
        return (DedicatedServer)super.func_56503_c();
    }

    public void func_58050_a(InetAddress par1InetAddress)
    {
        field_56508_c.func_58051_a(par1InetAddress);
    }

    public MinecraftServer func_56503_c()
    {
        return func_56507_d();
    }
}
