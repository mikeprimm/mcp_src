package net.minecraft.src;

import java.util.*;
import net.minecraft.server.MinecraftServer;

public class CommandServerWhitelist extends CommandBase
{
    public CommandServerWhitelist()
    {
    }

    public String func_55148_a()
    {
        return "whitelist";
    }

    public String func_55150_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55069_a("commands.whitelist.usage", new Object[0]);
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length >= 1)
        {
            if (par2ArrayOfStr[0].equals("on"))
            {
                MinecraftServer.func_55089_q().func_56173_Y().func_55206_a(true);
                func_55154_a(par1ICommandSender, "commands.whitelist.enabled", new Object[0]);
                return;
            }

            if (par2ArrayOfStr[0].equals("off"))
            {
                MinecraftServer.func_55089_q().func_56173_Y().func_55206_a(false);
                func_55154_a(par1ICommandSender, "commands.whitelist.disabled", new Object[0]);
                return;
            }

            if (par2ArrayOfStr[0].equals("list"))
            {
                par1ICommandSender.func_55072_b(par1ICommandSender.func_55069_a("commands.whitelist.list", new Object[]
                        {
                            Integer.valueOf(MinecraftServer.func_55089_q().func_56173_Y().getWhiteListedIPs().size()), Integer.valueOf(MinecraftServer.func_55089_q().func_56173_Y().func_52019_t().length)
                        }));
                par1ICommandSender.func_55072_b(func_56236_a(MinecraftServer.func_55089_q().func_56173_Y().getWhiteListedIPs().toArray(new String[0])));
                return;
            }

            if (par2ArrayOfStr[0].equals("add"))
            {
                if (par2ArrayOfStr.length < 2)
                {
                    throw new WrongUsageException("commands.whitelist.add.usage", new Object[0]);
                }
                else
                {
                    MinecraftServer.func_55089_q().func_56173_Y().addToWhiteList(par2ArrayOfStr[1]);
                    func_55154_a(par1ICommandSender, "commands.whitelist.add.success", new Object[]
                            {
                                par2ArrayOfStr[1]
                            });
                    return;
                }
            }

            if (par2ArrayOfStr[0].equals("remove"))
            {
                if (par2ArrayOfStr.length < 2)
                {
                    throw new WrongUsageException("commands.whitelist.remove.usage", new Object[0]);
                }
                else
                {
                    MinecraftServer.func_55089_q().func_56173_Y().removeFromWhiteList(par2ArrayOfStr[1]);
                    func_55154_a(par1ICommandSender, "commands.whitelist.remove.success", new Object[]
                            {
                                par2ArrayOfStr[1]
                            });
                    return;
                }
            }

            if (par2ArrayOfStr[0].equals("reload"))
            {
                MinecraftServer.func_55089_q().func_56173_Y().reloadWhiteList();
                func_55154_a(par1ICommandSender, "commands.whitelist.reloaded", new Object[0]);
                return;
            }
        }

        throw new WrongUsageException("commands.whitelist.usage", new Object[0]);
    }

    public List func_55149_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_55157_a(par2ArrayOfStr, new String[]
                    {
                        "on", "off", "list", "add", "remove", "reload"
                    });
        }

        if (par2ArrayOfStr.length == 2)
        {
            if (par2ArrayOfStr[0].equals("add"))
            {
                String as[] = MinecraftServer.func_55089_q().func_56173_Y().func_52019_t();
                ArrayList arraylist = new ArrayList();
                String s = par2ArrayOfStr[par2ArrayOfStr.length - 1];
                String as1[] = as;
                int i = as1.length;

                for (int j = 0; j < i; j++)
                {
                    String s1 = as1[j];

                    if (func_55155_a(s, s1) && !MinecraftServer.func_55089_q().func_56173_Y().getWhiteListedIPs().contains(s1))
                    {
                        arraylist.add(s1);
                    }
                }

                return arraylist;
            }

            if (par2ArrayOfStr[0].equals("remove"))
            {
                return func_55158_a(par2ArrayOfStr, MinecraftServer.func_55089_q().func_56173_Y().getWhiteListedIPs());
            }
        }

        return null;
    }
}
