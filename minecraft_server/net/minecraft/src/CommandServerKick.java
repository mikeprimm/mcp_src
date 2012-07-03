package net.minecraft.src;

import java.util.List;
import net.minecraft.server.MinecraftServer;

public class CommandServerKick extends CommandBase
{
    public CommandServerKick()
    {
    }

    public String func_55148_a()
    {
        return "kick";
    }

    public String func_55150_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55069_a("commands.kick.usage", new Object[0]);
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length > 0 && par2ArrayOfStr[0].length() > 1)
        {
            EntityPlayerMP entityplayermp = MinecraftServer.func_55089_q().func_56173_Y().getPlayerEntity(par2ArrayOfStr[0]);
            String s = "Kicked by an operator.";
            boolean flag = false;

            if (entityplayermp == null)
            {
                throw new PlayerNotFoundException();
            }

            if (par2ArrayOfStr.length >= 2)
            {
                s = func_55153_a(par2ArrayOfStr, 1);
                flag = true;
            }

            entityplayermp.playerNetServerHandler.kickPlayer(s);

            if (flag)
            {
                func_55154_a(par1ICommandSender, "commands.kick.success.reason", new Object[]
                        {
                            entityplayermp.getUsername(), s
                        });
            }
            else
            {
                func_55154_a(par1ICommandSender, "commands.kick.success", new Object[]
                        {
                            entityplayermp.getUsername()
                        });
            }

            return;
        }
        else
        {
            throw new WrongUsageException("commands.kick.usage", new Object[0]);
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
