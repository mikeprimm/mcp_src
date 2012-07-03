package net.minecraft.src;

import java.io.*;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;
import net.minecraft.server.MinecraftServer;

public class DedicatedPlayerList extends ServerConfigurationManager
{
    private File field_56444_e;
    private File field_56443_f;

    public DedicatedPlayerList(DedicatedServer par1DedicatedServer)
    {
        super(par1DedicatedServer);
        field_56444_e = par1DedicatedServer.getFile("ops.txt");
        field_56443_f = par1DedicatedServer.getFile("white-list.txt");
        field_56435_d = par1DedicatedServer.getIntProperty("view-distance", 10);
        maxPlayers = par1DedicatedServer.getIntProperty("max-players", 20);
        func_55206_a(par1DedicatedServer.func_56222_a("white-list", false));

        if (!par1DedicatedServer.func_56187_H())
        {
            func_56429_e().func_56744_a(true);
            func_56427_f().func_56744_a(true);
        }

        func_56429_e().func_56742_d();
        func_56429_e().func_56743_e();
        func_56427_f().func_56742_d();
        func_56427_f().func_56743_e();
        func_56442_s();
        func_56441_u();
        func_56438_t();
        func_56440_v();
    }

    public void func_55206_a(boolean par1)
    {
        super.func_55206_a(par1);
        func_56439_r().setProperty("white-list", Boolean.valueOf(par1));
        func_56439_r().saveProperties();
    }

    /**
     * This adds a username to the ops list, then saves the op list
     */
    public void addOp(String par1Str)
    {
        super.addOp(par1Str);
        func_56438_t();
    }

    /**
     * This removes a username from the ops list, then saves the op list
     */
    public void removeOp(String par1Str)
    {
        super.removeOp(par1Str);
        func_56438_t();
    }

    /**
     * remove the specified player from the whitelist
     */
    public void removeFromWhiteList(String par1Str)
    {
        super.removeFromWhiteList(par1Str);
        func_56440_v();
    }

    /**
     * add the specified player to the white list
     */
    public void addToWhiteList(String par1Str)
    {
        super.addToWhiteList(par1Str);
        func_56440_v();
    }

    /**
     * reloads the whitelist
     */
    public void reloadWhiteList()
    {
        func_56441_u();
    }

    private void func_56442_s()
    {
        try
        {
            func_55207_h().clear();
            BufferedReader bufferedreader = new BufferedReader(new FileReader(field_56444_e));

            for (String s = ""; (s = bufferedreader.readLine()) != null;)
            {
                func_55207_h().add(s.trim().toLowerCase());
            }

            bufferedreader.close();
        }
        catch (Exception exception)
        {
            logger.warning((new StringBuilder()).append("Failed to load operators list: ").append(exception).toString());
        }
    }

    private void func_56438_t()
    {
        try
        {
            PrintWriter printwriter = new PrintWriter(new FileWriter(field_56444_e, false));
            String s;

            for (Iterator iterator = func_55207_h().iterator(); iterator.hasNext(); printwriter.println(s))
            {
                s = (String)iterator.next();
            }

            printwriter.close();
        }
        catch (Exception exception)
        {
            logger.warning((new StringBuilder()).append("Failed to save operators list: ").append(exception).toString());
        }
    }

    private void func_56441_u()
    {
        try
        {
            getWhiteListedIPs().clear();
            BufferedReader bufferedreader = new BufferedReader(new FileReader(field_56443_f));

            for (String s = ""; (s = bufferedreader.readLine()) != null;)
            {
                getWhiteListedIPs().add(s.trim().toLowerCase());
            }

            bufferedreader.close();
        }
        catch (Exception exception)
        {
            logger.warning((new StringBuilder()).append("Failed to load white-list: ").append(exception).toString());
        }
    }

    private void func_56440_v()
    {
        try
        {
            PrintWriter printwriter = new PrintWriter(new FileWriter(field_56443_f, false));
            String s;

            for (Iterator iterator = getWhiteListedIPs().iterator(); iterator.hasNext(); printwriter.println(s))
            {
                s = (String)iterator.next();
            }

            printwriter.close();
        }
        catch (Exception exception)
        {
            logger.warning((new StringBuilder()).append("Failed to save white-list: ").append(exception).toString());
        }
    }

    /**
     * Determine if the player is allowed to connect based on current server settings
     */
    public boolean isAllowedToLogin(String par1Str)
    {
        par1Str = par1Str.trim().toLowerCase();
        return !func_56426_n() || isOp(par1Str) || getWhiteListedIPs().contains(par1Str);
    }

    public DedicatedServer func_56439_r()
    {
        return (DedicatedServer)super.func_56422_p();
    }

    public MinecraftServer func_56422_p()
    {
        return func_56439_r();
    }
}
