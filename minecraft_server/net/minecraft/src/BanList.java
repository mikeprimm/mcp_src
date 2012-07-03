package net.minecraft.src;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BanList
{
    private final LowerStringMap field_56751_a = new LowerStringMap();
    private final File field_56749_b;
    private boolean field_56750_c;

    public BanList(File par1File)
    {
        field_56750_c = true;
        field_56749_b = par1File;
    }

    public boolean func_56745_a()
    {
        return field_56750_c;
    }

    public void func_56744_a(boolean par1)
    {
        field_56750_c = par1;
    }

    public Map func_56741_b()
    {
        func_56746_c();
        return field_56751_a;
    }

    public boolean func_56740_a(String par1Str)
    {
        if (!func_56745_a())
        {
            return false;
        }
        else
        {
            func_56746_c();
            return field_56751_a.containsKey(par1Str);
        }
    }

    public void func_56748_a(BanEntry par1BanEntry)
    {
        field_56751_a.func_56535_a(par1BanEntry.func_56713_a(), par1BanEntry);
        func_56743_e();
    }

    public void func_56747_b(String par1Str)
    {
        field_56751_a.remove(par1Str);
        func_56743_e();
    }

    public void func_56746_c()
    {
        Iterator iterator = field_56751_a.values().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            BanEntry banentry = (BanEntry)iterator.next();

            if (banentry.func_56711_e())
            {
                iterator.remove();
            }
        }
        while (true);
    }

    public void func_56742_d()
    {
        if (!field_56749_b.isFile())
        {
            return;
        }

        BufferedReader bufferedreader;

        try
        {
            bufferedreader = new BufferedReader(new FileReader(field_56749_b));
        }
        catch (FileNotFoundException filenotfoundexception)
        {
            throw new Error();
        }

        try
        {
            do
            {
                String s;

                if ((s = bufferedreader.readLine()) == null)
                {
                    break;
                }

                if (!s.startsWith("#"))
                {
                    BanEntry banentry = BanEntry.func_56715_c(s);

                    if (banentry != null)
                    {
                        field_56751_a.func_56535_a(banentry.func_56713_a(), banentry);
                    }
                }
            }
            while (true);
        }
        catch (IOException ioexception)
        {
            Logger.getLogger("Minecraft").log(Level.SEVERE, "Could not load ban list", ioexception);
        }
    }

    public void func_56743_e()
    {
        func_56739_b(true);
    }

    public void func_56739_b(boolean par1)
    {
        func_56746_c();

        try
        {
            PrintWriter printwriter = new PrintWriter(new FileWriter(field_56749_b, false));

            if (par1)
            {
                printwriter.println((new StringBuilder()).append("# Updated ").append((new SimpleDateFormat()).format(new Date())).append(" by Minecraft ").append("12w26a").toString());
                printwriter.println("# victim name | ban date | banned by | banned until | reason");
                printwriter.println();
            }

            BanEntry banentry;

            for (Iterator iterator = field_56751_a.values().iterator(); iterator.hasNext(); printwriter.println(banentry.func_56714_g()))
            {
                banentry = (BanEntry)iterator.next();
            }

            printwriter.close();
        }
        catch (IOException ioexception)
        {
            Logger.getLogger("Minecraft").log(Level.SEVERE, "Could not save ban list", ioexception);
        }
    }
}
