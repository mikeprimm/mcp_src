package net.minecraft.src;

import java.util.List;
import net.minecraft.server.MinecraftServer;

public class CommandServerDeop extends CommandBase
{
    public CommandServerDeop()
    {
    }

    public String func_55148_a()
    {
        return "deop";
    }

    public String func_55150_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55069_a("commands.deop.usage", new Object[0]);
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1 && par2ArrayOfStr[0].length() > 0)
        {
            MinecraftServer.func_55089_q().func_56173_Y().removeOp(par2ArrayOfStr[0]);
            func_55154_a(par1ICommandSender, "commands.deop.success", new Object[]
                    {
                        par2ArrayOfStr[0]
                    });
            return;
        }
        else
        {
            throw new WrongUsageException("commands.deop.usage", new Object[0]);
        }
    }

    public List func_55149_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_55158_a(par2ArrayOfStr, MinecraftServer.func_55089_q().func_56173_Y().func_55207_h());
        }
        else
        {
            return null;
        }
    }
}
