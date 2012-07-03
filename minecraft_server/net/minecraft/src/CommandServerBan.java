package net.minecraft.src;

import java.util.List;
import net.minecraft.server.MinecraftServer;

public class CommandServerBan extends CommandBase
{
    public CommandServerBan()
    {
    }

    public String func_55148_a()
    {
        return "ban";
    }

    public String func_55150_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55069_a("commands.ban.usage", new Object[0]);
    }

    public boolean func_55147_c(ICommandSender par1ICommandSender)
    {
        return MinecraftServer.func_55089_q().func_56173_Y().func_56429_e().func_56745_a() && super.func_55147_c(par1ICommandSender);
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length >= 1 && par2ArrayOfStr[0].length() > 0)
        {
            EntityPlayerMP entityplayermp = MinecraftServer.func_55089_q().func_56173_Y().getPlayerEntity(par2ArrayOfStr[0]);
            BanEntry banentry = new BanEntry(par2ArrayOfStr[0]);
            banentry.func_56717_a(par1ICommandSender.func_55070_y_());

            if (par2ArrayOfStr.length >= 2)
            {
                banentry.func_56718_b(func_55153_a(par2ArrayOfStr, 1));
            }

            MinecraftServer.func_55089_q().func_56173_Y().func_56429_e().func_56748_a(banentry);

            if (entityplayermp != null)
            {
                entityplayermp.playerNetServerHandler.kickPlayer("You are banned from this server.");
            }

            func_55154_a(par1ICommandSender, "commands.ban.success", new Object[]
                    {
                        par2ArrayOfStr[0]
                    });
            return;
        }
        else
        {
            throw new WrongUsageException("commands.ban.usage", new Object[0]);
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
