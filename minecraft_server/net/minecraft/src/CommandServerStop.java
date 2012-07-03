package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerStop extends CommandBase
{
    public CommandServerStop()
    {
    }

    public String func_55148_a()
    {
        return "stop";
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        func_55154_a(par1ICommandSender, "commands.stop.start", new Object[0]);
        MinecraftServer.func_55089_q().initiateShutdown();
    }
}
