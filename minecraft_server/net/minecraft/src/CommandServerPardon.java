package net.minecraft.src;

import java.util.List;
import java.util.Map;
import net.minecraft.server.MinecraftServer;

public class CommandServerPardon extends CommandBase
{
    public CommandServerPardon()
    {
    }

    public String func_55148_a()
    {
        return "pardon";
    }

    public String func_55150_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55069_a("commands.unban.usage", new Object[0]);
    }

    public boolean func_55147_c(ICommandSender par1ICommandSender)
    {
        return MinecraftServer.func_55089_q().func_56173_Y().func_56429_e().func_56745_a() && super.func_55147_c(par1ICommandSender);
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1 && par2ArrayOfStr[0].length() > 0)
        {
            MinecraftServer.func_55089_q().func_56173_Y().func_56429_e().func_56747_b(par2ArrayOfStr[0]);
            func_55154_a(par1ICommandSender, "commands.unban.success", new Object[]
                    {
                        par2ArrayOfStr[0]
                    });
            return;
        }
        else
        {
            throw new WrongUsageException("commands.unban.usage", new Object[0]);
        }
    }

    public List func_55149_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_55158_a(par2ArrayOfStr, MinecraftServer.func_55089_q().func_56173_Y().func_56429_e().func_56741_b().keySet());
        }
        else
        {
            return null;
        }
    }
}
