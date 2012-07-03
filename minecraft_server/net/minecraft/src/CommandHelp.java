package net.minecraft.src;

import java.util.*;
import net.minecraft.server.MinecraftServer;

public class CommandHelp extends CommandBase
{
    public CommandHelp()
    {
    }

    public String func_55148_a()
    {
        return "help";
    }

    public String func_55150_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55069_a("commands.help.usage", new Object[0]);
    }

    public List func_55145_b()
    {
        return Arrays.asList(new String[]
                {
                    "?"
                });
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        List list = func_55166_b(par1ICommandSender);
        byte byte0 = 7;
        int i = list.size() / byte0;
        int j = 0;

        try
        {
            j = par2ArrayOfStr.length != 0 ? func_55152_a(par1ICommandSender, par2ArrayOfStr[0], 1, i + 1) - 1 : 0;
        }
        catch (NumberInvalidException numberinvalidexception)
        {
            Map map = func_56238_c();
            ICommand icommand = (ICommand)map.get(par2ArrayOfStr[0]);

            if (icommand != null)
            {
                throw new WrongUsageException(icommand.func_55150_a(par1ICommandSender), new Object[0]);
            }
            else
            {
                throw new CommandNotFoundException();
            }
        }

        int k = Math.min((j + 1) * byte0, list.size());
        par1ICommandSender.func_55072_b((new StringBuilder()).append("\2472").append(par1ICommandSender.func_55069_a("commands.help.header", new Object[]
                {
                    Integer.valueOf(j + 1), Integer.valueOf(i + 1)
                })).toString());

        for (int l = j * byte0; l < k; l++)
        {
            ICommand icommand1 = (ICommand)list.get(l);
            par1ICommandSender.func_55072_b(icommand1.func_55150_a(par1ICommandSender));
        }

        if (j == 0)
        {
            par1ICommandSender.func_55072_b((new StringBuilder()).append("\247a").append(par1ICommandSender.func_55069_a("commands.help.footer", new Object[0])).toString());
        }
    }

    protected List func_55166_b(ICommandSender par1ICommandSender)
    {
        List list = MinecraftServer.func_55089_q().func_55091_s().func_55235_a(par1ICommandSender);
        Collections.sort(list);
        return list;
    }

    protected Map func_56238_c()
    {
        return MinecraftServer.func_55089_q().func_55091_s().func_56557_a();
    }
}
