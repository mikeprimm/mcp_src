package net.minecraft.src;

class TcpMonitorThread extends Thread
{
    final TcpConnection field_56685_a;

    TcpMonitorThread(TcpConnection par1TcpConnection)
    {
        field_56685_a = par1TcpConnection;
    }

    public void run()
    {
        try
        {
            Thread.sleep(2000L);

            if (TcpConnection.func_56644_a(field_56685_a))
            {
                TcpConnection.func_56642_h(field_56685_a).interrupt();
                field_56685_a.networkShutdown("disconnect.closed", new Object[0]);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
