package net.minecraft.src;

import java.io.*;
import java.net.*;
import java.security.PrivateKey;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.SecretKey;

public class TcpConnection implements NetworkManager
{
    public static AtomicInteger field_56658_a = new AtomicInteger();
    public static AtomicInteger field_56656_b = new AtomicInteger();
    private Object field_56665_h;
    private Socket field_56666_i;
    private final SocketAddress field_56663_j;
    private volatile DataInputStream field_56664_k;
    private volatile DataOutputStream field_56661_l;
    private volatile boolean field_56662_m;
    private volatile boolean field_56659_n;
    private List field_56660_o;
    private List field_56674_p;
    private List field_56673_q;
    private NetHandler field_56672_r;
    private boolean field_56671_s;
    private Thread field_56670_t;
    private Thread field_56669_u;
    private String field_56668_v;
    private Object field_56667_w[];
    private int field_56677_x;
    private int field_56676_y;
    public static int field_56657_c[] = new int[256];
    public static int field_56654_d[] = new int[256];
    public int field_56655_e;
    boolean field_56652_f;
    boolean field_56653_g;
    private SecretKey field_56675_z;
    private PrivateKey field_56650_A;
    private int field_56651_B;

    public TcpConnection(Socket par1Socket, String par2Str, NetHandler par3NetHandler, PrivateKey par4PrivateKey) throws IOException
    {
        field_56665_h = new Object();
        field_56662_m = true;
        field_56659_n = false;
        field_56660_o = Collections.synchronizedList(new ArrayList());
        field_56674_p = Collections.synchronizedList(new ArrayList());
        field_56673_q = Collections.synchronizedList(new ArrayList());
        field_56671_s = false;
        field_56668_v = "";
        field_56677_x = 0;
        field_56676_y = 0;
        field_56655_e = 0;
        field_56652_f = false;
        field_56653_g = false;
        field_56675_z = null;
        field_56650_A = null;
        field_56651_B = 50;
        field_56650_A = par4PrivateKey;
        field_56666_i = par1Socket;
        field_56663_j = par1Socket.getRemoteSocketAddress();
        field_56672_r = par3NetHandler;

        try
        {
            par1Socket.setSoTimeout(30000);
            par1Socket.setTrafficClass(24);
        }
        catch (SocketException socketexception)
        {
            System.err.println(socketexception.getMessage());
        }

        field_56664_k = new DataInputStream(par1Socket.getInputStream());
        field_56661_l = new DataOutputStream(new BufferedOutputStream(par1Socket.getOutputStream(), 5120));
        field_56669_u = new TcpReaderThread(this, (new StringBuilder()).append(par2Str).append(" read thread").toString());
        field_56670_t = new TcpWriterThread(this, (new StringBuilder()).append(par2Str).append(" write thread").toString());
        field_56669_u.start();
        field_56670_t.start();
    }

    /**
     * Sets the NetHandler for this NetworkManager. Server-only.
     */
    public void setNetHandler(NetHandler par1NetHandler)
    {
        field_56672_r = par1NetHandler;
    }

    /**
     * Adds the packet to the correct send queue (chunk data packets go to a separate queue).
     */
    public void addToSendQueue(Packet par1Packet)
    {
        if (field_56671_s)
        {
            return;
        }

        synchronized (field_56665_h)
        {
            field_56676_y += par1Packet.getPacketSize() + 1;

            if (par1Packet.isChunkDataPacket)
            {
                field_56673_q.add(par1Packet);
            }
            else
            {
                field_56674_p.add(par1Packet);
            }
        }
    }

    private boolean func_56641_g()
    {
        boolean flag = false;

        try
        {
            if (!field_56674_p.isEmpty() && (field_56655_e == 0 || System.currentTimeMillis() - ((Packet)field_56674_p.get(0)).creationTimeMillis >= (long)field_56655_e))
            {
                Packet packet;

                synchronized (field_56665_h)
                {
                    packet = (Packet)field_56674_p.remove(0);
                    field_56676_y -= packet.getPacketSize() + 1;
                }

                Packet.writePacket(packet, field_56661_l);

                if ((packet instanceof Packet252SharedKey) && !field_56653_g)
                {
                    if (!field_56672_r.isServerHandler())
                    {
                        field_56675_z = ((Packet252SharedKey)packet).func_55106_b();
                    }

                    func_56639_j();
                }

                field_56654_d[packet.getPacketId()] += packet.getPacketSize() + 1;
                flag = true;
            }

            if (field_56651_B-- <= 0 && !field_56673_q.isEmpty() && (field_56655_e == 0 || System.currentTimeMillis() - ((Packet)field_56673_q.get(0)).creationTimeMillis >= (long)field_56655_e))
            {
                Packet packet1;

                synchronized (field_56665_h)
                {
                    packet1 = (Packet)field_56673_q.remove(0);
                    field_56676_y -= packet1.getPacketSize() + 1;
                }

                Packet.writePacket(packet1, field_56661_l);
                field_56654_d[packet1.getPacketId()] += packet1.getPacketSize() + 1;
                field_56651_B = 0;
                flag = true;
            }
        }
        catch (Exception exception)
        {
            if (!field_56659_n)
            {
                func_56649_a(exception);
            }

            return false;
        }

        return flag;
    }

