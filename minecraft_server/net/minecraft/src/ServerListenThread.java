package net.minecraft.src;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerListenThread extends Thread
{
    private static Logger field_56497_a = Logger.getLogger("Minecraft");
    private final List field_56495_b = Collections.synchronizedList(new ArrayList());
    private final HashMap field_56496_c = new HashMap();
    private int field_56493_d;
    private final ServerSocket field_56494_e;
    private NetworkListenThread field_56491_f;
    private final InetAddress field_56492_g;
    private final int field_56498_h;

    public ServerListenThread(NetworkListenThread par1NetworkListenThread, InetAddress par2InetAddress, int par3) throws IOException
    {
        super("Listen thread");
        field_56493_d = 0;
        field_56491_f = par1NetworkListenThread;
        field_56492_g = par2InetAddress;
        field_56498_h = par3;
        field_56494_e = new ServerSocket(par3, 0, par2InetAddress);
        field_56494_e.setPerformancePreferences(0, 2, 1);
    }

    public void func_56486_a()
    {
        synchronized (field_56495_b)
        {
            for (int i = 0; i < field_56495_b.size(); i++)
            {
                NetLoginHandler netloginhandler = (NetLoginHandler)field_56495_b.get(i);

                try
                {
                    netloginhandler.tryLogin();
                }
                catch (Exception exception)
                {
                    netloginhandler.kickUser("Internal server error");
                    field_56497_a.log(Level.WARNING, (new StringBuilder()).append("Failed to handle packet: ").append(exception).toString(), exception);
                }

                if (netloginhandler.finishedProcessing)
                {
                    field_56495_b.remove(i--);
                }

                netloginhandler.field_56681_b.func_55273_a();
            }
        }
    }

    public void run()
    {
        do
        {
            if (!field_56491_f.isListening)
            {
                break;
            }

            try
            {
                Socket socket = field_56494_e.accept();
                InetAddress inetaddress = socket.getInetAddress();
                long l = System.currentTimeMillis();

                synchronized (field_56496_c)
                {
                    if (field_56496_c.containsKey(inetaddress) && !func_56490_a(inetaddress) && l - ((Long)field_56496_c.get(inetaddress)).longValue() < 4000L)
                    {
                        field_56496_c.put(inetaddress, Long.valueOf(l));
                        socket.close();
                        continue;
                    }

                    field_56496_c.put(inetaddress, Long.valueOf(l));
                }

                NetLoginHandler netloginhandler = new NetLoginHandler(field_56491_f.func_56503_c(), socket, (new StringBuilder()).append("Connection #").append(field_56493_d++).toString());
                func_56489_a(netloginhandler);
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();
            }
        }
        while (true);

        System.out.println("Closing listening thread");
    }

    private void func_56489_a(NetLoginHandler par1NetLoginHandler)
    {
        if (par1NetLoginHandler == null)
        {
            throw new IllegalArgumentException("Got null pendingconnection!");
        }

        synchronized (field_56495_b)
        {
            field_56495_b.add(par1NetLoginHandler);
        }
    }

    private static boolean func_56490_a(InetAddress par0InetAddress)
    {
        return "127.0.0.1".equals(par0InetAddress.getHostAddress());
    }

    public void func_58051_a(InetAddress par1InetAddress)
    {
        if (par1InetAddress != null)
        {
            synchronized (field_56496_c)
            {
                field_56496_c.remove(par1InetAddress);
            }
        }
    }

    public void func_56488_b()
    {
        try
        {
            field_56494_e.close();
        }
        catch (Throwable throwable) { }
    }
}
