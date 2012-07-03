package net.minecraft.src;

import java.util.concurrent.atomic.AtomicInteger;

class TcpReaderThread extends Thread
{
    final TcpConnection field_56686_a;

    TcpReaderThread(TcpConnection par1TcpConnection, String par2Str)
    {
        super(par2Str);
        field_56686_a = par1TcpConnection;
    }

    public void run()
    {
        TcpConnection.field_56658_a.getAndIncrement();

        try
        {
            while (TcpConnection.func_56644_a(field_56686_a) && !TcpConnection.func_56643_b(field_56686_a))
            {
                while (TcpConnection.func_56637_c(field_56686_a)) ;

                try
                {
                    sleep(2L);
                }
                catch (InterruptedException interruptedexception) { }
            }
        }
        finally
        {
            TcpConnection.field_56658_a.getAndDecrement();
        }
    }
}
