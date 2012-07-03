package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerSaveAll extends CommandBase
{
    public CommandServerSaveAll()
    {
    }

    public String func_55148_a()
    {
        return "save-all";
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        MinecraftServer minecraftserver = MinecraftServer.func_55089_q();
        par1ICommandSender.func_55072_b(par1ICommandSender.func_55069_a("commands.save.start", new Object[0]));

        if (minecraftserver.func_56173_Y() != null)
        {
            minecraftserver.func_56173_Y().savePlayerStates();
        }

        try
        {
            for (int i = 0; i < minecraftserver.worldMngr.length; i++)
            {
                if (minecraftserver.worldMngr[i] != null)
                {
                    WorldServer worldserver = minecraftserver.worldMngr[i];
                    boolean flag = worldserver.levelSaving;
                    worldserver.levelSaving = false;
                    worldserver.func_56365_a(true, null);
                    worldserver.levelSaving = flag;
                }
            }
        }
        catch (MinecraftException minecraftexception)
        {
            func_55154_a(par1ICommandSender, "commands.save.failed", new Object[]
                    {
                        minecraftexception.getMessage()
                    });
            return;
        }

        func_55154_a(par1ICommandSender, "commands.save.success", new Object[0]);
    }
}
