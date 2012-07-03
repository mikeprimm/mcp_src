package net.minecraft.src;

import java.util.*;
import net.minecraft.server.MinecraftServer;

public class CommandServerBanlist extends CommandBase
{
    public CommandServerBanlist()
    {
    }

    public String func_55148_a()
    {
        return "banlist";
    }

    public boolean func_55147_c(ICommandSender par1ICommandSender)
    {
        return (MinecraftServer.func_55089_q().func_56173_Y().func_56427_f().func_56745_a() || MinecraftServer.func_55089_q().func_56173_Y().func_56429_e().func_56745_a()) && super.func_55147_c(par1ICommandSender);
    }

    public String func_55150_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55069_a("commands.banlist.usage", new Object[0]);
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length >= 1 && par2ArrayOfStr[0].equalsIgnoreCase("ips"))
        {
            par1ICommandSender.func_55072_b(par1ICommandSender.func_55069_a("commands.banlist.ips", new Object[]
                    {
                        Integer.valueOf(MinecraftServer.func_55089_q().func_56173_Y().func_56427_f().func_56741_b().size())
                    }));
            par1ICommandSender.func_55072_b(func_56236_a(MinecraftServer.func_55089_q().func_56173_Y().func_56427_f().func_56741_b().keySet().toArray()));
        }
        else
        {
            par1ICommandSender.func_55072_b(par1ICommandSender.func_55069_a("commands.banlist.players", new Object[]
                    {
                        Integer.valueOf(MinecraftServer.func_55089_q().func_56173_Y().func_56429_e().func_56741_b().size())
                    }));
            par1ICommandSender.func_55072_b(func_56236_a(MinecraftServer.func_55089_q().func_56173_Y().func_56429_e().func_56741_b().keySet().toArray()));
        }
    }

    public List func_55149_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_55157_a(par2ArrayOfStr, new String[]
                    {
                        "players", "ips"
                    });
        }
        else
        {
            return null;
        }
    }
}
