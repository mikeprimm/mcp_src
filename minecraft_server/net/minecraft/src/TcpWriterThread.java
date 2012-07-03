package net.minecraft.src;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

class TcpWriterThread extends Thread
{
    final TcpConnection field_56684_a;

    TcpWriterThread(TcpConnection par1TcpConnection, String par2Str)
    {
        super(par2Str);
        field_56684_a = par1TcpConnection;
    }

    public void run()
    {
        TcpConnection.field_56656_b.getAndIncrement();

        try
        {
            while (TcpConnection.func_56644_a(field_56684_a))
            {
                boolean flag;

                for (flag = false; TcpConnection.func_56648_d(field_56684_a); flag = true) { }

                try
                {
                    if (flag && TcpConnection.func_56645_e(field_56684_a) != null)
                    {
                        TcpConnection.func_56645_e(field_56684_a).flush();
                    }
                }
                catch (IOException ioexception)
                {
                    if (!TcpConnection.func_56646_f(field_56684_a))
                    {
                        TcpConnection.func_56638_a(field_56684_a, ioexception);
                    }

                    ioexception.printStackTrace();
                }

                try
                {
                    sleep(2L);
                }
                catch (InterruptedException interruptedexception) { }
            }
        }
        finally
        {
            TcpConnection.field_56656_b.getAndDecrement();
        }
    }
}
