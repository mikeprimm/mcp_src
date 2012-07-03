package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerSaveOn extends CommandBase
{
    public CommandServerSaveOn()
    {
    }

    public String func_55148_a()
    {
        return "save-on";
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        MinecraftServer minecraftserver = MinecraftServer.func_55089_q();

        for (int i = 0; i < minecraftserver.worldMngr.length; i++)
        {
            if (minecraftserver.worldMngr[i] != null)
            {
                WorldServer worldserver = minecraftserver.worldMngr[i];
                worldserver.levelSaving = false;
            }
        }

        func_55154_a(par1ICommandSender, "commands.save.enabled", new Object[0]);
    }
}
