package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandDefaultGameMode extends CommandGameMode
{
    public CommandDefaultGameMode()
    {
    }

    public String func_55148_a()
    {
        return "defaultgamemode";
    }

    public String func_55150_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55069_a("commands.defaultgamemode.usage", new Object[0]);
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length > 0)
        {
            EnumGameType enumgametype = func_56240_b(par1ICommandSender, par2ArrayOfStr[0]);
            func_56241_a(enumgametype);
            String s = StatCollector.translateToLocal((new StringBuilder()).append("gameMode.").append(enumgametype.func_56608_b()).toString());
            func_55154_a(par1ICommandSender, "commands.defaultgamemode.success", new Object[]
                    {
                        s
                    });
            return;
        }
        else
        {
            throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
        }
    }

    protected void func_56241_a(EnumGameType par1EnumGameType)
    {
        MinecraftServer.func_55089_q().func_58023_a(par1EnumGameType);
    }
}
