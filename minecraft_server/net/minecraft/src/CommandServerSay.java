package net.minecraft.src;

import java.util.List;
import net.minecraft.server.MinecraftServer;

public class CommandServerSay extends CommandBase
{
    public CommandServerSay()
    {
    }

    public String func_55148_a()
    {
        return "say";
    }

    public String func_55150_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55069_a("commands.say.usage", new Object[0]);
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length > 0 && par2ArrayOfStr[0].length() > 1)
        {
            String s = func_55153_a(par2ArrayOfStr, 0);
            MinecraftServer.func_55089_q().func_56173_Y().sendPacketToAllPlayers(new Packet3Chat(String.format("[%s] %s", new Object[]
                    {
                        par1ICommandSender.func_55070_y_(), s
                    })));
            return;
        }
        else
        {
            throw new WrongUsageException("commands.say.usage", new Object[0]);
        }
    }

    public List func_55149_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length >= 1)
        {
            return func_55157_a(par2ArrayOfStr, MinecraftServer.func_55089_q().getPlayerNamesAsList());
        }
        else
        {
            return null;
        }
    }
}
