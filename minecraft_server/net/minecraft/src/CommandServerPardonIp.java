package net.minecraft.src;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.server.MinecraftServer;

public class CommandServerPardonIp extends CommandBase
{
    public CommandServerPardonIp()
    {
    }

    public String func_55148_a()
    {
        return "pardon-ip";
    }

    public boolean func_55147_c(ICommandSender par1ICommandSender)
    {
        return MinecraftServer.func_55089_q().func_56173_Y().func_56427_f().func_56745_a() && super.func_55147_c(par1ICommandSender);
    }

    public String func_55150_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55069_a("commands.unbanip.usage", new Object[0]);
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1 && par2ArrayOfStr[0].length() > 1)
        {
            Matcher matcher = CommandServerBanIp.field_55168_a.matcher(par2ArrayOfStr[0]);

            if (matcher.matches())
            {
                MinecraftServer.func_55089_q().func_56173_Y().func_56427_f().func_56747_b(par2ArrayOfStr[0]);
                func_55154_a(par1ICommandSender, "commands.unbanip.success", new Object[]
                        {
                            par2ArrayOfStr[0]
                        });
                return;
            }
            else
            {
                throw new SyntaxErrorException("commands.unbanip.invalid", new Object[0]);
            }
        }
        else
        {
            throw new WrongUsageException("commands.unbanip.usage", new Object[0]);
        }
    }

    public List func_55149_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_55158_a(par2ArrayOfStr, MinecraftServer.func_55089_q().func_56173_Y().func_56427_f().func_56741_b().keySet());
        }
        else
        {
            return null;
        }
    }
}
