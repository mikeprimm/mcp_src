package net.minecraft.src;

import java.util.List;
import net.minecraft.server.MinecraftServer;

public class CommandServerEmote extends CommandBase
{
    public CommandServerEmote()
    {
    }

    public String func_55148_a()
    {
        return "me";
    }

    public String func_55150_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55069_a("commands.me.usage", new Object[0]);
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length > 0)
        {
            String s = func_55153_a(par2ArrayOfStr, 0);
            MinecraftServer.func_55089_q().func_56173_Y().sendPacketToAllPlayers(new Packet3Chat((new StringBuilder()).append("* ").append(par1ICommandSender.func_55070_y_()).append(" ").append(s).toString()));
            return;
        }
        else
        {
            throw new WrongUsageException("commands.me.usage", new Object[0]);
        }
    }

    public List func_55149_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        return func_55157_a(par2ArrayOfStr, MinecraftServer.func_55089_q().getPlayerNamesAsList());
    }
}
