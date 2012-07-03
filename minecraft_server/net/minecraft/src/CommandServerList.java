package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerList extends CommandBase
{
    public CommandServerList()
    {
    }

    public String func_55148_a()
    {
        return "list";
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        par1ICommandSender.func_55072_b(par1ICommandSender.func_55069_a("commands.players.list", new Object[]
                {
                    Integer.valueOf(MinecraftServer.func_55089_q().playersOnline()), Integer.valueOf(MinecraftServer.func_55089_q().getMaxPlayers())
                }));
        par1ICommandSender.func_55072_b(MinecraftServer.func_55089_q().func_56173_Y().getPlayerList());
    }
}
