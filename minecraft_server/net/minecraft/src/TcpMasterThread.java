package net.minecraft.src;

class TcpMasterThread extends Thread
{
    final TcpConnection field_56683_a;

    TcpMasterThread(TcpConnection par1TcpConnection)
    {
        field_56683_a = par1TcpConnection;
    }

    public void run()
    {
        try
        {
            Thread.sleep(5000L);

            if (TcpConnection.func_56636_g(field_56683_a).isAlive())
            {
                try
                {
                    TcpConnection.func_56636_g(field_56683_a).stop();
                }
                catch (Throwable throwable) { }
            }

            if (TcpConnection.func_56642_h(field_56683_a).isAlive())
            {
                try
                {
                    TcpConnection.func_56642_h(field_56683_a).stop();
                }
                catch (Throwable throwable1) { }
            }
        }
        catch (InterruptedException interruptedexception)
        {
            interruptedexception.printStackTrace();
        }
    }
}
