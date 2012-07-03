package net.minecraft.src;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.server.MinecraftServer;

public abstract class NetworkListenThread
{
    /** Reference to the logger. */
    public static Logger logger = Logger.getLogger("Minecraft");

    /** Reference to the MinecraftServer object. */
    private final MinecraftServer mcServer;
    private final List field_56505_d = Collections.synchronizedList(new ArrayList());

    /** Whether the network listener object is listening. */
    public volatile boolean isListening;

    public NetworkListenThread(MinecraftServer par1MinecraftServer) throws IOException
    {
        isListening = false;
        mcServer = par1MinecraftServer;
        isListening = true;
    }

    /**
     * adds this connection to the list of currently connected players
     */
    public void addPlayer(NetServerHandler par1NetServerHandler)
    {
        field_56505_d.add(par1NetServerHandler);
    }

    public void func_56504_a()
    {
        isListening = false;
    }

    /**
     * Handles all incoming connections and packets
     */
    public void handleNetworkListenThread()
    {
        for (int i = 0; i < field_56505_d.size(); i++)
        {
            NetServerHandler netserverhandler = (NetServerHandler)field_56505_d.get(i);

            try
            {
                netserverhandler.handlePackets();
            }
            catch (Exception exception)
            {
                logger.log(Level.WARNING, (new StringBuilder()).append("Failed to handle packet: ").append(exception).toString(), exception);
                netserverhandler.kickPlayer("Internal server error");
            }

            if (netserverhandler.connectionClosed)
            {
                field_56505_d.remove(i--);
            }

            netserverhandler.netManager.func_55273_a();
        }
    }

    public MinecraftServer func_56503_c()
    {
        return mcServer;
    }
}
