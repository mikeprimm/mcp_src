package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerPublishLocal extends CommandBase
{
    public CommandServerPublishLocal()
    {
    }

    public String func_55148_a()
    {
        return "publish";
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        String s = MinecraftServer.func_55089_q().func_58020_a(EnumGameType.SURVIVAL, false);

        if (s != null)
        {
            func_55154_a(par1ICommandSender, "commands.publish.started", new Object[]
                    {
                        s
                    });
        }
        else
        {
            func_55154_a(par1ICommandSender, "commands.publish.failed", new Object[0]);
        }
    }
}
