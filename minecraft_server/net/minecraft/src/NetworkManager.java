package net.minecraft.src;

import java.net.SocketAddress;

public interface NetworkManager
{
    /**
     * Sets the NetHandler for this NetworkManager. Server-only.
     */
    public abstract void setNetHandler(NetHandler nethandler);

    /**
     * Adds the packet to the correct send queue (chunk data packets go to a separate queue).
     */
    public abstract void addToSendQueue(Packet packet);

    public abstract void func_55273_a();

    /**
     * Checks timeouts and processes all pending read packets.
     */
    public abstract void processReadPackets();

    /**
     * Returns the socket address of the remote side. Server-only.
     */
    public abstract SocketAddress getRemoteAddress();

    /**
     * Shuts down the server. (Only actually used on the server)
     */
    public abstract void serverShutdown();

    /**
     * Returns the number of chunk data packets waiting to be sent.
     */
    public abstract int getNumChunkDataPackets();

    /**
     * Shuts down the network with the specified reason. Closes all streams and sockets, spawns NetworkMasterThread to
     * stop reading and writing threads.
     */
    public abstract void networkShutdown(String s, Object aobj[]);
}
