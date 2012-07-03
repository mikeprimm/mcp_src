package net.minecraft.src;

import java.util.List;
import net.minecraft.server.MinecraftServer;

public class CommandXP extends CommandBase
{
    public CommandXP()
    {
    }

    public String func_55148_a()
    {
        return "xp";
    }

    public String func_55150_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55069_a("commands.xp.usage", new Object[0]);
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length > 0)
        {
            int i = func_55152_a(par1ICommandSender, par2ArrayOfStr[0], 0, 5000);
            EntityPlayer entityplayer;

            if (par2ArrayOfStr.length > 1)
            {
                entityplayer = func_55165_a(par2ArrayOfStr[1]);
            }
            else
            {
                entityplayer = func_55159_d(par1ICommandSender);
            }

            entityplayer.addExperience(i);
            func_55154_a(par1ICommandSender, "commands.xp.success", new Object[]
                    {
                        Integer.valueOf(i), entityplayer.getUsername()
                    });
            return;
        }
        else
        {
            throw new WrongUsageException("commands.xp.usage", new Object[0]);
        }
    }

    public List func_55149_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 2)
        {
            return func_55157_a(par2ArrayOfStr, func_55164_c());
        }
        else
        {
            return null;
        }
    }

    protected EntityPlayer func_55165_a(String par1Str)
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

    protected String[] func_55164_c()
    {
        return MinecraftServer.func_55089_q().getPlayerNamesAsList();
    }
}
