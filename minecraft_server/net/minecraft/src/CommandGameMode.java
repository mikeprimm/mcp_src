package net.minecraft.src;

import java.util.List;
import net.minecraft.server.MinecraftServer;

public class CommandGameMode extends CommandBase
{
    public CommandGameMode()
    {
    }

    public String func_55148_a()
    {
        return "gamemode";
    }

    public String func_55150_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55069_a("commands.gamemode.usage", new Object[0]);
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length > 0)
        {
            EnumGameType enumgametype = func_56240_b(par1ICommandSender, par2ArrayOfStr[0]);
            EntityPlayer entityplayer = par2ArrayOfStr.length < 2 ? func_55159_d(par1ICommandSender) : func_55175_a(par2ArrayOfStr[1]);
            entityplayer.func_56147_a(enumgametype);
            String s = StatCollector.translateToLocal((new StringBuilder()).append("gameMode.").append(enumgametype.func_56608_b()).toString());

            if (entityplayer != par1ICommandSender)
            {
                func_55154_a(par1ICommandSender, "commands.gamemode.success.other", new Object[]
                        {
                            entityplayer.getUsername(), s
                        });
            }
            else
            {
                func_55154_a(par1ICommandSender, "commands.gamemode.success.self", new Object[]
                        {
                            s
                        });
            }

            return;
        }
        else
        {
            throw new WrongUsageException("commands.gamemode.usage", new Object[0]);
        }
    }

    protected EnumGameType func_56240_b(ICommandSender par1ICommandSender, String par2Str)
    {
        if (par2Str.equalsIgnoreCase(EnumGameType.SURVIVAL.func_56608_b()) || par2Str.equalsIgnoreCase("s"))
        {
            return EnumGameType.SURVIVAL;
        }

        if (par2Str.equalsIgnoreCase(EnumGameType.CREATIVE.func_56608_b()) || par2Str.equalsIgnoreCase("c"))
        {
            return EnumGameType.CREATIVE;
        }

        if (par2Str.equalsIgnoreCase(EnumGameType.ADVENTURE.func_56608_b()) || par2Str.equalsIgnoreCase("a"))
        {
            return EnumGameType.ADVENTURE;
        }
        else
        {
            return WorldSettings.func_56524_a(func_55156_a(par1ICommandSender, par2Str));
        }
    }

    public List func_55149_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_55157_a(par2ArrayOfStr, new String[]
                    {
                        "survival", "creative", "adventure"
                    });
        }

        if (par2ArrayOfStr.length == 2)
        {
            return func_55157_a(par2ArrayOfStr, func_55173_c());
        }
        else
        {
            return null;
        }
    }

    protected EntityPlayer func_55175_a(String par1Str)
    {
        EntityPlayerMP entityplayermp = MinecraftServer.func_55089_q().func_56173_Y().getPlayerEntity(par1Str);

        if (entityplayermp == null)
        {
            throw new PlayerNotFoundException();
        }
        else
        {
            return entityplayermp;
        }
    }

    protected String[] func_55173_c()
    {
        return MinecraftServer.func_55089_q().getPlayerNamesAsList();
    }
}
