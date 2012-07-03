package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerSaveOff extends CommandBase
{
    public CommandServerSaveOff()
    {
    }

    public String func_55148_a()
    {
        return "save-off";
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        MinecraftServer minecraftserver = MinecraftServer.func_55089_q();

        for (int i = 0; i < minecraftserver.worldMngr.length; i++)
        {
            if (minecraftserver.worldMngr[i] != null)
            {
                WorldServer worldserver = minecraftserver.worldMngr[i];
                worldserver.levelSaving = true;
            }
        }

        func_55154_a(par1ICommandSender, "commands.save.disabled", new Object[0]);
    }
}
