package net.minecraft.src;

import java.io.*;

class DedicatedServerCommandThread extends Thread
{
    final DedicatedServer field_56783_a;

    DedicatedServerCommandThread(DedicatedServer par1DedicatedServer)
    {
        field_56783_a = par1DedicatedServer;
    }

    public void run()
    {
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(System.in));

        try
        {
            String s;

            for (; !field_56783_a.func_56210_X() && field_56783_a.func_56190_j() && (s = bufferedreader.readLine()) != null; field_56783_a.func_56225_a(s, field_56783_a)) { }
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }
}