    public void func_55273_a()
    {
        if (field_56669_u != null)
        {
            field_56669_u.interrupt();
        }

        if (field_56670_t != null)
        {
            field_56670_t.interrupt();
        }
    }

    private boolean func_56635_h()
    {
        boolean flag = false;

        try
        {
            Packet packet = Packet.readPacket(field_56664_k, field_56672_r.isServerHandler());

            if (packet != null)
            {
                if ((packet instanceof Packet252SharedKey) && !field_56652_f)
                {
                    if (field_56672_r.isServerHandler())
                    {
                        field_56675_z = ((Packet252SharedKey)packet).func_55107_a(field_56650_A);
                    }

                    func_56647_i();
                }

                field_56657_c[packet.getPacketId()] += packet.getPacketSize() + 1;

                if (!field_56671_s)
                {
                    field_56660_o.add(packet);
                }

                flag = true;
            }
            else
            {
                networkShutdown("disconnect.endOfStream", new Object[0]);
            }
        }
        catch (Exception exception)
        {
            if (!field_56659_n)
            {
                func_56649_a(exception);
            }

            return false;
        }

        return flag;
    }

    private void func_56649_a(Exception par1Exception)
    {
        par1Exception.printStackTrace();
        networkShutdown("disconnect.genericReason", new Object[]
                {
                    (new StringBuilder()).append("Internal exception: ").append(par1Exception.toString()).toString()
                });
    }

    /**
     * Shuts down the network with the specified reason. Closes all streams and sockets, spawns NetworkMasterThread to
     * stop reading and writing threads.
     */
    public void networkShutdown(String par1Str, Object par2ArrayOfObj[])
    {
        if (!field_56662_m)
        {
            return;
        }

        field_56659_n = true;
        field_56668_v = par1Str;
        field_56667_w = par2ArrayOfObj;
        field_56662_m = false;
        (new TcpMasterThread(this)).start();

        try
        {
            field_56664_k.close();
            field_56664_k = null;
            field_56661_l.close();
            field_56661_l = null;
            field_56666_i.close();
            field_56666_i = null;
        }
        catch (Throwable throwable) { }
    }

    /**
     * Checks timeouts and processes all pending read packets.
     */
    public void processReadPackets()
    {
        if (field_56676_y > 0x100000)
        {
            networkShutdown("disconnect.overflow", new Object[0]);
        }

        if (field_56660_o.isEmpty())
        {
            if (field_56677_x++ == 1200)
            {
                networkShutdown("disconnect.timeout", new Object[0]);
            }
        }
        else
        {
            field_56677_x = 0;
        }

        Packet packet;

        for (int i = 1000; !field_56660_o.isEmpty() && i-- >= 0; packet.processPacket(field_56672_r))
        {
            packet = (Packet)field_56660_o.remove(0);
        }

        func_55273_a();

        if (field_56659_n && field_56660_o.isEmpty())
        {
            field_56672_r.handleErrorMessage(field_56668_v, field_56667_w);
        }
    }

    /**
     * Returns the socket address of the remote side. Server-only.
     */
    public SocketAddress getRemoteAddress()
    {
        return field_56663_j;
    }

    /**
     * Shuts down the server. (Only actually used on the server)
     */
    public void serverShutdown()
    {
        if (field_56671_s)
        {
            return;
        }
        else
        {
            func_55273_a();
            field_56671_s = true;
            field_56669_u.interrupt();
            (new TcpMonitorThread(this)).start();
            return;
        }
    }

    private void func_56647_i() throws IOException
    {
        field_56652_f = true;
        field_56664_k = new DataInputStream(CryptManager.func_55265_a(field_56675_z, field_56666_i.getInputStream()));
    }

    private void func_56639_j() throws IOException
    {
        field_56661_l.flush();
        field_56653_g = true;
        field_56661_l = new DataOutputStream(new BufferedOutputStream(CryptManager.func_55267_a(field_56675_z, field_56666_i.getOutputStream()), 5120));
    }

    /**
     * Returns the number of chunk data packets waiting to be sent.
     */
    public int getNumChunkDataPackets()
    {
        return field_56673_q.size();
    }

    public Socket func_56640_f()
    {
        return field_56666_i;
    }

    static boolean func_56644_a(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.field_56662_m;
    }

    static boolean func_56643_b(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.field_56671_s;
    }

    static boolean func_56637_c(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.func_56635_h();
    }

    static boolean func_56648_d(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.func_56641_g();
    }

    static DataOutputStream func_56645_e(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.field_56661_l;
    }

    static boolean func_56646_f(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.field_56659_n;
    }

    static void func_56638_a(TcpConnection par0TcpConnection, Exception par1Exception)
    {
        par0TcpConnection.func_56649_a(par1Exception);
    }

    static Thread func_56636_g(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.field_56669_u;
    }

    static Thread func_56642_h(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.field_56670_t;
    }
}
