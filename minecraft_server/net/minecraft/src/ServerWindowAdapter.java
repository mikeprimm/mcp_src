package net.minecraft.src;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

final class ServerWindowAdapter extends WindowAdapter
{
    final DedicatedServer field_56549_a;

    ServerWindowAdapter(DedicatedServer par1DedicatedServer)
    {
        field_56549_a = par1DedicatedServer;
    }

    public void windowClosing(WindowEvent par1WindowEvent)
    {
        field_56549_a.initiateShutdown();

        while (!field_56549_a.func_56210_X())
        {
            try
            {
                Thread.sleep(100L);
            }
            catch (InterruptedException interruptedexception)
            {
                interruptedexception.printStackTrace();
            }
        }

        System.exit(0);
    }
}
